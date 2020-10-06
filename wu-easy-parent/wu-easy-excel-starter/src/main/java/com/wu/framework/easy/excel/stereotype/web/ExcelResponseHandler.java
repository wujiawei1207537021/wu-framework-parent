package com.wu.framework.easy.excel.stereotype.web;


import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.excel.util.ExcelExportUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Objects;

/**
 * description excel 导出拦截
 *
 * @author 吴佳伟
 * @date 2020/8/17 下午3:25
 */
@RestControllerAdvice
public class ExcelResponseHandler implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getMethod().getAnnotation(EasyExcel.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object o, @NotNull MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        EasyExcel easyExcel = AnnotatedElementUtils.findMergedAnnotation(Objects.requireNonNull(methodParameter.getMethod()), EasyExcel.class);
        if (null != easyExcel && o instanceof Collection) {
            Collection collection = (Collection) o;
            HttpHeaders headers = serverHttpResponse.getHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            String encodedFileName = null;
            try {
                encodedFileName = java.net.URLEncoder.encode(easyExcel.fileName(), StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s.%s\"; filename*=utf-8''%s.%s", encodedFileName, easyExcel.suffix(), encodedFileName, easyExcel.suffix()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            byte[] bytes = ExcelExportUtil.exportExcel(easyExcel.fileName(), collection);
            try {
                OutputStream body = serverHttpResponse.getBody();
                body.write(bytes);
                body.flush();
                body.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return o;
    }
}