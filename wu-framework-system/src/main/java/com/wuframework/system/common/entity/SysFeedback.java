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
import java.util.Date;


@Data
@Entity
@Table(name = "sys_feedback")
@ApiModel("用户反馈")
@TableName("sys_feedback")
public class SysFeedback extends Model<SysFeedback> {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "feedback_id", nullable = false, columnDefinition = "INTEGER  COMMENT '用户反馈ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "feedback_id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户反馈ID")
    private Integer feedbackId;

    @Basic
    @Column(name = "feedback_user_id", nullable = true, columnDefinition = "INTEGER   COMMENT ' 反馈用户id'")
    @ApiModelProperty(value = "用户ID")
    private Integer feedbackUserId;

    @Basic
    @Column(name = "feedback_content", nullable = true, length = 255, columnDefinition = "VARCHAR ( 255 )   COMMENT '反馈内容'")
    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;

    @Basic
    @Column(name = "feedback_user_phone", nullable = true, length = 255, columnDefinition = "VARCHAR ( 255 )   COMMENT '用户联系方式'")
    @ApiModelProperty(value = "用户联系方式")
    private String feedbackUserPhone;

    @Basic
    @Column(name = "feedback_status", nullable = true, length = 255, columnDefinition = "VARCHAR ( 255 ) DEFAULT 0   COMMENT '反馈状态：0未查看，1已处理，2未处理，3不予处理'")
    @ApiModelProperty(value = "反馈状态：0未查看，1已处理，2未处理，3不予处理")
    private String feedbackStatus;

    @Basic
    @Column(name = "feedback_remark", nullable = true, length = 255, columnDefinition = "VARCHAR ( 255 )   COMMENT '反馈备注'")
    @ApiModelProperty(value = "反馈备注")
    private String feedbackRemark;

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
        return feedbackId;
    }
}
