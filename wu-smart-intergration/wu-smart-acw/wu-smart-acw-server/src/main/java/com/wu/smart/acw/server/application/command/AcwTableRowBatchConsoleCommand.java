package com.wu.smart.acw.server.application.command;

import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import lombok.Data;

import java.util.List;

/**
 * 表行批量 更新、删除 参数
 */
@Data
public class AcwTableRowBatchConsoleCommand {

    private String instanceId;
    private String schemaName;
    private String tableName;

    /**
     * 表行数据
     */
    private List<EasyHashMap<String, Object>> tableRowList;

}
