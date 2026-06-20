package com.sende.user.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Nacos 配置中心加载器（不依赖 Spring Cloud）
 *
 * 流程：
 *   1. 读取 application.yml 中 nacos.config.* 配置（server-addr / namespace / data-id / group）
 *   2. 从 Nacos 拉取对应配置（YAML 格式）
 *   3. 解析为 PropertySource，注入到 Spring Environment 最前面（最高优先级）
 *
 * 注册方式：META-INF/spring/org.springframework.boot.env.EnvironmentPostProcessor.imports
 */
public class NacosConfigEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final Logger log = LoggerFactory.getLogger(NacosConfigEnvironmentPostProcessor.class);

    private static final String PROPERTY_SOURCE_NAME = "nacosConfig";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication app) {
        String serverAddr = environment.getProperty("nacos.config.server-addr");
        if (!StringUtils.hasText(serverAddr)) {
            log.info("[NacosConfig] nacos.config.server-addr 未配置，跳过远程配置加载");
            return;
        }

        String appName = environment.getProperty("spring.application.name", "application");
        String namespace = environment.getProperty("nacos.config.namespace", "DEFAULT");
        String dataId = environment.getProperty("nacos.config.data-id", appName + ".yaml");
        String group = environment.getProperty("nacos.config.group", "DEFAULT_GROUP");
        long timeoutMs = Long.parseLong(environment.getProperty("nacos.config.timeout-ms", "5000"));

        Properties props = new Properties();
        props.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        props.put(PropertyKeyConst.NAMESPACE, namespace);
        props.put(PropertyKeyConst.CONFIG_LONG_POLL_TIMEOUT, timeoutMs);

        try {
            ConfigService configService = NacosFactory.createConfigService(props);
            String content = configService.getConfig(dataId, group, timeoutMs);
            if (!StringUtils.hasText(content)) {
                log.warn("[NacosConfig] Nacos 上无配置: dataId={} group={} namespace={}", dataId, group, namespace);
                return;
            }

            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            PropertySource<?> ps = loader.load(PROPERTY_SOURCE_NAME,
                    new ByteArrayResource(content.getBytes(StandardCharsets.UTF_8)))
                    .get(0);
            environment.getPropertySources().addFirst(ps);
            log.info("[NacosConfig] 加载成功: dataId={} group={} namespace={} 字节数={}",
                    dataId, group, namespace, content.length());

        } catch (NacosException e) {
            log.error("[NacosConfig] 加载失败: dataId={} group={}, err={}", dataId, group, e.getErrMsg());
        } catch (Exception e) {
            log.error("[NacosConfig] 解析失败", e);
        }
    }

    /** 高优先级：在其他 PropertySource 之前加载 */
    @Override
    public int getOrder() {
        // 必须在 ConfigDataEnvironmentPostProcessor（order=HIGHEST_PRECEDENCE+10）之后，
        // 否则 application.yml 还没加载，nacos.config.* 是空的
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }
}