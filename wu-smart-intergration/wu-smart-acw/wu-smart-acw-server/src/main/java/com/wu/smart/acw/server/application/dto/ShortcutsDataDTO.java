package com.wu.smart.acw.server.application.dto;

import lombok.Data;

@Data
public class ShortcutsDataDTO {
    /**
     * 菜单数量
     */
    private Long menuNum;
    /**
     * 角色数量
     */
    private Long roleNum;
    /**
     * 用户数量
     */
    private Long userNum;
    /**
     * 数据库实例数量
     */
    private Long instanceNum;
    /**
     * 数据库数量
     */
    private Long schemaNum;
    /**
     * 表数量
     */
    private Long tableNum;
    /**
     * 表字段数量
     */
    private Long tableColumnNum;
}
