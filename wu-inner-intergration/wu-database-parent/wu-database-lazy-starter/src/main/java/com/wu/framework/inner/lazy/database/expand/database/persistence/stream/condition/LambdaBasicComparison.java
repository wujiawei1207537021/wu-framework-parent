package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/8/23 8:36 下午
 */
public class LambdaBasicComparison<T> implements BasicComparison<T, Snippet,LambdaBasicComparison<T>> {

    @Override
    public LambdaBasicComparison<T> table(Class primaryTable) {
        return null;
    }

    /**
     * @return
     * @describe 添加where 条件
     * @author Jia wei Wu
     * @date 2021/8/8 12:33 下午
     **/
    @Override
    public LambdaBasicComparison<T> where() {
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
    public LambdaBasicComparison<T> eq(boolean condition, Snippet row, Object var) {
        return null;
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
    public LambdaBasicComparison<T> gt(boolean condition, Snippet row, Object var) {
        return null;
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
    public LambdaBasicComparison<T> lt(boolean condition, Snippet row, Object var) {
        return null;
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
    public LambdaBasicComparison<T> like(boolean condition, Snippet row, Object var) {
        return null;
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
    public LambdaBasicComparison<T> between(boolean condition, Snippet row, Object leftVar, Object rightVar) {
        return null;
    }

    /**
     * @return
     * @describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    @Override
    public ConditionList getConditionList() {
        return null;
    }
}
