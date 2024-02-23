package com.wu.smart.acw.server.application.command;

import lombok.Data;

/**
 * 表某一列字段数据 查询条件
 */
@Data
public class AcwTableColumnConsoleCommand {

    private String instanceId;
    private String schemaName;
    private String tableName;

    /**
     * 列字段
     */
    private String column;


    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;

    /**
     * 当前页
     */
    private int current = 1;

}
