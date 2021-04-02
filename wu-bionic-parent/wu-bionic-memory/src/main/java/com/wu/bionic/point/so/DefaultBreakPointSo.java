package com.wu.bionic.point.so;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import sun.reflect.generics.repository.MethodRepository;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * description 断点续传
 *
 * @author Jia wei Wu
 * @date 2021/2/2 下午4:17
 */
@Accessors(chain = true)
@Data
public final class DefaultBreakPointSo extends BreakPointSoAbstract implements Serializable {

    private Class<?> clazz;
    //        private int                 slot;
    private String name;
    private Class<?> returnType;
    private Class<?>[] parameterTypes;
    private Class<?>[] exceptionTypes;
    //    private int                 modifiers;
    // Generics and annotations support
    private transient String signature;
    // generic info repository; lazily initialized
    private transient MethodRepository genericInfo;
    private Annotation[] annotations;
    private Annotation[][] parameterAnnotations;
    private Object[] params;


    public DefaultBreakPointSo(@NonNull Method method) {
        this.clazz = method.getDeclaringClass();
        this.name = method.getName();
        this.returnType = method.getReturnType();
        this.parameterTypes = method.getParameterTypes();
        this.exceptionTypes = method.getExceptionTypes();
        this.annotations = method.getAnnotations();
        this.parameterAnnotations = method.getParameterAnnotations();
    }

    public String getKey() {
        return clazz.getName() + "#" + name;
    }


    /**
     * description 获取断点源头
     *
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/3 上午11:08
     */
    @Override
    public Method getMethod() throws NoSuchMethodException {
        return clazz.getMethod(name, parameterTypes);
    }
}
