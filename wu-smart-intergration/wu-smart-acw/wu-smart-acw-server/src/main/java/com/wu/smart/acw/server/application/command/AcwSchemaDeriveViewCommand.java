package com.wu.smart.acw.server.application.command;

import lombok.Data;

/**
 * 数据库衍生视图
 */
@Data
public class AcwSchemaDeriveViewCommand {

    /**
     * 实例ID
     */
    private String instanceId;
    /**
     * 数据库名
     */
    private String schemaName;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 字段
     */
    private String columnName;

    /**
     * 分割符
     */
    private String separator="_";

}
