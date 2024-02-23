package com.wu.framework.easy.excel;

import lombok.Data;

/**
 * description 单元格合并模型
 *
 * @author Jia wei Wu
 * @date 2022/12/15 3:32 下午
 */
@Data
public class CellRangeAddressMerge {

    /**
     * 相同数据单元格所在行第一行
     */
    private int firstRow;
    /**
     * 相同数据单元格所在列第一列
     */
    private int firstCol;
    /**
     * 相同数据单元格所在行最后一行
     */
    private int lastRow;
    /**
     * 相同数据单元格所在列最后一列
     */
    private int lastCol;
    /**
     * 单元格数据第一个数据
     */
    private Object firstValue;

    /**
     * 单元格最后一个数据
     */
    private Object lastValue;
}
