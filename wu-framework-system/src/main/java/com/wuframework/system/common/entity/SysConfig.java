package com.wuframework.system.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统配置参数表
 * </p>
 *
 * @author Xiongxz
 * @since 2019-05-19
 */
@Data
@Entity
@Table(name = "sys_config")
@TableName("sys_config")
public class SysConfig extends Model<SysConfig> {
    @Id
    @Column(name = "sys_config_id", nullable = false, columnDefinition = "INTEGER  COMMENT '配置主键'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "sys_config_id", type = IdType.AUTO)
    private Integer sysConfigId;
    /**
     * 配置类型
     */
    @Basic
    @Column(name = "config_type", nullable = true, length = 30, columnDefinition = "VARCHAR ( 30 )    COMMENT '配置类型'")
    private String configType;
    /**
     * 配置名称
     */
    @Basic
    @Column(name = "config_name", nullable = true, length = 30, columnDefinition = "VARCHAR ( 30 )   COMMENT '配置名称'")
    private String configName;
    /**
     * 值
     */
    @Basic
    @Column(name = "config_value", nullable = true, length = 100, columnDefinition = "VARCHAR ( 100 )   COMMENT '配置值'")
    private String configValue;
    /**
     * 配置描述
     */
    @Basic
    @Column(name = "config_description", nullable = true, length = 255, columnDefinition = "VARCHAR ( 255 )   COMMENT '配置描述'")
    private String configDescription;
    /**
     * 条目创建人
     */
    @Basic
    @Column(name = "user_id_create", nullable = true, columnDefinition = "INTEGER   COMMENT '条目创建人'")
    private Integer userIdCreate;
    /**
     * 修改日期
     */
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
     * 是否启用（1启用，0禁用）
     */
    @Basic
    @Column(name = "enabled", nullable = true, columnDefinition = "INTEGER  DEFAULT 1  COMMENT '是否启用（1启用，0禁用）'")
    private Integer enabled;


    @Override
    protected Serializable pkVal() {
        return sysConfigId;
    }
}
