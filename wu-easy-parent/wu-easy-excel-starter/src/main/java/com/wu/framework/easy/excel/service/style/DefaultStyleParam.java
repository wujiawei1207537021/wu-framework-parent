package com.wu.framework.easy.excel.service.style;

import com.wu.framework.easy.excel.endpoint.EasyExcelFiledPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.annotation.Annotation;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 样式参数
 * @date : 2020/12/11 9:00 下午
 */
@Data
@AllArgsConstructor
public class DefaultStyleParam {
    /**
     * 当前工作簿
     */
    private Workbook workbook;
    /**
     * 当前sheet
     */
    private Sheet sheet;

    /**
     * 当前字段信息
     */
    private EasyExcelFiledPoint easyExcelFiledPoint;
}
