package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;

import java.util.Collection;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 执行sql 并返回数据
 * @date : 2021/8/8 11:47 上午
 */
public abstract class ReferencePipelineCollector<T, R> extends ReferencePipelineSplicing<T, R>
        implements LambdaStreamCollector<T, R> {
    private final LazyOperation lazyOperation;

    protected ReferencePipelineCollector(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    //    private final LazyOperation lazyOperation;
//    protected T t;

    /**
     * @param r1Class@return
     * @describe 返回指定类型的数据
     * 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:03 下午
     **/
    @Override
    public <R> Collection<R> collection(Class<R> r1Class) {
        return lazyOperation.executeSQL(getSqlStatement(), r1Class);
    }

    /**
     * @return
     * @describe 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public Collection<T> collection() {
        return null;
    }

    /**
     * @param r1Class@return
     * @describe 返回指定类型的数据
     * 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public <R1> T collectOne(Class<R1> r1Class) {
        return null;
    }

    /**
     * @return
     * @describe 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public T collectOne() {
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
    public LambdaStreamCollector<T, R> between(boolean condition, R row, Object leftVar, Object rightVar) {
        if (checkCondition(condition)) {
            this.SQLExecuted
                    .append(NormalUsedString.SPACE)
                    .append(row)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.BETWEEN)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.SINGLE_QUOTE)
                    .append(leftVar)
                    .append(NormalUsedString.SINGLE_QUOTE)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.AND)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.SINGLE_QUOTE)
                    .append(rightVar)
                    .append(NormalUsedString.SINGLE_QUOTE);
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
    public LambdaStreamCollector<T, R> like(boolean condition, R row, Object var) {
        if (checkCondition(condition)) {
            this.SQLExecuted
                    .append(NormalUsedString.SPACE)
                    .append(row)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.LIKE)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.SINGLE_QUOTE)
                    .append(var)
                    .append(NormalUsedString.SINGLE_QUOTE);
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
    public LambdaStreamCollector<T, R> eq(boolean condition, R row, Object var) {
        if (checkCondition(condition)) {
            this.SQLExecuted
                    .append(NormalUsedString.SPACE)
                    .append(row)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.EQUALS)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.SINGLE_QUOTE)
                    .append(var)
                    .append(NormalUsedString.SINGLE_QUOTE);
        }
        return this;
    }

    /**
     * 检查条件
     */
    public boolean checkCondition(boolean condition) {
        this.where();
        if (condition) {
            if (haveAnd) {
                this.SQLExecuted.
                        append(NormalUsedString.SPACE)
                        .append(NormalUsedString.AND)
                        .append(NormalUsedString.SPACE);
            } else {
                this.haveAnd = true;
            }
        }
        return condition;
    }

//    public ReferencePipelineCollector(LazyOperation lazyOperation) {
//        this.lazyOperation = lazyOperation;
//        Class<?> entitiClass = null;
//        Type genericSuperclass = this.getClass().getGenericSuperclass();
//        if (genericSuperclass instanceof ParameterizedType) {
//            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
//                    .getActualTypeArguments();
//            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
//                entitiClass = (Class<?>) actualTypeArguments[0];
//            }
//        }
//        System.out.println(entitiClass);
//    }


}
