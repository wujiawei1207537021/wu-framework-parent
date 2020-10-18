package com.wu.framework.easy.excel.service;

import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.excel.util.ISheetShowContextMethod;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.core.annotation.AnnotationUtils;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description 正常Excel导出工具
 * @author 吴佳伟
 * @date 2020/10/6 下午8:28
 */
public class NormalExcelExportService implements ExcelExcelService {

    /**
     * description
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/10/6 上午11:12
     */
    @SneakyThrows
    public static byte[] exportExcel(EasyExcel easyExcel, Collection collection) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 多工作簿
        if (easyExcel.multipleSheet()) {
            List<List> splitList = ExcelExcelService.splitList(new ArrayList(collection), easyExcel.limit());
            ISheetShowContextMethod iSheetShowContextMethod = easyExcel.sheetShowContext().getISheetShowContextMethod().newInstance();
            List<String> sheetContextList = iSheetShowContextMethod.sheetContext(collection.size(), easyExcel.limit());
            for (int i = 0; i < splitList.size(); i++) {
                normalSingleSheet(workbook, sheetContextList.get(i), easyExcel.filedColumnAnnotation(), easyExcel.filedColumnAnnotationAttribute(), easyExcel.useAnnotation(), splitList.get(i));
            }
        } else {
            normalSingleSheet(workbook, easyExcel.fileName(), easyExcel.filedColumnAnnotation(), easyExcel.filedColumnAnnotationAttribute(), easyExcel.useAnnotation(), collection);
        }
        workbook.write(out);
        return out.toByteArray();
    }

    /**
     * 正常单工作簿导出
     * @param workbook
     * @param sheetName
     * @param filedColumnAnnotation
     * @param filedColumnAnnotationAttribute
     * @param useAnnotation
     * @param collection
     */
    public static void normalSingleSheet(HSSFWorkbook workbook,
                                         String sheetName,
                                         Class<? extends Annotation> filedColumnAnnotation,
                                         String filedColumnAnnotationAttribute, boolean useAnnotation, Collection collection) {
        try {
            //首先检查数据看是否是正确的
            Iterator iterator = collection.iterator();
            if (!iterator.hasNext()) {
                throw new Exception("数据错误");
            }
            //取得实际泛型类
            Object ts = iterator.next();
            /* 生成一个表格 */
            HSSFSheet sheet = workbook.createSheet(sheetName);
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth(20);
            // 生成一个样式
            HSSFCellStyle style = workbook.createCellStyle();
            // 设置标题样式
//            style = ExcelStyle.setHeadStyle(workbook, style);
            List<Field> fieldList;
            if (useAnnotation) {
                fieldList = Arrays.stream(ts.getClass().getDeclaredFields()).
                        filter(field -> null != field.getAnnotation(filedColumnAnnotation)).
                        peek(field -> field.setAccessible(true)).
                        collect(Collectors.toList());
            } else {
                fieldList = Arrays.stream(ts.getClass().getDeclaredFields()).peek(field -> field.setAccessible(true)).collect(Collectors.toList());
            }

            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < fieldList.size(); i++) {
                HSSFCell hssfCell = row.createCell(i);
                hssfCell.setCellStyle(style);
                Field field = fieldList.get(i);
                String headerName;
                if (useAnnotation) {
                    Annotation filedAnnotation = field.getAnnotation(filedColumnAnnotation);
                    if (null==filedAnnotation){
                        return;
                    }else {
                        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(filedAnnotation);
                        headerName = String.valueOf(annotationAttributes.getOrDefault(filedColumnAnnotationAttribute, field.getName()));
                    }
                } else {
                    headerName=field.getName();
                }
                HSSFRichTextString text = new HSSFRichTextString(headerName);
                hssfCell.setCellValue(text);
            }
            int index = 0;
            // 循环整个集合
            iterator = collection.iterator();
            while (iterator.hasNext()) {
                //从第二行开始写，第一行是标题
                index++;
                row = sheet.createRow(index);
                Object t = iterator.next();
                if (t instanceof Map) {
                    Collection<String> values = ((Map<String, String>) t).values();
                    Iterator<? extends String> mapIterator = values.iterator();
                    for (int k = 0; mapIterator.hasNext(); k++) {
                        HSSFCell cell = row.createCell(k);
                        cell.setCellValue(mapIterator.next());
                    }
                } else {
                    for (int i = 0; i < fieldList.size(); i++) {
                        HSSFCell cell = row.createCell(i);
                        String textValue = String.valueOf(fieldList.get(i).get(t));
                        cell.setCellValue(textValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * description 多个sheet 导出
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/10/6 上午11:12
     */
    public static byte[] exportExcelMultipleSheet(EasyExcel easyExcel, List<String> titlist, List<List> dataSet) {
        //首先检查数据看是否是正确的
        if (titlist.size() == 0 || !dataSet.iterator().hasNext()) {
            throw new RuntimeException("传入的数据不对！");
        }
        byte[] bytes = new byte[0];
        for (int o = 0; o < dataSet.size(); o++) {
            exportExcel(easyExcel, dataSet.get(0));
        }
        return bytes;
    }


}



