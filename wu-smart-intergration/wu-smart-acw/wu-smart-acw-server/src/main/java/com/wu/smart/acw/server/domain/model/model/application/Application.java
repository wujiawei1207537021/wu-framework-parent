package com.wu.smart.acw.server.domain.model.model.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 应用 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "application",description = "应用")
public class Application {


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
     * 
     */
    @Schema(description ="",name ="databaseSchemaId",example = "")
    private Long databaseSchemaId;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="databaseSchemaName",example = "")
    private String databaseSchemaName;

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
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}