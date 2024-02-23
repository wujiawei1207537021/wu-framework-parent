package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.DatabaseApplication;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * describe : 数据库操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/3 5:40 下午
 */
@Tag(name = "ACW-数据库操作")
@EasyController("/database")
public class DatabaseController {
    private final LazyLambdaStream lambdaStream;

    private final DatabaseApplication databaseService;

    public DatabaseController(LazyLambdaStream lambdaStream, DatabaseApplication databaseService) {
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
    public Result list(@Parameter(required = true, example = "acw_test", description = "数据库表") @RequestParam(required = true, defaultValue = "acw_test") String schema,
                       @Parameter(required = true, example = "1", description = "数据库服务器ID") @RequestParam(required = true, defaultValue = "1") String instanceId) {
        return databaseService.listTable(instanceId, schema);
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
            @Parameter(required = true, example = "1", description = "数据库服务器ID") @RequestParam(defaultValue = "1") String instanceId,
            @Parameter(required = true, description = "数据库") @RequestParam(defaultValue = "acw_test") String schema,
            @Parameter(required = true, description = "表") @RequestParam(defaultValue = "tableName") String tableName) {
        return databaseService.listLazyColumn(instanceId, schema, tableName);

    }

}
