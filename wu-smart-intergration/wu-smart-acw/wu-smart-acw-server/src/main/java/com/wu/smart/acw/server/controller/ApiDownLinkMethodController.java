package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.stereotype.LazyTransactional;
import com.wu.framework.response.Result;
import com.wu.framework.response.mark.ValidType;
import com.wu.smart.acw.core.domain.qo.ApiDownLinkMethodQo;
import com.wu.smart.acw.server.service.ApiDownLinkMethodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * API 下联 Method 表
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/1 10:55 下午
 */
@Api(tags = "ACW-API 下联 Method")
@EasyController("/api/down/method")
public class ApiDownLinkMethodController {
    private final ApiDownLinkMethodService apiDownLinkMethodService;

    public ApiDownLinkMethodController(ApiDownLinkMethodService apiDownLinkMethodService) {
        this.apiDownLinkMethodService = apiDownLinkMethodService;
    }

    /**
     * API 下联 Method 表
     *
     * @author : Jia wei Wu
     * @date : 2022/1/1 11:00 下午
     * @version : 1.0
     */
    @LazyTransactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增/更新API 下联 Method")
    @PostMapping()
    public Result save(@Validated(ValidType.Create.class) @RequestBody ApiDownLinkMethodQo apiDownLinkMethodQo) {
        return apiDownLinkMethodService.save(apiDownLinkMethodQo);
    }


}
