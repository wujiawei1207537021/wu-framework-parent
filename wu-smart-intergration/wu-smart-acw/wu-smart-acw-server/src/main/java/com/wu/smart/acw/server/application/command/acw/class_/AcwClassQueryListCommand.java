package com.wu.smart.acw.server.application.command.acw.class_;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;
/**
 * describe 对应的class字节码 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryListCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_class_query_List_command",description = "对应的class字节码")
public class AcwClassQueryListCommand {


    /**
     * 
     * 
     */
    @Schema(description ="",name ="annotationList",example = "")
    private Map annotationList;

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
    @Schema(description ="null",name ="includedClasses",example = "")
    private Map includedClasses;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="interfaceClass",example = "")
    private String interfaceClass;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="javaClassType",example = "")
    private String javaClassType;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="name",example = "")
    private String name;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="packageName",example = "")
    private String packageName;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="parentClass",example = "")
    private String parentClass;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="projectId",example = "")
    private Long projectId;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="type",example = "")
    private String type;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}