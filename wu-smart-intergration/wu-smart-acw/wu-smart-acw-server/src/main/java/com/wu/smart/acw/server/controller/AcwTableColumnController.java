package com.wu.smart.acw.server.controller;

import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.AcwTableColumnUo;
import com.wu.smart.acw.server.application.AcwTableColumnApplication;
import com.wu.smart.acw.server.application.dto.AcwTableCommandColumnDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * describe 数据库表字段
 *
 * @author Jia wei Wu
 * @date 2022/06/06 11:55 晚上
 **/
@Tag(name = "ACW-数据库表字段提供者")
@EasyController("/database/table/column")
public class AcwTableColumnController extends AbstractLazyCrudProvider<AcwTableColumnUo, AcwTableColumnUo, Long> {
    private final LazyLambdaStream lazyLambdaStream;
    private final AcwTableColumnApplication acwTableColumnApplication;

    protected AcwTableColumnController(LazyLambdaStream lazyLambdaStream, AcwTableColumnApplication acwTableColumnApplication) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.acwTableColumnApplication = acwTableColumnApplication;
    }

    /**
     * describe  查询出所有的数据
     *
     * @param tableIds
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     */
    @ApiOperation("查询出所有的数据")
    @GetMapping("/tableIds/{tableIds}")
    public Result<List<AcwTableColumnUo>> retrieveAllByTableIds(@PathVariable List<Long> tableIds) {
        Collection<AcwTableColumnUo> acwTableColumnUos = lazyLambdaStream
                .selectList(LazyWrappers.<AcwTableColumnUo>lambdaWrapper().in(AcwTableColumnUo::getTableId, tableIds));
        return ResultFactory.successOf(new ArrayList<>(acwTableColumnUos));
    }

    /**
     * describe  查询出所有的数据
     *
     * @param instanceId    实例ID
     * @param schemaName    schema
     * @param tableNameList 表名称
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     */
    @ApiOperation("查询出所有的数据")
    @GetMapping("/tableIds/{instanceId}/{schemaName}/{tableNameList}")
    public Result<List<AcwTableCommandColumnDTO.AcwTableColumnDTO>> retrieveAllByTableNameList(
            @PathVariable String instanceId,
            @PathVariable String schemaName,
            @PathVariable List<String> tableNameList
    ) {
        return acwTableColumnApplication.retrieveAllByTableNameList(instanceId, schemaName, tableNameList);
    }

    /**
     * 查询实例上所有的字段
     * @param instanceId 实例ID
     * @param schemaName schema
     * @return
     */
    @ApiOperation("查询实例上所有的字段")
    @GetMapping("/findInstanceSchemaColumnList/{instanceId}/{schemaName}")
    public Result<List<AcwTableCommandColumnDTO.AcwTableColumnDTO>> findInstanceSchemaColumnList(
            @PathVariable String instanceId,
            @PathVariable String schemaName
    ) {
        return acwTableColumnApplication.findInstanceSchemaColumnList(instanceId, schemaName);
    }
    /**
     * describe  查询出所有的数据 和索引
     *
     * @param instanceId 实例ID
     * @param schemaName schema
     * @param tableName  表名称
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     */
    @ApiOperation("查询出所有的数据")
    @GetMapping("/{instanceId}/{schemaName}/{tableName}")
    public Result<AcwTableCommandColumnDTO> retrieveAllByTableName(
            @PathVariable String instanceId,
            @PathVariable String schemaName,
            @PathVariable String tableName
    ) {
        return acwTableColumnApplication.retrieveAllByTableName(instanceId, schemaName, tableName);
    }

    /**
     * describe  查询出表上所有字段
     *
     * @param instanceId 实例ID
     * @param schemaName schema
     * @param tableName  表名称
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     */
    @ApiOperation("查询出表上所有字段")
    @GetMapping("/findColumn/{instanceId}/{schemaName}/{tableName}")
    public Result<List<AcwTableCommandColumnDTO.AcwTableColumnDTO> > findColumnByTableName(
            @PathVariable String instanceId,
            @PathVariable String schemaName,
            @PathVariable String tableName
    ) {
        return acwTableColumnApplication.findColumnByTableName(instanceId, schemaName, tableName);
    }
}
