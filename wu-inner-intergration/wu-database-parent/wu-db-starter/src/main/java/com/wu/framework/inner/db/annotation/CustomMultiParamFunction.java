package com.wu.framework.inner.db.annotation;


@FunctionalInterface
public interface CustomMultiParamFunction {

    Object multiParam(Object... objects);

}
