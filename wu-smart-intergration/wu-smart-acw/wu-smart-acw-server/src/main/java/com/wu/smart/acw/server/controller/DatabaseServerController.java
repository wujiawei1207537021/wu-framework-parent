package com.wu.smart.acw.server.controller;


import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.DatabaseServerUo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "ACW-数据库服务器")
@EasyController("/databaseServer")
public class DatabaseServerController {

    private final LazyOperation lazyOperation;
    private final LazyLambdaStream lambdaStream;

    public DatabaseServerController(LazyOperation lazyOperation, LazyLambdaStream lambdaStream) {
        this.lazyOperation = lazyOperation;
        this.lambdaStream = lambdaStream;
    }

    @ApiOperation(value = "新增/更新数据库服务器")
    @PostMapping()
    public Result save(@RequestBody DatabaseServerUo databaseServerUo) {
        lazyOperation.smartUpsert(databaseServerUo);
        return ResultFactory.successOf();
    }

    @ApiOperation(value = "删除数据库服务器")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        Long count = lambdaStream.of(DatabaseServerUo.class).delete(LazyWrappers.<DatabaseServerUo>lambdaWrapper().eq(DatabaseServerUo::getId, id));
        return ResultFactory.successOf(count);
    }


}
