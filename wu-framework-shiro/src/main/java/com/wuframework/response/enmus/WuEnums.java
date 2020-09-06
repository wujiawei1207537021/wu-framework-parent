package com.wuframework.response.enmus;

/**
 * @ Description   :  wu枚举接口
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/3/20 0020 15:16
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/3/20 0020 15:16
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 * TODO 抽象类
 */


public interface WuEnums {
    /**
     * 枚举名称
     *
     * @return
     */
    default String getName() {
        return this.getDesc();
    }


    /**
     * 枚举值
     *
     * @return
     */
    default Object getValue() {
        return this.getCode();
    }

    default Integer getEnable() {
        return DefaultMySQLEnum.TRUE.getCode();
    }

    /**
     * 基础类枚举常用字段
     * @return
     */
    String getDesc();

    Object getCode();




}
