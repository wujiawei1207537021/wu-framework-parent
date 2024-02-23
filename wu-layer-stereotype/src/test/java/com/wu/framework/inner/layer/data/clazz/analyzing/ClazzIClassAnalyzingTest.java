package com.wu.framework.inner.layer.data.clazz.analyzing;

import com.wu.framework.inner.layer.data.clazz.LazyClass;
import com.wu.framework.inner.layer.data.translation.NormalTranslation;

class ClazzIClassAnalyzingTest {

    public static void main(String[] args) {
        ClazzIClassAnalyzing clazzIClassAnalyzing = new ClazzIClassAnalyzing();
        LazyClass lazyClass = clazzIClassAnalyzing.getLazyClass(NormalTranslation.class);
        System.out.println(lazyClass);
    }
}