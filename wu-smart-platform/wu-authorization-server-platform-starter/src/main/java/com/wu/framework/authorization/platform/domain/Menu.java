package com.wu.framework.authorization.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@LazyTable(tableName = "menu", schema = "acw")
@ApiModel(value = "menu", description = "null")
public class Menu {

    /**
     * 菜单code
     */
    @ApiModelProperty(value = "菜单code", name = "code", example = "")
    @LazyTableField(name = "code", comment = "菜单code")
    private String code;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", name = "name", example = "")
    @LazyTableField(name = "name", comment = "菜单名称")
    private String name;
    /**
     * 菜单url
     */
    @ApiModelProperty(value = "菜单url", name = "url", example = "")
    @LazyTableField(name = "url", comment = "菜单url")
    private String url;
    /**
     * 菜单type
     */
    @ApiModelProperty(value = "菜单type", name = "type", example = "")
    @LazyTableField(name = "type", comment = "菜单type")
    private Integer type;
    /**
     * 菜单icon
     */
    @ApiModelProperty(value = "菜单icon", name = "icon", example = "")
    @LazyTableField(name = "icon", comment = "菜单icon")
    private String icon;
    /**
     * 菜单sort
     */
    @ApiModelProperty(value = "菜单sort", name = "sort", example = "")
    @LazyTableField(name = "sort", comment = "菜单sort")
    private String sort;
    /**
     * 菜单iframe
     */
    @ApiModelProperty(value = "菜单iframe", name = "iframe", example = "")
    @LazyTableField(name = "iframe", comment = "菜单iframe")
    private Integer iframe;
    /**
     * 菜单父编码
     */
    @ApiModelProperty(value = "菜单父编码", name = "parentCode", example = "")
    @LazyTableField(name = "parent_code", comment = "菜单父编码")
    private String parentCode;
    /**
     * 菜单描述
     */
    @ApiModelProperty(value = "菜单描述", name = "desc", example = "")
    @LazyTableField(name = "desc", comment = "菜单描述")
    private String desc;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id", example = "")
    @LazyTableField(name = "id", comment = "主键")
    private Long id;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除", name = "isDeleted", example = "")
    @LazyTableField(name = "is_deleted", comment = "是否删除")
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "")
    @LazyTableField(name = "create_time", comment = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "")
    @LazyTableField(name = "update_time", comment = "更新时间")
    private LocalDateTime updateTime;
    /**
     *
     */
    @ApiModelProperty(value = "", name = "menu", example = "")
    @LazyTableField(name = "menu", comment = "")
    private String menu;
}