package com.wu.framework.inner.layer.data.translation.adapter;

import com.wu.framework.inner.layer.data.multiple.MultipleTranslationAdapterAdvanced;
import com.wu.framework.inner.layer.data.translation.advanced.TranslationAdvanced;
import org.springframework.core.Ordered;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description 翻译适配器
 *
 * @author 吴佳伟
 * @date 2023/09/22 13:30
 */
public class TranslationAdapter implements MultipleTranslationAdapterAdvanced {

    private final List<TranslationAdvanced> translationAdvancedList;

    private List<TranslationAdvanced> translationAdvancedOrderList;


    public TranslationAdapter(List<TranslationAdvanced> translationAdvancedList) {
        this.translationAdvancedList = translationAdvancedList;
    }

    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    public void transformation(Object source) {
        for (TranslationAdvanced translationAdvanced : getTranslationAdvancedOrderList()) {
            if (translationAdvanced.support(source)) {
                translationAdvanced.transformation(source);
                return;
            }
        }
        throw new IllegalArgumentException("无法匹配到可以使用的转换器");
    }

    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    @Override
    public boolean support(Object source) {
        for (TranslationAdvanced translationAdvanced : getTranslationAdvancedOrderList()) {
            if (translationAdvanced.support(source)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取排序后的字典转换
     *
     * @return
     */
    public List<TranslationAdvanced> getTranslationAdvancedOrderList() {
        if (ObjectUtils.isEmpty(translationAdvancedList)) {
            throw new IllegalArgumentException("需要至少一个转换实现类但是未发现任何实现类");
        }

        if (ObjectUtils.isEmpty(translationAdvancedOrderList)) {
            translationAdvancedOrderList = translationAdvancedList.stream().sorted(Comparator.comparing(Ordered::getOrder)).collect(Collectors.toList());
        }
        return translationAdvancedOrderList;
    }
}
