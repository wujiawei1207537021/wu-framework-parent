package com.wu.framework.inner.layer.stereotype;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2021/4/1 下午3:37
 */
public interface LayerDefault extends Layer {

    @Override
    default void before() {

    }

    @Override
    default void run() {

    }

    @Override
    default void after() {

    }
}
