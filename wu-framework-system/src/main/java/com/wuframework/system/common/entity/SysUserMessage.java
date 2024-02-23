package com.wuframework.system.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("系统用户消息")
@Data
@TableName("sys_user_message")
@Entity
@Table(name = "sys_user_message")
public class SysUserMessage<TO> extends Model<SysUserMessage> {

    @ApiModelProperty(value = "用户ID")
    @Basic
    @Column(name = "user_id", nullable = false, columnDefinition = "INTEGER   COMMENT '用户ID'")
    public Integer userId;
    @ApiModelProperty(value = "系统用户消息状态")
    @Basic
    @Column(name = "sys_message_status", nullable = false, columnDefinition = "INTEGER   COMMENT '系统用户消息状态'")
    public Integer sysMessageStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "数据传输对象")
    @TableField(exist = false)
    @Transient
    public TO to;
    @ApiModelProperty(value = "修改时间")
    @UpdateTimestamp
    @Basic
    @Column(name = "gmt_modified", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    public LocalDateTime gmtModified;
    @ApiModelProperty(value = "创建时间")
    @CreationTimestamp
    @Basic
    @Column(name = "gmt_create", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    public LocalDateTime gmtCreate;
    @ApiModelProperty(value = "系统用户消息Id")
    @Id
    @TableId(value = "sys_user_message_id", type = IdType.AUTO)
    @Column(name = "sys_user_message_id", nullable = false, columnDefinition = "INTEGER  COMMENT '系统用户消息Id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sysUserMessageId;
    @ApiModelProperty(value = "系统消息Id")
    @Basic
    @Column(name = "sys_message_id", nullable = false, columnDefinition = "INTEGER  COMMENT '系统消息Id'")
    private Integer sysMessageId;

    @Override
    protected Serializable pkVal() {
        return sysUserMessageId;
    }
}
