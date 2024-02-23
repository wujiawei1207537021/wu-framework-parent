package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * describe : 数据库操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/3 5:40 下午
 */
@Api(tags = "ACW-数据库操作")
@EasyController("/database")
public class DatabaseController {
    private final LazyLambdaStream lambdaStream;

    public DatabaseController(LazyLambdaStream lambdaStream) {
        this.lambdaStream = lambdaStream;
    }

    /**
     * describe 查看表
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/3 5:40 下午
     **/
    @ApiOperation("查看表")
    @GetMapping("/table/list")
    public Result list(@ApiParam(required = false, defaultValue = "acw_test") @RequestParam(required = false, defaultValue = "acw_test") String schema) {
        final Collection collection = lambdaStream.of(LazyTableInfo.class).
                select(LazyWrappers.<LazyTableInfo>lambdaWrapper().eq(LazyTableInfo::getTableSchema, schema)).collection();
        return ResultFactory.successOf(collection);
    }

    /**
     * describe 查看表中的字段
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/3 5:41 下午
     **/
    @ApiOperation("查看表中的字段")
    @GetMapping("/table/column")
    public Result listLazyColumn(@ApiParam(required = false) @RequestParam(required = false, defaultValue = "acw_test") String schema,
                                 @ApiParam(required = false) @RequestParam(required = false, defaultValue = "database_server") String tableName) {
        final Collection collection = lambdaStream.of(LazyColumn.class).select(LazyWrappers.<LazyColumn>lambdaWrapper().
                        eq(LazyColumn::getTableSchema, schema).
                        eq(LazyColumn::getTableName, tableName)).
                collection();
        return ResultFactory.successOf(collection);
    }

}
