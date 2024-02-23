package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter;


import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.advanced.LazyTranslationAdvanced;
import org.springframework.core.Ordered;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description 数据库orm数据库转换适配器
 *
 * @author 吴佳伟
 * @date 2023/09/22 13:30
 */
public class LazyTranslationAdapter {

    private final List<LazyTranslationAdvanced> lazyTranslationAdvancedList;

    private List<LazyTranslationAdvanced> lazyTranslationAdvancedOrderList;


    public LazyTranslationAdapter(List<LazyTranslationAdvanced> lazyTranslationAdvancedList) {
        this.lazyTranslationAdvancedList = lazyTranslationAdvancedList;
    }

    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    public void transformation(Object source) {
        for (LazyTranslationAdvanced translationAdvanced : getLazyTranslationAdvancedOrderList()) {
            if (translationAdvanced.support(source)) {
                translationAdvanced.transformation(source);
                return;
            }
        }
//        throw new IllegalArgumentException("无法匹配到可以使用的转换器");
    }

    /**
     * 获取排序后的字典转换
     *
     * @return
     */
    public List<LazyTranslationAdvanced> getLazyTranslationAdvancedOrderList() {
        if (ObjectUtils.isEmpty(lazyTranslationAdvancedList)) {
            throw new IllegalArgumentException("需要至少一个转换实现类但是未发现任何实现类");
        }

        if (ObjectUtils.isEmpty(lazyTranslationAdvancedOrderList)) {
            lazyTranslationAdvancedOrderList = lazyTranslationAdvancedList.stream().sorted(Comparator.comparing(Ordered::getOrder)).collect(Collectors.toList());
        }
        return lazyTranslationAdvancedOrderList;
    }
}
