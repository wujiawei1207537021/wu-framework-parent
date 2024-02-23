package com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * describe tts 中文对应制定音色数据
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryOneCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "TtsChineseCharactersTimbreQueryTextToBytesCommand", description = "tts 中文对应制定音色数据")
public class TtsChineseCharactersTimbreQueryTextToBytesCommand {
    /**
     * 文字
     */
    @Schema(description = "文字", name = "text")
    private String text;

    /**
     * 音色编码
     */
    @Schema(description = "音色编码", name = "timbreCode")
    private String timbreCode;


}