package com.wu.framework.easy.excel.processor;

import com.wu.framework.easy.excel.CellCoordinate;
import com.wu.framework.easy.excel.endpoint.EasyExcelFiledPoint;
import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * description map 数据处理Excel
 *
 * @author 吴佳伟
 * @date 2023/06/29 20:37
 */
public abstract class AbstractNormalMapEasyExcelProcessor extends AbstractNormalBeanEasyExcelProcessor
        implements EasyExcelProcessor {
    /**
     * 是否支持excel 处理
     *
     * @param excelBean
     * @return
     */
    @Override
    public boolean support(Object excelBean) {
        return excelBean != null && Map.class.isAssignableFrom(excelBean.getClass());
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
     * 创建表格标题行
     *
     * @param workbook
     * @param hssfSheet      sheet
     * @param easyExcelPoint
     * @param beanLis
     * @param columnIndex    表头的索引 第一次默认是 0
     * @return
     */
    @Override
    public List<EasyExcelFiledPoint> createRowTitle(Workbook workbook, Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List<?> beanLis, int columnIndex) throws InstantiationException, IllegalAccessException {
        return super.createRowTitle(workbook, hssfSheet, easyExcelPoint, beanLis, columnIndex);
    }

    /**
     * 动态创建表格头
     *
     * @param hssfSheet
     * @param columnIndex
     * @return
     */
    public List<EasyExcelFiledPoint> dynamicCreateRowTitle(Sheet hssfSheet,
                                                           int columnIndex,
                                                           Object columnName
    ) {
        Row row = hssfSheet.getRow(TITLE_COLUMN);
        if (row == null) {
            row = hssfSheet.createRow(TITLE_COLUMN);
        }
        Cell cell = row.createCell(columnIndex);
        setRowColumnContent(cell, columnName);

        return null;
    }

//    /**
//     * 处理单个sheet 数据导出
//     *
//     * @param workbook
//     * @param easyExcel
//     * @param beanList
//     */
//    @Override
//    public void processorSingleSheet(Workbook workbook, EasyExcelPoint easyExcel, List beanList) {
//        try {
//            // 创建sheet
//            Sheet singleSheet = createSingleSheet(workbook, easyExcel, beanList);
//            if (!beanList.stream().findFirst().isPresent()) {
//                throw new RuntimeException("数据有误");
//            }
//            // 填充数据
//            padRowData(singleSheet, easyExcel, beanList, 1, 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 填充 一行数据
     *
     * @param hssfSheet      sheet
     * @param easyExcelPoint
     * @param rowData        填充数据
     * @param cellCoordinate
     */
    public CellCoordinate handlerPadOneMapRowData(Sheet hssfSheet,
                                                  EasyExcelPoint easyExcelPoint,
                                                  List<EasyExcelFiledPoint> excelFiledPointList,
                                                  Object rowData, CellCoordinate cellCoordinate,
                                                  Boolean isDeepMerge) {
        int startRowIndex = cellCoordinate.getEndRowIndex();

        int lastCellStartRowIndex = cellCoordinate.getStartRowIndex();// 上一个表格 起始行
        int lastCellEndRowIndex = cellCoordinate.getEndRowIndex(); // 上一个表格 结束行
        int lastCellStartColumnIndex = cellCoordinate.getStartColumnIndex(); // 上一个表格 开始列
        int lastCellEndColumnIndex = cellCoordinate.getEndColumnIndex(); // 上一个表格 结束列

        try {
            //从第二行开始写，第一行是标题
            Row row = hssfSheet.getRow(startRowIndex);
            if (row == null) {
                row = hssfSheet.createRow(startRowIndex);
            }
            // 字段获取样式
            for (EasyExcelFiledPoint easyExcelFiledPoint : excelFiledPointList) {

                // 当前列
                Integer currentColumnIndex = easyExcelFiledPoint.getCurrentColumnIndex();
                if (Map.class.isAssignableFrom(rowData.getClass())) {
                    String fieldName = easyExcelFiledPoint.getFieldName();
                    Object fieldValue = ((Map) rowData).get(fieldName);
                    if (easyExcelFiledPoint.isBeanFiled()) {

                        if (null == fieldValue) {
                            cellCoordinate.setEndColumnIndex(currentColumnIndex + easyExcelFiledPoint.getExcelBeanFiledPointList().size());
                            continue;
                        }
                        cellCoordinate.setEndColumnIndex(currentColumnIndex);
                        cellCoordinate = handlerPadOneMapRowData(hssfSheet, easyExcelPoint, easyExcelFiledPoint.getExcelBeanFiledPointList(), fieldValue, cellCoordinate, isDeepMerge);
                        // 默认将结束的行加一此处需要缩减回去
                        cellCoordinate.setEndRowIndex(cellCoordinate.getEndRowIndex() - 1);
                        continue;
                    } else if (easyExcelFiledPoint.isCollectionFiled()) {
                        List fieldValueList = (List) fieldValue;
                        if (ObjectUtils.isEmpty(fieldValueList)) {
                            cellCoordinate.setEndColumnIndex(cellCoordinate.getEndColumnIndex() + 1);
                            continue;
                        }

                        cellCoordinate.setEndColumnIndex(currentColumnIndex);
                        cellCoordinate.setStartRowIndex(startRowIndex);
                        cellCoordinate = handlerPadRowMapData(hssfSheet, easyExcelPoint, easyExcelFiledPoint.getExcelBeanFiledPointList(), fieldValueList, cellCoordinate, true);
                        // 默认将结束的行加一此处需要缩减回去
                        cellCoordinate.setEndRowIndex(cellCoordinate.getEndRowIndex() - 1);
                        // 上一级合并上下
//                    CellRangeAddress region = new CellRangeAddress(startRowIndex, cellCoordinate.getEndRowIndex(),
//                            currentColumnIndex, currentColumnIndex);
//                    sheet.addMergedRegionUnsafe(region);
                        Integer endColumnIndex = currentColumnIndex - 1;
                        CellCoordinate mergeCellCoordinate = new CellCoordinate();
                        mergeCellCoordinate.setStartRowIndex(isDeepMerge ? cellCoordinate.getStartRowIndex() : startRowIndex);
                        mergeCellCoordinate.setEndRowIndex(cellCoordinate.getEndRowIndex());

                        EasyExcelFiledPoint miniColumnEasyExcelFiledPoint = excelFiledPointList.stream().min(Comparator.comparing(EasyExcelFiledPoint::getCurrentColumnIndex)).get();

                        mergeCellCoordinate.setStartColumnIndex(miniColumnEasyExcelFiledPoint.getCurrentColumnIndex());
                        mergeCellCoordinate.setEndColumnIndex(endColumnIndex);
                        mergeColumn(mergeCellCoordinate, hssfSheet, 1);

                        continue;
                    } else {
                        Cell hssfCell = row.createCell(currentColumnIndex);
                        hssfCell.setCellStyle(easyExcelFiledPoint.getCellStyle());
                        setRowColumnContent(hssfCell, fieldValue);
                        cellCoordinate.setEndColumnIndex(currentColumnIndex);
                    }
                } else if (isWrapClass(rowData.getClass())) {
                    Cell hssfCell = row.createCell(currentColumnIndex);
                    hssfCell.setCellStyle(easyExcelFiledPoint.getCellStyle());
                    setRowColumnContent(hssfCell, rowData);
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
     */
    public CellCoordinate handlerPadRowMapData(Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List<EasyExcelFiledPoint> excelFiledPointList, List rowData, CellCoordinate cellCoordinate, Boolean isDeepMerge) {

        int columnIndex = cellCoordinate.getStartColumnIndex();
        // 循环整个集合
        for (Object rowDatum : rowData) {
            cellCoordinate = handlerPadOneMapRowData(hssfSheet, easyExcelPoint, excelFiledPointList, rowDatum, cellCoordinate, isDeepMerge);
            cellCoordinate.setStartColumnIndex(columnIndex);
            cellCoordinate.setStartRowIndex(cellCoordinate.getEndRowIndex());
        }
        return cellCoordinate;
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
    @Override
    public void padMapRowData(Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List<Map<?, ?>> rowData, int rowIndex, int columnIndex) {
        List<EasyExcelFiledPoint> excelFiledPointList = easyExcelPoint.getExcelFiledPointList();
        //填充数据 处理
        CellCoordinate cellCoordinate = new CellCoordinate();
        cellCoordinate.setStartRowIndex(rowIndex);
        cellCoordinate.setEndRowIndex(rowIndex);
        cellCoordinate.setStartColumnIndex(columnIndex);
        cellCoordinate.setEndColumnIndex(columnIndex);
        handlerPadRowMapData(hssfSheet, easyExcelPoint, excelFiledPointList, rowData, cellCoordinate, false);
    }

//    /**
//     * 填充数据
//     *
//     * @param hssfSheet      sheet
//     * @param easyExcelPoint 字段信息
//     * @param rowData        填充数据
//     * @param rowIndex       行
//     * @param columnIndex    列
//     */
//    @Override
//    public void padRowData(Sheet hssfSheet, EasyExcelPoint easyExcelPoint, List rowData, int rowIndex, int columnIndex) {
//
//        //填充数据 处理
//        CellCoordinate cellCoordinate = new CellCoordinate();
//        cellCoordinate.setStartRowIndex(rowIndex);
//        cellCoordinate.setStartColumnIndex(columnIndex);
//        handlerPadRowData(hssfSheet, easyExcelPoint, rowData, cellCoordinate);
//    }
}
