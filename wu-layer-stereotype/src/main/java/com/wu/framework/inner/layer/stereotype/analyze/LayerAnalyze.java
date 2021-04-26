package com.wu.framework.inner.layer.stereotype.analyze;

/**
 * description layer 解析适配器
 *
 * @author Jia wei Wu
 * @date 2021/4/1 下午3:32
 */
public interface LayerAnalyze<A> {

    /**
     * description 是否支持
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/26 4:31 下午
     */
    default boolean supportParameter(AnalyzeParameter analyzeParameter) {
        return false;
    }


    <A> A analyze(Class clazz);
}
