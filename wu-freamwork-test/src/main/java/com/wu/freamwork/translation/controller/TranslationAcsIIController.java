package com.wu.freamwork.translation.controller;

import com.wu.framework.inner.layer.data.translation.ArgsNormalTranslation;
import com.wu.framework.inner.layer.data.translation.NormalTranslation;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.toolkit.DynamicLazyHttpBodyContextHolder;
import com.wu.freamwork.translation.domain.UserAcsII;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;

@Tag(name = "AcsII转换")
@EasyController("/translation/acsII")
public class TranslationAcsIIController {

    @Operation(summary = "转换用户ID，用户名称 为acsII")
    @NormalTranslation
    @PostMapping("/acsII")
    public Result<UserAcsII> translationAcsII(@ArgsNormalTranslation @RequestBody UserAcsII userAcsII) {
        byte[] peek = DynamicLazyHttpBodyContextHolder.peek();
        System.out.println("body size ==> "+new String(peek, StandardCharsets.UTF_8));
        return ResultFactory.successOf(userAcsII);
    }

}
