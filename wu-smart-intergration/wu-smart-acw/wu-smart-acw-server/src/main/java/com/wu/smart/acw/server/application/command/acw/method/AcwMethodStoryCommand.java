package com.wu.smart.acw.server.application.command.acw.method;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe ACW 方法 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_method_story_command",description = "ACW 方法")
public class AcwMethodStoryCommand {


    /**
     * 
     * null
     */
    @Schema(description ="null",name ="body",example = "")
    private String body;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="classId",example = "")
    private Long classId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="id",example = "")
    private Long id;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="inParams",example = "")
    private String inParams;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="name",example = "")
    private String name;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="outParams",example = "")
    private String outParams;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}