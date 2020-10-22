package com.wuframework.system.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

/**
 * CREATE TABLE `sys_menu`  (
 * `menu_id` bigint(20) NOT NULL,
 * `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
 * `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
 * `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
 * `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
 * `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
 * `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
 * `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
 * `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 * `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
 * PRIMARY KEY (`menu_id`) USING BTREE
 * ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;
 */

@Data
@Entity
@Table(name = "sys_menu")
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {
    @Id
    @Column(name = "menu_id", nullable = false, columnDefinition = "INTEGER   COMMENT 'id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;
    @Basic
    @Column(name = "parent_id", nullable = true, columnDefinition = "INTEGER  COMMENT '父菜单ID，一级菜单为0'")
    private Integer parentId;
    @Basic
    @Column(name = "name", nullable = true, length = 50, columnDefinition = "VARCHAR ( 50 )  COMMENT '菜单名称'")
    private String name;
    @Basic
    @Column(name = "url", nullable = true, length = 200, columnDefinition = "VARCHAR ( 200 )  COMMENT '菜单URL'")
    private String url;
    @Basic
    @Column(name = "perms", nullable = true, length = 500, columnDefinition = "VARCHAR ( 50 )  COMMENT '授权(多个用逗号分隔，如：user:list,user:create)'")
    private String perms;
    @Basic
    @Column(name = "type", nullable = true, columnDefinition = "INTEGER COMMENT '类型   0：目录   1：菜单   2：按钮'")
    private Integer type;

    @Basic
    @Column(name = "icon", nullable = true, length = 50, columnDefinition = "VARCHAR ( 50 )  COMMENT '菜单图标'")
    private String icon;

    @Basic
    @Column(name = "order_num", nullable = true, columnDefinition = "INTEGER  COMMENT '排序'")
    private Integer orderNum;

    @UpdateTimestamp
    @Basic
    @Column(name = "gmt_modified", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    private Date gmtModified;

    @CreationTimestamp
    @Basic
    @Column(name = "gmt_create", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date gmtCreate;


    @Override
    protected Serializable pkVal() {
        return menuId;
    }
}
