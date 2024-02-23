package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.service.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private final DatabaseService databaseService;

    public DatabaseController(LazyLambdaStream lambdaStream, DatabaseService databaseService) {
        this.lambdaStream = lambdaStream;
        this.databaseService = databaseService;
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
    public Result list(@ApiParam(required = true, defaultValue = "acw_test", value = "数据库表") @RequestParam(required = true, defaultValue = "acw_test") String schema,
                       @ApiParam(required = true, defaultValue = "1", value = "数据库服务器ID") @RequestParam(required = true, defaultValue = "1") Long databaseServerId) {
        return databaseService.listTable(databaseServerId, schema);
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
    public Result listLazyColumn(
            @ApiParam(required = true, defaultValue = "1", value = "数据库服务器ID") @RequestParam(defaultValue = "1") Long databaseServerId,
            @ApiParam(required = true, value = "数据库") @RequestParam(defaultValue = "acw_test") String schema,
            @ApiParam(required = true, value = "表") @RequestParam(defaultValue = "database_server") String tableName) {
        return databaseService.listLazyColumn(databaseServerId, schema, tableName);

    }

}
