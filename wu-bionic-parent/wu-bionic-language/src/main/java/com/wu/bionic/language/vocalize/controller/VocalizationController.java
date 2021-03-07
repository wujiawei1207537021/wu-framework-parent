package com.wu.bionic.language.vocalize.controller;

import com.wu.bionic.language.vocalize.Vocalization;
import com.wu.framework.easy.stereotype.upsert.converter.stereotype.Word;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.web.EasyController;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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


    @ApiOperation(tags = "文本转换成语音",value = "文本转换成语音-获取语音数据")
    @GetMapping("/word/voice")
    public List<Word> voiceData() {
       return vocalization.voiceData();
    }

    @ApiOperation(tags = "文本转换成语音", value = "文本转换成语音-发声文件")
    @PostMapping("/word/voice/byte")
    public void textToByte(String text) {
        vocalization.textToByte(text);
    }
}
