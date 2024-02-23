package com.wu.smart.acw.server.application.command.acw.annotation.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe ACW 使用的代码注解 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryListCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_annotation_code_query_List_command",description = "ACW 使用的代码注解")
public class AcwAnnotationCodeQueryListCommand {


    /**
     * 
     * 
     */
    @Schema(description ="",name ="className",example = "")
    private String className;

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

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="value",example = "")
    private String value;

}