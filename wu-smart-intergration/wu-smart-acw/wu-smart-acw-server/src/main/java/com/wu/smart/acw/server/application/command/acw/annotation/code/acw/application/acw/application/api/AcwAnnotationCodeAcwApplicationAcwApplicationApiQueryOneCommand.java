package com.wu.smart.acw.server.application.command.acw.annotation.code.acw.application.acw.application.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * describe acw_annotation_code_acw_application_acw_application_api 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryOneCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_annotation_code_acw_application_acw_application_api_query_one_command",description = "")
public class AcwAnnotationCodeAcwApplicationAcwApplicationApiQueryOneCommand {


    /**
     * 
     * null
     */
    @Schema(description ="null",name ="applicationId",example = "")
    private String applicationId;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="applicationName",example = "")
    private String applicationName;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="className",example = "")
    private String className;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="createTime",example = "")
    private String createTime;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="description",example = "")
    private String description;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="id",example = "")
    private String id;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="isDeleted",example = "")
    private String isDeleted;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="method",example = "")
    private String method;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="path",example = "")
    private String path;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="projectId",example = "")
    private String projectId;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="schemaName",example = "")
    private String schemaName;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="tag",example = "")
    private String tag;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="updateTime",example = "")
    private String updateTime;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="value",example = "")
    private String value;

}