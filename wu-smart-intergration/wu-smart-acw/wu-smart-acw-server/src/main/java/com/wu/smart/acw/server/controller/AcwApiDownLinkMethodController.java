package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.framework.response.mark.ValidType;
import com.wu.smart.acw.core.domain.qo.ApiDownLinkMethodQo;
import com.wu.smart.acw.server.application.ApiDownLinkMethodApplication;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
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
@Tag(name = "ACW-API 下联 Method")
@EasyController("/api/down/method")
public class AcwApiDownLinkMethodController {
    private final ApiDownLinkMethodApplication apiDownLinkMethodApplication;

    public AcwApiDownLinkMethodController(ApiDownLinkMethodApplication apiDownLinkMethodApplication) {
        this.apiDownLinkMethodApplication = apiDownLinkMethodApplication;
    }

    /**
     * API 下联 Method 表
     *
     * @author : Jia wei Wu
     * @date : 2022/1/1 11:00 下午
     * @version : 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增/更新API 下联 Method")
    @PostMapping()
    public Result save(@Validated(ValidType.Create.class) @RequestBody ApiDownLinkMethodQo apiDownLinkMethodQo) {
        return apiDownLinkMethodApplication.save(apiDownLinkMethodQo);
    }


}
