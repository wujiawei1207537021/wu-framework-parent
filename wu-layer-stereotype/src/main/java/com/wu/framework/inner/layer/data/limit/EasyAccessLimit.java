package com.wu.framework.inner.layer.data.limit;

import java.lang.annotation.*;

/**
 * description 接口访问限制
 * <p>
 *
 * @EasyAccessLimit(seconds = 1000, maxCount = 2, key = "#id")
 * @GetMapping("/s") public void ss(String id) {
 * System.out.println("接口只能访问两次");
 * }
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EasyAccessLimit {
    /**
     * @return 验证周期
     */
    int seconds();

    /**
     * 最大访问次数
     *
     * @return
     */
    int maxCount();

    /**
     * 限制模式
     * 默认所有状态限制
     *
     * @return
     */
    AccessLimitModel accessLimitModel() default AccessLimitModel.ALL;

    /**
     * 统计次数的key 可从方法中参数获取
     * 如果为空则所有请求都限制
     *
     * <p>
     * #root.args[0] 请求方法的第一个参数
     * #user.id   请求方法中含有参数对象User中含有属性id
     * #id 请求方法中含有参数id
     * </p>
     *
     * @return
     */
    String key() default "";

    /**
     * 限制信息
     *
     * @return
     */
    String msg() default "接口访问被限制";

    /**
     * 限制模式
     */
    enum AccessLimitModel {
        SUCCESS,
        FAIL,
        ALL

    }
}
