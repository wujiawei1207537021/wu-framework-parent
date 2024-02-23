package com.wu.framework.inner.lazy.stereotype;

/**
 * 字段策略枚举类
 * <p>
 * 如果字段是基本数据类型则最终效果等同于 {@link #IGNORED}
 *
 * @author wujiawei
 * @since 2016-09-09
 */
public enum LazyFieldStrategy {
    /**
     * 忽略判断（什么都不操作）
     *
     * @see LazyFieldStrategy#NO_VERIFY
     */
    IGNORED,
    /**
     * 不验证 什么都不操作
     */
    NO_VERIFY,
    /**
     * 忽略null
     */
    IGNORED_NULL,
    /**
     * null 数据会被过滤
     */
    NOT_NULL,
    /**
     * 空数据会被过滤
     */
    NOT_EMPTY,
    /**
     * 不加入 SQL 也不加入DUPLICATE
     */
    NEVER,
    /**
     * 不加入 DUPLICATE KEY
     */
    NEVER_JOIN_DUPLICATE_KEY
}