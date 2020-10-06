package com.wu.framework.easy.excel.util;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import org.apache.poi.hssf.usermodel.*;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelExportUtil {
    /**
     * 格式化日期
     */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @param title      标题
     * @param collection 集合
     */
    public static byte[] exportExcel(String title, Collection collection) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            //首先检查数据看是否是正确的
            Iterator iterator = collection.iterator();
            if (!iterator.hasNext() || title == null) {
                throw new Exception("传入的数据不对！");
            }
            //取得实际泛型类
            Object ts =  iterator.next();
            Class tCls = ts.getClass();
            HSSFWorkbook workbook = new HSSFWorkbook();
            /* 生成一个表格 */
            HSSFSheet sheet = workbook.createSheet(title);
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth(20);
            // 生成一个样式
            HSSFCellStyle style = workbook.createCellStyle();
            // 设置标题样式
            style = ExcelStyle.setHeadStyle(workbook, style);
            List<Field> fieldList = Arrays.stream(ts.getClass().getDeclaredFields()).
                    filter(field -> null != field.getAnnotation(EasyExcelFiled.class)).
                    peek(field -> field.setAccessible(true)).
                    collect(Collectors.toList());
            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < fieldList.size(); i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(fieldList.get(i).getAnnotation(EasyExcelFiled.class).name());
                cell.setCellValue(text);
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
            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * //多sheet版本
     *
     * @param titlist 标题
     * @param dataSet 集合
     * @author byb
     */
    public static byte[] exportExcelMoreSheet(List<String> titlist, List<List> dataSet) {
        //首先检查数据看是否是正确的
        if (titlist.size() == 0 || !dataSet.iterator().hasNext()) {
            throw new RuntimeException("传入的数据不对！");
        }
        byte[] bytes = new byte[0];
        for (int o = 0; o < dataSet.size(); o++) {
            exportExcel(titlist.get(o), dataSet.get(0));
        }
        return bytes;
    }
}



