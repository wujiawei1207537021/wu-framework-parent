package com.wuframework.system.common.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
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
@ApiModel("部门&市场VO")
@TableName("sys_dept")
public class DeptVO {
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 部门上级ID
     */
    private Long parentId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 负责人ID
     */
    private Long managerUserId;
    /**
     * 负责人姓名
     */
    private String managerUserName;

    /**
     * 负责人电话
     */
    private String managerTelephone;
    /**
     * 上级部门名称
     */
    private String superiorName;
    /**
     * 负责人手机号
     */
    private String mobile;
    /**
     * 单位类型
     */
    private Integer deptUnitType;
    /**
     * 单位类型名称
     */
    private String deptUnitTypeName;
    /**
     * 部门类型
     */
    private Integer deptType;
    /**
     * 部门类型名称
     */
    private String deptTypeName;

    /**
     * 摊位数量
     */
    private Integer stallCount;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 部门备注
     */
    private String deptRemark;

    /**
     * 统一社会信用代码
     */
    private String creditCode;
    /**
     * 营业执照编号
     */
    private String deptPermitId;
    /**
     * 市场类型
     */
    private String deptClassify;
    /**
     * 市场类型名称
     */
    private String deptClassifyName;

    /**
     * 市场规模
     */
    private String marketSize;

    /**
     * 市场布局图片地址
     */
    private String layoutPath;
}
