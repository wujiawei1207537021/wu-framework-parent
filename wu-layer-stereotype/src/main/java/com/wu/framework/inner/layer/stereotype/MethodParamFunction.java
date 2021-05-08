package com.wu.framework.inner.layer.stereotype;

import com.wu.framework.inner.layer.data.ProcessException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 方法参数功能
 * @date : 2021/4/6 7:51 下午
 */
@FunctionalInterface
public interface MethodParamFunction<R> {

    R defaultMethod(R r) throws MethodParamFunctionException, IOException, ProcessException, SQLException;


}
