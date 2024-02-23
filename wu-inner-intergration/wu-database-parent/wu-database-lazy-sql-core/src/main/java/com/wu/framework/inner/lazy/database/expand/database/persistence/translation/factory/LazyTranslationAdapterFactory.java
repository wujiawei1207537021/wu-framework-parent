package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.factory;

import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter.LazyTranslationAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.advanced.LazyTranslationAdvancedTarget;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.DefaultLazyTableTranslation;

import java.util.List;

/**
 * description 懒人ORM转译适配器工厂
 *
 * @author 吴佳伟
 * @date 2023/10/04 15:45
 */
public class LazyTranslationAdapterFactory {

    /**
     * 创建转译适配器
     *
     * @return
     */
    public static LazyTranslationAdapter createLazyTranslationAdapter() {
        DefaultLazyTableTranslation defaultLazyTableTranslation = new DefaultLazyTableTranslation();
        LazyTranslationAdvancedTarget lazyTranslationAdvancedTarget = new LazyTranslationAdvancedTarget(List.of(defaultLazyTableTranslation));

        LazyTranslationAdapter lazyTranslationAdapter = new LazyTranslationAdapter(List.of(lazyTranslationAdvancedTarget));
        return lazyTranslationAdapter;
    }
}
