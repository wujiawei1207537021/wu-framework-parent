package com.wu.framework.easy.excel.service.style;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author : 吴佳伟
 * @version : 1.0
 * @describe: 颜色类型
 * @date : 2020/12/9 8:57 下午
 */
public interface Style {

    HSSFCellStyle color(HSSFWorkbook workbook, EasyExcelFiled easyExcelFiled);

    HSSFFont font(HSSFWorkbook workbook, EasyExcelFiled easyExcelFiled);

    default HSSFCellStyle init(HSSFWorkbook workbook, EasyExcelFiled filedAnnotation) {
      return   color(workbook, filedAnnotation);
    }

}
