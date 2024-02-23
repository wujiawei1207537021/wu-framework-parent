package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.as;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;

public interface FunctionAsComparison<T, C extends FunctionAsComparison<T, C>> {


    /**
     * 统计数据
     *
     * @param snippet
     * @param <T1>
     * @return
     */
    <T1> C count(Snippet<T1, ?> snippet);

    /**
     * 获取和集
     *
     * @param snippet
     * @param <T1>
     * @return
     */
    <T1> C sum(Snippet<T1, ?> snippet);

    /**
     * 获取最大值
     *
     * @param snippet
     * @param <T1>
     * @return
     */
    <T1> C max(Snippet<T1, ?> snippet);

    /**
     * 获取最小值
     *
     * @param snippet
     * @param <T1>
     * @return
     */
    <T1> C min(Snippet<T1, ?> snippet);

    /**
     * 获取sql函数片段
     *
     * @return
     */
    public String getFunctionFragment();

}
