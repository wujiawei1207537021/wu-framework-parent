package com.wu.framework.authorization.platform.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "menu_command", description = "")
public class MenuCommand {


    /**
     * 菜单描述
     */
    @Schema(description = "菜单描述", name = "desc")
    private String desc;

    /**
     * 菜单code
     */
    @Schema(description = "菜单code", name = "code")
    private String code;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * 菜单icon
     */
    @Schema(description = "菜单icon", name = "icon")
    private String icon;

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    private Long id;

    /**
     * 菜单iframe 0：菜单路由 1：转发
     */
    @Schema(description = "菜单iframe 0：菜单路由 1：转发", name = "iframe")
    private Integer iframe;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;

    /**
     *
     */
    @Schema(description = "", name = "menu")
    private String menu;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称", name = "name")
    private String name;

    /**
     * 菜单父编码
     */
    @Schema(description = "菜单父编码", name = "parentCode")
    private String parentCode;

    /**
     * 菜单sort
     */
    @Schema(description = "菜单sort", name = "sort")
    private String sort;

    /**
     * 菜单type（0:目录 1：菜单 2：按钮）
     */
    @Schema(description = "菜单type（0:目录 1：菜单 2：按钮）", name = "type")
    private Integer type;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

    /**
     * 菜单url
     */
    @Schema(description = "菜单url", name = "url")
    private String url;

}