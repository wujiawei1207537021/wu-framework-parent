package com.wu.framework.easy.stereotype.upsert.upsert.entity;

import com.wu.framework.easy.stereotype.upsert.CustomTableFile;
import lombok.Data;

/**
 * description 字段名和需要映射属性名
 *
 * @author 吴佳伟
 * @date 2020/9/17 下午1:28
 */
@Data
public class ConvertedField {
    private String fieldName;

    private String convertedFieldName;
    /**
     * 数据库字段索引类型
     */
    private CustomTableFile.CustomTableFileIndexType fieldIndexType;
    /**
     * 字段类型
     */
    private Class clazz;
}

