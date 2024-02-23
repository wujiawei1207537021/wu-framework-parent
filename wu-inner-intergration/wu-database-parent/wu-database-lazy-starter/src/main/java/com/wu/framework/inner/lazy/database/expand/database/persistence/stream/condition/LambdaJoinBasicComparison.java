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
public class LambdaJoinBasicComparison<T1, T2> extends AbstractJoinBasicComparison<T1, T2, Snippet<T1, ?>, Snippet<T2, ?>> {


    protected Class<T1> t1Class;
    protected Class<T2> t2Class;


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
     * 获取T1 的class
     *
     * @return
     */
    @Override
    public Class<T1> getClassT1() {
        if (null != t1Class) {
            return t1Class;
        } else {
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<T1> type = (Class<T1>) superClass.getActualTypeArguments()[0];
            return type;
        }

    }

    /**
     * 获取T2 的class
     *
     * @return
     */
    @Override
    public Class<T2> getClassT2() {
        if (null != t2Class) {
            return t2Class;
        } else {
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<T2> type = (Class<T2>) superClass.getActualTypeArguments()[1];
            return type;
        }
    }


    @Override
    protected String column1ToString(Snippet<T1, ?> row) {
        final LambdaMeta meta = SnippetUtil.extract(row);
        assert meta != null;
        final Class tClass = meta.instantiatedClass();
        t1Class = tClass;
        final String field = CamelAndUnderLineConverter.methodToField(meta.methodName());
        final String fieldRowName = CamelAndUnderLineConverter.humpToLine2(field);
        final String tableName = LazyTableUtil.getTableName(tClass);
        return tableName + NormalUsedString.DOT + fieldRowName;
    }


    @Override
    protected String column2ToString(Snippet<T2, ?> row) {
        final LambdaMeta meta = SnippetUtil.extract(row);
        assert meta != null;
        final Class tClass = meta.instantiatedClass();
        t2Class = tClass;
        final String field = CamelAndUnderLineConverter.methodToField(meta.methodName());
        final String fieldRowName = CamelAndUnderLineConverter.humpToLine2(field);
        final String tableName = LazyTableUtil.getTableName(tClass);
        return tableName + NormalUsedString.DOT + fieldRowName;
    }
}
