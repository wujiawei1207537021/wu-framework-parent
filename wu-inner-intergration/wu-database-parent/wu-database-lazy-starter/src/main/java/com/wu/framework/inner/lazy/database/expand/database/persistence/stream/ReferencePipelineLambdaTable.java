package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;

import java.util.function.Consumer;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 表声明通道
 * @date : 2021/8/8 12:28 下午
 */
public abstract class ReferencePipelineLambdaTable<T, R> extends ReferencePipelineCollector<T,R> implements LambdaTable<T, R> {
    protected LambdaTable.LambdaTableType lambdaTableType;
    protected Class<T> primaryTable;


    protected ReferencePipelineLambdaTable(LazyOperation lazyOperation) {
        super(lazyOperation);
    }



    /**
     * description 右关联
     *
     * @param consumer @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:48 下午
     * @return
     */
    @Override
    public LambdaSplicing<T, R> rightJoin(Consumer consumer) {
        return null;
    }

    /**
     * 操作表类型
     *
     * @param lambdaTableType
     * @return
     */
    @Override
    public LambdaStreamCollector<T, R> lambdaTableType(LambdaTableType lambdaTableType) {
        return null;
    }
}
