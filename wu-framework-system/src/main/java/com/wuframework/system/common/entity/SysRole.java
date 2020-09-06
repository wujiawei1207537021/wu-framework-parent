package com.wuframework.system.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/***
 * CREATE TABLE `sys_role`  (
 *   `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
 *   `role_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标识',
 *   `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
 *   `user_id_create` bigint(255) NULL DEFAULT NULL COMMENT '创建用户id',
 *   `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
 *   `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '使用状态(0-停用,1-使用)',
 *   `default_menu_id` int(20) NULL DEFAULT NULL COMMENT '默认菜单ID',
 *   `data_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色可查询数据类型',
 *   `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
 *   PRIMARY KEY (`role_id`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;
 */
@Data
@Entity
@Table(name = "sys_role")
@TableName("sys_role")
public class SysRole extends Model<SysRole> {
    @Id
    @Column(name = "role_id", nullable = false,columnDefinition = "INTEGER   COMMENT '角色id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    @Basic
    @Column(name = "parent_id", nullable = true, length = 100,columnDefinition = "INTEGER   DEFAULT '0'  COMMENT '父类权限ID'")
    private String parentId;

    @Basic
    @Column(name = "role_name", nullable = true, length = 100,columnDefinition = "VARCHAR(100)   COMMENT '角色名称'")
    private String roleName;
    @Basic
    @Column(name = "role_sign", nullable = true, length = 100,columnDefinition = "VARCHAR(100)   COMMENT '角色标识'")
    private String roleSign;
    @Basic
    @Column(name = "remark", nullable = true, length = 100,columnDefinition = "VARCHAR(100)   COMMENT '备注'")
    private String remark;
    @Basic
    @Column(name = "user_id_create", nullable = true,columnDefinition = "bigint   COMMENT '创建用户id'")
    private Integer userIdCreate;

    @Basic
    @Column(name = "status", nullable = true, length = 1,columnDefinition = "INTEGER   DEFAULT '1' COMMENT '使用状态(0-停用,1-使用)'")
    private Integer status;
    @Basic
    @Column(name = "default_menu_id", nullable = true,columnDefinition = "INTEGER   COMMENT '默认菜单ID'")
    private Integer defaultMenuId;
    @Basic
    @Column(name = "data_type", nullable = true, length = 20,columnDefinition = "VARCHAR(20)   COMMENT '角色可查询数据类型'")
    private String dataType;


    @UpdateTimestamp
    @Basic
    @Column(name = "gmt_modified", nullable = true,columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    private Date gmtModified;
    /**
     * 记录创建日期
     */
    @CreationTimestamp
    @Basic
    @Column(name = "gmt_create", nullable = true,columnDefinition = "datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date gmtCreate;


    /**
     * 当前角色可管理的其他角色ID字符串拼接
     */
    @Transient
    @TableField(strategy = FieldStrategy.IGNORED)
    private String manageableRole;


    /**
     * 角色所拥有的菜单列表
     */
    @Transient
    @TableField(exist = false)
    private List<SysMenu> menuList;


    @Override
    protected Serializable pkVal() {
        return roleId;
    }
}
