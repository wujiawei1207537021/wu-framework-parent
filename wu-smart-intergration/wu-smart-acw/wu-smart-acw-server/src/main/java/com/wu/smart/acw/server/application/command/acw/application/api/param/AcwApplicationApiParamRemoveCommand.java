package com.wu.smart.acw.server.application.command.acw.application.api.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe api参数 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_application_api_param_remove_command",description = "api参数")
public class AcwApplicationApiParamRemoveCommand {


    /**
     * 
     * apiId
     */
    @Schema(description ="apiId",name ="apiId",example = "")
    private Long apiId;

    /**
     * 
     * 数据库字段ID
     */
    @Schema(description ="数据库字段ID",name ="columnName",example = "")
    private String columnName;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * api参数主键
     */
    @Schema(description ="api参数主键",name ="id",example = "")
    private Long id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 参数名称
     */
    @Schema(description ="参数名称",name ="name",example = "")
    private String name;

    /**
     * 
     * 条件
     */
    @Schema(description ="条件",name ="term",example = "")
    private String term;

    /**
     * 
     * 类型 路径参数、请求参数、请求体参数
     */
    @Schema(description ="类型 路径参数、请求参数、请求体参数",name ="type",example = "")
    private String type;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}