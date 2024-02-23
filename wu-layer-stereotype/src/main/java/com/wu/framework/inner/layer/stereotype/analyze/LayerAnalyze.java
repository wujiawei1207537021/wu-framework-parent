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
     * @author Jia wei Wu
     * @date 2021/4/26 4:31 下午
     */
    boolean supportParameter(AnalyzeParameter analyzeParameter);


    /**
     * description  supportParameter 结果返回为true 时解析数据为指定类型的数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/27 3:05 下午
     */
    <A> A analyze(AnalyzeParameter analyzeParameter);
}
