package com.wu.framework.easy.excel.service;

import com.wu.framework.easy.excel.CellRangeAddressMerge;
import com.wu.framework.easy.excel.endpoint.EasyExcelFiledPoint;
import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.endpoint.convert.EasyExcelFiledPointConvert;
import com.wu.framework.easy.excel.endpoint.convert.EasyExcelPointConvert;
import com.wu.framework.easy.excel.service.style.Style;
import com.wu.framework.easy.excel.service.style.StyleParam;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelContextHolder;
import com.wu.framework.easy.excel.util.ISheetShowContextMethod;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

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
    public static EasyExcelPointConvert defaultPointConvert = new EasyExcelPointConvert();

    private static EasyExcelFiledPointConvert easyExcelFiledPointConvert = new EasyExcelFiledPointConvert();

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
    public static byte[] exportExcel(EasyExcelPoint easyExcelPoint, Collection collection) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HSSFWorkbook workbook = new HSSFWorkbook();
//        SXSSFWorkbook
        // 多工作簿
        if (easyExcelPoint.isMultipleSheet()) {
            List<List> splitList = ExcelExcelService.splitList(new ArrayList(collection), easyExcelPoint.getLimit());
            ISheetShowContextMethod iSheetShowContextMethod = easyExcelPoint.getSheetShowContext().getISheetShowContextMethod().newInstance();
            List<String> sheetContextList = iSheetShowContextMethod.sheetContext(collection.size(), easyExcelPoint.getLimit());
            for (int i = 0; i < splitList.size(); i++) {
                easyExcelPoint.setSheetName(sheetContextList.get(i));
                normalSingleSheet(workbook, easyExcelPoint, splitList.get(i));
            }
        } else {
            normalSingleSheet(workbook, easyExcelPoint, collection);
        }
        workbook.write(out);
        return out.toByteArray();
    }

    /**
     * @param workbook
     * @param easyExcel
     * @param collection describe 正常单工作簿导出
     */
    public static void normalSingleSheet(HSSFWorkbook workbook, EasyExcelPoint easyExcel, Collection collection) {
        try {
            //首先检查数据看是否是正确的
            Iterator iterator = collection.iterator();
            if (!iterator.hasNext()) {
                throw new Exception("数据错误");
            }
            //取得实际泛型类
            Object ts = iterator.next();

            // 支持多个sheet 导出
            if (Collection.class.isAssignableFrom(ts.getClass())) {
                for (Object o : collection) {
                    easyExcel.setSheetName(easyExcel.getSheetName() + "-1");
                    normalSingleSheet(workbook, easyExcel, (Collection) o);
                }
                return;
            }
            /* 生成一个表格 */
            HSSFSheet sheet = workbook.createSheet(easyExcel.getSheetName());
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth(20);
            // 生成一个样式
            HSSFCellStyle style = workbook.createCellStyle();
            // 设置标题样式
//            style = ExcelStyle.setHeadStyle(workbook, style);
            List<String> peek = DynamicEasyExcelContextHolder.peek();
            List<EasyExcelFiledPoint> excelFiledPointList;
            if (easyExcel.isUseAnnotation()) {

                excelFiledPointList = Arrays.stream(ts.getClass().getDeclaredFields())
                        .filter(field -> null != field.getAnnotation(easyExcel.getFieldColumnAnnotation()))
                        .peek(field -> field.setAccessible(true))
                        .map(field -> {
                            EasyExcelFiled easyExcelFiled = AnnotatedElementUtils.findMergedAnnotation(field, EasyExcelFiled.class);
                            assert easyExcelFiled != null;
                            EasyExcelFiledPoint converter = easyExcelFiledPointConvert.converter(easyExcelFiled);
                            converter.setField(field);
                            converter.setEasyExcelFiled(easyExcelFiled);
                            return converter;
                        }).sorted(Comparator.comparing(EasyExcelFiledPoint::getSerialNumber).reversed())
                        .collect(Collectors.toList());
            } else {
                excelFiledPointList = Arrays.stream(ts.getClass().getDeclaredFields()).peek(field -> field.setAccessible(true)).map(field -> {
                    EasyExcelFiledPoint easyExcelFiledPoint = new EasyExcelFiledPoint();
                    easyExcelFiledPoint.setField(field);
                    return easyExcelFiledPoint;
                }).collect(Collectors.toList());
            }
            if (!ObjectUtils.isEmpty(peek)) {
                excelFiledPointList = excelFiledPointList.stream()
                        .filter(easyExcelFiledPoint -> !peek.contains(easyExcelFiledPoint.getField().getName()))
                        .collect(Collectors.toList());
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
                if (easyExcel.isTitleFixedHead()) {
                    sheet.createFreezePane(excelFiledPointList.size(), TITLE_COLUMN + 1);
                }
                for (int i = 0; i < excelFiledPointList.size(); i++) {
                    HSSFCell hssfCell = row.createCell(i);
                    hssfCell.setCellStyle(style);
                    EasyExcelFiledPoint easyExcelFiledPoint = excelFiledPointList.get(i);
                    Field field = easyExcelFiledPoint.getField();
                    String headerName;
                    if (easyExcel.isUseAnnotation()) {
                        Annotation filedAnnotation = field.getAnnotation(easyExcel.getFieldColumnAnnotation());
                        if (null == filedAnnotation) {
                            return;
                        } else {
                            final Class<? extends Style> styleClass = easyExcel.getStyle();
                            final Style newInstance = styleClass.newInstance();
                            CellStyle tempStyle =
                                    newInstance.titleStyle(new StyleParam(workbook, sheet, filedAnnotation, i));
                            hssfCell.setCellStyle(tempStyle);
                            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(filedAnnotation);
                            headerName = String.valueOf(annotationAttributes.getOrDefault(easyExcel.getFieldColumnAnnotationAttribute(), field.getName()));
                        }
                        EasyExcelFiled easyExcelFiled = easyExcelFiledPoint.getEasyExcelFiled();
                        if (!ObjectUtils.isEmpty(easyExcelFiled)) {
                            String[] dropdownOptions = easyExcelFiled.dropdownOptions();
                            if (!ObjectUtils.isEmpty(dropdownOptions)) {
                                DataValidationHelper helper = sheet.getDataValidationHelper();

                                CellRangeAddressList addressList = new CellRangeAddressList(1, 9999, i, i);
                                // 设置下拉框数据
                                DataValidationConstraint constraint = helper.createExplicitListConstraint(dropdownOptions);
                                DataValidation dataValidation = helper.createValidation(constraint, addressList);

                                // Excel兼容性问题
                                if (dataValidation instanceof XSSFDataValidation) {
                                    dataValidation.setSuppressDropDownArrow(true);
                                    dataValidation.setShowErrorBox(true);
                                } else {
                                    dataValidation.setSuppressDropDownArrow(false);
                                }
                                sheet.addValidationData(dataValidation);
                            }
                        }

                    } else {
                        headerName = field.getName();
                    }
                    HSSFRichTextString text = new HSSFRichTextString(headerName);
                    ExcelExcelService.setRowColumnContent(hssfCell, text);
                }
            }

            // 暂存的上一条数据 用于水平合并
            Map<Field, CellRangeAddressMerge> mergeLevelFieldMap =
                    excelFiledPointList.stream().filter(easyExcelFiledPoint -> {
                        EasyExcelFiled mergedAnnotation = easyExcelFiledPoint.getEasyExcelFiled();
                        // 左边字段、是否需要合并
                        return mergedAnnotation != null && EasyExcelFiled.EasyExcelFieldMerge.LEVEL == mergedAnnotation.fieldMerge();

                    }).collect(Collectors.toMap(EasyExcelFiledPoint::getField, o -> new CellRangeAddressMerge()));
            // 上下合并
            Map<Field, CellRangeAddressMerge> mergeVerticalFieldMap =
                    excelFiledPointList.stream().filter(easyExcelFiledPoint -> {
                        EasyExcelFiled mergedAnnotation = easyExcelFiledPoint.getEasyExcelFiled();
                        return mergedAnnotation != null && EasyExcelFiled.EasyExcelFieldMerge.VERTICAL == mergedAnnotation.fieldMerge();
                    }).collect(Collectors.toMap(EasyExcelFiledPoint::getField, o -> new CellRangeAddressMerge()));

            int size = collection.size();
            int index = 0;
            // 循环整个集合
            iterator = collection.iterator();
            List<CellStyle> columnStyleList = new LinkedList<>();
            while (iterator.hasNext()) {
                //从第二行开始写，第一行是标题
                index++;
                row = sheet.createRow(index);
                Object t = iterator.next();
                if (t instanceof Map) {
                    Collection<Object> values = ((Map<String, Object>) t).values();
                    Iterator<? extends Object> mapIterator = values.iterator();
                    for (int k = 0; mapIterator.hasNext(); k++) {
                        HSSFCell cell = row.createCell(k);
                        Object value = mapIterator.next();
                        cell.setCellValue(null == value ? null : String.valueOf(value));
                    }
                } else {
                    for (int i = 0; i < excelFiledPointList.size(); i++) {
                        final Field field = excelFiledPointList.get(i).getField();

                        Object fieldValue = field.get(t);
                        if (mergeVerticalFieldMap.containsKey(field)) {
                            CellRangeAddressMerge rangeAddressMerge = mergeVerticalFieldMap.get(field);
                            //垂直合并
                            if (ObjectUtils.isEmpty(rangeAddressMerge.getFirstValue())) {
                                // 第一次记录
                                CellRangeAddressMerge cellRangeAddressMerge = new CellRangeAddressMerge();
                                cellRangeAddressMerge.setFirstValue(fieldValue);
                                cellRangeAddressMerge.setFirstRow(index);
                                cellRangeAddressMerge.setFirstCol(i);
                                mergeVerticalFieldMap.put(field, cellRangeAddressMerge);
                            } else if (rangeAddressMerge.getFirstValue().equals(fieldValue)) {
                                //第二次记录
                                // 合并数据开始拉框
                                rangeAddressMerge.setLastRow(index);
                                rangeAddressMerge.setLastCol(i);
                                rangeAddressMerge.setLastValue(fieldValue);
                                mergeVerticalFieldMap.put(field, rangeAddressMerge);
                                if (index == size) {
                                    CellRangeAddress region = new CellRangeAddress(rangeAddressMerge.getFirstRow(), rangeAddressMerge.getLastRow(),
                                            rangeAddressMerge.getFirstCol(), rangeAddressMerge.getLastCol());
                                    sheet.addMergedRegionUnsafe(region);
                                }
                            } else if (!ObjectUtils.isEmpty(rangeAddressMerge.getLastValue()) && !rangeAddressMerge.getLastValue().equals(fieldValue)) {
                                // 要合并下一个了或者结束了
                                // 上下合并
                                // 合并单元格,合并后的内容取决于合并区域的左上角单元格的值
                                CellRangeAddress region = new CellRangeAddress(rangeAddressMerge.getFirstRow(), rangeAddressMerge.getLastRow(),
                                        rangeAddressMerge.getFirstCol(), rangeAddressMerge.getLastCol());
                                sheet.addMergedRegionUnsafe(region);
                            }
                        }

                        if (mergeLevelFieldMap.containsKey(field)) {
                            //水平合并
                        }

                        HSSFCell hssfCell = row.createCell(i);
                        if (easyExcel.isUseAnnotation()) {
                            Annotation filedAnnotation = field.getAnnotation(easyExcel.getFieldColumnAnnotation());
                            if (null != filedAnnotation) {
                                final Class<? extends Style> styleClass = easyExcel.getStyle();
                                final Style newInstance = styleClass.newInstance();
                                CellStyle tempStyle;
                                if (columnStyleList.size() != excelFiledPointList.size()) {
                                    tempStyle =
                                            newInstance.columnStyle(new StyleParam(workbook, sheet, filedAnnotation, i));
                                    columnStyleList.add(tempStyle);
                                } else {
                                    tempStyle = columnStyleList.get(i);
                                }
                                hssfCell.setCellStyle(tempStyle);
                            }
                        }
                        ExcelExcelService.setRowColumnContent(hssfCell, fieldValue);
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
    public static byte[] exportExcelMultipleSheet(EasyExcelPoint easyExcel, List<String> titlist, List<List> dataSet) {
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



