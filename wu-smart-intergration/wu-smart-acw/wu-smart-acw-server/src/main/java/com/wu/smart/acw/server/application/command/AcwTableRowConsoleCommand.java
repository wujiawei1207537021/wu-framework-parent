package com.wu.smart.acw.server.application.command;

import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import lombok.Data;

/**
 * 表行更新、删除 参数
 */
@Data
public class AcwTableRowConsoleCommand {

    private String instanceId;
    private String schemaName;
    private String tableName;

    /**
     * 表行数据
     */
    private EasyHashMap<String,Object> tableRow;

}
