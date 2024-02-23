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

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("系统消息")
@Data
@TableName("sys_message")
@Entity
@Table(name = "sys_message")
public class SysMessage extends Model<SysMessage> {

    @ApiModelProperty(value = "系统消息类型")
    @Basic
    @Column(name = "sys_message_type", nullable = false, columnDefinition = "INTEGER   COMMENT '系统消息类型'")
    public Integer sysMessageType;
    @ApiModelProperty(value = "修改时间")
    @UpdateTimestamp
    @Basic
    @Column(name = "gmt_modified", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    public LocalDateTime gmtModified;
    @ApiModelProperty(value = "记录创建日期")
    @CreationTimestamp
    @Basic
    @Column(name = "gmt_create", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    public LocalDateTime gmtCreate;
    @ApiModelProperty(value = "系统消息Id")
    @Id
    @TableId(value = "sys_message_id", type = IdType.AUTO)
    @Column(name = "sys_message_id", nullable = false, columnDefinition = "INTEGER  COMMENT '系统消息Id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sysMessageId;
    @ApiModelProperty(value = "关联ID")
    @Basic
    @Column(name = "association_id", nullable = false, columnDefinition = "INTEGER   COMMENT '关联ID'")
    private Integer associationId;

    @Override
    protected Serializable pkVal() {
        return sysMessageId;
    }
}
