package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.factory;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.LambdaBuild;

/***
 *
 * BuildFactory
 *
 */
public class LambdaBuildFactory {

    /**
     * 创建一个class的绑定
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> LambdaBuild<T> of(Class<T> clazz) {
        return new LambdaBuild<T>(clazz, null);
    }


}
