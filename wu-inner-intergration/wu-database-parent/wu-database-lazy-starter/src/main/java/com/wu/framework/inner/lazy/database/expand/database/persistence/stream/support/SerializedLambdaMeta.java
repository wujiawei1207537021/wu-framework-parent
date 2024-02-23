package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support;

import org.springframework.objenesis.instantiator.util.ClassUtils;

import java.lang.invoke.SerializedLambda;

public class SerializedLambdaMeta implements LambdaMeta {


    private final SerializedLambda lambda;

    public SerializedLambdaMeta(SerializedLambda lambda) {
        this.lambda = lambda;
    }

    @Override
    public String methodName() {
        return lambda.getImplMethodName();
    }

    @Override
    public Class<?> instantiatedClass() {
        String instantiatedMethodType = lambda.getInstantiatedMethodType();
        String instantiatedType = instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(';')).replace('/', '.');
        return ClassUtils.getExistingClass(getCapturingClass().getClassLoader(), instantiatedType);
    }

    public Class<?> getCapturingClass() {
        try {
            final String capturingClass = lambda.getCapturingClass();
            final String replace = capturingClass.replace('/', '.');
            return Class.forName(replace);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}