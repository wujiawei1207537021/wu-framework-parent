package com.wu.bionic.language.vocalize.controller;

import com.wu.bionic.language.vocalize.Vocalization;
import com.wu.framework.easy.stereotype.upsert.converter.stereotype.ChineseCharacters;
import com.wu.framework.inner.layer.web.EasyController;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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


    @ApiOperation(tags = "文本转换成语音", value = "文本转换成语音-获取语音基础数据")
    @GetMapping("/word/voice")
    public List<ChineseCharacters> voiceData() {
        return vocalization.voiceData();
    }

    @ApiOperation(tags = "文本转换成语音", value = "文本转换成语音-发声文件")
    @PostMapping("/word/voice/byte")
    public void textToByte(String text, HttpServletResponse httpServletResponse) {
        byte[] textToByte = vocalization.textToByte(text);
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.%s\"; filename*=utf-8''%s.%s", text, "mp3", text, "mp3"));
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setHeader("Expires", "0");
        httpServletResponse.setHeader("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
        try {
            OutputStream body = httpServletResponse.getOutputStream();
            body.write(textToByte);
            body.flush();
            body.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
