package com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.Long;
import java.lang.String;
import java.io.File;
import java.lang.Boolean;
import java.time.LocalDateTime;
/**
 * describe tts 中文对应制定音色数据 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryListCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "tts_chinese_characters_timbre_query_List_command",description = "tts 中文对应制定音色数据")
public class TtsChineseCharactersTimbreQueryListCommand {


    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    private Long id;

    /**
     * 
     * 汉字
     */
    @Schema(description ="汉字",name ="word",example = "")
    private String word;

    /**
     * 
     * 音色编码
     */
    @Schema(description ="音色编码",name ="timbreCode",example = "")
    private String timbreCode;


    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 修改时间
     */
    @Schema(description ="修改时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}