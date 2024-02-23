package com.wu.framework.database.lazy.web.plus;

import lombok.Data;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/10/11 7:20 下午
 */
@Data
public class User {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 车牌号码
     */
    private String licensePlate;

    /**
     * 车轴类型
     */
    private String axleType;

    /**
     * 国标
     */
    private String countryStandard;
}
