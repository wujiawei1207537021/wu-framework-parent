package com.wu.framework.easy.excel.service.style;

import com.wu.framework.easy.excel.endpoint.EasyExcelFiledPoint;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import org.apache.poi.ss.usermodel.*;

import java.lang.annotation.Annotation;


/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/12/9 9:07 下午
 */
public class DefaultStyle implements Style {

    /**
     * 标题样式
     *
     * @param styleParam
     * @return
     */
    @Deprecated
    @Override
    public CellStyle titleStyle(StyleParam styleParam) {
        Annotation filedAnnotation = styleParam.getFiledAnnotation();
        if (!EasyExcelFiled.class.isAssignableFrom(filedAnnotation.getClass())) {
            return styleParam.getWorkbook().createCellStyle();
        }
        EasyExcelFiled easyExcelFiled = styleParam.getFiledAnnotation(EasyExcelFiled.class);
        final CellStyle style = styleParam.getWorkbook().createCellStyle();
//        设置背景色：   style.setFillBackgroundColor(IndexedColors.BLUE.index);
        style.setFillForegroundColor(easyExcelFiled.titleBackgroundColor());
        style.setFillPattern(easyExcelFiled.fillPatternType()); //填充样式
//       设置边框:
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setAlignment(HorizontalAlignment.CENTER); // 居中
        //        设置字体:
        Font font = styleParam.getWorkbook().createFont();
        font.setFontName(easyExcelFiled.titleFontName());
        font.setColor(easyExcelFiled.titleFontColor());
        font.setFontHeightInPoints(easyExcelFiled.titleFontSize());//设置字体大小
        style.setFont(font);//选择需要用到的字体格式
//        设置列宽:
        styleParam.getHssfSheet().setColumnWidth(styleParam.getColumnIndex(), easyExcelFiled.width() * 255); //第一个参数代表列id(从0开始),第2个参数代表宽度值
        style.setWrapText(easyExcelFiled.wrapText());//设置自动换行
        return style;
    }

    /**
     * 标题样式
     *
     * @param styleParam
     * @return
     */
    @Override
    public CellStyle titleStyle(DefaultStyleParam styleParam) {
        EasyExcelFiledPoint easyExcelFiledPoint = styleParam.getEasyExcelFiledPoint();
        if (easyExcelFiledPoint == null) {
            return styleParam.getWorkbook().createCellStyle();
        }
        short columnBackgroundColor = easyExcelFiledPoint.getColumnBackgroundColor();
        FillPatternType fillPatternType = easyExcelFiledPoint.getFillPatternType();
        String titleFontName = easyExcelFiledPoint.getTitleFontName();
        short titleFontColor = easyExcelFiledPoint.getTitleFontColor();
        short titleFontSize = easyExcelFiledPoint.getTitleFontSize();
        int width = easyExcelFiledPoint.getWidth();
        boolean wrapText = easyExcelFiledPoint.isWrapText();


        final CellStyle style = styleParam.getWorkbook().createCellStyle();
//        设置背景色：   style.setFillBackgroundColor(IndexedColors.BLUE.index);
        style.setFillForegroundColor(columnBackgroundColor);
        style.setFillPattern(fillPatternType); //填充样式
//       设置边框:
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setAlignment(HorizontalAlignment.CENTER); // 居中
        //        设置字体:
        Font font = styleParam.getWorkbook().createFont();
        font.setFontName(titleFontName);
        font.setColor(titleFontColor);
        font.setFontHeightInPoints(titleFontSize);//设置字体大小
        style.setFont(font);//选择需要用到的字体格式
//        设置列宽:
        styleParam.getSheet().setColumnWidth(easyExcelFiledPoint.getCurrentColumnIndex(), width * 255); //第一个参数代表列id(从0开始),第2个参数代表宽度值
        style.setWrapText(wrapText);//设置自动换行
        return style;
    }

    /**
     * 列样式
     *
     * @param styleParam
     * @return
     */
    @Override
    public CellStyle columnStyle(StyleParam styleParam) {
        Annotation filedAnnotation = styleParam.getFiledAnnotation();
        if (!EasyExcelFiled.class.isAssignableFrom(filedAnnotation.getClass())) {
            return styleParam.getWorkbook().createCellStyle();
        }
        EasyExcelFiled easyExcelFiled = styleParam.getFiledAnnotation(EasyExcelFiled.class);
        CellStyle style = styleParam.getWorkbook().createCellStyle();
//        设置背景色：
        style.setFillBackgroundColor(easyExcelFiled.columnBackgroundColor());// 设置背景色
//        style.setFillPattern(easyExcelFiled.fillPatternType()); //填充样式
//       设置边框:
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setAlignment(HorizontalAlignment.CENTER); // 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //        设置字体:
        Font font = styleParam.getWorkbook().createFont();
        font.setFontName(easyExcelFiled.columnFontName());
        font.setFontHeightInPoints(easyExcelFiled.columnFontSize());//设置字体大小
        font.setColor(easyExcelFiled.columnFontColor());
        style.setFont(font);//选择需要用到的字体格式
        style.setFont(font);//选择需要用到的字体格式
//        设置列宽:
        styleParam.getHssfSheet().setColumnWidth(styleParam.getColumnIndex(), easyExcelFiled.width() * 255); //第一个参数代表列id(从0开始),第2个参数代表宽度值
        style.setWrapText(easyExcelFiled.wrapText());//设置自动换行
        return style;
    }

    /**
     * 创建 列样式
     *
     * @param styleParam 列样式参数
     * @return
     */
    @Override
    public CellStyle columnStyle(DefaultStyleParam styleParam) {
        EasyExcelFiledPoint easyExcelFiledPoint = styleParam.getEasyExcelFiledPoint();
        EasyExcelFiled easyExcelFiled = easyExcelFiledPoint.getEasyExcelFiled();
        CellStyle style = styleParam.getWorkbook().createCellStyle();
        if (easyExcelFiled == null) {
            return style;
        }

//        设置背景色：
        style.setFillBackgroundColor(easyExcelFiled.columnBackgroundColor());// 设置背景色
//        style.setFillPattern(easyExcelFiled.fillPatternType()); //填充样式
//       设置边框:
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setAlignment(HorizontalAlignment.CENTER); // 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //        设置字体:
        Font font = styleParam.getWorkbook().createFont();
        font.setFontName(easyExcelFiled.columnFontName());
        font.setFontHeightInPoints(easyExcelFiled.columnFontSize());//设置字体大小
        font.setColor(easyExcelFiled.columnFontColor());
        style.setFont(font);//选择需要用到的字体格式
        style.setFont(font);//选择需要用到的字体格式
//        设置列宽:
        styleParam.getSheet().setColumnWidth(easyExcelFiledPoint.getCurrentColumnIndex(), easyExcelFiled.width() * 255); //第一个参数代表列id(从0开始),第2个参数代表宽度值
        style.setWrapText(easyExcelFiled.wrapText());//设置自动换行
        return style;
    }
}
