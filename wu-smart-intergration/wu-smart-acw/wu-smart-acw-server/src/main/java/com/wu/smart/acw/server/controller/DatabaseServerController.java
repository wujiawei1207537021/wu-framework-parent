package com.wu.smart.acw.server.controller;


import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.DatabaseServerUo;
import com.wu.smart.acw.server.service.DatabaseServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wujiawei
 */
@Api(tags = "ACW-数据库服务器")
@EasyController("/databaseServer")
public class DatabaseServerController extends AbstractLazyCrudProvider<DatabaseServerUo,Integer> {

    private final LazyOperation lazyOperation;
    private final LazyLambdaStream lambdaStream;
    private final DatabaseServerService databaseServerService;

    protected DatabaseServerController(LazyLambdaStream lazyLambdaStream, LazyOperation lazyOperation, LazyLambdaStream lambdaStream, DatabaseServerService databaseServerService) {
        super(lazyLambdaStream);
        this.lazyOperation = lazyOperation;
        this.lambdaStream = lambdaStream;
        this.databaseServerService = databaseServerService;
    }



    @Override
    @ApiOperation(value = "新增/更新数据库服务器")
    @PostMapping("/save")
    public Result save(@RequestBody DatabaseServerUo databaseServerUo) {
        return databaseServerService.save(databaseServerUo);
    }

    @ApiOperation(value = "删除数据库服务器")
    @DeleteMapping("/remove/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return databaseServerService.delete(id);
    }


}
