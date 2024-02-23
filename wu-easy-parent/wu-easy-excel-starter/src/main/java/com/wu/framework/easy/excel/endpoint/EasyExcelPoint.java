package com.wu.framework.easy.excel.endpoint;

import com.wu.framework.easy.excel.service.style.DefaultStyle;
import com.wu.framework.easy.excel.service.style.Style;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.inner.layer.toolkit.DynamicLazyAttributeContextHolder;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * describe : EasyExcel 注解对应的断点数据
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/2 18:52
 */
@Data
public class EasyExcelPoint {
    /**
     * 表头信息
     */
    List<EasyExcelFiledPoint> excelFiledPointList;
    /**
     * 文件名
     *
     * @return String
     * <p>
     * to dynamic fileName
     * Class:EasyExcel  Object:fileName
     * @see DynamicLazyAttributeContextHolder#push(Class, Object)
     * @see EasyExcel#fileName()
     */
    private String fileName = "temp";
    /**
     * 工作簿名字
     *
     * @return
     * @see EasyExcel#sheetName()
     */
    private String sheetName = "sheet";
    /**
     * 文件后缀
     *
     * @return String
     * @see EasyExcel#suffix()
     */
    private String suffix = "xls";
    /**
     * 默认 true 通过参数中注解获取表头字段
     * 是否使用注解方式获取表头
     *
     * @return boolean
     * @see EasyExcel#useAnnotation()
     */
    private boolean useAnnotation = true;
    /**
     * useAnnotation true 有效
     * 字段列名注解
     *
     * @return Class
     * @see EasyExcel#fieldColumnAnnotation()
     */
    private Class<? extends Annotation> fieldColumnAnnotation = EasyExcelFiled.class;
    /**
     * useAnnotation true 有效
     * 字段列名注解属性名
     *
     * @return String
     * @see EasyExcel#fieldColumnAnnotationAttribute()
     */
    private String fieldColumnAnnotationAttribute = "name";
    /**
     * 多个 sheet
     *
     * @return boolean
     * @see EasyExcel#multipleSheet()
     */
    private boolean multipleSheet = false;
    /**
     * multipleSheet true 有效
     * 工作簿每页限制长度
     *
     * @return int
     * @see EasyExcel#limit()
     */
    private int limit = 65534;
    /**
     * multipleSheet true 有效
     * 工作簿名字内容
     *
     * @see EasyExcel#sheetShowContext()
     */
    private EasyExcel.SheetShowContext sheetShowContext = EasyExcel.SheetShowContext.NUM;
    /**
     * 样式添加类
     *
     * @return
     * @see EasyExcel#style()
     */
    private Class<? extends Style> style = DefaultStyle.class;
    /**
     * 表头固定
     *
     * @see EasyExcel#titleFixedHead()
     */
    private boolean titleFixedHead = false;
    /**
     * 导出数据类型
     */
    private ExportTypeEnum exportTypeEnum;


    /**
     * 导出数据类型
     */
    public enum ExportTypeEnum {
        MAP,
        JAVA_BEAN;
    }

}


