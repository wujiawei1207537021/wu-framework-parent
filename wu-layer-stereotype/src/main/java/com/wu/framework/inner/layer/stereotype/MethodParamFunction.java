package com.wu.framework.inner.layer.stereotype;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/4/6 7:51 下午
 */
@FunctionalInterface
public interface MethodParamFunction<R> {

    R defaultMethod(R r) throws Exception;


}
