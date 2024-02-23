package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.as.FunctionAsComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.as.LambdaFunctionAsComparison;

/**
 * describe : LazyFunctionWrappers 懒惰的包装
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/9 1:11 下午
 */
public class LazyFunctionWrappers {


    public static <T> FunctionAsComparison<T, ?> function() {
        final FunctionAsComparison<T, ?> function = new LambdaFunctionAsComparison<T>() {
        };
        return function;
    }

    /**
     * 统计
     *
     * @return
     */
    public static String count() {
        return " count(1) ";
    }

}
