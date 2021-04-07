package com.wu.framework.inner.layer.stereotype;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2021/4/1 下午3:37
 */
public interface LayerDefault extends Layer {

    @Override
    default Object before(Object o) {
        return o;
    }

    @Override
    default Object run(Object o) {
        return o;
    }

    @Override
    default Object after(Object o) {
        return o;
    }
}
