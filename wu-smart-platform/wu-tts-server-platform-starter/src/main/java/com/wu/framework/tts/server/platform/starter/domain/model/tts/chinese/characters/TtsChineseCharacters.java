package com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.String;
import java.io.File;
import java.lang.Long;
import java.lang.Boolean;
import java.time.LocalDateTime;
import java.lang.Integer;
/**
 * describe tts 中文 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "tts_chinese_characters",description = "tts 中文")
public class TtsChineseCharacters {


    /**
     * 
     * 拼音
     */
    @Schema(description ="拼音",name ="pinYin",example = "")
    private String pinYin;

    /**
     * 
     * 笔划
     */
    @Schema(description ="笔划",name ="strokes",example = "")
    private String strokes;

    /**
     * 
     * 部首
     */
    @Schema(description ="部首",name ="radicals",example = "")
    private String radicals;

    /**
     * 
     * 更多
     */
    @Schema(description ="更多",name ="more",example = "")
    private String more;

    /**
     * 
     * 繁体字
     */
    @Schema(description ="繁体字",name ="oldWord",example = "")
    private String oldWord;

    /**
     * 
     * 例子
     */
    @Schema(description ="例子",name ="explanation",example = "")
    private String explanation;

    /**
     * 
     * 汉字
     */
    @Schema(description ="汉字",name ="word",example = "")
    private String word;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    private Long id;

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
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;



}