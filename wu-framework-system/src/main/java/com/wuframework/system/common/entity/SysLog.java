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
 * CREATE TABLE `sys_log`  (
 * `id` bigint(20) NOT NULL AUTO_INCREMENT,
 * `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
 * `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
 * `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
 * `time` int(11) NULL DEFAULT NULL COMMENT '响应时间',
 * `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
 * `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
 * `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
 * `gmt_create` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
 * PRIMARY KEY (`id`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;
 */

@Data
@Entity
@Table(name = "sys_log")
@TableName("sys_log")
public class SysLog extends Model<SysLog> {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "INTEGER   COMMENT 'id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @Basic
    @Column(name = "user_id", nullable = true, columnDefinition = "INTEGER COMMENT '用户id'")
    private Long userId;
    @Basic
    @Column(name = "username", nullable = true, length = 50, columnDefinition = "VARCHAR ( 50 )  COMMENT '用户名'")
    private String username;
    @Basic
    @Column(name = "operation", nullable = true, length = 50, columnDefinition = "VARCHAR ( 50 )  COMMENT '用户操作'")
    private String operation;
    @Basic
    @Column(name = "time", nullable = true, columnDefinition = "INTEGER  COMMENT '响应时间'")
    private Integer time;
    @Basic
    @Column(name = "method", nullable = true, length = 200, columnDefinition = "VARCHAR ( 200 )  COMMENT '请求方法'")
    private String method;
    @Basic
    @Column(name = "params", nullable = true, length = 5000, columnDefinition = "VARCHAR ( 5000 )  COMMENT '请求参数'")
    private String params;
    @Basic
    @Column(name = "ip", nullable = true, length = 64, columnDefinition = "VARCHAR ( 64 )  COMMENT 'IP地址'")
    private String ip;

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
