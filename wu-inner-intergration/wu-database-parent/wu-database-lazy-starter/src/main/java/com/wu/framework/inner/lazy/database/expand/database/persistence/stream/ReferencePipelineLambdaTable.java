package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 表声明通道
 * @date : 2021/8/8 12:28 下午
 */
public abstract class ReferencePipelineLambdaTable<T, R> implements LambdaTable<T, R> {
    protected LambdaTable.LambdaTableType lambdaTableType;
    protected Class<T> primaryTable;


}
