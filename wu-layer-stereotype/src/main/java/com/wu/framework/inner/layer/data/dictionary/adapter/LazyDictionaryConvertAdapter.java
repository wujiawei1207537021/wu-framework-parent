package com.wu.framework.inner.layer.data.dictionary.adapter;

import com.wu.framework.inner.layer.data.dictionary.convert.LazyDictionaryConvert;
import com.wu.framework.inner.layer.data.multiple.MultipleTranslationAdapterAdvanced;
import org.springframework.core.Ordered;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description 转换适配器 用于获取具体需要转换的逻辑选取
 *
 * @author 吴佳伟
 * @date 2023/08/30 10:39
 */
public class LazyDictionaryConvertAdapter implements MultipleTranslationAdapterAdvanced {

    private final List<LazyDictionaryConvert> lazyDictionaryConvertList;

    private List<LazyDictionaryConvert> lazyDictionaryConvertOrderList;


    public LazyDictionaryConvertAdapter(List<LazyDictionaryConvert> lazyDictionaryConvertList) {
        this.lazyDictionaryConvertList = lazyDictionaryConvertList;
    }

    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    @Override
    public void transformation(Object source) {
        for (LazyDictionaryConvert lazyDictionaryConvert : getConvertDictionaryOrderList()) {
            if (lazyDictionaryConvert.support(source)) {
                lazyDictionaryConvert.transformation(source);
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
        for (LazyDictionaryConvert lazyDictionaryConvert : getConvertDictionaryOrderList()) {
            if (lazyDictionaryConvert.support(source)) {
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
    public List<LazyDictionaryConvert> getConvertDictionaryOrderList() {
        if (ObjectUtils.isEmpty(lazyDictionaryConvertList)) {
            throw new IllegalArgumentException("需要至少一个转换字典实现类但是未发现任何实现类");
        }

        if (ObjectUtils.isEmpty(lazyDictionaryConvertOrderList)) {
            lazyDictionaryConvertOrderList = lazyDictionaryConvertList.stream().sorted(Comparator.comparing(Ordered::getOrder)).collect(Collectors.toList());
        }
        return lazyDictionaryConvertOrderList;
    }
}
