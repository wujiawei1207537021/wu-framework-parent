package com.wu.framework.inner.lazy.persistence.map;

import com.wu.framework.inner.lazy.config.enums.RowValueType;
import com.wu.framework.inner.lazy.persistence.util.LazySQLUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

/**
 * db 数据查询结果
 * 自动转换时间格式为字符串
 */
@Setter
@Getter
public class DbMap extends LinkedHashMap<String, Object> {

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     * (A {@code null} return can also indicate that the map
     * previously associated {@code null} with {@code key}.)
     */
    @Override
    public Object put(String key, Object value) {
        if (value != null) {
            value = LazySQLUtil.valueToSqlValue(value, RowValueType.EXPRESSION);
            return super.put(key, value);
        } else {
            return super.put(key, value);
        }

    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }


    @Override
    public String toString() {
        return "DbMap{} " + super.toString();
    }
}
