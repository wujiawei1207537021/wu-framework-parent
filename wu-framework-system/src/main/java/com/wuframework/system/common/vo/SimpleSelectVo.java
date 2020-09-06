package com.wuframework.system.common.vo;

import lombok.Data;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @description: 简单的下拉框对象
 * @author: maohh
 * @create: 2018-07-24 01:14
 **/
@Data
public class SimpleSelectVo {
    /**
     * 是否禁用
     */
    private Boolean disabled;
    /**
     * 和 value 含义一致。如果 React 需要你设置此项，此项值与 value 的值相同，然后可以省略 value 设置
     */
    private String key;
    /**
     * 和 value 含义一致。如果 React 需要你设置此项，此项值与 value 的值相同，然后可以省略 value 设置
     */
    private String rowKey;
    /**
     * 选中该 Option 后，Select 的 title
     */
    private String title;
    /**
     * 默认根据此属性值进行筛选
     */
    private String value;
}