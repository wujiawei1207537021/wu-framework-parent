package com.wu.framework.easy.excel.stereotype;


import org.apache.poi.ss.usermodel.FillPatternType;

import java.lang.annotation.*;

/**
 * description 导出字段注解
 *
 * @author Jia wei Wu
 * @date 2020/10/5 下午7:08
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyExcelFiled {

    /**
     * 列名
     *
     * @return String
     */
    String name();

    /**
     * 列颜色
     * @return
     */
    String color() default "";

    /**
     * The enumeration value indicating the style of fill pattern being used for a cell format.
     */
    FillPatternType fillPatternType() default FillPatternType.NO_FILL ;
}
