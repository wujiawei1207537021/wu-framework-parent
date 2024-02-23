package com.wu.framework.easy.excel.endpoint.convert;

import com.wu.framework.easy.excel.endpoint.EasyExcelFiledPoint;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.inner.layer.stereotype.converter.LayerAnnotationConverterAdapter;

/**
 * describe : 将注解转换成端点信息
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/2 19:03
 */
public class EasyExcelFiledPointConvert implements LayerAnnotationConverterAdapter<EasyExcelFiled, EasyExcelFiledPoint> {


    /**
     * 是否支持
     *
     * @param annotation          注解
     * @param easyExcelFiledPoint 断点
     * @return
     */
    @Override
    public boolean supports(EasyExcelFiled annotation, EasyExcelFiledPoint easyExcelFiledPoint) {
        return true;
    }

    /**
     * @param annotation 原始注解
     * @return 返回断点信息
     */
    @Override
    public EasyExcelFiledPoint converter(EasyExcelFiled annotation) {

        EasyExcelFiledPoint easyExcelFiledPoint = new EasyExcelFiledPoint();
        easyExcelFiledPoint.setName(annotation.name());
        easyExcelFiledPoint.setWidth(easyExcelFiledPoint.getWidth());
        easyExcelFiledPoint.setWrapText(annotation.wrapText());
        easyExcelFiledPoint.setFillPatternType(annotation.fillPatternType());
        easyExcelFiledPoint.setTitleBackgroundColor(annotation.titleBackgroundColor());
        easyExcelFiledPoint.setTitleFontColor(annotation.titleFontColor());
        easyExcelFiledPoint.setTitleFontName(annotation.titleFontName());
        easyExcelFiledPoint.setTitleFontSize(annotation.titleFontSize());
        easyExcelFiledPoint.setColumnFontColor(annotation.columnFontColor());
        easyExcelFiledPoint.setColumnFontName(annotation.columnFontName());
        easyExcelFiledPoint.setColumnFontSize(annotation.columnFontSize());
        easyExcelFiledPoint.setColumnBackgroundColor(annotation.columnBackgroundColor());
        easyExcelFiledPoint.setDropdownOptions(annotation.dropdownOptions());
        easyExcelFiledPoint.setFieldMerge(annotation.fieldMerge());
        easyExcelFiledPoint.setSerialNumber(annotation.serialNumber());
        return easyExcelFiledPoint;
    }


}
