package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.mark.ValidType;
import com.wu.smart.acw.core.domain.dto.ApiDto;
import com.wu.smart.acw.core.domain.qo.ApiQo;
import com.wu.smart.acw.core.domain.uo.AcwApplicationApiUo;
import com.wu.smart.acw.server.application.ApiApplication;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * api 控制层
 */
@Tag(name = "ACW-API 控制层")
@EasyController("/api")
public class AcwApiController {

    private final LazyLambdaStream lazyLambdaStream;
    private final ApiApplication apiApplication;

    public AcwApiController(LazyLambdaStream lazyLambdaStream, ApiApplication apiApplication) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.apiApplication = apiApplication;
    }


    @ApiOperation(value = "查询api")
    @GetMapping("/retrieve/page")
    public Result page(@ModelAttribute AcwApplicationApiUo acwApplicationApiUo, @Parameter(description = "分页大小") @RequestParam(defaultValue = "10") int size,
                       @Parameter(description = "当前页数") @RequestParam(defaultValue = "1") int current) {
        LazyPage<ApiDto> lazyPage = new LazyPage<ApiDto>(current, size);
        acwApplicationApiUo.setIsDeleted(false);
        LazyPage<ApiDto> apiUoLazyPage = lazyLambdaStream
                .selectPage(
                        LazyWrappers.lambdaWrapperBean(acwApplicationApiUo).
                                orderByDesc(AcwApplicationApiUo::getId, AcwApplicationApiUo::getApplicationId),
                        lazyPage,
                        ApiDto.class
                );
        return ResultFactory.successOf(apiUoLazyPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增/更新api")
    @PostMapping("/save")
    public Result save(@Validated(ValidType.Create.class) @RequestBody ApiQo apiQo) {
        return apiApplication.save(apiQo);
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "更新api")
    @PutMapping("/update")
    public Result update(@Validated(ValidType.Update.class) @RequestBody ApiQo apiQo) {
        return apiApplication.save(apiQo);
    }

    @ApiOperation(value = "删除api")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        Integer delete = lazyLambdaStream.of(AcwApplicationApiUo.class).delete(LazyWrappers.<AcwApplicationApiUo>lambdaWrapper().eq(AcwApplicationApiUo::getId, id));
        return ResultFactory.successOf();
    }

}
