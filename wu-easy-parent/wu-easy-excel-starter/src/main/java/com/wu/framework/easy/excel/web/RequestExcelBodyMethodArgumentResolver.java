package com.wu.framework.easy.excel.web;

import com.wu.framework.easy.excel.stereotype.RequestExcelBody;
import com.wu.framework.easy.excel.util.FastExcelImp;
import com.wu.framework.request.method.LazyHandlerMethodArgumentResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/9 20:55
 */
public class RequestExcelBodyMethodArgumentResolver implements LazyHandlerMethodArgumentResolver {


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
        if (parameter.hasParameterAnnotation(RequestExcelBody.class)) {
            if (Map.class.isAssignableFrom(parameter.nestedIfOptional().getNestedParameterType())) {
                RequestExcelBody requestExcelBody = parameter.getParameterAnnotation(RequestExcelBody.class);
                return (requestExcelBody != null && StringUtils.hasText(requestExcelBody.name()));
            } else {
                return true;
            }
        } else {
            if (parameter.hasParameterAnnotation(RequestExcelBody.class)) {
                return false;
            }
            parameter = parameter.nestedIfOptional();
            if (MultipartResolutionDelegate.isMultipartArgument(parameter)) {
                return true;
            } else {
                return false;
            }
        }
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

        RequestExcelBody requestExcelBody = parameter.getParameterAnnotation(RequestExcelBody.class);
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        String name = requestExcelBody.name();
        if (servletRequest != null) {
            Object mpArg = MultipartResolutionDelegate.resolveMultipartArgument(name, parameter, servletRequest);
            if (mpArg != MultipartResolutionDelegate.UNRESOLVABLE) {
                return mpArg;
            }
        }

        Object arg = null;
        MultipartRequest multipartRequest = request.getNativeRequest(MultipartRequest.class);
        if (multipartRequest != null) {
            List<MultipartFile> files = multipartRequest.getFiles(name);
            if (!files.isEmpty()) {
                arg = (files.size() == 1 ? files.get(0) : files);
            }
        }
        if (arg == null) {
            String[] paramValues = request.getParameterValues(name);
            if (paramValues != null) {
                arg = (paramValues.length == 1 ? paramValues[0] : paramValues);
            }
        }
        Type parameterType = parameter.getGenericParameterType();
        Class<? extends Type> parameterTypeClass = parameterType.getClass();
        if (ParameterizedType.class.isAssignableFrom(parameterTypeClass)) {
            ParameterizedType parameterizedType = (ParameterizedType) parameterType;
            Class<?> actualTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            return FastExcelImp.parseExcel((MultipartFile) arg, actualTypeArgument);

        }
        return null;
    }
}
