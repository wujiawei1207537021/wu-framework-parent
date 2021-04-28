package com.wu.framework.inner.lazy.database.expand.database.persistence.lambda;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * description 参考管道
 *
 * @author 吴佳伟
 * @date 2021/4/27 5:20 下午
 */
@Deprecated
public class ReferencePipeline<T> implements LambdaStream<T> {

    private final LazyOperation lazyOperation;

    // 对象拆分成 持久层对象属性
    private final Persistence persistence = new Persistence();

    ReferencePipeline(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    /**
     * description 提供静态初始化方法
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/28 1:23 下午
     */
    public static <T> LambdaStream<T> lambdaStream(LazyOperation lazyOperation, Class<T> clazz) {
        ReferencePipeline<T> tReferencePipeline = new ReferencePipeline<>(lazyOperation);
        return tReferencePipeline;
    }

    /**
     * description
     *
     * @param clazz@return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/28 1:19 下午
     */
    @Deprecated
    @Override
    public LambdaStream<T> type(Class<T> clazz) {
        return this;
    }

    /**
     * description 主表
     *
     * @param primaryTable@return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 5:08 下午
     */
    @Override
    public LambdaStream<T> table(String primaryTable) {
        persistence.setTableName(primaryTable);
        return this;
    }

    /**
     * description 左关联
     *
     * @param consumer@return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:47 下午
     */
    @Override
    public LambdaStream<T> leftJoin(Consumer<T> consumer) {

        return this;
    }

    /**
     * description 右关联
     *
     * @param consumer@return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:48 下午
     */
    @Override
    public LambdaStream<T> rightJoin(Consumer<T> consumer) {
        return this;
    }

    /**
     * description 参数过滤
     *
     * @param predicate@return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:46 下午
     */
    @Override
    public LambdaStream<T> filter(Predicate<? super T> predicate) {
        Predicate<? super T> negate = predicate.negate();
        return this;
    }

    /**
     * description 数据收集
     *
     * @param collector@return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:53 下午
     */
    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return null;
    }


    @Override
    public void close() throws Exception {

    }
}
