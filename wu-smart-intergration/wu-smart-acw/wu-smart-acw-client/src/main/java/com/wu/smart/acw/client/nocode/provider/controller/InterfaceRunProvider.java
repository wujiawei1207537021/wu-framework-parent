package com.wu.smart.acw.client.nocode.provider.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.config.constant.BaseUrlConstant;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceRunApplication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * description 接口运行
 *
 * @author 吴佳伟
 * @date 2023/08/14 15:15
 */
@ConditionalOnBean(LazyLambdaStream.class)
@Tag(name = "接口运行-提供者")
@EasyController(BaseUrlConstant.api_url)
public class InterfaceRunProvider {


    private final InterfaceRunApplication interfaceRunApplication;

    public InterfaceRunProvider(InterfaceRunApplication interfaceRunApplication) {
        this.interfaceRunApplication = interfaceRunApplication;
    }

    /**
     * 调用
     *
     * @return
     */
    @GetMapping("/**")
    public Result invoke() {
        return interfaceRunApplication.invoke();
    }

}
