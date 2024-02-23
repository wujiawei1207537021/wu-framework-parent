package com.wu.smart.acw.server.application.command.acw.application.api.down.link.method;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe ACW API 下联 Method 表 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_application_api_down_link_method_story_command",description = "ACW API 下联 Method 表")
public class AcwApplicationApiDownLinkMethodStoryCommand {


    /**
     * 
     * null
     */
    @Schema(description ="null",name ="apiId",example = "")
    private Long apiId;

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
    @Schema(description ="null",name ="methodId",example = "")
    private Long methodId;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="projectId",example = "")
    private Long projectId;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}