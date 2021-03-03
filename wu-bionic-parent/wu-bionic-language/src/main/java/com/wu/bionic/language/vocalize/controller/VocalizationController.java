package com.wu.bionic.language.vocalize.controller;

import com.wu.bionic.language.vocalize.Vocalization;
import com.wu.framework.easy.stereotype.web.EasyController;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * description
 *
 * @author Jia Wei Wu
 * @date 2021/3/3 下午3:42
 */
@EasyController("/vocalize")
public class VocalizationController {

    private final Vocalization vocalization;

    public VocalizationController(Vocalization vocalization) {
        this.vocalization = vocalization;
    }

    @ApiOperation(tags = "文本转换成语音", value = "文本转换成语音-服务器发声")
    @PostMapping("/play")
    public void play(String text) {
        vocalization.play(text);
    }
}
