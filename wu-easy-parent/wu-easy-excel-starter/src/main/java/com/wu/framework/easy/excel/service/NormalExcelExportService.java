package com.wu.framework.easy.excel.service;

import com.wu.framework.easy.excel.service.style.Style;
import com.wu.framework.easy.excel.service.style.StyleParam;
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
 *
 * @author Jia wei Wu
 * @date 2020/10/6 下午8:28
 */
public class NormalExcelExportService implements ExcelExcelService {

    /**
     * description
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
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
                int finalI = i;
                EasyExcel tempEasyExcel = new EasyExcel() {
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return easyExcel.annotationType();
                    }

                    /**
                     * 文件名
                     *
                     * @return String
                     */
                    @Override
                    public String fileName() {
                        return easyExcel.fileName();
                    }

                    /**
                     * 工作簿名字
                     *
                     * @return
                     */
                    @Override
                    public String sheetName() {
                        return sheetContextList.get(finalI);
                    }

                    /**
                     * 文件后缀
                     *
                     * @return String
                     */
                    @Override
                    public String suffix() {
                        return easyExcel.suffix();
                    }

                    /**
                     * 默认 false
                     * 是否复杂导出
                     *
                     * @return boolean
                     */
                    @Override
                    public boolean isComplicated() {
                        return easyExcel.isComplicated();
                    }

                    /**
                     * 默认 false 通过参数中注解获取表头字段
                     * 是否使用注解方式获取表头
                     *
                     * @return boolean
                     */
                    @Override
                    public boolean useAnnotation() {
                        return easyExcel.useAnnotation();
                    }

                    /**
                     * useAnnotation true 有效
                     * 字段列名注解
                     *
                     * @return Class
                     */
                    @Override
                    public Class<? extends Annotation> fieldColumnAnnotation() {
                        return easyExcel.fieldColumnAnnotation();
                    }

                    /**
                     * useAnnotation true 有效
                     * 字段列名注解属性名
                     *
                     * @return String
                     */
                    @Override
                    public String fieldColumnAnnotationAttribute() {
                        return easyExcel.fieldColumnAnnotationAttribute();
                    }

                    /**
                     * 多个 sheet
                     *
                     * @return boolean
                     */
                    @Override
                    public boolean multipleSheet() {
                        return easyExcel.multipleSheet();
                    }

                    /**
                     * multipleSheet true 有效
                     * 工作簿每页限制长度
                     *
                     * @return int
                     */
                    @Override
                    public int limit() {
                        return easyExcel.limit();
                    }

                    /**
                     * multipleSheet true 有效
                     * 工作簿展示内容
                     */
                    @Override
                    public SheetShowContext sheetShowContext() {
                        return easyExcel.sheetShowContext();
                    }

                    @Override
                    public Class<? extends Style> style() {
                        return easyExcel.style();
                    }

                    /**
                     * 表头固定
                     */
                    @Override
                    public boolean titleFixedHead() {
                        return easyExcel.titleFixedHead();
                    }
                };
                normalSingleSheet(workbook, tempEasyExcel, splitList.get(i));
            }
        } else {
            normalSingleSheet(workbook, easyExcel, collection);
        }
        workbook.write(out);
        return out.toByteArray();
    }

    /**
     * @param workbook
     * @param easyExcel
     * @param collection describe 正常单工作簿导出
     */
    public static void normalSingleSheet(HSSFWorkbook workbook, EasyExcel easyExcel, Collection collection) {
        try {
            //首先检查数据看是否是正确的
            Iterator iterator = collection.iterator();
            if (!iterator.hasNext()) {
                throw new Exception("数据错误");
            }
            //取得实际泛型类
            Object ts = iterator.next();
            /* 生成一个表格 */
            HSSFSheet sheet = workbook.createSheet(easyExcel.sheetName());
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth(20);
            // 生成一个样式
            HSSFCellStyle style = workbook.createCellStyle();
            // 设置标题样式
//            style = ExcelStyle.setHeadStyle(workbook, style);
            List<Field> fieldList;
            if (easyExcel.useAnnotation()) {
                fieldList = Arrays.stream(ts.getClass().getDeclaredFields()).
                        filter(field -> null != field.getAnnotation(easyExcel.fieldColumnAnnotation())).
                        peek(field -> field.setAccessible(true)).
                        collect(Collectors.toList());
            } else {
                fieldList = Arrays.stream(ts.getClass().getDeclaredFields()).peek(field -> field.setAccessible(true)).collect(Collectors.toList());
            }
            // 获取数据实体类型
            iterator = collection.iterator();
            Object next = iterator.next();
            Class<?> dataEntityType = next.getClass();
            // 产生表格标题行
            HSSFRow row = sheet.createRow(TITLE_COLUMN);
            if (Map.class.isAssignableFrom(dataEntityType)) {
                Set<String> keySet = ((Map<String, String>) next).keySet();
                int i = 0;
                for (String key : keySet) {
                    HSSFCell hssfCell = row.createCell(i);
                    hssfCell.setCellStyle(style);
                    HSSFRichTextString text = new HSSFRichTextString(key);
                    ExcelExcelService.setRowColumnContent(hssfCell, text);
                    i++;
                }
            } else {
                if (easyExcel.titleFixedHead()) {
                    sheet.createFreezePane(fieldList.size(), TITLE_COLUMN + 1);
                }
                for (int i = 0; i < fieldList.size(); i++) {
                    HSSFCell hssfCell = row.createCell(i);
                    hssfCell.setCellStyle(style);
                    Field field = fieldList.get(i);
                    String headerName;
                    if (easyExcel.useAnnotation()) {
                        Annotation filedAnnotation = field.getAnnotation(easyExcel.fieldColumnAnnotation());
                        if (null == filedAnnotation) {
                            return;
                        } else {
                            final Class<? extends Style> styleClass = easyExcel.style();
                            final Style newInstance = styleClass.newInstance();
                            HSSFCellStyle tempStyle =
                                    newInstance.titleStyle(new StyleParam(workbook, sheet, filedAnnotation, i));
                            hssfCell.setCellStyle(tempStyle);
                            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(filedAnnotation);
                            headerName = String.valueOf(annotationAttributes.getOrDefault(easyExcel.fieldColumnAnnotationAttribute(), field.getName()));
                        }
                    } else {
                        headerName = field.getName();
                    }
                    HSSFRichTextString text = new HSSFRichTextString(headerName);
                    ExcelExcelService.setRowColumnContent(hssfCell, text);
                }
            }


            int index = 0;
            // 循环整个集合
            iterator = collection.iterator();
            List<HSSFCellStyle> columnStyleList = new LinkedList<>();
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
                        final Field field = fieldList.get(i);
                        HSSFCell hssfCell = row.createCell(i);
                        if (easyExcel.useAnnotation()) {
                            Annotation filedAnnotation = field.getAnnotation(easyExcel.fieldColumnAnnotation());
                            if (null != filedAnnotation) {
                                final Class<? extends Style> styleClass = easyExcel.style();
                                final Style newInstance = styleClass.newInstance();
                                HSSFCellStyle tempStyle;
                                if (columnStyleList.size() != fieldList.size()) {
                                    tempStyle =
                                            newInstance.columnStyle(new StyleParam(workbook, sheet, filedAnnotation, i));
                                    columnStyleList.add(tempStyle);
                                } else {
                                    tempStyle = columnStyleList.get(i);
                                }
                                hssfCell.setCellStyle(tempStyle);
                            }
                        }
                        ExcelExcelService.setRowColumnContent(hssfCell, field.get(t));
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
     * @author Jia wei Wu
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



