package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaStreamCollector;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTable;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.ReferencePipelineLambdaTable;
import lombok.Getter;

/**
 * description 参考管道
 *
 * @author 吴佳伟
 * @date 2021/4/27 5:20 下午
 */
@Getter
public class ReferencePipeline<T> implements LambdaStream<T, Object> {
    private final LazyOperation lazyOperation;

    public ReferencePipeline(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    @Override
    public LambdaTable<T, Object> select() {
        return new ReferencePipelineLambdaTable<T, Object>(LambdaTable.LambdaTableType.SELECT, this);
    }

    @Override
    public LambdaStreamCollector collector() {
        return null;
    }
}
