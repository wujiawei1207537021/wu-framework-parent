package com.wu.framework.dynamic.iframe.platform.application.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
/**
 * describe 动态Iframe 
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "dynamic_iframe_command",description = "动态Iframe")
public class DynamicIframeDTO {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 动态参数
     */
    @Schema(description ="动态参数",name ="dynamicParameter",example = "")
    private Map dynamicParameter;

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
     * 是否站内
     */
    @Schema(description ="是否站内",name ="isStation",example = "")
    private Boolean isStation;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 
     * iframe地址
     */
    @Schema(description ="iframe地址",name ="url",example = "")
    private String url;

}