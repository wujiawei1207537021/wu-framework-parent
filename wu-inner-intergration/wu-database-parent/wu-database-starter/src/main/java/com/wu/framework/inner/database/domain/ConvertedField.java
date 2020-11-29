package com.wu.framework.inner.database.domain;

import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 转换后的字段
 * @date : 2020/7/4 下午10:17
 */
@Data
public class ConvertedField {
    private String fieldName;

    private String convertedFieldName;
    /**
     * 数据库字段索引类型
     */
    private EasyTableField.TableFileIndexType fieldIndexType;
    /**
     * 字段类型
     */
    private Class clazz;
}
