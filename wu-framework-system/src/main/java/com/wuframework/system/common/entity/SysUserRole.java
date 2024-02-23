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
 * CREATE TABLE `sys_user_role`  (
 * `id` bigint(20) NOT NULL AUTO_INCREMENT,
 * `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
 * `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
 * `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 * `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
 * PRIMARY KEY (`id`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;
 */
@Data
@Entity
@Table(name = "sys_user_role")
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "INTEGER   COMMENT 'id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @Basic
    @Column(name = "user_id", nullable = true)
    private Integer userId;
    @Basic
    @Column(name = "role_id", nullable = true)
    private Integer roleId;


    @UpdateTimestamp
    @Basic
    @Column(name = "gmt_modified", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    private Date gmtModified;
    /**
     * 记录创建日期
     */
    @CreationTimestamp
    @Basic
    @Column(name = "gmt_create", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date gmtCreate;


    @Override
    protected Serializable pkVal() {
        return id;
    }
}
