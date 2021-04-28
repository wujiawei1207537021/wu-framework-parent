package com.wu.framework.inner.lazy.database.expand.database.persistence.lambda;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * description 数据库操作lambda 表达式写法
 *
 * @author 吴佳伟
 * @date 2021/4/27 3:41 下午
 */
@Deprecated
public interface LambdaStream<T> extends AutoCloseable{

    /**
     * description 初始化数据范性
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/28 1:19 下午
     */
    @Deprecated
    LambdaStream<T> type(Class<T> clazz);
    /**
     * description 主表
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 5:08 下午
     */
    LambdaStream<T> table(String primaryTable);
    /**
     * description 左关联
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:47 下午
     */
    LambdaStream<T> leftJoin(Consumer<T> consumer);

    /**
     * description 右关联
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:48 下午
     */
    LambdaStream<T> rightJoin(Consumer<T> consumer);

    /**
     * description 参数过滤
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:46 下午
     */
    LambdaStream<T> filter(Predicate<? super T> predicate);

    /**
     * description 数据收集
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:53 下午
     */
    <R, A> R collect(Collector<? super T, A, R> collector);


}
