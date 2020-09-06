package com.wuframework.system.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author Xiongxz
 * @since 2019-02-14
 */
@Data
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @Id
    @TableId(value = "dept_id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    /**
     * 上级部门ID，一级部门为0
     */
    private Long parentId;

    /**
     * 负责人
     */
    private String managerUserId;

    /**
     * 部门负责人姓名
     */
    private String managerUserName;

    /**
     * 负责人联系方式
     */
    private String managerTelephone;

    /**
     * 所属辖区
     */
    private String jurisdiction;

    /**
     * 单位类型(0监管单位，1被监管单位)
     */
    private Integer deptUnitType;

    /**
     * 部门类型（字典）
     */
    private Integer deptType;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 单位地址
     */
    private String address;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 是否启用（1启用，0禁用）
     */
    private Integer enabled;

    /**
     * 部门备注
     */
    private String deptRemark;

    /**
     * 所在地区，关联area表
     */
    private String deptZoneId;

    /**
     * 部门分类
     */
    private String deptClassify;

    /**
     * 市场布局图地址
     */
    private String layoutPath;

    /**
     * 企业类型
     */
    @Transient
    @TableField(exist = false)
    private String companyType;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
