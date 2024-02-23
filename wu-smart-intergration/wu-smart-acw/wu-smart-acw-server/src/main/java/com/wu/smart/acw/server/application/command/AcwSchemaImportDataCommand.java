package com.wu.smart.acw.server.application.command;

import lombok.Data;

/**
 * schema 导入sql文件 参数
 */
@Data
public class AcwSchemaImportDataCommand {
    /**
     * 实例ID
     */
    private String instanceId;
    /**
     * 数据库名
     */
    private String schema;
}
