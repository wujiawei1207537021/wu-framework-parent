package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/21 7:09 下午
 */
public abstract class AbstractBasicComparison<T, R, V> implements BasicComparison<T, R, V, AbstractBasicComparison<T, R, V>> {

    protected ConditionList conditionList = new ConditionList(NormalUsedString.SPACE + NormalUsedString.WHERE + NormalUsedString.SPACE);


    /**
     * 左关联
     *
     * @param leftJoinBasicComparison
     * @param <T2>
     * @return
     */
    public <T2> AbstractBasicComparison<T, R, V> leftJoin(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> leftJoinBasicComparison) {
        final ConditionList conditionList = leftJoinBasicComparison.getConditionList();
        final Class entityClass = conditionList.getEntityClass();
        final String tableName = LazyTableUtil.getTableName(entityClass);
        conditionList.setPrefix("left join ");
        conditionList.setConditionType(NormalUsedString.ON);
        conditionList.setPrimaryTable(tableName);
        this.conditionList.join(conditionList);
        return this;
    }

    /**
     * 右边 关联
     *
     * @param rightJoinBasicComparison
     * @param <T2>
     * @return
     */
    public <T2> AbstractBasicComparison<T, R, V> rightJoin(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> rightJoinBasicComparison) {
        final ConditionList conditionList = rightJoinBasicComparison.getConditionList();
        final Class entityClass = conditionList.getEntityClass();
        final String tableName = LazyTableUtil.getTableName(entityClass);
        conditionList.setPrefix("right join ");
        conditionList.setConditionType(NormalUsedString.ON);
        conditionList.setPrimaryTable(tableName);
        this.conditionList.join(conditionList);
        return this;
    }


    /**
     * @param condition
     * @param row
     * @param v
     * @return describe 等于条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    @Override
    public AbstractBasicComparison<T, R, V> eq(boolean condition, R row, V v) {
        if (condition) {
            conditionList.put(columnToString(row), NormalUsedString.EQUALS, valueToString(v));
        }
        return this;
    }

    /**
     * describe 大于
     *
     * @param condition
     * @param row
     * @param var
     * @return
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public AbstractBasicComparison<T, R, V> gt(boolean condition, R row, V var) {
        if (condition) {
            conditionList.put(columnToString(row), NormalUsedString.RIGHT_CHEV, var);
        }
        return this;
    }


    /**
     * describe 小于
     *
     * @param condition
     * @param row
     * @param var
     * @return AbstractBasicComparison
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public AbstractBasicComparison<T, R, V> lt(boolean condition, R row, V var) {
        if (condition) {
            conditionList.put(columnToString(row), NormalUsedString.LEFT_CHEV, var);
        }
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public AbstractBasicComparison<T, R, V> like(boolean condition, R row, V var) {
        if (condition) {
            conditionList.put(columnToString(row), NormalUsedString.LIKE, var);
        }
        return this;
    }

    /**
     * in 查询
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return
     */
    @Override
    public AbstractBasicComparison<T, R, V> in(boolean condition, R row, V var) {
        if (condition) {
            if (Collection.class.isAssignableFrom(var.getClass())) {
                final String in = ((Collection<?>) var).stream().map(o -> NormalUsedString.SINGLE_QUOTE + o.toString() + NormalUsedString.SINGLE_QUOTE).collect(Collectors.joining(NormalUsedString.COMMA));
                conditionList.put(columnToString(row), NormalUsedString.IN, ConditionList.RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + in + NormalUsedString.RIGHT_BRACKET);
            } else {
                conditionList.put(columnToString(row), NormalUsedString.IN, ConditionList.RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + var + NormalUsedString.RIGHT_BRACKET);
            }

        }
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param leftVar
     * @param rightVar
     * @return describe 区间
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public AbstractBasicComparison<T, R, V> between(boolean condition, R row, Object leftVar, Object rightVar) {
        if (condition) {
            conditionList.put(columnToString(row), NormalUsedString.BETWEEN, leftVar);
            conditionList.put(columnToString(row), NormalUsedString.AND, rightVar);
        }
        return this;
    }

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
    protected Class<T> getClassT() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
        return type;
    }

    /**
     * 列到字符串
     *
     * @param row
     * @return
     */
    protected String columnToString(R row) {
        return (String) row;
    }


    protected Object valueToString(V v) {
        return v;
    }

}
