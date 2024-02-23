package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.SnippetUtil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.LambdaMeta;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;

import java.lang.reflect.ParameterizedType;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/23 8:36 下午
 */
public class LambdaBasicComparison<T> extends AbstractBasicComparison<T, Snippet<T, ?>, Object> {

    protected Class<T> tClassType;

    /**
     * @return describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    @Override
    public ConditionList getConditionList() {
        return conditionList;
    }

    /**
     * 获取T 的class
     *
     * @return
     */
    @Override
    public Class<T> getClassT() {
        if (null == tClassType) {
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            this.tClassType = (Class<T>) superClass.getActualTypeArguments()[0];
        }
        return tClassType;
    }

    /**
     * 列到字符串
     *
     * @param row
     * @return
     */
    @Override
    protected String columnToString(Snippet<T, ?> row) {
        final LambdaMeta meta = SnippetUtil.extract(row);
        assert meta != null;
        final Class tClass = meta.instantiatedClass();
        if (null == tClassType) {
            tClassType = tClass;
        }
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
     * 初始化 表
     */
    public LambdaBasicComparison<T> toString(Snippet<T, ?> row, Object o) {
        columnToString(row);
        return this;
    }
}
