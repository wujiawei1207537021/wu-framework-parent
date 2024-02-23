package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.as;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.SnippetUtil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.LambdaMeta;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.persistence.util.SqlMessageFormatUtil;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/08/10 17:04
 */
public class LambdaFunctionAsComparison<T> implements FunctionAsComparison<T, LambdaFunctionAsComparison<T>> {


    /**
     * 函数片段
     */
    private String functionFragment;

    /**
     * 列到字符串
     *
     * @param row
     * @return
     */
    protected <T1> String snippetToString(Snippet<T1, ?> row) {
        final LambdaMeta meta = SnippetUtil.extract(row);
        assert meta != null;
        final Class tClass = meta.instantiatedClass();

        final String methodName = meta.methodName();
        if ("toString".equals(methodName)) {
            return methodName;
        }
        final String field = CamelAndUnderLineConverter.methodToField(methodName);
        final String fieldRowName = CamelAndUnderLineConverter.humpToLine2(field);
        final String tableName = LazyTableUtil.getTableName(tClass);
        return tableName + NormalUsedString.DOT + fieldRowName;
    }

    /**
     * 统计数据
     *
     * @param snippet
     * @return
     */
    @Override
    public <T1> LambdaFunctionAsComparison<T> count(Snippet<T1, ?> snippet) {
        // 获取数据
        functionFragment = SqlMessageFormatUtil.format(" count({0}) ", snippetToString(snippet));
        return this;
    }

    /**
     * 获取和集
     *
     * @param snippet
     * @return
     */
    @Override
    public <T1> LambdaFunctionAsComparison<T> sum(Snippet<T1, ?> snippet) {
        // 获取数据
        functionFragment = SqlMessageFormatUtil.format(" sum({0}) ", snippetToString(snippet));
        return this;
    }

    /**
     * 获取最大值
     *
     * @param snippet
     * @return
     */
    @Override
    public <T1> LambdaFunctionAsComparison<T> max(Snippet<T1, ?> snippet) {
        // 获取数据
        functionFragment = SqlMessageFormatUtil.format(" max({0}) ", snippetToString(snippet));
        return this;
    }

    /**
     * 获取最小值
     *
     * @param snippet
     * @return
     */
    @Override
    public <T1> LambdaFunctionAsComparison<T> min(Snippet<T1, ?> snippet) {
        // 获取数据
        functionFragment = SqlMessageFormatUtil.format(" min({0}) ", snippetToString(snippet));
        return this;
    }

    /**
     * 获取sql函数片段
     *
     * @return
     */
    @Override
    public String getFunctionFragment() {
        return functionFragment;
    }
}
