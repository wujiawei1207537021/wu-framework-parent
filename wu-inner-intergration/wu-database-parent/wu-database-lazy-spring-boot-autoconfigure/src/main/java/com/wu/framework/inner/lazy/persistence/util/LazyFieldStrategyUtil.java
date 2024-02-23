package com.wu.framework.inner.lazy.persistence.util;

import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import org.springframework.util.ObjectUtils;

/**
 * LazyFieldStrategy 策略工具
 * 验证参数是否满足 LazyFieldStrategy
 */
public class LazyFieldStrategyUtil {

    /**
     * 验证字段是否满足update对应的策略
     *
     * @param fieldValue        字段
     * @param lazyFieldStrategy 字段策略
     * @return Boolean
     */
    public static Boolean testUpdate(Object fieldValue, LazyFieldStrategy lazyFieldStrategy) {
        switch (lazyFieldStrategy) {
            case NEVER -> {
                return false;
            }
            case IGNORED_NULL -> {
                return !ObjectUtils.isEmpty(fieldValue);
            }
            case NO_VERIFY -> {
                return true;
            }
        }
        return false;
    }

}
