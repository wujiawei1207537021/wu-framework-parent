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
 * 授权链表（如果使用 菜单用户  角色用户 角色菜单 三个的关系可以直接弃用）
 */
@ApiModel(value = "SysPrivilege", description = "授权链表")
@Data
@Entity
@Table(name = "sys_privilege")
@TableName("sys_privilege")
public class SysPrivilege extends Model<SysPrivilege> {

    @ApiModelProperty(value = "主键ID")
    @Id
    @Column(name = "privilege_id", nullable = false, columnDefinition = "INTEGER   COMMENT '主键ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "privilege_id", type = IdType.AUTO)
    private Integer privilegeId;

    @ApiModelProperty(value = "授权对象 user rloe")
    @Basic
    @Column(name = "privilege_master", nullable = true, length = 100, columnDefinition = "VARCHAR(25) DEFAULT 'R'  COMMENT '授权对象 user rloe'")
    private String privilegeMaster;

    @ApiModelProperty(value = "userID 或roleId")
    @Basic
    @Column(name = "privilege_master_value", nullable = true, length = 100, columnDefinition = "VARCHAR(100)   COMMENT 'userID 或roleId'")
    private String privilegeMasterValue;

    @ApiModelProperty(value = "授权区域 menu  role  button")
    @Basic
    @Column(name = "privilege_access", nullable = true, length = 100, columnDefinition = "VARCHAR(100)   COMMENT '授权区域 menu  role  button'")
    private String privilegeAccess;

    @ApiModelProperty(value = "授权区域ID menuId rloeId buttonId")
    @Basic
    @Column(name = "privilege_access_value", nullable = true, columnDefinition = "bigint   COMMENT ' 授权区域ID menuId rloeId buttonId'")
    private Integer privilegeAccessValue;

    @ApiModelProperty(value = "使用状态(0-停用,1-使用)")
    @Basic
    @Column(name = "privilege_operation", nullable = true, length = 1, columnDefinition = "VARCHAR(1)   DEFAULT '1' COMMENT '使用状态(0-停用,1-使用)'")
    private String privilegeOperation;


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
        return privilegeId;
    }
}
