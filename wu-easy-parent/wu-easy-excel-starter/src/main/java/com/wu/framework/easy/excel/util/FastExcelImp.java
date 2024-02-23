package com.wu.framework.easy.excel.util;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.inner.layer.data.convert.LazyDataFactory;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.LocaleUtil;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class FastExcelImp {

    /**
     * 根据路径解析
     *
     * @param path
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseExcel(String path, Class<T> clazz) {
        File file = new File(path);
//       return this.parseExcel(file,clazz);
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseExcel(workbook, clazz);
    }

    /**
     * MultipartFile
     *
     * @param file
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseExcel(MultipartFile file, Class<T> clazz) {
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseExcel(is, clazz);
    }

    /**
     * 根据输入输出流解析
     *
     * @param is
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseExcel(InputStream is, Class<T> clazz) {

        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseExcel(workbook, clazz);
    }

    public static <T> List<T> parseExcel(Workbook workbook, Class<T> clazz) {
        Sheet sheet = workbook.getSheetAt(0);
        List<T> rst = new ArrayList<>();
        if (sheet == null) {
            return rst;
        }
        int firstRowNum = sheet.getFirstRowNum();
        Row row = sheet.getRow(firstRowNum);
        short lastCellNum = row.getLastCellNum();
        // key:表头,value:对应的列数
        Map<String, Integer> cellNames = getCellMapping(row, lastCellNum);
        // key:映射的表头名字,value:对应的字段
        Map<String, Field> fieldMap = getFieldMapping(clazz);
        int lastRowNum = sheet.getLastRowNum();
        Set<String> keys = cellNames.keySet();
        try {
            CellFormat cellFormat = CellFormat.getInstance(LocaleUtil.getUserLocale(), "General");
            for (int rowIndex = (++firstRowNum); rowIndex <= lastRowNum; rowIndex++) {
                Row r = sheet.getRow(rowIndex);

                if (Map.class.isAssignableFrom(clazz)) {
                    Map map = (Map) clazz.newInstance();
                    cellNames.entrySet().stream().forEach(stringIntegerEntry -> {
                        Cell cel = r.getCell(stringIntegerEntry.getValue());
                        Object value = "";
                        if (cel != null) {
                            if (CellType.NUMERIC.equals(cel.getCellType()) && DateUtil.isCellDateFormatted(cel)) {
                                // 日期格式
                                value = cel.getDateCellValue();
                            } else {
                                value = cellFormat.apply(cel).text;
                            }
                        }

                        map.put(stringIntegerEntry.getKey(), value);
                    });
                    rst.add((T) map);
                } else {
                    T inst = clazz.newInstance();
                    for (String key : keys) {
                        Field field = fieldMap.get(key);
                        if (field == null) {
                            continue;
                        }
                        Integer col = cellNames.get(key);
                        Cell cel = r.getCell(col);
                        if (cel == null) {
                            continue;
                        }

                        field.setAccessible(true);

                        Object text;
                        if (CellType.NUMERIC.equals(cel.getCellType()) && DateUtil.isCellDateFormatted(cel)) {
                            // 日期格式
                            text = cel.getDateCellValue();
                        } else {
                            text = cellFormat.apply(cel).text;
                        }
                        LazyDataFactory.handler(inst, field, text);
//                        Object transform = BeanTypeTransformUtil.transform(text, field.getType());
//                        field.set(inst, transform);
                    }
                    rst.add(inst);
                }

            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return rst;
    }

    /**
     * 获取表头和列的映射关系
     *
     * @param row
     * @param lastCellNum
     * @return
     */
    private static Map<String, Integer> getCellMapping(Row row, short lastCellNum) {
        // key:表头,value:对应的列数
        Map<String, Integer> cellNames = new HashMap<>();
        Cell cell;
        for (int col = 0; col < lastCellNum; col++) {
            cell = row.getCell(col);
            String val = cell.getStringCellValue();
            cellNames.put(val, col);
        }
        return cellNames;
    }

    private static <T> Map<String, Field> getFieldMapping(Class<T> clazz, Class annotationClass, String annotationProfile) {
        // key:映射的表头名字,value:对应的字段
        Map<String, Field> annotations = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length < 1) {
            return annotations;
        }
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(annotationClass);
            if (annotation == null) {
                annotations.put(field.getName(), field);
            } else {
                Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
                annotations.put(annotationAttributes.getOrDefault(annotationProfile, "").toString(), field);
            }
        }
        return annotations;
    }

    /**
     * 获取对象字段和Excel表头的字段映射关联
     *
     * @param clazz
     * @return
     */
    private static <T> Map<String, Field> getFieldMapping(Class<T> clazz) {
        return getFieldMapping(clazz, EasyExcelFiled.class, "name");
    }

//    /***
//     * 测试
//     * @param args
//     */
//    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
//
//       Class clazz= changeAnnotation(Person.class, new ArrayList<>());
//        Class clazz2= changeAnnotation(clazz, new ArrayList<>());
//
//    }
//
//    /***
//     * 测试修改注解
//     */
//    public static <T> Class   changeAnnotation(Class<T> clazz,List<String> hideList)  {
//        Field[] fields = clazz.getDeclaredFields();
//        if (fields == null || fields.length < 1) {
//            return clazz;
//        }
//        for (Field field : fields) {
//            Excel excel = field.getAnnotation(Excel.class);
//            if(hideList.indexOf(excel.name())!=-1){
//                InvocationHandler invocationHandler = Proxy.getInvocationHandler(excel);
//                Field declaredField = null;
//                try {
//                    declaredField = invocationHandler.getClass().getDeclaredField("memberValues");
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                }
//                // 因为这个字段事 private final 修饰，所以要打开权限
//                declaredField.setAccessible(true);
//                // 获取 memberValues
//                Map memberValues = null;
//                try {
//                    memberValues = (Map) declaredField.get(invocationHandler);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                // 修改 value 属性值
//                memberValues.put("isColumnHidden", true);
//                System.out.println(excel.isColumnHidden());
//            }
//
//        }
//        return clazz;
//    }


}