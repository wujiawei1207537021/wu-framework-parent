package com.wu.framework.inner.lazy.stereotype;

import java.lang.annotation.*;

/**
 * 实体扫描
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/2 6:18 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LazyScan {


    /**
     * describe 扫描包路径
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 6:19 下午
     **/
    String[] scanBasePackages() default {};

    /**
     * describe  扫描基础包类
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 6:19 下午
     **/
    Class<?>[] scanBasePackageClasses() default {};
}