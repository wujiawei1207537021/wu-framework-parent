package com.wu.framework.easy.excel.processor;

import com.wu.framework.easy.excel.CellCoordinate;
import com.wu.framework.easy.excel.endpoint.EasyExcelFiledPoint;
import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.util.ISheetShowContextMethod;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * description 抽象excel 处理类
 *
 * @author 吴佳伟
 * @date 2023/06/29 20:50
 */
public abstract class AbstractEasyExcelProcessor implements EasyExcelProcessor {


    /**
     * 表头
     */
    int TITLE_COLUMN = 0;

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
    static void setRowColumnContent(Cell hssfCell, Object value) {
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
        } else if (valueClass.isAssignableFrom(RichTextString.class)) {
            hssfCell.setCellValue((RichTextString) value);
        } else {
            hssfCell.setCellValue(String.valueOf(value));
        }
    }

    /**
     * 是否基本数据类型
     *
     * @param clazz
     * @return
     */
    public static boolean isWrapClass(Class<?> clazz) {
        try {
            if (String.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (Byte[].class.isAssignableFrom(clazz)) {
                return true;
            }
            if (Byte.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (byte[].class.isAssignableFrom(clazz)) {
                return true;
            }
            if (byte.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (Long.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (long.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (Integer.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (int.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (short.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (Float.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (float.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (Boolean.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (boolean.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (LocalDateTime.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (LocalDate.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (java.sql.Date.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (java.sql.Date.class.isAssignableFrom(clazz)) {
                return true;
            }
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 导出excel
     *
     * @param excelBean
     * @param easyExcelPoint
     * @return
     */
    @Override
    public byte[] exportExcel(Object excelBean, EasyExcelPoint easyExcelPoint) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook workbook = createWorkbook(excelBean, easyExcelPoint);
        try {
            workbook.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }

    /**
     * 创建  Workbook
     *
     * @param excelBean
     * @param easyExcelPoint
     * @return
     */
    public Workbook createWorkbook(Object excelBean, EasyExcelPoint easyExcelPoint) {
        //        HSSFWorkbook workbook = new HSSFWorkbook();
        XSSFWorkbook workbook = new XSSFWorkbook();      // 最多只能创建 16384 列
        //HSSFWorkbook workbook = new HSSFWorkbook();    // 最多只能创建 256   列
        List<Object> excelBeanList;
        if (excelBean instanceof Collection) {
            excelBeanList = Arrays.asList(((Collection) excelBean).toArray());
        } else {
            excelBeanList = Arrays.asList(excelBean);
        }
        try {
            //        SXSSFWorkbook
            // 多工作簿
            if (easyExcelPoint.isMultipleSheet()) {
                List<List> splitList = splitList(excelBeanList, easyExcelPoint.getLimit());
                ISheetShowContextMethod iSheetShowContextMethod = easyExcelPoint.getSheetShowContext().getISheetShowContextMethod().newInstance();
                List<String> sheetContextList = iSheetShowContextMethod.sheetContext(excelBeanList.size(), easyExcelPoint.getLimit());
                for (int i = 0; i < splitList.size(); i++) {
                    easyExcelPoint.setSheetName(sheetContextList.get(i));
                    processorSheet(workbook, i, easyExcelPoint, splitList.get(i));
                }
            } else {
                processorSheet(workbook, 0, easyExcelPoint, excelBeanList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 导出excel到文件
     *
     * @param excelBean        excel 对象
     * @param easyExcelPoint   Excel 的配置信息
     * @param fileOutputStream 文件输出流
     * @return
     */
    @Override
    public void exportExcel(Object excelBean, EasyExcelPoint easyExcelPoint, FileOutputStream fileOutputStream) throws IOException {
        Workbook workbook = createWorkbook(excelBean, easyExcelPoint);
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /***
     * 处理sheet
     * @param workbook 画布
     * @param sheetIndex  sheet 位置
     * @param easyExcel 导出实体数据
     * @param sheetList 导出苏韩剧
     */
    public void processorSheet(Workbook workbook, int sheetIndex, EasyExcelPoint easyExcel, List sheetList) {

        try {
            //首先检查数据看是否是正确的
            Iterator iterator = sheetList.iterator();
            if (!iterator.hasNext()) {
                throw new Exception("数据错误");
            }
            //取得实际泛型类
            Object ts = iterator.next();

            // 支持多个sheet 导出
            if (Collection.class.isAssignableFrom(ts.getClass())) {
                for (int i = 0; i < sheetList.size(); i++) {
                    easyExcel.setSheetName(easyExcel.getSheetName() + "-1");
                    processorSheet(workbook, sheetIndex + i, easyExcel, (List) sheetList.get(i));
                }
                return;
            }
            /**
             * 处理单个表格
             */
            processorSingleSheet(workbook, sheetIndex, easyExcel, sheetList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充Map数据
     *
     * @param hssfSheet      sheet
     * @param easyExcelPoint 字段信息
     * @param rowData        填充数据
     * @param rowIndex       行
     * @param columnIndex    列
     */
    public abstract void padMapRowData(Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List<Map<?, ?>> rowData, int rowIndex, int columnIndex);

    // 填充数据
    // 递归处理

    /**
     * 处理单个sheet 数据导出
     *
     * @param workbook   画布
     * @param sheetIndex sheet 索引
     * @param easyExcel  导出配置信息
     * @param beanList   导出对象
     */
    public void processorSingleSheet(Workbook workbook, int sheetIndex, EasyExcelPoint easyExcel, List<?> beanList) {
        try {
            // 创建sheet
            Sheet singleSheet = createSingleSheet(workbook, easyExcel, beanList);
            if (beanList.stream().findFirst().isEmpty()) {
                throw new RuntimeException("数据有误");
            }
            Object firstBean = beanList.stream().findFirst().get();

            Class<?> firstBeanClass = firstBean.getClass();
            // 如果是map、基础数据类型、JavaBean
            if (isWrapClass(firstBeanClass)) {
                return;
            } else if (Map.class.isAssignableFrom(firstBeanClass)) {
                // 创建MAP表头
                easyExcel.setExportTypeEnum(EasyExcelPoint.ExportTypeEnum.MAP);
                List<EasyExcelFiledPoint> easyExcelFiledPointList = createRowTitle(workbook, singleSheet, easyExcel, beanList, 0);
                easyExcel.setExcelFiledPointList(easyExcelFiledPointList);
                padMapRowData(singleSheet, easyExcel, (List<Map<?, ?>>) beanList, 1, 0);
            } else {
                // 创建表头
                easyExcel.setExportTypeEnum(EasyExcelPoint.ExportTypeEnum.JAVA_BEAN);
                List<EasyExcelFiledPoint> easyExcelFiledPointList = createRowTitle(workbook, singleSheet, easyExcel, beanList, 0);
                easyExcel.setExcelFiledPointList(easyExcelFiledPointList);
                // 填充数据
                padRowData(singleSheet, easyExcel, beanList, 1, 0);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建sheet
     *
     * @param workbook
     * @param easyExcel
     * @param beanList
     * @return
     */
    public Sheet createSingleSheet(Workbook workbook, EasyExcelPoint easyExcel, List beanList) throws InstantiationException, IllegalAccessException {

        // 创建表头
        Sheet sheet = workbook.createSheet(easyExcel.getSheetName());
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        return sheet;
    }

    /**
     * 创建表格标题行
     *
     * @param workbook
     * @param hssfSheet      sheet
     * @param easyExcelPoint
     * @param beanLis        需要导出的原始数据
     * @param columnIndex    表头的索引 第一次默认是 0
     * @return
     */
    public abstract List<EasyExcelFiledPoint> createRowTitle(Workbook workbook, Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List<?> beanLis, int columnIndex) throws InstantiationException, IllegalAccessException;

    /**
     * 填充数据
     *
     * @param hssfSheet      sheet
     * @param easyExcelPoint 字段信息
     * @param rowData        填充数据
     * @param rowIndex       行
     * @param columnIndex    列
     */
    public abstract void padRowData(Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List rowData, int rowIndex, int columnIndex);

    /**
     * 优先级 数字越大优先级越高
     *
     * @return
     */
    @Override
    public int order() {
        return 0;
    }

    /**
     * 获取多层级列
     *
     * @param easyExcelFiledPoints
     * @return
     */
    public Integer getLastColumnIndex(List<EasyExcelFiledPoint> easyExcelFiledPoints, Integer defaultLastColumnIndex) {
        if (easyExcelFiledPoints.isEmpty()) {
            return defaultLastColumnIndex;
        }
        EasyExcelFiledPoint easyExcelFiledPoint = easyExcelFiledPoints.stream().max(Comparator.comparing(EasyExcelFiledPoint::getCurrentColumnIndex)).get();
        if (ObjectUtils.isEmpty(easyExcelFiledPoint.getExcelBeanFiledPointList())) {
            return easyExcelFiledPoint.getCurrentColumnIndex();
        }
        return getLastColumnIndex(easyExcelFiledPoint.getExcelBeanFiledPointList(), defaultLastColumnIndex);
//        return defaultLastColumnIndex;
    }

    /**
     * 合并列
     *
     * @param cellCoordinate    合并位置右下角坐标
     *                          （1，2
     *                          2，2
     *                          ）
     * @param hssfSheet
     * @param mergeColumnLength 合并列长度
     */
    public void mergeColumn(CellCoordinate cellCoordinate, Sheet hssfSheet, int mergeColumnLength) {
        int startRowIndex = cellCoordinate.getStartRowIndex();
        int endRowIndex = cellCoordinate.getEndRowIndex();
        int startColumnIndex = cellCoordinate.getStartColumnIndex();
        int endColumnIndex = cellCoordinate.getEndColumnIndex();
        if (startColumnIndex > endColumnIndex || startRowIndex > endRowIndex) {
            return;
        } else {
            // Merged region G9 must contain 2 or more cells 此时一行数据中的list只有一条数据 不进行合并
            if (startRowIndex == endRowIndex) {
                return;
            }
            // 上一级合并上下
            CellRangeAddress region = new CellRangeAddress(startRowIndex, endRowIndex, endColumnIndex - (mergeColumnLength - 1)// 合并两列实际上是1
                    , endColumnIndex);
            hssfSheet.addMergedRegionUnsafe(region);
            cellCoordinate.setEndColumnIndex(endColumnIndex - mergeColumnLength);
            mergeColumn(cellCoordinate, hssfSheet, mergeColumnLength);
        }

    }
}
