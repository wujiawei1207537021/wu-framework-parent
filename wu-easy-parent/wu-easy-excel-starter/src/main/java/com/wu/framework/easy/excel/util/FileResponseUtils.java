package com.wu.framework.easy.excel.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Deprecated
public class FileResponseUtils {

    public static ResponseEntity<byte[]> from(String fileName, String suffix, byte[] bytes) {
        // 判断导出数据量
        if (bytes == null || bytes.length == 0) {
            throw new RuntimeException("数据量为0，无法导出!");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        String encodedFileName = null;
        try {
            encodedFileName = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s.%s\"; filename*=utf-8''%s.%s", encodedFileName, suffix, encodedFileName, suffix));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(bytes.length).contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytes);
    }



}