package com.wuframework.system.common.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author Xiongxz
 * @since 2018-08-02
 */
@Data
@ApiModel("部门&市场DTO")
@TableName("sys_dept")
public class DeptDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    @ApiModelProperty(value = "部门&市场ID")
    private Long deptId;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门&市场ID,字符串", hidden = true)
    private String deptIds;

    /**
     * 上级部门ID，一级部门为0
     */
    @ApiModelProperty(value = "部门&市场上级ID")
    private Long parentId;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String managerUserId;

    /**
     * 负责人姓名
     */
    @ApiModelProperty(value = "负责人姓名（模糊）")
    private String managerUserName;

    /**
     * 负责人联系方式
     */
    @ApiModelProperty(value = "负责人联系方式", hidden = true)
    private String managerTelephone;

    /**
     * 所属辖区
     */
    @ApiModelProperty(value = "所属辖区", hidden = true)
    private String jurisdiction;

    /**
     * 单位类型(0监管单位，1被监管单位)
     */
    @ApiModelProperty(value = "单位类型(0监管单位，1被监管单位)")
    private Integer deptUnitType;

    /**
     * 部门类型
     */
    @ApiModelProperty(value = "部门类型（市局，区局···）")
    private Integer deptType;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门&市场名称")
    private String deptName;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度", hidden = true)
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度", hidden = true)
    private String latitude;

    /**
     * 单位地址
     */
    @ApiModelProperty(value = "单位地址", hidden = true)
    private String address;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", hidden = true)
    private Integer orderNum;

    /**
     * 是否启用（1启用，0禁用）
     */
    @ApiModelProperty(value = "是否启用（1启用，0禁用）", hidden = true)
    private Integer enabled;

    /**
     * 部门备注
     */
    @ApiModelProperty(value = "部门&市场备注", hidden = true)
    private String deptRemark;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty(value = "部门&市场备注")
    private String creditCode;

    /**
     * 经营许可证
     */
    @ApiModelProperty(value = "经营许可证-市场需要")
    private Long deptPermitId;

    /**
     * 所在地区，关联area表
     */
    @ApiModelProperty(value = "所在地区，关联area表-暂时不用")
    private String deptZoneId;

    /**
     * 部门分类：农贸市场、农批市场、超市、其他部门分类
     */
    @ApiModelProperty(value = "部门分类：农贸市场、农批市场、超市、其他部门分类-市场需要")
    private String deptClassify;

    @ApiModelProperty(value = "是否需要分页，true需要，false不需要")
    private Boolean needPage;

}
