package com.wu.framework.easy.excel.stereotype;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.lang.annotation.*;

/**
 * description 导出字段注解
 *
 * @author Jia wei Wu
 * @date 2020/10/5 下午7:08
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
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
     * 列宽
     *
     * @return
     */
    int width() default 30;

    /**
     * 设置自动换行
     *
     * @return
     */
    boolean wrapText() default false;

    /**
     * 背景填充
     */
    FillPatternType fillPatternType() default FillPatternType.SOLID_FOREGROUND;

    /**
     * title背景颜色
     * {@link HSSFColor.HSSFColorPredefined#index}
     *
     * @return
     */
    short titleBackgroundColor() default 0x30;

    /**
     * 标题字体颜色
     *
     * @return
     */
    short titleFontColor() default 16;

    /**
     * 标题字体
     *
     * @return
     */
    String titleFontName() default "黑体";

    /**
     * 标题字体大小
     *
     * @return
     */
    short titleFontSize() default 12;

    /**
     * 列字体颜色
     *
     * @return
     */
    short columnFontColor() default 16;

    /**
     * 列字体
     *
     * @return
     */
    String columnFontName() default "黑体";

    /**
     * 列字体大小
     *
     * @return
     */
    short columnFontSize() default 10;

    /**
     * 列背景
     *
     * @return
     */
    short columnBackgroundColor() default 0x30;
//    /**
//     * 列背景表达式
//     */
//    String columnBGExpression() default "";

    /**
     * 下拉框选项
     *
     * @return
     */
    String[] dropdownOptions() default {};

    /**
     * 字段合并方式
     *
     * @return
     */
    EasyExcelFieldMerge fieldMerge() default EasyExcelFieldMerge.NONE;

    /**
     * 序号 数值越大越靠前
     */
    int serialNumber() default 0;

    /**
     * 字段合并 枚举
     */
    enum EasyExcelFieldMerge {
        //水平方向
        LEVEL,
        // 垂直方向
        VERTICAL,
        // 空的
        NONE
    }
}
