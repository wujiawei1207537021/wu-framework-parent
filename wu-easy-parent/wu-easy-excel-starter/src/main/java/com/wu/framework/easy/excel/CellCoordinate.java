package com.wu.framework.easy.excel;

import lombok.Data;

/**
 * description 单元格坐标
 *
 * @author Jia wei Wu
 * @date 2022/12/15 3:32 下午
 */
@Data
public class CellCoordinate {

    /**
     * 开始行
     */
    private int startRowIndex;
    /**
     * 结束行
     */
    private int endRowIndex;
    /**
     * 开始列
     */
    private int startColumnIndex;
    /**
     * 结束列
     */
    private int endColumnIndex;
}
