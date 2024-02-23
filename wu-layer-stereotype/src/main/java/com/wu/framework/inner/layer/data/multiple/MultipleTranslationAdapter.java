package com.wu.framework.inner.layer.data.multiple;

import java.util.List;

/**
 * 广义的、、多种多样、网格 转译
 */
public class MultipleTranslationAdapter {
    private final List<MultipleTranslationAdapterAdvanced> multipleTranslationAdapterAdvancedList;

    public MultipleTranslationAdapter(List<MultipleTranslationAdapterAdvanced> multipleTranslationAdapterAdvancedList) {
        this.multipleTranslationAdapterAdvancedList = multipleTranslationAdapterAdvancedList;
    }
    public void transformation(Object source) {
        for (MultipleTranslationAdapterAdvanced multipleTranslationAdapterAdvanced : multipleTranslationAdapterAdvancedList) {
            if(multipleTranslationAdapterAdvanced.support(source)){
                multipleTranslationAdapterAdvanced.transformation(source);
            }
        }
    }
}
