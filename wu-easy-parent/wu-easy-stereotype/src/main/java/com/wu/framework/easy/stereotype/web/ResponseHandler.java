package com.wu.framework.easy.stereotype.web;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2020/8/17 下午3:25
 */
@RestControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {

//    private final DictionaryAdapter dictionaryAdapter;
//
//    public ResponseHandler(DictionaryAdapter dictionaryAdapter) {
//        this.dictionaryAdapter = dictionaryAdapter;
//    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        ConvertController convertController = AnnotatedElementUtils.findMergedAnnotation(methodParameter.getMethod(), ConvertController.class);
//        if (null!= convertController && o instanceof HttpResult) {
//            HttpResult httpResult = (HttpResult) o;
//            if (httpResult.isStatus()) {
//                // 基本数据类型
//                if (httpResult.getData().getClass().isPrimitive()) {
//                    return httpResult;
//                }
//                // 处理分页问题
//                dictionaryAdapter.convertObjects(httpResult.getData());
//                return httpResult;
//            }
//        }
        return o;
    }
}