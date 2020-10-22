package com.wuframework.system.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_dict_type")
@TableName("sys_dict_type")
public class SysDictType extends Model<SysDictType> {
    @Id
    @Column(name = "type_id", nullable = false, length = 30, columnDefinition = "VARCHAR ( 255 )   COMMENT '类型类型编号'")
    @TableId(value = "type_id", type = IdType.INPUT)
    private String typeId;
    @Basic
    @Column(name = "type_name", nullable = false, length = 255, columnDefinition = "VARCHAR ( 255 )   COMMENT '类型类型名称'")
    private String typeName;
    @Basic
    @Column(name = "enabled", nullable = true, columnDefinition = "INTEGER DEFAULT 1 COMMENT '是否启用（1启用，0禁用）'")
    private Integer enabled;

    @Transient
    @TableField(exist = false)
    @UpdateTimestamp
    @Basic
    @Column(name = "gmt_modified", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    private Date gmtModified;
    /**
     * 记录创建日期
     */
    @Transient
    @TableField(exist = false)
    @CreationTimestamp
    @Basic
    @Column(name = "gmt_create", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date gmtCreate;

    @Override
    protected Serializable pkVal() {
        return typeId;
    }
}
