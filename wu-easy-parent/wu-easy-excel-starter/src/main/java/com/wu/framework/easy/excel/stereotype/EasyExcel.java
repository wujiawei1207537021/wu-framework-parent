package com.wu.framework.easy.excel.stereotype;

import java.lang.annotation.*;

/**
 * description 导出注解
 * @author 吴佳伟
 * @date 2020/10/5 下午7:08
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyExcel {

    /**
     * 文件名
     * @return
     */
    String fileName();

    /**
     * 文件后缀
     * @return
     */
    String suffix() default "xls";

    /**
     * 字段列名注解
     * @return
     */
    Class<? extends Annotation> filedColumnAnnotation() default EasyExcelFiled.class;

    /**
     * 字段列名注解属性名
     * @return
     */
   String filedColumnAnnotationAttribute() default "name";

    /**
     * 多个 sheet
     * @return
     */
   boolean multipleSheet() default false;


}
