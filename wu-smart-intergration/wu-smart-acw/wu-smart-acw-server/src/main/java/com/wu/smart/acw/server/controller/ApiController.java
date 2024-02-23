package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.stereotype.LazyTransactional;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.mark.ValidType;
import com.wu.smart.acw.core.domain.qo.ApiQo;
import com.wu.smart.acw.core.domain.uo.ApiUo;
import com.wu.smart.acw.server.service.ApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * api 控制层
 */
@Api(tags = "ACW-API 控制层")
@EasyController("/api")
public class ApiController {

    private final LazyLambdaStream lazyLambdaStream;
    private final ApiService apiService;

    public ApiController(LazyLambdaStream lazyLambdaStream, ApiService apiService) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.apiService = apiService;
    }


    @ApiOperation(value = "查询api")
    @GetMapping("/page")
    public Result page(@ModelAttribute ApiQo apiQo) {
        final Collection<ApiUo> apiUoCollection = lazyLambdaStream.of(ApiUo.class).select(LazyWrappers.<ApiQo>lambdaWrapper()
                .eqIgnoreEmpty(ApiQo::getId, apiQo.getId())
                .eqIgnoreEmpty(ApiQo::getMethod, apiQo.getMethod())
                .eqIgnoreEmpty(ApiQo::getTableName, apiQo.getTableName())
                .eqIgnoreEmpty(ApiQo::getPath, apiQo.getPath())
                .eqIgnoreEmpty(ApiQo::getTag, apiQo.getTag())
                .eqIgnoreEmpty(ApiQo::getProjectId, apiQo.getProjectId())
        ).collection();
        return ResultFactory.successOf(apiUoCollection);
    }

    @LazyTransactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增/更新api")
    @PostMapping()
    public Result save(@Validated(ValidType.Create.class) @RequestBody ApiQo apiQo) {
        return apiService.save(apiQo);
    }

    @ApiOperation(value = "删除api")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
//        Collection collection = lambdaStream.delete().table(ApiUo.class).eq("id", id).collection();
        Collection collection = null;
        return ResultFactory.successOf(collection);
    }
}
