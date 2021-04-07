package com.wu.framework.inner.layer.stereotype;

/**
 * description 层转换接口
 *
 * @author Jia wei Wu
 * @date 2021/4/1 下午3:32
 */
public interface Layer<O> {

    <O> O before(O o);

    <O> O run(O o);

    <O> O after(O o);
}
