package com.wu.framework.easy.upsert.core.dynamic.toolkit;


import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;

import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 核心基于ThreadLocal的切换数据源工具类
 * @date : 2021/7/4 5:50 下午
 * @see {@link EasyUpsert}
 */
public final class DynamicEasyUpsertContextHolder {

    /**
     * 为什么要用链表存储(准确的是栈)
     * <pre>
     * 为了支持嵌套切换，如ABC三个service都是不同的数据源
     * 其中A的某个业务要调B的方法，B的方法需要调用C的方法。一级一级调用切换，形成了链。
     * 传统的只设置当前线程的方式不能满足此业务需求，必须模拟栈，后进先出。
     * </pre>
     */
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Deque<EasyUpsert>> LOOKUP_KEY_HOLDER = ThreadLocal.withInitial(ArrayDeque::new);

    private DynamicEasyUpsertContextHolder() {
    }

    /**
     * 获得当前线程数据源
     *
     * @return 数据源名称
     */
    public static EasyUpsert peek() {
        return LOOKUP_KEY_HOLDER.get().peek();
    }

    public static <T extends Annotation> T peek(Class<T> easyUpsertType) {
        Annotation annotation = peek();
        if (annotation != null && annotation.annotationType().isAnnotationPresent(easyUpsertType)) {
            return annotation.annotationType().getAnnotation(easyUpsertType);
        } else {
            throw new IllegalArgumentException(String.format("无法找到 类型%s的注解", easyUpsertType));
        }
    }

    /**
     * 设置当前线程数据源
     * <p>
     * 如非必要不要手动调用，调用后确保最终清除
     * </p>
     *
     * @param ds 数据源名称
     */
    public static void push(EasyUpsert ds) {
        LOOKUP_KEY_HOLDER.get().push(ds);
    }

    /**
     * 清空当前线程数据源
     * <p>
     * 如果当前线程是连续切换数据源
     * 只会移除掉当前线程的数据源名称
     * </p>
     */
    public static void poll() {
        Deque<EasyUpsert> deque = LOOKUP_KEY_HOLDER.get();
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
