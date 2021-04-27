package com.wu.framework.inner.layer.stereotype.analyze;

import java.lang.annotation.Annotation;

/**
 * description layer 解析适配器
 *
 * @author Jia wei Wu
 * @date 2021/4/1 下午3:32
 */
public class LayerMethodAnalyze implements LayerAnalyze {


    public Annotation analyze(AnalyzeParameter analyzeParameter) {
        return null;
    }

    /**
     * description 是否支持
     *
     * @param analyzeParameter@return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/26 4:31 下午
     */
    @Override
    public boolean supportParameter(AnalyzeParameter analyzeParameter) {
        return true;
    }
}
