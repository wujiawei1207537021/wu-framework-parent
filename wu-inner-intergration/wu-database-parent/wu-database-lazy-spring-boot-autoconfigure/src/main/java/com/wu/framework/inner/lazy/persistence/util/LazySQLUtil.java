package com.wu.framework.inner.lazy.persistence.util;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.util.BinHexSwitchUtil;
import com.wu.framework.inner.layer.util.JsonUtils;
import com.wu.framework.inner.lazy.config.enums.RowValueType;
import org.springframework.core.io.InputStreamSource;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * describe : mysql 工具
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/2/15 21:05
 */
public final class LazySQLUtil {


    /**
     * describe 字段转换成 sql 列数据
     *
     * @param fieldValue   字段数据
     * @param rowValueType 字段类型 表达式、对象
     * @return mysql 列数据
     * @author Jia wei Wu
     * @date 2022/2/15 20:16
     **/
    public static Object valueToSqlValue(Object fieldValue, RowValueType rowValueType) {
        if (RowValueType.STRING.equals(rowValueType)) {
            // RowValueType.STRING 对象字段
            return valueToSqlValue(fieldValue, "'%s'");
        } else {
            // RowValueType.EXPRESSION 表达式
            return valueToSqlValue(fieldValue, "%s");
        }

    }

    /**
     * describe 字段转换成 sql 列数据
     *
     * @param fieldValue 字段数据
     * @return mysql 列数据
     * @author Jia wei Wu
     * @date 2022/2/15 20:16
     **/
    public static Object valueToSqlValue(Object fieldValue) {
        return valueToSqlValue(fieldValue, "'%s'");
    }

    /**
     * describe 字段转换成 sql 列数据
     *
     * @param fieldValue  字段数据
     * @param placeholder 占位符 '%s'、%s
     * @return mysql 列数据
     * @author Jia wei Wu
     * @date 2022/2/15 20:16
     **/
    public static Object valueToSqlValue(Object fieldValue, String placeholder) {
        if (ObjectUtils.isEmpty(fieldValue)) {
            return null;
        }
        final Class<?> fieldValueClass = fieldValue.getClass();
        // 二进制
        final byte[] binary = isBinary(fieldValue);
        if (!ObjectUtils.isEmpty(binary)) {
            return BinHexSwitchUtil.bytesToHexSql(binary);
        }
        // 布尔类型
        if (fieldValueClass.isAssignableFrom(Boolean.class)) {
            return fieldValue;
        }
        if (fieldValueClass.isAssignableFrom(boolean.class)) {
            return fieldValue;
        }
        // Long 数字
        if (fieldValueClass.isAssignableFrom(Long.class)) {
            return fieldValue;
        }
        if (fieldValueClass.isAssignableFrom(long.class)) {
            return fieldValue;
        }
        // Integer 类型
        if (fieldValueClass.isAssignableFrom(Integer.class)) {
            return fieldValue;
        }
        if (fieldValueClass.isAssignableFrom(int.class)) {
            return fieldValue;
        }
        // 枚举
        if (fieldValueClass.isEnum()) {
            return String.format(placeholder, ((Enum<?>) fieldValue).name());
        }
        // 字符串
        if (fieldValueClass.isAssignableFrom(String.class)
                || fieldValueClass.isAssignableFrom(Double.class) // Double
                || fieldValueClass.isAssignableFrom(double.class) //double
                || fieldValueClass.isAssignableFrom(short.class) //short
                || fieldValueClass.isAssignableFrom(Short.class) //Short
                || fieldValueClass.isAssignableFrom(Float.class) //Float
                || fieldValueClass.isAssignableFrom(float.class) //float
                || fieldValueClass.isAssignableFrom(BigDecimal.class) //BigDecimal
                || fieldValueClass.isEnum() // 枚举
        ) {
            return String.format(placeholder, fieldValue.toString()
                    .replaceAll("'", "\"")
                    .replaceAll("\\\\", "\\\\\\\\")// 处理toString 后反斜杠问题
            );
        }
        // 时间
        if (java.util.Date.class.isAssignableFrom(fieldValueClass)) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return String.format(placeholder, simpleDateFormat.format(fieldValue));
        }
        if (java.sql.Date.class.isAssignableFrom(fieldValueClass)) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return String.format(placeholder, simpleDateFormat.format(fieldValue));
        }
        if (LocalDateTime.class.isAssignableFrom(fieldValueClass)) {
            return String.format(placeholder, ((LocalDateTime) fieldValue).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (LocalDate.class.isAssignableFrom(fieldValueClass)) {
            return String.format(placeholder, ((LocalDate) fieldValue).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (LocalTime.class.isAssignableFrom(fieldValueClass)) {
            return String.format(placeholder, ((LocalTime) fieldValue).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }
        if (Collection.class.isAssignableFrom(fieldValueClass)) {
            return sqlValue(JsonUtils.toJsonString(fieldValue), true);
        }
//        return String.format("'%placeholder'", fieldValue.toString().replaceAll("'", "\""));
        return sqlValue(JsonUtils.toJsonString(fieldValue), true);
    }


    /**
     * describe 判断是对象是否为二进制
     *
     * @param fieldValue 对象字段数据
     * @return boolean true 是二进制 false 不是二进制
     * @author Jia wei Wu
     * @date 2022/2/15 21:09
     **/
    public static byte[] isBinary(Object fieldValue) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param rowValue
     * @return
     * @see LazySQLUtil#valueToSqlValue(Object)
     */
    @Deprecated
    public static Object sqlValue(Object rowValue) {
        return sqlValue(rowValue, true);
    }

    /**
     * 获取sql 对应的value
     *
     * @param rowValue 列数据
     * @param hasComma 是否包含'
     * @see LazySQLUtil#valueToSqlValue(Object)
     */
    @Deprecated
    public static Object sqlValue(Object rowValue, boolean hasComma) {
        if (null == rowValue) {
            return NormalUsedString.SPACE + rowValue;
        }
        Class<?> rowValueClass = rowValue.getClass();
        if (String.class.isAssignableFrom(rowValueClass) || Enum.class.isAssignableFrom(rowValueClass)) {
            return NormalUsedString.SPACE + (hasComma ? NormalUsedString.SINGLE_QUOTE + rowValue + NormalUsedString.SINGLE_QUOTE : rowValue) + NormalUsedString.SPACE;
        } else if (Boolean.class.isAssignableFrom(rowValueClass) || boolean.class.isAssignableFrom(rowValueClass)) {
            return NormalUsedString.SPACE + rowValue;
        } else if (java.util.Date.class.isAssignableFrom(rowValueClass) || java.sql.Date.class.isAssignableFrom(rowValueClass)) {
            return NormalUsedString.SPACE + (hasComma ? NormalUsedString.SINGLE_QUOTE + rowValue + NormalUsedString.SINGLE_QUOTE : rowValue) + NormalUsedString.SPACE;
        } else if (int.class.isAssignableFrom(rowValueClass) || Integer.class.isAssignableFrom(rowValueClass)
                || Long.class.isAssignableFrom(rowValueClass) || Long.class.isAssignableFrom(rowValueClass)
                || Double.class.isAssignableFrom(rowValueClass) || double.class.isAssignableFrom(rowValueClass)
                || Float.class.isAssignableFrom(rowValueClass) || float.class.isAssignableFrom(rowValueClass)) {
            return NormalUsedString.SPACE + rowValue;
        }
        return NormalUsedString.SPACE + rowValue;
    }
}
