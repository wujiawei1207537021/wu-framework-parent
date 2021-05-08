package com.wu.framework.inner.layer.stereotype.proxy;

import com.wu.framework.inner.layer.stereotype.MethodParamFunction;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/4/6 7:51 下午
 */
@FunctionalInterface
public interface ProxyMethodFunction extends MethodParamFunction<Object> {

    Object defaultMethod(Object method) throws MethodParamFunctionException;


}
