package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.Build;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.LambdaBuild;

/**
 * description 数据库操作lambda 表达式写法 LazyLambdaStream
 *
 * @author Jia wei Wu
 * @date 2021/4/27 3:41 下午
 */

public class LazyLambdaStream {
    private final LazyOperation lazyOperation;

    public LazyLambdaStream(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    /**
     * 创建一个class的绑定
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Build<T> of(Class<T> clazz) {
        return new LambdaBuild<T>(clazz, lazyOperation);
    }


    /**
     * 更新或者插入单个 去除空值、对比表
     * 多个数据性能会慢，不经常使用
     *
     * @param save
     * @param <T>
     */
    public <T> void smartUpsert(T save) {
        lazyOperation.smartUpsert(save);
    }

    /**
     * describe upsert
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/20 8:44 下午
     **/
    public <T> void upsert(T save) {
        lazyOperation.upsert(save);
    }

    /**
     * 数据插入
     *
     * @param save
     * @param <T>
     */
    public <T> void insert(T save) {
        lazyOperation.insert(save);
    }
}
