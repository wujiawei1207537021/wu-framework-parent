package com.wu.framework.easy.excel.web;

import com.wu.framework.easy.excel.adapter.ExcelExcelServiceAdapter;
import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.endpoint.convert.EasyExcelPointConvert;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelContextHolder;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelPointContextHolder;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.toolkit.DynamicLazyAttributeContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * description excel 导出拦截
 *
 * @author Jia wei Wu
 * @date 2020/8/17 下午3:25
 */
@RestControllerAdvice
public class ExcelResponseHandler implements ResponseBodyAdvice<Object> {
    private final ExcelExcelServiceAdapter excelExcelServiceAdapter;

    protected EasyExcelPointConvert excelPointConvert = new EasyExcelPointConvert();

    public ExcelResponseHandler(ExcelExcelServiceAdapter excelExcelServiceAdapter) {
        this.excelExcelServiceAdapter = excelExcelServiceAdapter;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return AnnotatedElementUtils.getMergedAnnotation(methodParameter.getMethod(), EasyExcel.class) != null
                || AnnotatedElementUtils.getMergedAnnotation(methodParameter.getMethod().getDeclaringClass(), EasyExcel.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        EasyExcel easyExcel = AnnotatedElementUtils.findMergedAnnotation(Objects.requireNonNull(methodParameter.getMethod()), EasyExcel.class);

        EasyExcelPoint easyExcelPoint = excelPointConvert.converter(easyExcel);

        EasyExcelPoint peek = DynamicEasyExcelPointContextHolder.peek();
        easyExcelPoint = ObjectUtils.isEmpty(peek) ? easyExcelPoint : peek;

        String fileName = easyExcelPoint.getFileName();
        String suffix = easyExcelPoint.getSuffix();
        DynamicEasyExcelPointContextHolder.clear();
        Collection collection;
        if (o instanceof Collection) {
            collection = (Collection) o;

        } else {
            collection = Arrays.asList(o);
        }
        HttpHeaders headers = serverHttpResponse.getHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        String encodedFileName = null;
        encodedFileName = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s.%s\"; filename*=utf-8''%s.%s", encodedFileName, suffix, encodedFileName, suffix));
        headers.add("File-Name", encodedFileName + NormalUsedString.DOT + suffix);
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        byte[] bytes = excelExcelServiceAdapter.exportExcel(collection, easyExcelPoint);
        try {
            OutputStream body = serverHttpResponse.getBody();
            body.write(bytes);
            body.flush();
            body.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DynamicEasyExcelContextHolder.clearALL();
        }
        return o;
    }
}