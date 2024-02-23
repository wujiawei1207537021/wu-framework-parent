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
@Repeatable(LazyScans.class)
public @interface LazyScan {


    /**
     * describe 扫描包路径
     * <p>
     * 支持通配符 如 "com.wu.framework.inner.lazy.example.**.entity"
     * </p>
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

    /**
     * describe 扫描class
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/8/10 20:51
     **/
    Class<?>[] scanClass() default {};
}