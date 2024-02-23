package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.advanced;

import org.springframework.core.Ordered;

/**
 * description 数据库ORM转译适配者
 *
 * @author 吴佳伟
 * @date 2023/09/22 13:43
 */
public interface LazyTranslationAdvanced extends Ordered {
    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    void transformation(Object source);

    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    boolean support(Object source);
}
