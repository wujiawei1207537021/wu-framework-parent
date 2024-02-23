package com.wu.framework.easy.excel.service;

import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.CellType;

import java.util.*;

public interface ExcelExcelService {

    /**
     * 表头
     */
    int TITLE_COLUMN = 0;

    @SneakyThrows
    static byte[] exportExcel(EasyExcelPoint easyExcelPoint, Collection collection) {
        return NormalExcelExportService.exportExcel(easyExcelPoint, collection);
    }

    static List<List> splitList(List list, int len) {
        if (list == null || list.isEmpty() || len < 1) {
            return Collections.emptyList();
        }
        List<List> result = new ArrayList<>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List subList = list.subList(i * len, (Math.min((i + 1) * len, size)));
            result.add(subList);
        }
        return result;
    }

    /**
     * 设置行列内容
     */
    static void setRowColumnContent(HSSFCell hssfCell, Object value) {
        if (null == value) {
            return;
        }
        final Class<?> valueClass = value.getClass();
        if (valueClass.isAssignableFrom(String.class)) {
            hssfCell.setCellValue(String.valueOf(value));
        } else if (valueClass.isAssignableFrom(Double.class)) {
            hssfCell.setCellType(CellType.NUMERIC);
            hssfCell.setCellValue((Double) value);
        } else if (valueClass.isAssignableFrom(Integer.class)) {
            hssfCell.setCellType(CellType.NUMERIC);
            hssfCell.setCellValue((Integer) value);
        } else if (valueClass.isAssignableFrom(Long.class)) {
            hssfCell.setCellType(CellType.NUMERIC);
            hssfCell.setCellValue((Long) value);
        } else if (valueClass.isAssignableFrom(Float.class)) {
            hssfCell.setCellType(CellType.NUMERIC);
            hssfCell.setCellValue((Float) value);
        } else if (valueClass.isAssignableFrom(Date.class)) {
            hssfCell.setCellType(CellType.STRING);
            hssfCell.setCellValue((Date) value);
        } else if (valueClass.isAssignableFrom(Calendar.class)) {
            hssfCell.setCellValue((Calendar) value);
        } else if (valueClass.isAssignableFrom(Boolean.class)) {
            hssfCell.setCellType(CellType.BOOLEAN);
            hssfCell.setCellValue((Boolean) value);
        } else if (valueClass.isAssignableFrom(HSSFRichTextString.class)) {
            hssfCell.setCellValue((HSSFRichTextString) value);
        } else {
            hssfCell.setCellValue(String.valueOf(value));
        }
    }
}
