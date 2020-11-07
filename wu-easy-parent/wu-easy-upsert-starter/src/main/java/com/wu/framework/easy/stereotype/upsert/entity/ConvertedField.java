package com.wu.framework.easy.stereotype.upsert.entity;

import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

/**
 * description 字段名和需要映射属性名
 *
 * @author Jia wei Wu
 * @date 2020/9/17 下午1:28
 */
@Data
public class ConvertedField {
    private String fieldName;

    private String convertedFieldName;
    /**
     * 数据库字段索引类型
     */
    private EasyTableField.CustomTableFileIndexType fieldIndexType;
    /**
     * 字段类型
     */
    private Class clazz;
}

