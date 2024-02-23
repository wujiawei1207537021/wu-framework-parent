package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.DeleteReferencePipeline;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTable;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.SelectReferencePipeline;
import lombok.Getter;

/**
 * description 参考管道
 *
 * @author 吴佳伟
 * @date 2021/4/27 5:20 下午
 */
@Getter
public class ReferencePipeline implements LambdaStream {
    private final LazyOperation lazyOperation;

    public ReferencePipeline(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    @Override
    public LambdaTable select() {
//        return new ReferencePipelineLambdaTable<T, Object>().lambdaTableType(LambdaTable.LambdaTableType.SELECT);
        return new SelectReferencePipeline(lazyOperation);
    }

    @Override
    public LambdaTable delete() {
        return new DeleteReferencePipeline(lazyOperation);
    }
}
