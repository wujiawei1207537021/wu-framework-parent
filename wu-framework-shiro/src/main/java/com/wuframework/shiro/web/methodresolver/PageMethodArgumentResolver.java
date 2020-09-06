// package com.yuntsoft.cloud.iot.shiro.web.methodresolver;
//
//// import com.baomidou.mybatisplus.plugins.Page;
//// import com.yuntsoft.cloud.iot.common.annotation.RequestPage;
//// import com.yuntsoft.cloud.iot.common.config.consts.ConfigConsts;
// import org.springframework.core.MethodParameter;
// import org.springframework.lang.Nullable;
// import org.springframework.web.bind.support.WebDataBinderFactory;
// import org.springframework.web.context.request.NativeWebRequest;
// import org.springframework.web.method.support.HandlerMethodArgumentResolver;
// import org.springframework.web.method.support.ModelAndViewContainer;
//
// import javax.servlet.http.HttpServletRequest;
// import java.util.Optional;
//
/// **
// * @description: 分页参数数据获取类
// * @author: wangq
// * @create: 2019-10-30 21:15
// **/
// public class PageMethodArgumentResolver implements HandlerMethodArgumentResolver {
//
//    @Override
//    public boolean supportsParameter(MethodParameter methodParameter) {
////        return methodParameter.getParameterType().isAssignableFrom(Page.class)
////                && methodParameter.hasParameterAnnotation(RequestPage.class);
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer
// modelAndViewContainer,
//                                  NativeWebRequest nativeWebRequest, @Nullable
// WebDataBinderFactory webDataBinderFactory) {
//        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//        assert request != null;
////        Integer current =
// Optional.ofNullable(request.getParameter(ConfigConsts.PAGE_CURRENT)).map(Integer::valueOf).orElse(1);
////        Integer size =
// Optional.ofNullable(request.getParameter(ConfigConsts.PAGE_SIZE)).map(Integer::valueOf).orElse(10);
////        return new Page(current, size);
//        return null;
//    }
// }
