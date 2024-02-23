package com.wuframework.system.common.vo;

import lombok.Data;

/**
 * @description:
 * @author: Zeng Ao
 * @create: 2018-07-23 15:48
 **/
@Data
public class DeptRankVO {

    /**
     * 市场id
     */
    private Long deptId;

    /**
     * 市场名称
     */
    private String deptName;

    /**
     * 评分
     */
    private Double avgScore;
}
