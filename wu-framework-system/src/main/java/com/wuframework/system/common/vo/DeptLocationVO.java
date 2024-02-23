package com.wuframework.system.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 部门市场地理位置坐标数据展现对象
 * @author: Zeng Ao
 * @create: 2018-08-13 14:07
 **/
@Data
public class DeptLocationVO {

    private Long deptId;
    private String deptName;
    private String deptZoneId;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String address;
    private Integer positive;

}
