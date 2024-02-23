package com.wu.framework.inner.log.annotation;



import com.wu.framework.inner.log.enums.DynamicExecutEnum;

import java.lang.annotation.*;

/**
 * @author :  wujiawei
 * @ Description   :  动态注解
 * @ CreateDate    :  2020/4/2 0002 9:51
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/4/2 0002 9:51
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 * {@link DynamicAnnotationAOP}
 */


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Inherited
public @interface DynamicAnnotation {

    /**
     * 描述信息
     */
    String description() default "";

    /**
     * 执行模式
     *
     * @return
     */
    DynamicExecutEnum.ExecutionMode executionMode() default DynamicExecutEnum.ExecutionMode.ALWAYS;

    /**
     * 执行时机
     *
     * @return
     */
    DynamicExecutEnum.ExecutionTiming executionTiming() default DynamicExecutEnum.ExecutionTiming.AFTER;

    /**
     * 获取参数方式 出参 入参
     */
    DynamicExecutEnum.ParamMode getParamMode() default DynamicExecutEnum.ParamMode.MODE_IN;


    /**
     * 指定执行类
     */
    Class<? extends ExecuteDynamic> executeClass() default ExecuteDynamicDefault.class;


}
