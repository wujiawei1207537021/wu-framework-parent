package com.wu.framework.authorization.toolkit;


import com.wu.framework.authorization.model.UserDetails;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/8 8:55 下午
 */
public class AuthorizationContextHolder {
    private static final ThreadLocal<Deque<UserDetails>> LOOKUP_KEY_HOLDER = ThreadLocal.withInitial(ArrayDeque::new);

    /**
     * 获得当前线程授权用户信息
     *
     * @return 授权用户信息
     */
    public static UserDetails currentAuthorization() {
        return LOOKUP_KEY_HOLDER.get().peek();
    }


    /**
     * 设置当前线程授权用户信息
     * <p>
     * 如非必要不要手动调用，调用后确保最终清除
     * </p>
     *
     * @param userDetails 授权用户信息
     */
    public static void setCurrentAuthorization(UserDetails userDetails) {
        LOOKUP_KEY_HOLDER.get().push(userDetails);
    }

    /**
     * 清空当前线程授权用户信息
     * <p>
     * 如果当前线程是连续切换授权用户信息
     * 只会移除掉当前线程的授权用户信息
     * </p>
     */
    public static void pollCurrentAuthorization() {
        Deque<UserDetails> deque = LOOKUP_KEY_HOLDER.get();
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
