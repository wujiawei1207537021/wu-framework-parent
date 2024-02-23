package com.wu.smart.acw.server.domain.model.model.application.api.down.link.method;

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
@Schema(title = "application_api_down_link_method",description = "")
public class ApplicationApiDownLinkMethod {


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