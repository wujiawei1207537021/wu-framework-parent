package com.wu.framework.play.platform.application.command.translate;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.lang.String;
import java.lang.Long;
/**
 * describe 翻译数据 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryListCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "translate_query_List_command",description = "翻译数据")
public class TranslateQueryListCommand {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 源文本类型
     */
    @Schema(description ="源文本类型",name ="from",example = "")
    private String from;

    /**
     * 
     * 数据ID
     */
    @Schema(description ="数据ID",name ="id",example = "")
    private Long id;

    /**
     * 
     * 源文本
     */
    @Schema(description ="源文本",name ="sourceWord",example = "")
    private String sourceWord;

    /**
     * 
     * 目标文本类型
     */
    @Schema(description ="目标文本类型",name ="to",example = "")
    private String to;

    /**
     * 
     * 翻译后的文本
     */
    @Schema(description ="翻译后的文本",name ="translateWord",example = "")
    private String translateWord;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}