package com.wu.freamwork.test;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import jakarta.annotation.Resource;

public abstract class AbstractTestRunner implements TestRunner {

    @Resource
    private LazyLambdaStream lazyLambdaStream;

    /**
     * 执行类型
     *
     * @return
     */
    public abstract LambdaTableType type();

    public abstract TestLazyResult result();

    public void report() {
        TestLazyResult result = result();
        if(result.getExecuteSql()==null){
            System.out.println("");
        }
        lazyLambdaStream.upsert(result);
    }

    /**
     * 执行
     */
    @Override
    public void run() {
        doRun();
        report();
    }

    protected abstract void doRun();
}
