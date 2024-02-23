package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.select;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import java.util.function.Consumer;

/**
 * describe :  滚动查询
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/1 21:24
 */
public interface ScrollSelectLazyLambdaStream extends SimpleSelectLazyLambdaStream {


    /**
     * 分页查询
     *
     * @param lazyPage   分页参数
     * @param returnType 返回结果
     * @param sql        执行sql <p>select * from {0}.{1}"</p>
     * @param params     参数
     * @return
     */
    default <T> void scroll(LazyPage lazyPage, Class<T> returnType, String sql, Consumer<LazyPage<T>> consumer, Object... params) {
        LazyPage scrollLazyPage = lazyPage;
        if (ObjectUtils.isEmpty(scrollLazyPage)) {
            scrollLazyPage = new LazyPage<>(1, 10000);
        }
        do {
            LazyPage<T> lazyPageResult = selectPage(scrollLazyPage, returnType, sql, params);
            consumer.accept(lazyPageResult);
            if (ObjectUtils.isEmpty(lazyPage)) {
                scrollLazyPage.setCurrent(scrollLazyPage.getCurrent() + 1);
            }
        } while (scrollLazyPage.getRecord() != null && scrollLazyPage.getRecord().size() == scrollLazyPage.getSize());
    }

    /**
     * describe 滚动查询
     *
     * @param lazyPage   分页参数
     * @param <T>        返回结果范型取决于LazyWrappers.<T>lambdaWrapper()
     * @param comparison 查询参数
     * @param consumer   返回结果函数
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/1 21:28
     **/
    default <T> void scroll(LazyPage lazyPage, BasicComparison<T, ?, ?, ?> comparison, Consumer<LazyPage<T>> consumer) {
        LazyPage scrollLazyPage = lazyPage;
        if (ObjectUtils.isEmpty(scrollLazyPage)) {
            scrollLazyPage = new LazyPage<>(1, 1000);
        }
        do {
            LazyPage<T> lazyPageResult = selectPage(comparison, scrollLazyPage);
            consumer.accept(lazyPageResult);
            if (ObjectUtils.isEmpty(lazyPage)) {
                scrollLazyPage.setCurrent(scrollLazyPage.getCurrent() + 1);
            }
        } while (scrollLazyPage.getRecord() != null && scrollLazyPage.getRecord().size() == scrollLazyPage.getSize());
    }


    /**
     * describe 滚动查询
     *
     * @param lazyPage   分页参数
     * @param <R>        返回结果范型取决于 returnType
     * @param comparison 查询参数
     * @param consumer   返回结果函数
     * @param returnType 返回结果
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/1 21:28
     **/
    default <T, R> void scroll(LazyPage lazyPage, BasicComparison<T, ?, ?, ?> comparison, @NonNull Class<R> returnType, Consumer<LazyPage<R>> consumer) {
        LazyPage scrollLazyPage = lazyPage;
        if (ObjectUtils.isEmpty(scrollLazyPage)) {
            scrollLazyPage = new LazyPage<>(1, 1000);
        }
        do {
            LazyPage<R> lazyPageResult = selectPage(comparison, scrollLazyPage, returnType);
            consumer.accept(lazyPageResult);
            if (ObjectUtils.isEmpty(lazyPage)) {
                scrollLazyPage.setCurrent(scrollLazyPage.getCurrent() + 1);
            }
        } while (scrollLazyPage.getRecord() != null && scrollLazyPage.getRecord().size() == scrollLazyPage.getSize());
    }


}
