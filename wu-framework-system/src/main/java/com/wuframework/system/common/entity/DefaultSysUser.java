package com.wuframework.system.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.wuframework.response.mark.JsonViewType;
import com.wuframework.shiro.model.UserDetails;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * CREATE TABLE `sys_user`  (
 *   `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
 *   `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
 *   `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
 *   `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
 *   `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
 *   `sex` int(1) NULL DEFAULT 1 COMMENT '0女性，1男性',
 *   `dept_id` int(20) NULL DEFAULT NULL COMMENT '所属部门',
 *   `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
 *   `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
 *   `telephone` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座机号码',
 *   `user_classify` bigint(64) NULL DEFAULT NULL COMMENT '用户分类，1监管2检测3销售4经营户',
 *   `status` tinyint(255) NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
 *   `user_id_create` bigint(255) NULL DEFAULT NULL COMMENT '创建用户id',
 *   `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
 *   `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
 *   `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
 *   `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openId',
 *   `manufacturer_id` int(11) NULL DEFAULT NULL COMMENT '用户所属厂商id',
 *   PRIMARY KEY (`user_id`) USING BTREE,
 *   UNIQUE INDEX `mobile`(`mobile`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 205 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;
 */
@Data
@Entity
@Table(name = "sys_user")
@TableName("sys_user")
@JsonView(JsonViewType.Default.class)
public class DefaultSysUser extends Model<DefaultSysUser> implements UserDetails, Serializable {
    @Id
    @Column(name = "user_id", nullable = false, columnDefinition = "INTEGER   COMMENT '用户ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @Basic
    @Column(name = "username", nullable = true, length = 50, columnDefinition = "VARCHAR ( 50 )  COMMENT '用户名'")
    private String username;

    @Basic
    @Column(name = "name", nullable = true, length = 100, columnDefinition = "VARCHAR ( 100 )  COMMENT '姓名'")
    private String name;

    @Basic
    @Column(name = "password", nullable = true, length = 100, columnDefinition = "VARCHAR ( 100 )  COMMENT '密码'")
    private String password;

    @Basic
    @JsonInclude()
    @Column(name = "id_card", nullable = true, length = 20,columnDefinition = "VARCHAR ( 50 )  COMMENT '身份证号'")
    private String idCard;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Basic
    @Column(name = "sex", nullable = true,columnDefinition = "Integer  COMMENT '性别'")
    private Integer sex;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Basic
    @Column(name = "dept_id", nullable = true,columnDefinition = "Integer  COMMENT '部门ID'")
    private Integer deptId;

    @Basic
    @Column(name = "email", nullable = true, length = 100,columnDefinition = "VARCHAR ( 100 )  COMMENT '邮箱'")
    private String email;

    @Basic
    @Column(name = "mobile", nullable = true, length = 100,columnDefinition = "VARCHAR ( 100 )  COMMENT '手机'")
    private String mobile;

    @Basic
    @Column(name = "telephone", nullable = true, length = 13,columnDefinition = "VARCHAR ( 13 )  COMMENT '座机号'")
    private String telephone;

    @Basic
    @Column(name = "user_classify", nullable = true,columnDefinition = "bigint ( 64 )  COMMENT '用户分类，1监管2检测3销售4经营户'")
    private Integer userClassify;

    @Basic
    @Column(name = "status", nullable = true,columnDefinition = "tinyint(255) NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常'")
    private Integer status;

    @Basic
    @Column(name = "user_id_create", nullable = true,columnDefinition = "bigint   COMMENT '创建用户id'")
    private Integer userIdCreate;

    @Basic
    @Column(name = "photo", nullable = true, length = 255,columnDefinition = "VARCHAR ( 255 )  COMMENT '用户头像'")
    private String photo;

    @Basic
    @Column(name = "open_id", nullable = true, length = 255,columnDefinition = "VARCHAR ( 255 )  COMMENT '微信openId'")
    private String openId;

    @UpdateTimestamp
    @Basic
    @Column(name = "gmt_modified", nullable = true,columnDefinition = "datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '修改时间'")
    private LocalDateTime gmtModified;
    /**
     * 记录创建日期
     */
    @CreationTimestamp
    @Basic
    @Column(name = "gmt_create", nullable = true,columnDefinition = "datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private LocalDateTime gmtCreate;


    @Basic
    @Column(name = "dingtalk_open_id",unique = true,columnDefinition = "VARCHAR ( 255 )  COMMENT '钉钉登录OpenID'")
    private String dingtalkOpenId;

    @Basic
    @Column(name = "we_chat_open_id",unique = true, columnDefinition = "VARCHAR ( 255 )  COMMENT '微信登录OpenID'")
    private String weChatOpenId;

    @Basic
    @Column(name = "qq_open_id",unique = true, columnDefinition = "VARCHAR ( 255 )  COMMENT 'QQ登录OpenID'")
    private String qqOpenId;

    @Basic
    @Column(name = "alipay_open_id", unique = true,columnDefinition = "VARCHAR ( 255 )  COMMENT '支付宝登录OpenID（user_id）'")
    private String alipayOpenId;



    //    @Transient
//    @TableField(exist = false)
//    @ApiModelProperty(value = "用户所属厂商id")
//    public Integer manufacturerId;

    /**
     * 用户签名
     */
    @Transient
    @TableField(exist = false)
    private String signature;

    /**
     * 用户角色
     * 弃用原因含有多余字段
     */
    @Deprecated
    @Transient
    @TableField(exist = false)
    private List<SysRole> roles;

    /**
     * 用户当前角色包含的字段
     */
    @Transient
    @TableField(exist = false)
    private List<SysMenu> menus;
    /**
     * 用户权限
     */
    @Transient
    @TableField(exist = false)
    private List<SysPermission> permissionList;

    /**
     * 用户角色标示
     */
    @Transient
    @TableField(exist = false)
    private List<String> roleSignList;

    /**
     * 关联ID
     */
    @Transient
    @TableField(exist = false)
    private Integer associationId;


    @Override
    protected Serializable pkVal() {
        return userId;
    }
}
