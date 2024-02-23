package com.wu.smart.acw.server.domain.model.model.dependency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "dependency",description = "")
public class Dependency {


    /**
     * 
     * 
     */
    @Schema(description ="",name ="artifactId",example = "")
    private String artifactId;

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
    @Schema(description ="",name ="groupId",example = "")
    private String groupId;

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
    @Schema(description ="null",name ="optional",example = "")
    private Boolean optional;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="scope",example = "")
    private String scope;

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

    /**
     * 
     * 
     */
    @Schema(description ="",name ="version",example = "")
    private String version;

}