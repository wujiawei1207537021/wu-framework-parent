package com.wu.framework.easy.excel.stereotype;

import com.wu.framework.easy.excel.service.style.DefaultStyle;
import com.wu.framework.easy.excel.service.style.Style;
import com.wu.framework.easy.excel.util.ISheetShowContextMethod;
import com.wu.framework.easy.excel.util.SheetNumContextMethod;
import com.wu.framework.easy.excel.util.SheetTextContextMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.*;

/**
 * description 导出注解
 *
 * @author Jia wei Wu
 * @date 2020/10/5 下午7:08
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyExcel {

    /**
     * 文件名
     *
     * @return String
     */
    String fileName() default "temp";

    /**
     * 工作簿名字
     *
     * @return
     */
    String sheetName() default "sheet";

    /**
     * 文件后缀
     *
     * @return String
     */
    String suffix() default "xls";

    /**
     * 默认 false
     * 是否复杂导出
     *
     * @return boolean
     */
    boolean isComplicated() default false;

    /**
     * 默认 true 通过参数中注解获取表头字段
     * 是否使用注解方式获取表头
     *
     * @return boolean
     */
    boolean useAnnotation() default true;

    /**
     * useAnnotation true 有效
     * 字段列名注解
     *
     * @return Class
     */
    Class<? extends Annotation> fieldColumnAnnotation() default EasyExcelFiled.class;

    /**
     * useAnnotation true 有效
     * 字段列名注解属性名
     *
     * @return String
     */
    String fieldColumnAnnotationAttribute() default "name";

    /**
     * 多个 sheet
     *
     * @return boolean
     */
    boolean multipleSheet() default false;

    /**
     * multipleSheet true 有效
     * 工作簿每页限制长度
     *
     * @return int
     */
    int limit() default 65534;

    /**
     * multipleSheet true 有效
     * 工作簿名字内容
     */
    SheetShowContext sheetShowContext() default SheetShowContext.NUM;

    /**
     * 样式添加类
     *
     * @return
     */
    Class<? extends Style> style() default DefaultStyle.class;

    /**
     * 表头固定
     */
    boolean titleFixedHead() default true;

    @Getter
    @AllArgsConstructor
    enum SheetShowContext {
        NUM(SheetNumContextMethod.class),// 1000~2000
        TEXT(SheetTextContextMethod.class);// 一  二 三
        private Class<? extends ISheetShowContextMethod> iSheetShowContextMethod;
    }


}
