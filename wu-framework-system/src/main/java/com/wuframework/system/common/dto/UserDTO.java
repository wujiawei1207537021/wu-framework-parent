package com.wuframework.system.common.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author Xiongxz
 * @since 2018-08-17
 */
@Data
@TableName("sys_user")
@ApiModel("用户DTO")
public class UserDTO {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", hidden = true)
    private String password;

    /**
     * 关联部门id
     */
    @ApiModelProperty(value = "关联部门id")
    private Integer deptId;

    /**
     * 部门ID字符串
     */
    private String deptIds;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", hidden = true)
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", hidden = true)
    private String mobile;

    /**
     * 用户分类，1监管2检测3销售
     */
    @ApiModelProperty(value = "用户分类，1监管2检测3销售")
    private String userClassify;

    @ApiModelProperty(value = "是否查询全部，null或者true查询全部，false反之")
    private Boolean isAll;

    /**
     * 状态 0:禁用，1:正常
     */
    @ApiModelProperty(value = "状态 0:禁用，1:正常")
    private Integer status;

    /**
     * 创建用户id
     */
    @ApiModelProperty(value = "创建用户id", hidden = true)
    private Long userIdCreate;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date gmtModified;

    /**
     * 岗位
     */
    @ApiModelProperty(value = "岗位")
    private String job;

    private String startDate;

    private String endDate;

    /**
     * 用户签名文件
     */
    private MultipartFile file;

    /**
     * 签名宽度
     */
    private Integer width;

    /**
     * 签名高度
     */
    private Integer height;

    /**
     * 用户的角色类型
     */
    private String roleIds;

    /**
     * 用户的角色id
     */
    private List<String> roleIdList;
}
