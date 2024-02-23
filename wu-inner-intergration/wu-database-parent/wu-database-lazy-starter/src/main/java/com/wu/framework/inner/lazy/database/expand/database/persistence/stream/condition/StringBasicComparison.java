package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.SnippetUtil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.LambdaMeta;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/23 8:36 下午
 */
public class StringBasicComparison<T> extends AbstractBasicComparison<T, Snippet<T, ?>, Object> {

    /**
     * @return describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    @Override
    public ConditionList getConditionList() {
        return conditionList;
    }


    @Override
    protected String columnToString(Snippet<T, ?> row) {
        final LambdaMeta meta = SnippetUtil.extract(row);
        assert meta != null;
        final Class<?> tClass = meta.instantiatedClass();
        final String field = CamelAndUnderLineConverter.methodToField(meta.methodName());
        final String fieldRowName = CamelAndUnderLineConverter.humpToLine2(field);
        final String tableName = LazyTableUtil.getTableName(tClass);
        return tableName + NormalUsedString.DOT + fieldRowName;
    }

}
