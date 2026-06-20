-- ===========================================
-- goods_insight_user 库 - 用户微服务数据
-- ===========================================
CREATE DATABASE IF NOT EXISTS goods_insight_user
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE goods_insight_user;

-- -------------------------------------------
-- sys_user 系统用户表
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS sys_user (
  id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  username        VARCHAR(100) NOT NULL COMMENT '用户名',
  email           VARCHAR(255) NOT NULL COMMENT '邮箱',
  password        VARCHAR(255) NOT NULL COMMENT '密码Hash（BCrypt）',
  avatar          VARCHAR(500) NULL COMMENT '头像URL',
  status          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 1启用 0禁用',
  last_login_time DATETIME     NULL COMMENT '最后登录时间',
  created_by      VARCHAR(64)  NULL DEFAULT 'system' COMMENT '创建人',
  updated_by      VARCHAR(64)  NULL DEFAULT 'system' COMMENT '更新人',
  created_time    DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  updated_time    DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-否 1-是',
  PRIMARY KEY (id),
  UNIQUE KEY uk_username (username),
  UNIQUE KEY uk_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- -------------------------------------------
-- workspace 工作空间表
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS workspace (
  id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  workspace_name  VARCHAR(128) NOT NULL COMMENT '工作空间名称',
  workspace_code  VARCHAR(64)  NOT NULL COMMENT '工作空间编码（全局唯一）',
  owner_user_id   BIGINT       NOT NULL COMMENT '拥有者用户ID',
  plan_type       VARCHAR(32)  NOT NULL DEFAULT 'FREE' COMMENT '套餐 FREE/BASIC/PRO',
  status          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 1启用 0停用',
  created_by      VARCHAR(64)  NULL DEFAULT 'system' COMMENT '创建人',
  updated_by      VARCHAR(64)  NULL DEFAULT 'system' COMMENT '更新人',
  created_time    DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  updated_time    DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (id),
  UNIQUE KEY uk_workspace_code (workspace_code),
  KEY idx_owner_user_id (owner_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作空间表';

-- -------------------------------------------
-- workspace_user 工作空间成员表
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS workspace_user (
  id            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  workspace_id  BIGINT       NOT NULL COMMENT '工作空间ID',
  user_id       BIGINT       NOT NULL COMMENT '用户ID',
  role_code     VARCHAR(32)  NOT NULL COMMENT 'OWNER/ADMIN/MEMBER',
  created_time  DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_workspace_user (workspace_id, user_id),
  KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作空间成员表';