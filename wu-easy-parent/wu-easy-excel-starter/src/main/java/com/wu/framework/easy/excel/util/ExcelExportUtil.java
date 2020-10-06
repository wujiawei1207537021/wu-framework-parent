package com.wu.framework.easy.excel.util;

import com.wu.framework.easy.excel.stereotype.EasyExcel;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelExportUtil {


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
            List<List> splitList = splitList(new ArrayList(collection), easyExcel.limit());
            ISheetShowContextMethod iSheetShowContextMethod = easyExcel.sheetShowContext().getISheetShowContextMethod().newInstance();
            List<String> sheetContextList = iSheetShowContextMethod.sheetContext(collection.size(), easyExcel.limit());
            for (int i = 0; i < splitList.size(); i++) {
                exportSingleSheet(workbook,sheetContextList.get(i), easyExcel.filedColumnAnnotation(), easyExcel.filedColumnAnnotationAttribute(), splitList.get(i), out);
            }
        } else {
            exportSingleSheet(workbook,easyExcel.fileName(), easyExcel.filedColumnAnnotation(), easyExcel.filedColumnAnnotationAttribute(), collection, out);
        }
        workbook.write(out);
        return out.toByteArray();
    }

    public static void exportSingleSheet(HSSFWorkbook workbook,
                                         String sheetName,
                                         Class<? extends Annotation> filedColumnAnnotation,
                                         String filedColumnAnnotationAttribute, Collection collection,
                                         ByteArrayOutputStream out) {
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
            style = ExcelStyle.setHeadStyle(workbook, style);
            List<Field> fieldList = Arrays.stream(ts.getClass().getDeclaredFields()).
                    filter(field -> null != field.getAnnotation(filedColumnAnnotation)).
                    peek(field -> field.setAccessible(true)).
                    collect(Collectors.toList());
            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < fieldList.size(); i++) {
                HSSFCell hssfCell = row.createCell(i);
                hssfCell.setCellStyle(style);
                Field field = fieldList.get(i);
                Annotation filedAnnotation = field
                        .getAnnotation(filedColumnAnnotation);
                Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(filedAnnotation);
                String headerName = String.valueOf(annotationAttributes.getOrDefault(filedColumnAnnotationAttribute, field.getName()));
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
     * description
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

    public static List<List> splitList(List list, int len) {

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
}



