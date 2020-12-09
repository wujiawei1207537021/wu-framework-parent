package com.wu.framework.easy.excel.service.style;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/12/9 9:07 下午
 */
public class DefaultStyle implements Style {

    @Override
    public HSSFCellStyle color(HSSFWorkbook workbook, EasyExcelFiled easyExcelFiled) {
        final HSSFCellStyle style = workbook.createCellStyle();
//        设置背景色：
        style.setFillForegroundColor((short) 13);// 设置背景色
        style.setFillPattern(easyExcelFiled.fillPatternType()); //填充样式
//       设置边框:
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框

//        设置居中:
        style.setAlignment(HorizontalAlignment.CENTER); // 居中

        final HSSFFont font = font(workbook, easyExcelFiled);
        style.setFont(font);//选择需要用到的字体格式

//        设置列宽:

//        sheet.setColumnWidth(0, 3766); //第一个参数代表列id(从0开始),第2个参数代表宽度值

//        style.setWrapText(true);//设置自动换行
        return style;

    }

    @Override
    public HSSFFont font(HSSFWorkbook workbook, EasyExcelFiled easyExcelFiled) {
//        设置字体:
        HSSFFont font = workbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 16);//设置字体大小
        return font;
    }
}
