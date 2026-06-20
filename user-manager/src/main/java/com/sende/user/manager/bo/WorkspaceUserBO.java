package com.sende.user.manager.bo;

import lombok.Data;

@Data
public class WorkspaceUserBO {
    private Long id;
    private Long workspaceId;
    private Long userId;
    private String roleCode;
}