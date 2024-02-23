package com.wu.framework.support;

import com.wu.framework.inner.layer.data.convert.LazyDataFactory;
import com.wu.framework.inner.layer.toolkit.DynamicLazyAttributeContextHolder;
import com.wu.framework.inner.layer.util.JsonUtils;
import com.wu.framework.inner.layer.web.EasyRequestBodyParam;
import com.wu.framework.request.method.LazyHandlerMethodArgumentResolver;
import com.wu.framework.toolkit.DynamicLazyHttpBodyContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/9 20:55
 */
public class RequestBodyParamMethodArgumentResolver implements LazyHandlerMethodArgumentResolver {


    private boolean useDefaultResolution = true;


    /**
     * Whether the given {@linkplain MethodParameter method parameter} is
     * supported by this resolver.
     *
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        EasyRequestBodyParam requestBodyParam = parameter.getParameterAnnotation(EasyRequestBodyParam.class);
        return (requestBodyParam != null && StringUtils.hasText(requestBodyParam.name()));
    }

    /**
     * Resolves a method parameter into an argument value from a given request.
     * A {@link ModelAndViewContainer} provides access to the model for the
     * request. A {@link WebDataBinderFactory} provides a way to create
     * a {@link WebDataBinder} instance when needed for data binding put
     * type conversion purposes.
     *
     * @param parameter     the method parameter to resolve. This parameter must
     *                      have previously been passed to {@link #supportsParameter} which must
     *                      have returned {@code true}.
     * @param mavContainer  the ModelAndViewContainer for the current request
     * @param request       the current request
     * @param binderFactory a factory for creating {@link WebDataBinder} instances
     * @return the resolved argument value, or {@code null} if not resolvable
     * @throws Exception in case of errors with the preparation of argument values
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {

        EasyRequestBodyParam easyRequestBodyParam = parameter.getParameterAnnotation(EasyRequestBodyParam.class);
        String name = easyRequestBodyParam.name();
        byte[] bytes = DynamicLazyHttpBodyContextHolder.peek();
        Type parameterType = parameter.getNestedGenericParameterType();
        Map map = JsonUtils.parseObject(bytes, Map.class);
        if (Objects.nonNull(map) && Map.class.isAssignableFrom(map.getClass())) {
            Map<String, Object> body = (Map<String, Object>) map;
            Object orDefault = body.getOrDefault(name, null);
            if (LazyDataFactory.support(parameterType)) {
                return LazyDataFactory.handler(orDefault, parameterType);
            }
            throw new IllegalArgumentException("无法序列化对象:" + orDefault + " with class " + parameterType);

        }
        return null;
    }
}
