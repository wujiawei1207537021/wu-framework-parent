package com.wu.smart.acw.server.application;


import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.dto.AcwTableCommandColumnDTO;

import java.util.List;

public interface AcwTableColumnApplication {

    /**
     * describe  查询出多个表所有的数据
     *
     * @param instanceId    实例ID
     * @param schemaName    schema
     * @param tableNameList 表名称集合
     * @return Result<T>
     * @author Jia wei Wu
     */
    Result<List<AcwTableCommandColumnDTO.AcwTableColumnDTO>> retrieveAllByTableNameList(String instanceId, String schemaName, List<String> tableNameList);

    /**
     * 根据表名 获取当前表中字段
     *
     * @param instanceId    实例ID
     * @param schemaName    schema
     * @param tableName 表
     * @return 当前表中字段
     */
    Result<AcwTableCommandColumnDTO> retrieveAllByTableName(String instanceId, String schemaName, String tableName);

    /**
     * 查询实例上所有的字段
     * @param instanceId 实例ID
     * @param schemaName schema
     * @return 实例上所有的字段
     */
    Result<List<AcwTableCommandColumnDTO.AcwTableColumnDTO>> findInstanceSchemaColumnList(String instanceId, String schemaName);

    /**
     * 查询出表上所有字段
     * @param instanceId 实例ID
     * @param schemaName schema
     * @param tableName  表名称
     * @return Result<T>
     */
    Result<List<AcwTableCommandColumnDTO.AcwTableColumnDTO>> findColumnByTableName(String instanceId, String schemaName, String tableName);
}