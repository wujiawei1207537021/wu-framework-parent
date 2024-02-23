package com.wu.smart.acw.server.application.command;

import lombok.Data;

/**
 * schema 导出sql文件 参数
 */
@Data
public class AcwSchemaBatchExportCommand {

    /**
     * 实例名称
     */
    private String instanceName;
    /**
     * 实例ID
     */
    private String instanceId;
    /**
     * 数据库名
     */
    private String schema;
}
