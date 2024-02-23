package com.wu.framework.authorization.platform.infrastructure.entity;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "menu",  comment = "菜单")
@Schema(title = "menu", description = "")
public class MenuDO {


    /**
     * 菜单描述
     */
    @Schema(description = "菜单描述", name = "desc")
    @LazyTableField(name = "desc", comment = "菜单描述", columnType = "varchar(255)")
    private String desc;

    /**
     * 菜单code
     */
    @Schema(description = "菜单code", name = "code")
    @LazyTableField(name = "code", comment = "菜单code", lazyTableIndexs = {@LazyTableIndex(indexName = "menu_unique", indexType = LayerField.LayerFieldType.UNIQUE)})
    private String code;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "")
    private LocalDateTime createTime;

    /**
     * 菜单icon
     */
    @Schema(description = "菜单icon", name = "icon")
    @LazyTableField(name = "icon", comment = "菜单icon", columnType = "varchar(255)")
    private String icon;

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    @LazyTableFieldId(name = "id", comment = "主键")
    private Long id;

    /**
     * 菜单iframe 0：菜单路由 1：转发
     */
    @Schema(description = "菜单iframe 0：菜单路由 1：转发", name = "iframe")
    @LazyTableField(name = "iframe", comment = "菜单iframe 0：菜单路由 1：转发", columnType = "int")
    private Integer iframe;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     *
     */
    @Schema(description = "", name = "menu")
    @LazyTableField(name = "menu", comment = "", columnType = "varchar(255)")
    private String menu;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称", name = "name")
    @LazyTableField(name = "name", comment = "菜单名称", columnType = "varchar(255)")
    private String name;

    /**
     * 菜单父编码
     */
    @Schema(description = "菜单父编码", name = "parentCode")
    @LazyTableField(name = "parent_code", comment = "菜单父编码", columnType = "varchar(255)")
    private String parentCode;

    /**
     * 菜单sort
     */
    @Schema(description = "菜单sort", name = "sort")
    @LazyTableField(name = "sort", comment = "菜单sort", columnType = "varchar(255)")
    private String sort;

    /**
     * 菜单type（0:目录 1：菜单 2：按钮）
     */
    @Schema(description = "菜单type（0:目录 1：菜单 2：按钮）", name = "type")
    @LazyTableField(name = "type", comment = "菜单type（0:目录 1：菜单 2：按钮）", columnType = "int")
    private Integer type;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    /**
     * 菜单url
     */
    @Schema(description = "菜单url", name = "url")
    @LazyTableField(name = "url", comment = "菜单url", columnType = "varchar(255)")
    private String url;

}