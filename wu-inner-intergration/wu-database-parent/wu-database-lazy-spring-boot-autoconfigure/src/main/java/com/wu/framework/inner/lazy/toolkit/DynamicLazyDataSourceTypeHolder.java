package com.wu.framework.inner.lazy.toolkit;


import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;

import javax.sql.DataSource;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 运行时切换上下文数据源类型
 * @see SourceFactory
 */
public final class DynamicLazyDataSourceTypeHolder {

    /**
     * 为什么要用链表存储(准确的是栈)
     * <pre>
     * 为了支持嵌套切换，如ABC三个service都是不同的数据源类型
     * 其中A的某个业务要调B的方法，B的方法需要调用C的方法。一级一级调用切换，形成了链。
     * 传统的只设置当前线程的方式不能满足此业务需求，必须模拟栈，后进先出。
     * </pre>
     */
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Deque<LazyDataSourceType>> LOOKUP_KEY_HOLDER = ThreadLocal.withInitial(ArrayDeque::new);

    private DynamicLazyDataSourceTypeHolder() {
    }

    /**
     * 获得当前线程数据源类型
     *
     * @return 数据源类型名称
     */
    public static LazyDataSourceType peek() {
        return LOOKUP_KEY_HOLDER.get().peek();
    }


    /**
     * 设置当前线程数据源类型
     * <p>
     * 如非必要不要手动调用，调用后确保最终清除
     * </p>
     *
     * @param ds 数据源类型名称
     */
    public static void push(LazyDataSourceType ds) {
        LOOKUP_KEY_HOLDER.get().push(ds);
    }

    /**
     * 清空当前线程数据源类型
     * <p>
     * 如果当前线程是连续切换数据源类型
     * 只会移除掉当前线程的数据源类型名称
     * </p>
     */
    public static void poll() {
        Deque<LazyDataSourceType> deque = LOOKUP_KEY_HOLDER.get();
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

