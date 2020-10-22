package com.wu.framework.inner.db.component.web.methodresolver;


import com.wu.framework.easy.stereotype.web.RequestPage;
import com.wu.framework.inner.db.config.pro.DBPageConfigPro;
import com.wu.framework.inner.db.utils.ValGet;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 分页参数数据获取类
 * @author: wangq
 * @create: 2019-10-30 21:15
 */

public class JpaPageRequestMethodArgumentResolver implements HandlerMethodArgumentResolver {


    @Resource
    private DBPageConfigPro dbPageConfigPro;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(PageRequest.class)
                && methodParameter.hasParameterAnnotation(RequestPage.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            @Nullable ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            @Nullable WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        return ValGet.getJpaPageRequest4Request(request, dbPageConfigPro);
    }
}
