package com.wu.framework.easy.excel.toolkit;


import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * 核心基于ThreadLocal的Excel 行数据动态切换删除列、动态选择只导出指定列
 * @date : 2021/7/4 5:50 下午
 */
public final class DynamicEasyExcelPointContextHolder {


//----------------------------------------------------------------------------------------------------------------------
    /**
     * 导出Excel 文件名称、工作簿名字
     */
    private static final ThreadLocal<Deque<EasyExcelPoint>> EXCEL_POINT = ThreadLocal.withInitial(ArrayDeque::new);
//----------------------------------------------------------------------------------------------------------------------

    /**
     * 获取导出Excel 文件名称、工作簿名字
     *
     * @return 导出Excel 文件名称、工作簿名字
     */
    public static EasyExcelPoint peek() {
        return EXCEL_POINT.get().peek();
    }

    /**
     * 设置导出Excel 文件名称、工作簿名字
     *
     * @param easyExcelPoint 导出Excel 文件名称、工作簿名字
     */
    public static void push(EasyExcelPoint easyExcelPoint) {
        EXCEL_POINT.get().push(easyExcelPoint);
    }

    /**
     * 清空当前导出Excel 文件名称、工作簿名字
     */
    public static void poll() {
        Deque<EasyExcelPoint> deque = EXCEL_POINT.get();
        deque.poll();
        if (deque.isEmpty()) {
            EXCEL_POINT.remove();
        }
    }

    /**
     * 强制清空导出Excel 文件名称、工作簿名字
     */
    public static void clear() {
        EXCEL_POINT.remove();
    }

//----------------------------------------------------------------------------------------------------------------------

}
