package com.wu.framework.easy.excel.processor;

import com.wu.framework.easy.excel.CellCoordinate;
import com.wu.framework.easy.excel.endpoint.EasyExcelFiledPoint;
import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.endpoint.convert.EasyExcelFiledPointConvert;
import com.wu.framework.easy.excel.service.style.DefaultStyleParam;
import com.wu.framework.easy.excel.service.style.Style;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelContextHolder;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description 常用对象Excel 处理器
 *
 * @author 吴佳伟
 * @date 2023/06/29 20:38
 */
public abstract class AbstractNormalBeanEasyExcelProcessor extends AbstractEasyExcelProcessor implements EasyExcelProcessor {

    private static EasyExcelFiledPointConvert easyExcelFiledPointConvert = new EasyExcelFiledPointConvert();
    private Logger log = LoggerFactory.getLogger(AbstractNormalBeanEasyExcelProcessor.class);

    /**
     * 是否支持excel 处理
     *
     * @param excelBean
     * @return
     */
    @Override
    public boolean support(Object excelBean) {
        return excelBean != null;
    }

    /**
     * 导入excel
     *
     * @param excel
     * @return
     */
    @Override
    public <T> List<T> importExcel(Object excel) {
        return null;
    }


    /**
     * 优先级 数字越大优先级越高
     *
     * @return
     */
    @Override
    public int order() {
        return -1;
    }


    /**
     * 处理 map 形成表头
     *
     * @param workbook
     * @param sheet
     * @param easyExcelPoint
     * @param beanList         需要导出的对象
     * @param startColumnIndex 开始的列
     * @return
     */
    public List<EasyExcelFiledPoint> handlerMapRowTitle(Workbook workbook, Sheet sheet, EasyExcelPoint easyExcelPoint, List<?> beanList, int startColumnIndex) {
        int easyExcelFiledPointStartColumnIndex = startColumnIndex;
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) beanList;
        List<EasyExcelFiledPoint> excelFiledPointList = new ArrayList<>();
        Map<String, Object> mergedMap = mapList.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> Optional.ofNullable(entry.getValue()).orElse(""), (A, B) -> {
                    if (!ObjectUtils.isEmpty(A)) {
                        return A;
                    } else {
                        return B;
                    }
                }));
        for (Map.Entry<String, Object> stringObjectEntry : mergedMap.entrySet()) {
            String key = stringObjectEntry.getKey();
            Object o = stringObjectEntry.getValue();
            EasyExcelFiledPoint easyExcelFiledPoint = new EasyExcelFiledPoint();
            easyExcelFiledPoint.setFieldName(key);
            easyExcelFiledPoint.setName(key);
            if (!ObjectUtils.isEmpty(o)) {
                if (isWrapClass(o.getClass())) {
                    easyExcelFiledPoint.setCurrentColumnIndex(easyExcelFiledPointStartColumnIndex);
                    easyExcelFiledPointStartColumnIndex++;
                } else if (Collection.class.isAssignableFrom(o.getClass())) {
                    // 集合中放的是基础数据类型
                    if (isWrapClass(((List) o).get(0).getClass())) {

                        easyExcelFiledPoint.setCurrentColumnIndex(easyExcelFiledPointStartColumnIndex);
                        List<EasyExcelFiledPoint> easyExcelFiledPoints = new ArrayList<>();
                        EasyExcelFiledPoint normal = new EasyExcelFiledPoint();
                        normal.setName(key);
                        normal.setFieldName(key);
                        normal.setCurrentColumnIndex(easyExcelFiledPointStartColumnIndex);
                        easyExcelFiledPoints.add(normal);
                        easyExcelFiledPoint.setExcelBeanFiledPointList(easyExcelFiledPoints);
                        easyExcelFiledPoint.setCollectionFiled(true);
                        easyExcelFiledPointStartColumnIndex++;
                    } else {
                        // 集合
                        List<EasyExcelFiledPoint> easyExcelFiledPoints = handlerMapRowTitle(workbook, sheet, easyExcelPoint, (List<?>) o, startColumnIndex);


                        easyExcelFiledPoint.setCollectionFiled(true);
                        // 内部有字段当前只做牟点
                        easyExcelFiledPoint.setCurrentColumnIndex(easyExcelFiledPointStartColumnIndex);
                        // 获取递归内部最后一列
                        Integer lastColumnIndex = getLastColumnIndex(easyExcelFiledPoints, easyExcelFiledPointStartColumnIndex);
                        easyExcelFiledPointStartColumnIndex = lastColumnIndex + 1;
                        easyExcelFiledPoint.setExcelBeanFiledPointList(easyExcelFiledPoints);
                    }

                } else if (Map.class.isAssignableFrom(o.getClass())) {
                    // map
                    List<EasyExcelFiledPoint> easyExcelFiledPoints = handlerMapRowTitle(workbook, sheet, easyExcelPoint, List.of(o), startColumnIndex);
                    easyExcelFiledPoint.setBeanFiled(true);
                    // 内部有字段当前只做牟点
                    easyExcelFiledPoint.setCurrentColumnIndex(easyExcelFiledPointStartColumnIndex);
                    // 获取递归内部最后一列
                    Integer lastColumnIndex = getLastColumnIndex(easyExcelFiledPoints, easyExcelFiledPointStartColumnIndex);
                    easyExcelFiledPointStartColumnIndex = lastColumnIndex + 1;
                    easyExcelFiledPoint.setExcelBeanFiledPointList(easyExcelFiledPoints);
                } else {
                    // Java对象
                }
            } else {
                easyExcelFiledPoint.setCurrentColumnIndex(easyExcelFiledPointStartColumnIndex);
                easyExcelFiledPointStartColumnIndex++;
            }
            excelFiledPointList.add(easyExcelFiledPoint);
        }


        return excelFiledPointList;
    }


    /**
     * 处理实体到表头
     *
     * @param workbook
     * @param sheet
     * @param easyExcelPoint
     * @param firstBeanClass   第一个bean class
     * @param startColumnIndex 开始的列
     * @return
     */
    public List<EasyExcelFiledPoint> handlerBeanRowTitle(Workbook workbook, Sheet sheet, EasyExcelPoint easyExcelPoint, Class<?> firstBeanClass, int startColumnIndex) {


        List<EasyExcelFiledPoint> excelFiledPointList;

        int easyExcelFiledPointStartColumnIndex = startColumnIndex;

        if (easyExcelPoint.isUseAnnotation()) {
            log.debug("use annotation export excel");

            excelFiledPointList = Arrays.stream(firstBeanClass.getDeclaredFields()).filter(field -> AnnotatedElementUtils.hasAnnotation(field, easyExcelPoint.getFieldColumnAnnotation())).peek(field -> field.setAccessible(true)).map(field -> {
                EasyExcelFiled easyExcelFiled = AnnotatedElementUtils.findMergedAnnotation(field, EasyExcelFiled.class);

                EasyExcelFiledPoint converter = new EasyExcelFiledPoint();
                if (easyExcelFiled != null) {
                    converter = easyExcelFiledPointConvert.converter(easyExcelFiled);
                } else {
                    Annotation filedAnnotation = field.getAnnotation(easyExcelPoint.getFieldColumnAnnotation());
                    Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(filedAnnotation);
                    String columnTitleName = String.valueOf(annotationAttributes.getOrDefault(easyExcelPoint.getFieldColumnAnnotationAttribute(), field.getName()));
                    converter.setName(columnTitleName);
                }
                converter.setField(field);
                converter.setFieldName(field.getName());
                converter.setEasyExcelFiled(easyExcelFiled);


                return converter;
            }).sorted(Comparator.comparing(EasyExcelFiledPoint::getSerialNumber).reversed()).collect(Collectors.toList());

        } else {
            excelFiledPointList = Arrays.stream(firstBeanClass.getDeclaredFields()).peek(field -> field.setAccessible(true)).map(field -> {
                EasyExcelFiledPoint easyExcelFiledPoint = new EasyExcelFiledPoint();
                easyExcelFiledPoint.setField(field);
                easyExcelFiledPoint.setFieldName(field.getName());
                easyExcelFiledPoint.setName(field.getName());

                // 创建默认样式

                return easyExcelFiledPoint;
            }).collect(Collectors.toList());
        }

        List<EasyExcelFiledPoint> easyExcelFiledPointList = excelFiledPointList.stream().sorted(Comparator.comparing(EasyExcelFiledPoint::getSerialNumber)).collect(Collectors.toList());

        // 字段非基础数据类型
        for (EasyExcelFiledPoint easyExcelFiledPoint : easyExcelFiledPointList) {

            Field field = easyExcelFiledPoint.getField();
            Class<?> fieldType = field.getType();
            if (!isWrapClass(fieldType)) {
                if (Collection.class.isAssignableFrom(fieldType)) {
                    // 解析范型
                    ParameterizedType type = (ParameterizedType) field.getGenericType();
                    List<EasyExcelFiledPoint> easyExcelFiledPoints = handlerBeanRowTitle(workbook, sheet, easyExcelPoint, (Class<?>) type.getActualTypeArguments()[0], easyExcelFiledPointStartColumnIndex);


                    easyExcelFiledPoint.setExcelBeanFiledPointList(easyExcelFiledPoints);
                    easyExcelFiledPoint.setCollectionFiled(true);
                    // 内部有字段当前只做牟点
                    easyExcelFiledPoint.setCurrentColumnIndex(easyExcelFiledPointStartColumnIndex);
                    // 获取递归内部最后一列
                    Integer lastColumnIndex = getLastColumnIndex(easyExcelFiledPoints, easyExcelFiledPointStartColumnIndex);
                    easyExcelFiledPointStartColumnIndex = lastColumnIndex + 1;
                } else {
                    List<EasyExcelFiledPoint> easyExcelFiledPoints = handlerBeanRowTitle(workbook, sheet, easyExcelPoint, fieldType, easyExcelFiledPointStartColumnIndex);

                    easyExcelFiledPoint.setExcelBeanFiledPointList(easyExcelFiledPoints);
                    // 非基础数据类型

                    easyExcelFiledPoint.setBeanFiled(true);
                    // 内部有字段当前只做牟点
                    easyExcelFiledPoint.setCurrentColumnIndex(easyExcelFiledPointStartColumnIndex);
                    // 获取递归内部最后一列
                    Integer lastColumnIndex = getLastColumnIndex(easyExcelFiledPoints, easyExcelFiledPointStartColumnIndex);
                    easyExcelFiledPointStartColumnIndex = lastColumnIndex + 1;
                }

            } else {
                easyExcelFiledPoint.setCurrentColumnIndex(easyExcelFiledPointStartColumnIndex);
                easyExcelFiledPointStartColumnIndex++;
            }
            // 根据注解创建样式
            final Class<? extends Style> styleClass = easyExcelPoint.getStyle();
            final Style newInstance;
            try {
                newInstance = styleClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            CellStyle tempStyle = newInstance.columnStyle(new DefaultStyleParam(workbook, sheet, easyExcelFiledPoint));
            easyExcelFiledPoint.setCellStyle(tempStyle);
        }
        return easyExcelFiledPointList;
    }


    /**
     * 处理 创建表头信息
     *
     * @param hssfSheet
     * @param easyExcelPoint
     * @param columnIndex
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<EasyExcelFiledPoint> handlerCreateRowTitle(Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List<EasyExcelFiledPoint> excelFiledPointList, int columnIndex) throws InstantiationException, IllegalAccessException {


        Workbook workbook = hssfSheet.getWorkbook();
        // 创建表头
        Sheet sheet = hssfSheet;
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        CellStyle style = workbook.getCellStyleAt(columnIndex);
        if (style == null) {
            style = workbook.createCellStyle();
        }

        List<String> peek = DynamicEasyExcelContextHolder.peek();
        if (!ObjectUtils.isEmpty(peek)) {
            excelFiledPointList = excelFiledPointList.stream().filter(easyExcelFiledPoint -> !peek.contains(easyExcelFiledPoint.getField().getName())).collect(Collectors.toList());
        }

        // 获取数据实体类型

        // 产生表格标题行
        Row row = sheet.getRow(TITLE_COLUMN);
        if (row == null) {
            row = sheet.createRow(TITLE_COLUMN);
        }

        for (EasyExcelFiledPoint easyExcelFiledPoint : excelFiledPointList) {
            Integer currentColumnIndex = easyExcelFiledPoint.getCurrentColumnIndex();
            if (easyExcelFiledPoint.isBeanFiled() || easyExcelFiledPoint.isCollectionFiled()) {
                // 递归
                handlerCreateRowTitle(hssfSheet, easyExcelPoint, easyExcelFiledPoint.getExcelBeanFiledPointList(), 0);
                continue;
            }
            Cell hssfCell = row.getCell(currentColumnIndex);
            if (null == hssfCell) {
                hssfCell = row.createCell(currentColumnIndex);
            }
            hssfCell.setCellStyle(style);
            String headerName;
            if (easyExcelPoint.isUseAnnotation()) {
                Class<? extends Style> styleClass = easyExcelPoint.getStyle();
                Style newInstance = styleClass.newInstance();
                CellStyle tempStyle = newInstance.titleStyle(new DefaultStyleParam(workbook, sheet, easyExcelFiledPoint));
                hssfCell.setCellStyle(tempStyle);
                headerName = easyExcelFiledPoint.getName();
                String[] dropdownOptions = easyExcelFiledPoint.getDropdownOptions();
                if (!ObjectUtils.isEmpty(dropdownOptions)) {
                    DataValidationHelper helper = sheet.getDataValidationHelper();

                    CellRangeAddressList addressList = new CellRangeAddressList(1, 9999, currentColumnIndex, currentColumnIndex + 1);
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

            } else {
                headerName = easyExcelFiledPoint.getName();
            }
            HSSFRichTextString text = new HSSFRichTextString(headerName);
            setRowColumnContent(hssfCell, text);
        }
        return excelFiledPointList;

    }

    /**
     * 创建表格标题行
     *
     * @param workbook
     * @param hssfSheet      sheet
     * @param easyExcelPoint
     * @param beanList       需要导出的原始数据
     * @param columnIndex
     * @return
     */
    @Override
    public List<EasyExcelFiledPoint> createRowTitle(Workbook workbook, Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List<?> beanList, int columnIndex) throws InstantiationException, IllegalAccessException {

        // 创建表头
        Sheet sheet = hssfSheet;
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);

        // 设置标题样式
//            style = ExcelStyle.setHeadStyle(workbook, style);
        List<EasyExcelFiledPoint> excelFiledPointList;
        if (EasyExcelPoint.ExportTypeEnum.JAVA_BEAN.equals(easyExcelPoint.getExportTypeEnum())) {
            Object firstBean = beanList.stream().filter(Objects::nonNull).findFirst().get();

            Class<?> firstBeanClass = firstBean.getClass();
            excelFiledPointList = handlerBeanRowTitle(workbook, sheet, easyExcelPoint, firstBeanClass, 0);
        } else {
            excelFiledPointList = handlerMapRowTitle(workbook, sheet, easyExcelPoint, beanList, 0);
        }
        List<EasyExcelFiledPoint> peekOnlyExportField = DynamicEasyExcelContextHolder.peekOnlyExportField();
        if (peekOnlyExportField == null) {
            List<String> peek = DynamicEasyExcelContextHolder.peekIgnoreField();
            if (!ObjectUtils.isEmpty(peek)) {
                excelFiledPointList = excelFiledPointList.stream().filter(easyExcelFiledPoint -> !peek.contains(easyExcelFiledPoint.getFieldName())).collect(Collectors.toList());
            }
        } else {
            excelFiledPointList = peekOnlyExportField;
        }


        // 获取数据实体类型
        if (easyExcelPoint.isTitleFixedHead()) {
            sheet.createFreezePane(excelFiledPointList.size(), TITLE_COLUMN + 1);
        }
        // 执行创建表头
        handlerCreateRowTitle(hssfSheet, easyExcelPoint, excelFiledPointList, columnIndex);
        return excelFiledPointList;

    }

    /**
     * 填充 一行数据
     *
     * @param hssfSheet           sheet
     * @param easyExcelPoint
     * @param excelFiledPointList 字段信息
     * @param rowData             填充数据
     * @param cellCoordinate      最后一个表格的坐标
     * @param isDeepMerge         是否深度合并默认否
     */
    public CellCoordinate handlerPadOneRowData(Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List<EasyExcelFiledPoint> excelFiledPointList, Object rowData, CellCoordinate cellCoordinate, Boolean isDeepMerge) {

        int lastCellStartRowIndex = cellCoordinate.getStartRowIndex();// 上一个表格 起始行
        int lastCellEndRowIndex = cellCoordinate.getEndRowIndex(); // 上一个表格 结束行
        int lastCellStartColumnIndex = cellCoordinate.getStartColumnIndex(); // 上一个表格 开始列
        int lastCellEndColumnIndex = cellCoordinate.getEndColumnIndex(); // 上一个表格 结束列

        int startRowIndex = cellCoordinate.getEndRowIndex();

        try {
            // 循环整个集合
            List<CellStyle> columnStyleList = new LinkedList<>();

            //从第二行开始写，第一行是标题
            Row row = hssfSheet.getRow(startRowIndex);
            if (row == null) {
                row = hssfSheet.createRow(startRowIndex);
            }
            // 字段获取样式


            for (EasyExcelFiledPoint easyExcelFiledPoint : excelFiledPointList) {

                // 当前列
                Integer currentColumnIndex = easyExcelFiledPoint.getCurrentColumnIndex();

                Field field = easyExcelFiledPoint.getField();

                Object fieldValue = field.get(rowData);
                if (easyExcelFiledPoint.isBeanFiled()) {
                    if (null == fieldValue) {
                        cellCoordinate.setEndColumnIndex(currentColumnIndex + easyExcelFiledPoint.getExcelBeanFiledPointList().size());
                        continue;
                    }
                    cellCoordinate.setEndColumnIndex(currentColumnIndex);
                    cellCoordinate = handlerPadOneRowData(hssfSheet, easyExcelPoint, easyExcelFiledPoint.getExcelBeanFiledPointList(), fieldValue, cellCoordinate, isDeepMerge);
                    // 默认将结束的行加一此处需要缩减回去
                    cellCoordinate.setEndRowIndex(cellCoordinate.getEndRowIndex() - 1);
                } else if (easyExcelFiledPoint.isCollectionFiled()) {
                    List<?> fieldValueList = (List<?>) fieldValue;
                    if (ObjectUtils.isEmpty(fieldValueList)) {
                        cellCoordinate.setEndColumnIndex(cellCoordinate.getEndColumnIndex() + 1);
                        continue;
                    }

                    cellCoordinate.setEndColumnIndex(currentColumnIndex);
                    cellCoordinate.setStartRowIndex(startRowIndex);
                    cellCoordinate = handlerPadRowData(hssfSheet, easyExcelPoint, easyExcelFiledPoint.getExcelBeanFiledPointList(), fieldValueList, cellCoordinate, true);
                    // 默认将结束的行加一此处需要缩减回去
                    cellCoordinate.setEndRowIndex(cellCoordinate.getEndRowIndex() - 1);

                    Integer endColumnIndex = currentColumnIndex - 1;
                    CellCoordinate mergeCellCoordinate = new CellCoordinate();
                    mergeCellCoordinate.setStartRowIndex(isDeepMerge ? cellCoordinate.getStartRowIndex() : startRowIndex);
                    mergeCellCoordinate.setEndRowIndex(cellCoordinate.getEndRowIndex());

                    EasyExcelFiledPoint miniColumnEasyExcelFiledPoint = excelFiledPointList.stream().min(Comparator.comparing(EasyExcelFiledPoint::getCurrentColumnIndex)).get();

                    mergeCellCoordinate.setStartColumnIndex(miniColumnEasyExcelFiledPoint.getCurrentColumnIndex());
                    mergeCellCoordinate.setEndColumnIndex(endColumnIndex);
                    mergeColumn(mergeCellCoordinate, hssfSheet, 1);

                } else {

                    Cell hssfCell = row.createCell(currentColumnIndex);
                    hssfCell.setCellStyle(easyExcelFiledPoint.getCellStyle());
                    setRowColumnContent(hssfCell, fieldValue);
                    cellCoordinate.setEndColumnIndex(currentColumnIndex);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 数据写完后把上一层级数据进行合并
        if (isDeepMerge) {
            // 合并
            CellCoordinate mergeCellCoordinate = new CellCoordinate();
            // 开始、结束行
            mergeCellCoordinate.setStartRowIndex(lastCellStartRowIndex);
            mergeCellCoordinate.setEndRowIndex(cellCoordinate.getEndRowIndex());

            // 当前最小、最大的一列

            EasyExcelFiledPoint miniColumnEasyExcelFiledPoint = excelFiledPointList.stream().min(Comparator.comparing(EasyExcelFiledPoint::getCurrentColumnIndex)).get();
            EasyExcelFiledPoint maxColumnEasyExcelFiledPoint = excelFiledPointList.stream().max(Comparator.comparing(EasyExcelFiledPoint::getCurrentColumnIndex)).get();
            // 开始、结束列
            mergeCellCoordinate.setStartColumnIndex(miniColumnEasyExcelFiledPoint.getCurrentColumnIndex());
            mergeCellCoordinate.setEndColumnIndex(maxColumnEasyExcelFiledPoint.getCurrentColumnIndex()-1);
            mergeColumn(mergeCellCoordinate, hssfSheet, 1);
        }
        cellCoordinate.setEndRowIndex(cellCoordinate.getEndRowIndex() + 1);
        return cellCoordinate;
    }


    /**
     * 填充数据 处理
     *
     * @param hssfSheet      sheet
     * @param easyExcelPoint 字段信息
     * @param rowData        填充数据
     * @param isDeepMerge    是否深度合并，默认否
     */
    public CellCoordinate handlerPadRowData(Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List<EasyExcelFiledPoint> excelFiledPointList, List rowData, CellCoordinate cellCoordinate, Boolean isDeepMerge) {

        // 开始写数据的上一个表格的左上角到右下角位置
        CellCoordinate startCellCoordinate = cellCoordinate;

        CellCoordinate afterCellCoordinate = cellCoordinate;
        // 循环整个集合
        for (Object rowDatum : rowData) {
            afterCellCoordinate = handlerPadOneRowData(hssfSheet, easyExcelPoint, excelFiledPointList, rowDatum, afterCellCoordinate, isDeepMerge);
            cellCoordinate = afterCellCoordinate;
        }

        return cellCoordinate;
    }

    /**
     * 填充数据
     *
     * @param hssfSheet      sheet
     * @param easyExcelPoint 字段信息
     * @param rowData        填充数据
     * @param rowIndex       行
     * @param columnIndex    列
     */
    @Override
    public void padRowData(Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List rowData, int rowIndex, int columnIndex) {
        List<EasyExcelFiledPoint> excelFiledPointList = easyExcelPoint.getExcelFiledPointList();
        //填充数据 处理
        CellCoordinate cellCoordinate = new CellCoordinate();
        cellCoordinate.setStartRowIndex(rowIndex);
        cellCoordinate.setEndRowIndex(rowIndex);
        cellCoordinate.setStartColumnIndex(columnIndex);
        cellCoordinate.setEndColumnIndex(columnIndex);
        handlerPadRowData(hssfSheet, easyExcelPoint, excelFiledPointList, rowData, cellCoordinate, false);
    }
}
