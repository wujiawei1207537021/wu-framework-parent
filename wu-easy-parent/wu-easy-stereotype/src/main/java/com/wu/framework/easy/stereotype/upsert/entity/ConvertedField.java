package com.wu.framework.easy.stereotype.upsert.entity;

import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;

/**
 * description 字段名和需要映射属性名
 *
 * @author Jia wei Wu
 * @date 2020/9/17 下午1:28
 */
@Data
public class ConvertedField {
    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 数据库对应字段
     */
    private String convertedFieldName;
    /**
     * 数据库字段索引类型
     */
    private EasySmartField.TableFileIndexType fieldIndexType;
    /**
     * 字段类型
     */
    private Class clazz;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 字段描述
     */
    private String comment;
}

