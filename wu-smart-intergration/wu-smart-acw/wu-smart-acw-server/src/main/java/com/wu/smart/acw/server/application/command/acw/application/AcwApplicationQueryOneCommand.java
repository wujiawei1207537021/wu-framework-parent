package com.wu.smart.acw.server.application.command.acw.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 应用 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryOneCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_application_query_one_command",description = "应用")
public class AcwApplicationQueryOneCommand {


    /**
     * 
     * 
     */
    @Schema(description ="",name ="applicationId",example = "")
    private Long applicationId;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="applicationName",example = "")
    private String applicationName;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

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
    @Schema(description ="",name ="projectId",example = "")
    private Long projectId;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="schemaName",example = "")
    private String schemaName;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}