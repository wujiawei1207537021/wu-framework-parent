package com.wu.framework.easy.excel.web;

import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.endpoint.convert.EasyFilePointConvert;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.inner.layer.data.NormalUsedString;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * describe: 文件  导出拦截
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2023/1/2 18:48
 * @see EasyFile
 */
@RestControllerAdvice
public class EasyFileResponseHandler implements ResponseBodyAdvice<Object> {

    protected EasyFilePointConvert filePointConvert = new EasyFilePointConvert();

    /**
     * 合并多个 byte 数组
     *
     * @param first  第一个二进制数组
     * @param second 第二个二进制数组
     * @return 合并后的二进制数据
     */
    public static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.getMergedAnnotation(methodParameter.getMethod(), EasyFile.class) != null
                || AnnotatedElementUtils.getMergedAnnotation(methodParameter.getMethod().getDeclaringClass(), EasyFile.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> converterType,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        EasyFile easyFile = AnnotatedElementUtils.findMergedAnnotation(Objects.requireNonNull(methodParameter.getMethod()), EasyFile.class);
        EasyFilePoint easyFilePoint = filePointConvert.converter(easyFile);
        EasyFilePoint peek = DynamicEasyFileContextHolder.peek();
        easyFilePoint = peek == null ? easyFilePoint : peek;
        DynamicEasyFileContextHolder.clear();

        HttpHeaders requestHeaders = serverHttpRequest.getHeaders();
        requestHeaders.remove(HttpHeaders.ACCEPT);
        requestHeaders.add(HttpHeaders.ACCEPT,MediaType.APPLICATION_OCTET_STREAM_VALUE);




        if (body == null) {
            return null;
        }
        String fileName = easyFilePoint.getFileName();
        String suffix = easyFilePoint.getSuffix();
        EasyFile.FileType fileType = easyFilePoint.getFileType();
        InputStream inputStream = null;

        if (EasyFile.FileType.STRING_TYPE.equals(fileType)) {
            if (body.getClass().isAssignableFrom(Collection.class)) {
                byte[] bytes = new byte[0];
                // 数组
                for (Object item : ((Collection) body)) {
                    byte[] itemBytes = item.toString().getBytes(StandardCharsets.UTF_8);
                    bytes = concat(bytes, itemBytes);
                }
                inputStream = new ByteArrayInputStream(bytes);
            } else {
                byte[] bytes = ((String) body).getBytes(StandardCharsets.UTF_8);
                inputStream = new ByteArrayInputStream(bytes);
            }

        } else if (EasyFile.FileType.FILE_TYPE.equals(fileType)) {
            if (body.getClass().isAssignableFrom(File.class)) {
                // 数组
                File file = (File) body;
                if (ObjectUtils.isEmpty(fileName)) {
                    fileName = file.getName();
                }
                try {
                    inputStream = new BufferedInputStream(new FileInputStream(file));
                } catch (Exception ex) {
                    throw new RuntimeException("transform file into bin Array 出错", ex);
                }
            }
        } else if (EasyFile.FileType.BYTE_TYPE.equals(fileType)) {
            try {
                inputStream = new ByteArrayInputStream((byte[]) body);
                // spring 框架写数据
//                return body;
            } catch (Exception ex) {
                throw new RuntimeException("transform file into bin Array 出错", ex);
            }
        }


        HttpHeaders headers = serverHttpResponse.getHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        headers.add(HttpHeaders.TRANSFER_ENCODING, "chunked");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add(HttpHeaders.ACCEPT,MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String encodedFileName = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; " +
                                "filename=\"%s.%s\"; " +
                                "filename*=utf-8''%s.%s",
                        encodedFileName, suffix,
                        encodedFileName, suffix)
        );
        headers.add("File-Name", encodedFileName + NormalUsedString.DOT + suffix);



        try {
            OutputStream bodyOutputStream = new BufferedOutputStream(serverHttpResponse.getBody());
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {   //不能一次性读完，大文件会内存溢出（不能直接fis.read(buffer);）
                bodyOutputStream.write(buffer, 0, i);
            }
            inputStream.close();
            bodyOutputStream.flush();
            bodyOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }
}