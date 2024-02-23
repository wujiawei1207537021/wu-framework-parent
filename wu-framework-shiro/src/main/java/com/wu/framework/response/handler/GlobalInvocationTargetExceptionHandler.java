package com.wu.framework.response.handler;


import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射异常处理
 */
@RestControllerAdvice
public class GlobalInvocationTargetExceptionHandler {

    private GlobalDataAccessExceptionHandler globalDataAccessExceptionHandler;

    private GlobalBaseExceptionHandler globalBaseExceptionHandler;

    private GlobalTokenAccessExceptionHandler globalTokenAccessExceptionHandler;

    @Autowired
    public GlobalInvocationTargetExceptionHandler(GlobalDataAccessExceptionHandler globalDataAccessExceptionHandler, GlobalBaseExceptionHandler globalBaseExceptionHandler, GlobalTokenAccessExceptionHandler globalTokenAccessExceptionHandler) {
        this.globalDataAccessExceptionHandler = globalDataAccessExceptionHandler;
        this.globalBaseExceptionHandler = globalBaseExceptionHandler;
        this.globalTokenAccessExceptionHandler = globalTokenAccessExceptionHandler;
    }


    /**
     * 反射调用异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({InvocationTargetException.class})
    public Result invocationTargetException(InvocationTargetException exception) throws IllegalAccessException, InvocationTargetException {
        exception.printStackTrace();
        Throwable throwable = exception.getTargetException();
        //判断当前异常
        return (Result) chooseThrowableByType(throwable);
//        // 判断异常case
//        if (throwable.getCause() instanceof SQLIntegrityConstraintViolationException) {
//            return globalDataAccessExceptionHandler.sQLIntegrityConstraintViolationException((Exception) throwable);
//        } else {
//            return ResultFactory.of(DefaultResultCode.REFLEX_EXCEPTION, exception.getMessage());
//        }
    }

    public Object chooseThrowableByType(Throwable throwable) throws IllegalAccessException, InvocationTargetException {
        Field[] fields = GlobalInvocationTargetExceptionHandler.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldObj = field.get(this);
            Method[] methods = field.getType().getDeclaredMethods();
            for (Method method : methods) {
                ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
                if (ObjectUtils.isEmpty(exceptionHandler)) {
                    continue;
                }
                Class clzz = throwable.getClass();
                for (Class<? extends Throwable> aClass : exceptionHandler.value()) {
                    if (clzz.isAssignableFrom(aClass)) {
                        method.setAccessible(true);
                        return (Result) method.invoke(fieldObj, throwable);
                    }
                }

            }
        }
        if (ObjectUtils.isEmpty(throwable.getCause())) {
            return ResultFactory.of(DefaultResultCode.REFLEX_EXCEPTION, throwable.getMessage());
        } else {
            return chooseThrowableByType(throwable.getCause());
        }
    }


}
