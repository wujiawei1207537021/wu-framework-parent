package com.wu.framework.dynamic.iframe.platform.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * describe 动态Iframe
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "dynamic_iframe_command", description = "动态Iframe")
public class DynamicIframeCommand {


    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * 动态参数
     */
    @Schema(description = "动态参数", name = "dynamicParameter")
    private Map dynamicParameter;

    /**
     *
     */
    @Schema(description = "", name = "id")
    private Long id;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;

    /**
     * 是否站内
     */
    @Schema(description = "是否站内", name = "isStation")
    private Boolean isStation;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

    /**
     * iframe地址
     */
    @Schema(description = "iframe地址", name = "url")
    private String url;

}