package com.wu.framework.inner.layer.toolkit;


import org.springframework.util.ObjectUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;


/**
 * describe: 核心基于ThreadLocal的 属性获取
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/6/11 18:01
 */
public final class DynamicLazyAttributeContextHolder {

    /**
     * 为什么要用链表存储(准确的是栈)
     * <pre>
     * 为了支持嵌套切换，如ABC三个service都是不同的属性
     * 其中A的某个业务要调B的方法，B的方法需要调用C的方法。一级一级调用切换，形成了链。
     * 传统的只设置当前线程的方式不能满足此业务需求，必须模拟栈，后进先出。
     * </pre>
     */
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Deque<Map<Class, Object>>> LOOKUP_KEY_HOLDER = ThreadLocal.withInitial(ArrayDeque::new);

    private DynamicLazyAttributeContextHolder() {
    }

    /**
     * 获得当前线程属性
     *
     * @return 属性名称
     */
    public static <T> T peek(Class clazz) {
        Map<Class, Object> peek = peek();
        if (ObjectUtils.isEmpty(peek)) {
            return null;
        }
        return (T) LOOKUP_KEY_HOLDER.get().peek().get(clazz);
    }

    /**
     * 获得当前线程属性
     *
     * @return 属性名称
     */
    public static Map<Class, Object> peek() {
        return LOOKUP_KEY_HOLDER.get().peek();
    }

    /**
     * 设置当前线程属性
     * <p>
     * 如非必要不要手动调用，调用后确保最终清除
     * </p>
     *
     * @param attribute 属性名称
     */
    public static <T> void push(Class clazz, T attribute) {
        if (null == attribute) {
            return;
        }
        Map<Class, Object> peek = peek();
        if (null == peek) {
            peek = new HashMap<>();
        }
        peek.put(clazz, attribute);
        LOOKUP_KEY_HOLDER.get().push(peek);
    }

    /**
     * 清空当前线程属性
     * <p>
     * 如果当前线程是连续切换属性
     * 只会移除掉当前线程的属性名称
     * </p>
     */
    public static void poll() {
        Deque<Map<Class, Object>> deque = LOOKUP_KEY_HOLDER.get();
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

    /**
     * 强制清空本地线程
     * <p>
     * 防止内存泄漏，如手动调用了push可调用此方法确保清除
     * </p>
     */
    public static void clear(Class clazz) {
        Map<Class, Object> peek = LOOKUP_KEY_HOLDER.get().peek();
        if (null == peek) {
            return;
        }
        peek.remove(clazz);
        clear();
        LOOKUP_KEY_HOLDER.get().push(peek);
    }
}

