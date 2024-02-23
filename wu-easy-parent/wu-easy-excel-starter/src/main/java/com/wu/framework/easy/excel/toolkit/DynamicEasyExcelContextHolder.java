package com.wu.framework.easy.excel.toolkit;


import com.wu.framework.easy.excel.endpoint.EasyExcelFiledPoint;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * 核心基于ThreadLocal的Excel 行数据动态切换删除列、动态选择只导出指定列
 * @date : 2021/7/4 5:50 下午
 */
public final class DynamicEasyExcelContextHolder {

    /**
     * 删除字段
     * <pre>
     * </pre>
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Deque<List<String>>> LOOKUP_KEY_HOLDER = ThreadLocal.withInitial(ArrayDeque::new);


    private DynamicEasyExcelContextHolder() {
    }

    /**
     * 获得当前线程数据源
     *
     * @return 删除的列
     */
    public static List<String> peek() {
        return LOOKUP_KEY_HOLDER.get().peek();
    }

    /**
     * 设置当前线程数据源
     * <p>
     * 如非必要不要手动调用，调用后确保最终清除
     * </p>
     *
     * @param ds 删除的列
     */
    public static void push(List<String> ds) {
        LOOKUP_KEY_HOLDER.get().push(ds);
    }

    /**
     * 清空当前线程数据源
     * <p>
     * 如果当前线程是连续切换数据源
     * 只会移除掉当前线程的删除的列
     * </p>
     */
    public static void poll() {
        Deque<List<String>> deque = LOOKUP_KEY_HOLDER.get();
        deque.poll();
        if (deque.isEmpty()) {
            LOOKUP_KEY_HOLDER.remove();
        }
    }

    /**
     * 强制清空本地线程
     * <p>
     * 防止内存泄漏，如手动调用了push可调用此方法确保清除
     * </p>
     */
    public static void clear() {
        LOOKUP_KEY_HOLDER.remove();
    }


//----------------------------------------------------------------------------------------------------------------------
    /**
     * 导出Excel忽略字段
     */
    private static final ThreadLocal<Deque<List<String>>> EXCEL_IGNORE_FIELD = ThreadLocal.withInitial(ArrayDeque::new);
//----------------------------------------------------------------------------------------------------------------------

    /**
     * 获取当前忽略字段
     *
     * @return 删除的列对应的字段名
     */
    public static List<String> peekIgnoreField() {
        return EXCEL_IGNORE_FIELD.get().peek();
    }

    /**
     * 设置当前忽略字段
     *
     * @param ds 删除的列
     */
    public static void pushIgnoreField(List<String> ds) {
        EXCEL_IGNORE_FIELD.get().push(ds);
    }

    /**
     * 清空当前忽略字段
     */
    public static void pollIgnoreField() {
        Deque<List<String>> deque = EXCEL_IGNORE_FIELD.get();
        deque.poll();
        if (deque.isEmpty()) {
            EXCEL_IGNORE_FIELD.remove();
        }
    }

    /**
     * 强制清空当前忽略字段
     */
    public static void clearIgnoreField() {
        EXCEL_IGNORE_FIELD.remove();
    }

//----------------------------------------------------------------------------------------------------------------------


//----------------------------------------------------------------------------------------------------------------------
    /**
     * 只导出Excel字段
     */
    private static final ThreadLocal<Deque<List<EasyExcelFiledPoint>>> ONLY_EXPORT_EXCEL_FIELD = ThreadLocal.withInitial(ArrayDeque::new);

    /**
     * 获取当前只导出Excel字段
     *
     * @return 删除的列
     */
    public static List<EasyExcelFiledPoint> peekOnlyExportField() {
        return ONLY_EXPORT_EXCEL_FIELD.get().peek();
    }

    /**
     * 设置只导出Excel字段
     *
     * @param ds 删除的列
     */
    public static void pushOnlyExportField(List<EasyExcelFiledPoint> ds) {
        ONLY_EXPORT_EXCEL_FIELD.get().push(ds);
    }

    /**
     * 清空当前只导出Excel字段
     */
    public static void pollOnlyExportField() {
        Deque<List<EasyExcelFiledPoint>> deque = ONLY_EXPORT_EXCEL_FIELD.get();
        deque.poll();
        if (deque.isEmpty()) {
            ONLY_EXPORT_EXCEL_FIELD.remove();
        }
    }

    /**
     * 强制清空当前只导出Excel字段
     */
    public static void clearOnlyExportField() {
        ONLY_EXPORT_EXCEL_FIELD.remove();
    }
//----------------------------------------------------------------------------------------------------------------------


    /**
     * 清空excel相关的所有线程数据
     */
    public static void clearALL() {
        EXCEL_IGNORE_FIELD.remove();
        ONLY_EXPORT_EXCEL_FIELD.remove();
        LOOKUP_KEY_HOLDER.remove();
    }
}
