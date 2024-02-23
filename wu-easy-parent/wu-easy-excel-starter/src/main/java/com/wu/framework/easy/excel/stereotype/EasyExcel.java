package com.wu.framework.easy.excel.stereotype;

import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.service.style.DefaultStyle;
import com.wu.framework.easy.excel.service.style.Style;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelContextHolder;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelPointContextHolder;
import com.wu.framework.easy.excel.util.ISheetShowContextMethod;
import com.wu.framework.easy.excel.util.SheetNumContextMethod;
import com.wu.framework.easy.excel.util.SheetTextContextMethod;
import com.wu.framework.easy.excel.web.ExcelResponseHandler;
import com.wu.framework.inner.layer.toolkit.DynamicLazyAttributeContextHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.*;

/**
 * description 导出注解
 *
 * @author Jia wei Wu
 * @date 2020/10/5 下午7:08
 *
 *
 * <p>
 * @EasyExcel(fileName = "导出数据")
 * @GetMapping("/run/{size}") public List<UserLog> run(@PathVariable Integer size) {
 * return runService.run(size);
 * }
 * </p>
 * @see DynamicEasyExcelPointContextHolder#push(EasyExcelPoint)
 * @see EasyExcelPoint
 * @see class : EasyExcel object:EasyExcelPoint
 * @see ExcelResponseHandler
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyExcel {

    /**
     * 文件名
     *
     * @return String
     * <p>
     * to dynamic fileName
     * Class:EasyExcel  Object:fileName
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
     * 删除复杂导出 使用字段合并功能
     *
     * @return boolean
     */
    @Deprecated
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
    boolean titleFixedHead() default false;

    @Getter
    @AllArgsConstructor
    enum SheetShowContext {
        /**
         * 数字 1000~2000
         */
        NUM(SheetNumContextMethod.class),
        /**
         * 数字中文 一  二 三
         */
        TEXT(SheetTextContextMethod.class);
        private Class<? extends ISheetShowContextMethod> iSheetShowContextMethod;
    }


}
