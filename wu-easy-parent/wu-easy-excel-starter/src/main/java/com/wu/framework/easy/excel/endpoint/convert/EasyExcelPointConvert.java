package com.wu.framework.easy.excel.endpoint.convert;

import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.service.style.Style;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.inner.layer.stereotype.converter.LayerAnnotationConverterAdapter;

import java.lang.annotation.Annotation;

/**
 * describe : 将注解转换成端点信息
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/2 19:03
 */
public class EasyExcelPointConvert implements LayerAnnotationConverterAdapter<EasyExcel, EasyExcelPoint> {


    /**
     * 是否支持
     *
     * @param annotation     注解
     * @param easyExcelPoint 断点
     * @return
     */
    @Override
    public boolean supports(EasyExcel annotation, EasyExcelPoint easyExcelPoint) {
        return true;
    }

    /**
     * @param annotation 原始注解
     * @return 返回断点信息
     */
    @Override
    public EasyExcelPoint converter(EasyExcel annotation) {
        String fileName = annotation.fileName();
        Class<? extends Annotation> fieldColumnAnnotation = annotation.fieldColumnAnnotation();
        String fieldColumnAnnotationAttribute = annotation.fieldColumnAnnotationAttribute();
        int limit = annotation.limit();
        boolean multipleSheet = annotation.multipleSheet();
        String sheetName = annotation.sheetName();
        EasyExcel.SheetShowContext sheetShowContext = annotation.sheetShowContext();
        Class<? extends Style> style = annotation.style();
        String suffix = annotation.suffix();
        boolean titleFixedHead = annotation.titleFixedHead();
        boolean useAnnotation = annotation.useAnnotation();
        EasyExcelPoint easyExcelPoint = new EasyExcelPoint();
        easyExcelPoint.setFileName(fileName);
        easyExcelPoint.setFieldColumnAnnotation(fieldColumnAnnotation);
        easyExcelPoint.setFieldColumnAnnotationAttribute(fieldColumnAnnotationAttribute);
        easyExcelPoint.setLimit(limit);
        easyExcelPoint.setMultipleSheet(multipleSheet);
        easyExcelPoint.setSheetName(sheetName);
        easyExcelPoint.setSheetShowContext(sheetShowContext);
        easyExcelPoint.setStyle(style);
        easyExcelPoint.setSuffix(suffix);
        easyExcelPoint.setTitleFixedHead(titleFixedHead);
        easyExcelPoint.setUseAnnotation(useAnnotation);

        return easyExcelPoint;
    }
}
