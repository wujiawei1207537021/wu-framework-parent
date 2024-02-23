package com.wu.framework.easy.excel.service.style;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.lang.annotation.Annotation;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 样式参数
 * @date : 2020/12/11 9:00 下午
 */
@Data
@AllArgsConstructor
public class StyleParam {
    private HSSFWorkbook workbook;

    private HSSFSheet hssfSheet;

    private Annotation filedAnnotation;

    private Integer columnIndex;

    public <T extends Annotation> T getFiledAnnotation(Class<T> filedAnnotationClass) {
        return (T) filedAnnotation;
    }
}
