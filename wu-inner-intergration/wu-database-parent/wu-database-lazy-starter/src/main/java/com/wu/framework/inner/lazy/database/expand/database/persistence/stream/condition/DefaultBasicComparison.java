package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.data.NormalUsedString;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/8/21 7:09 下午
 */
public class DefaultBasicComparison<T, R> implements BasicComparison<T, R, DefaultBasicComparison<T,R>> {

    protected ConditionList conditionList = new ConditionList();

    /**
     * @return
     * @describe 添加where 条件
     * @author Jia wei Wu
     * @date 2021/8/8 12:33 下午
     **/
    @Override
    public DefaultBasicComparison<T, R> where() {
        return null;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return
     * @describe 等于条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    @Override
    public DefaultBasicComparison<T, R> eq(boolean condition, R row, Object var) {
        if (condition) {
            conditionList.put(row, NormalUsedString.AMPERSAND, var);
        }
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return
     * @describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public DefaultBasicComparison<T, R> gt(boolean condition, R row, Object var) {
        if (condition) {
            conditionList.put(row, NormalUsedString.RIGHT_CHEV, var);
        }
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return
     * @describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public DefaultBasicComparison<T, R> lt(boolean condition, R row, Object var) {
        if (condition) {
            conditionList.put(row, NormalUsedString.LEFT_CHEV, var);
        }
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return
     * @describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public DefaultBasicComparison<T, R> like(boolean condition, R row, Object var) {
        if (condition) {
            conditionList.put(row, NormalUsedString.LIKE, var);
        }
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param leftVar
     * @param rightVar
     * @return
     * @describe 区间
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public DefaultBasicComparison<T, R> between(boolean condition, R row, Object leftVar, Object rightVar) {
        if (condition) {
            conditionList.put(row, NormalUsedString.BETWEEN, leftVar);
            conditionList.put(row, NormalUsedString.AND, rightVar);
        }
        return this;
    }

    /**
     * @return
     * @describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    @Override
    public ConditionList getConditionList() {
        return conditionList;
    }
}
