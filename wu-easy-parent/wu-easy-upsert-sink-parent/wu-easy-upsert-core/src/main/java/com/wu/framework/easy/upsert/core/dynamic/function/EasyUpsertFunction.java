package com.wu.framework.easy.upsert.core.dynamic.function;

import java.util.List;

@FunctionalInterface
public interface EasyUpsertFunction {
    /**
     * 处理数据
     *
     * @param source
     * @param <T>
     */
    <T> void handle(List<T> source);
}
