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
import java.sql.Timestamp;
import java.util.Date;

/**
 * CREATE TABLE `sys_dict`  (
 * `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
 * `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '标签名',
 * `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据值',
 * `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类型',
 * `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
 * `sort` decimal(10, 0) NULL DEFAULT NULL COMMENT '排序（升序）',
 * `parent_id` bigint(64) NULL DEFAULT 0 COMMENT '父级编号',
 * `create_by` int(64) NULL DEFAULT NULL COMMENT '创建者',
 * `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
 * `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
 * `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
 * `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注信息',
 * `enabled` int(1) NULL DEFAULT 1 COMMENT '是否启用（1启用，0禁用）',
 * PRIMARY KEY (`id`) USING BTREE,
 * INDEX `sys_dict_value`(`value`) USING BTREE,
 * INDEX `sys_dict_label`(`name`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典表' ROW_FORMAT = Dynamic;
 */
@Data
@Entity
@Table(name = "sys_dict")
@TableName("sys_dict")
public class SysDict extends Model<SysDict> {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "bigint    COMMENT '编号'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @Basic
    @Column(name = "name", nullable = true, length = 100, columnDefinition = "VARCHAR ( 100 )   COMMENT '标签名'")
    private String name;
    @Basic
    @Column(name = "value", nullable = true, length = 100, columnDefinition = "VARCHAR ( 100 )   COMMENT '数据值'")
    private String value;
    @Basic
    @Column(name = "type", nullable = true, length = 100, columnDefinition = "VARCHAR ( 255 )   COMMENT '类型'")
    private String type;
    @Basic
    @Column(name = "description", nullable = true, length = 100, columnDefinition = "VARCHAR ( 100 )   COMMENT '描述'")
    private String description;
    @Basic
    @Column(name = "sort", nullable = true, precision = 0, columnDefinition = "int  COMMENT '排序（升序)'")
    private Integer sort;

    @Basic
    @Column(name = "parent_id", nullable = true, columnDefinition = "bigint   DEFAULT 0 COMMENT '父级编号'")
    private Long parentId;
    @Basic
    @Column(name = "create_by", nullable = true, columnDefinition = "int   COMMENT '创建者'")
    private Integer createBy;

    @CreationTimestamp
    @Basic
    @Column(name = "create_date", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Timestamp createDate;

    @Basic
    @Column(name = "update_by", nullable = true, columnDefinition = "bigint   COMMENT '更新者'")
    private Integer updateBy;

    @UpdateTimestamp
    @Basic
    @Column(name = "update_date", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    private Timestamp updateDate;

    @Basic
    @Column(name = "remarks", nullable = true, length = 255, columnDefinition = "VARCHAR ( 255 )   COMMENT '备注信息'")
    private String remarks;
    @Basic
    @Column(name = "enabled", nullable = true, columnDefinition = "INTEGER    DEFAULT 1 COMMENT '是否启用（1启用，0禁用）'")
    private Integer enabled;

    @Transient
    @TableField(exist = false)
    @UpdateTimestamp
    @Basic
    @Column(name = "gmt_modified", nullable = true, columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    private Date gmtModified;

    @Transient
    @TableField(exist = false)
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
