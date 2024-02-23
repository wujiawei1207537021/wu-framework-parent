package com.wu.framework.easy.excel.toolkit;


import com.wu.framework.easy.excel.endpoint.EasyFilePoint;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * 核心基于ThreadLocal的 导出文件配置信息
 * @date : 2021/7/4 5:50 下午
 */
public final class DynamicEasyFileContextHolder {

    /**
     * 删除字段
     * <pre>
     * </pre>
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Deque<EasyFilePoint>> LOOKUP_KEY_HOLDER = ThreadLocal.withInitial(ArrayDeque::new);


    private DynamicEasyFileContextHolder() {
    }

    /**
     * 获得当前线程 导出文件配置信息
     *
     * @return 删除的列
     */
    public static EasyFilePoint peek() {
        return LOOKUP_KEY_HOLDER.get().peek();
    }

    /**
     * 设置当前线程 导出文件配置信息
     * <p>
     * 如非必要不要手动调用，调用后确保最终清除
     * </p>
     *
     * @param easyFilePoint 导出文件配置信息
     */
    public static void push(EasyFilePoint easyFilePoint) {
        LOOKUP_KEY_HOLDER.get().push(easyFilePoint);
    }

    /**
     * 清空当前线程 导出文件配置信息
     * <p>
     * 如果当前线程是连续切换 导出文件配置信息
     * 只会移除掉当前线程的删除的列
     * </p>
     */
    public static void poll() {
        Deque<EasyFilePoint> deque = LOOKUP_KEY_HOLDER.get();
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


}
