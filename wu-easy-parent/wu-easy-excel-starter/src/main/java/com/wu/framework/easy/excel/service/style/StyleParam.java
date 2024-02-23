package com.wu.framework.easy.excel.service.style;

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
public class StyleParam {
    private Workbook workbook;

    private Sheet hssfSheet;

    private Annotation filedAnnotation;

    /**
     * 列
     */
    private Integer columnIndex;

    public <T extends Annotation> T getFiledAnnotation(Class<T> filedAnnotationClass) {
        return (T) filedAnnotation;
    }
}
