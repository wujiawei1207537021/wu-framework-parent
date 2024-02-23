package com.wuframework.system.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.wuframework.response.mark.ValidType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 权限
 */
@ApiModel(value = "权限", description = "sys_permission")
@Data
@Entity
@Table(name = "sys_permission")
@TableName("sys_permission")
public class SysPermission extends Model<SysPermission> {

    @ApiModelProperty(value = "权限ID")
    @Id
    @Column(name = "permission_id", nullable = false, columnDefinition = "INTEGER   COMMENT '权限ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "permission_id", type = IdType.AUTO)
    @NotNull(message = "权限ID不能为空", groups = ValidType.Update.class)
    private Integer permissionId;

    @ApiModelProperty(value = "权限类型 CRUD")
    @Basic
    @Column(name = "permission_type", nullable = true, length = 100, columnDefinition = "VARCHAR(25) DEFAULT 'R'  COMMENT '权限类型 CRUD'")
    private String permissionType;

    @ApiModelProperty(value = "权限请求根路径")
    @Basic
    @Column(name = "permission_root_path", nullable = true, length = 100, columnDefinition = "VARCHAR(100)   COMMENT '权限请求根路径'")
    private String permissionRootPath;

    @ApiModelProperty(value = "权限请求完整路径")
    @Basic
    @Column(name = "permission_complete_path", nullable = true, length = 100, columnDefinition = "VARCHAR(100)   COMMENT '权限请求完整路径'")
    private String permissionCompletePath;

    @ApiModelProperty(value = "权限备注")
    @Basic
    @Column(name = "remark", nullable = true, length = 100, columnDefinition = "VARCHAR(100)   COMMENT '权限备注'")
    private String remark;

    @ApiModelProperty(value = "创建用户id")
    @Basic
    @Column(name = "user_id_create", nullable = true, columnDefinition = "bigint   COMMENT '创建用户id'")
    private Integer userIdCreate;

    @ApiModelProperty(value = "使用状态(0-停用,1-使用) 默认 1")
    @Basic
    @Column(name = "status", nullable = true, length = 1, columnDefinition = "VARCHAR(1)   DEFAULT '1' COMMENT '使用状态(0-停用,1-使用)'")
    private String status;


    @ApiModelProperty(value = "修改时间")
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
        return permissionId;
    }
}
