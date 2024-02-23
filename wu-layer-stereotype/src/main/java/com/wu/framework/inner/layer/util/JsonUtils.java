package com.wu.framework.inner.layer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Jia wei Wu
 * @params
 * @return
 * @date 2020/9/18 下午10:27
 **/
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        OBJECT_MAPPER.registerModule(javaTimeModule);
    }

    public static String toJsonString(Object value) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换为Json字符串出错:%s" + e);
        }
        return json;
    }

    public static <T> T parseObject(String fromValue, Class<T> toValueType) {
        T toValue;
        try {
            toValue = OBJECT_MAPPER.readValue(fromValue, toValueType);
        } catch (IOException e) {
            throw new RuntimeException("转换为Json字符串出错:%s" + e);
        }
        return toValue;
    }

    public static <T> T parseObject(InputStream src, Class<T> valueType) {
        T toValue;
        try {
            toValue = OBJECT_MAPPER.readValue(src, valueType);
        } catch (IOException e) {
            throw new RuntimeException("转换为Json字符串出错:%s" + e);
        }
        return toValue;
    }

    public static <T> T parseObject(byte[] src, Class<T> valueType) {
        T toValue;
        try {
            toValue = OBJECT_MAPPER.readValue(src, valueType);
        } catch (IOException e) {
            throw new RuntimeException("转换为Json字符串出错:%s" + e);
        }
        return toValue;
    }

    public static <T> T parseObject(String fromValue, TypeReference<T> toValueType) {
        T toValue;
        try {
            toValue = OBJECT_MAPPER.readValue(fromValue, toValueType);
        } catch (IOException e) {
            throw new RuntimeException("转换为Json字符串出错:%s" + e);
        }
        return toValue;
    }

    public static <T> T convertObject(Object fromValue, Class<T> toValueType) {
        return OBJECT_MAPPER.convertValue(fromValue, toValueType);
    }
}
