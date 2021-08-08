package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LambdaStream;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 表声明通道
 * @date : 2021/8/8 12:28 下午
 */
public class ReferencePipelineLambdaTable<T, R> implements LambdaTable<T, R> {
    private final LambdaTableType lambdaTableType;
    private final LambdaStream lambdaStream;

    public ReferencePipelineLambdaTable(LambdaTableType lambdaTableType, LambdaStream lambdaStream) {
        this.lambdaTableType = lambdaTableType;
        this.lambdaStream = lambdaStream;
    }


    /**
     * @param primaryTable
     * @return
     * @describe 主表
     * @author Jia wei Wu
     * @date 2021/8/8 12:27 下午
     **/
    @Override
    public LambdaSplicing<T, R> table(Class primaryTable) {
        switch (lambdaTableType) {
            case SELECT:
                return new SelectReferencePipelineSplicing<T, R>(lambdaTableType, primaryTable).where();
            default:
                return null;
        }
    }
}
