package com.wuframework.system.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限表
 */
@ApiModel(value = "SysRolePermission", description = "角色权限表")
@Data
@Entity
@Table(name = "sys_role_permission")
@TableName("sys_role_permission")
public class SysRolePermission extends Model<SysRolePermission> {

    @ApiModelProperty(value = "角色权限关联ID")
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "bigint(20) COMMENT '角色权限关联ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色id")
    @Basic
    @Column(name = "role_id", nullable = false, columnDefinition = "bigint   COMMENT '角色id'")
    private Integer roleId;

    @ApiModelProperty(value = "权限ID")
    @Basic
    @Column(name = "permission_id", nullable = false, columnDefinition = "bigint   COMMENT '权限ID'")
    private Integer permissionId;

    @ApiModelProperty(value = "更新时间")
    @UpdateTimestamp
    @Basic
    @Column(name = "gmt_modified", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    private Date gmtModified;

    @ApiModelProperty(value = "创建时间")
    @CreationTimestamp
    @Basic
    @Column(name = "gmt_create", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date gmtCreate;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
