package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 获取对象中的字段
 * @date : 2021/8/23 8:24 下午
 */
@FunctionalInterface
public interface Snippet<T, R> extends Function<T, R>, Serializable {

}
