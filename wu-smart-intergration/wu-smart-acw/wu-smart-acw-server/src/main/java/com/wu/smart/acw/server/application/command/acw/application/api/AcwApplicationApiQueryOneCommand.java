package com.wu.smart.acw.server.application.command.acw.application.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 应用API 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryOneCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_application_api_query_one_command",description = "应用API")
public class AcwApplicationApiQueryOneCommand {


    /**
     * 
     * 
     */
    @Schema(description ="",name ="applicationId",example = "")
    private Long applicationId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 接口描述
     */
    @Schema(description ="接口描述",name ="description",example = "")
    private String description;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="id",example = "")
    private Long id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="method",example = "")
    private String method;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="path",example = "")
    private String path;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="tag",example = "")
    private String tag;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}