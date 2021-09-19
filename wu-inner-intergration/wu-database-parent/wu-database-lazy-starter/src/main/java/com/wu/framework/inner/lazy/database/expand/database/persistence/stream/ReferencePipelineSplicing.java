package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.ConditionList;

import java.util.function.Predicate;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 拼接参考流
 * @date : 2021/8/8 11:44 上午
 */
public abstract class ReferencePipelineSplicing<T, R> extends ReferencePipelineLambdaTable<T, R>
        implements LambdaSplicing<T, R> {

    //    protected LambdaTable.LambdaTableType lambdaTableType;
//    protected Class<T> primaryTable;


    protected ConditionList conditionList = new ConditionList();

    protected ReferencePipelineSplicing(LazyOperation lazyOperation) {
        super(lazyOperation);
    }


    /**
     * description 左关联
     *
     * @param comparison
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:47 下午
     */
    @Override
    public LambdaSplicing<T, R> leftJoin(BasicComparison comparison) {
        conditionList.put("leftjoin", "", "");
        conditionList.addAll(comparison.getConditionList());
        return this;
    }

    /**
     * @return
     * @describe 添加where 条件
     * @author Jia wei Wu
     * @date 2021/8/8 12:33 下午
     **/
    @Override
    public LambdaTable<T, R> where() {
        return this;
    }

    /**
     * description 参数过滤
     *
     * @param predicate @return
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:46 下午
     */
    @Override
    public LambdaTable<T, R> filter(Predicate<? super T> predicate) {
        return null;
    }


    /**
     * @return
     * @describe and条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    @Override
    public LambdaSplicing<T, R> and(BasicComparison comparison) {
        conditionList.addAll(comparison.getConditionList());
        return this;
    }

    /**
     * @param comparison
     * @return
     * @describe or 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    @Override
    public LambdaSplicing<T, R> or(BasicComparison comparison) {
        conditionList.addAll(comparison.getConditionList());
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
    public LambdaSplicing<T, R> gt(boolean condition, R row, Object var) {
        // 检查是否
        if (checkCondition(condition)) {
            conditionList.put(row, NormalUsedString.RIGHT_CHEV, var);
            return this;

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
    public LambdaSplicing<T, R> lt(boolean condition, R row, Object var) {
        if (checkCondition(condition)) {
            conditionList.put(row, NormalUsedString.LEFT_CHEV, var);
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
    public LambdaSplicing<T, R> between(boolean condition, R row, Object leftVar, Object rightVar) {
        if (checkCondition(condition)) {
            conditionList.put(row, NormalUsedString.BETWEEN, leftVar);
            conditionList.put(row, NormalUsedString.AND, rightVar);
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
    public LambdaSplicing<T, R> like(boolean condition, R row, Object var) {
        if (checkCondition(condition)) {
            conditionList.put(row, NormalUsedString.LIKE, var);
        }

        return this;
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
    public LambdaSplicing<T, R> eq(boolean condition, R row, Object var) {
        if (checkCondition(condition)) {
            conditionList.put(row, NormalUsedString.EQUALS, var);
        }
        return this;
    }

    /**
     * 检查条是否存在and件
     */
    public boolean checkCondition(boolean condition) {
        return condition;
    }

    /**
     * 预处理
     */
    protected void pretreatment() {

    }

}
