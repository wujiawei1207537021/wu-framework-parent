//package com.wuframework.shiro.web.interceptors;
//
//import com.wuframework.shiro.annotation.AccessLimit;
//import org.springframework.core.MethodParameter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//@Component
//public class HandlerMethodReturnValueHandler1 implements HandlerMethodReturnValueHandler {
//    @Override
//    public boolean supportsReturnType(MethodParameter methodParameter) {
//        boolean hasJSONAnno = methodParameter.getMethodAnnotation(AccessLimit.class) != null || methodParameter.getMethodAnnotation(AccessLimit.class) != null;
//        return hasJSONAnno;
//    }
//
//    @Override
//    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
//
//        System.out.println(o);
//    }
//}
