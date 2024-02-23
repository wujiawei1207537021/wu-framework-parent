package com.wuframework.system.common.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author Xiongxz
 * @since 2018-08-17
 */
@Data
@ApiModel("用户VO")
public class UserVO {
    private Long userId;
    private String username;
    private String name;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 头像
     */
    private String photo;
    private Long deptId;
    private String deptName;
    private String email;
    private String mobile;
    private Integer status;
    private String statusName;
    private Long userClassify;
    private String userClassifyName;

    /**
     * 账户绑定的从业人员的名称
     */
    private String employeeName;

    /**
     * 账户绑定的从业人员的职位名称
     */
    private String jobName;
    /**
     * 创建时间
     */
    private LocalDate gmtCreate;

    /**
     * 用户签名
     */
    private String signature;

    /**
     * 用于公众号绑定当前用户所有角色
     */
    private String binding;

    /**
     * 用户的角色，可能包含有多个
     */
    private String roleNames;
}
