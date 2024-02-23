package com.wu.framework.inner.lazy.persistence.util;

import com.wu.framework.inner.layer.util.BinHexSwitchUtil;
import org.springframework.core.io.InputStreamSource;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * describe : mysql 工具
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/2/15 21:05
 */
public class MySQLUtil {


    /**
     * describe 字段转换成mysql 列数据
     *
     * @param fieldValue 字段数据
     * @return mysql 列数据
     * @author Jia wei Wu
     * @date 2022/2/15 20:16
     **/
    public static final Object convertValueToMysqlColumnData(Object fieldValue) {
        if (ObjectUtils.isEmpty(fieldValue)) {
            return null;
        }
        final Class<?> fieldValueClass = fieldValue.getClass();
//        // 二进制
//        final byte[] binary = isBinary(fieldValue);
//        if (!ObjectUtils.isEmpty(binary)) {
//            return BinHexSwitchUtil.bytesToHexSql(binary);
//        }
        // 布尔类型
        if (fieldValueClass.isAssignableFrom(Boolean.class)) {
            return fieldValue;
        }
        // 数字
        if (fieldValueClass.isAssignableFrom(Long.class)) {
            return fieldValue;
        }
        if (fieldValueClass.isAssignableFrom(Integer.class)) {
            return fieldValue;
        }

        // 时间
        if (fieldValueClass.isAssignableFrom(java.util.Date.class)) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return String.format("'%s'", simpleDateFormat.format(fieldValue));
        }
        if (fieldValueClass.isAssignableFrom(java.sql.Date.class)) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return String.format("'%s'", simpleDateFormat.format(fieldValue));
        }
        if (fieldValueClass.isAssignableFrom(LocalDateTime.class)) {
            return String.format("'%s'", ((LocalDateTime) fieldValue).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (fieldValueClass.isAssignableFrom(LocalDate.class)) {
            return String.format("'%s'", ((LocalDate) fieldValue).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (fieldValueClass.isAssignableFrom(LocalTime.class)) {
            return String.format("'%s'", ((LocalTime) fieldValue).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }
        return String.format("'%s'", fieldValue.toString().replaceAll("'", "\""));
    }

    /**
     * describe 判断是对象是否为二进制
     *
     * @param fieldValue 对象字段数据
     * @return boolean true 是二进制 false 不是二进制
     * @author Jia wei Wu
     * @date 2022/2/15 21:09
     **/
    public static final byte[] isBinary(Object fieldValue) throws Exception {
        if (fieldValue == null) {
            return null;
        }
        if (File.class.equals(fieldValue.getClass())) {
            final FileInputStream fileInputStream = new FileInputStream((File) fieldValue);
            return BinHexSwitchUtil.readBytes(fileInputStream);
        } else if (InputStream.class.isAssignableFrom(fieldValue.getClass())) {
            return BinHexSwitchUtil.readBytes((InputStream) fieldValue);
        } else if (InputStreamSource.class.isAssignableFrom(fieldValue.getClass())) {
            return BinHexSwitchUtil.readBytes(((InputStreamSource) fieldValue).getInputStream());
        } else if (Byte[].class.isAssignableFrom(fieldValue.getClass())) {
            return (byte[]) fieldValue;
        } else if (byte[].class.isAssignableFrom(fieldValue.getClass())) {
            return (byte[]) fieldValue;
        }
        return null;
    }
}
