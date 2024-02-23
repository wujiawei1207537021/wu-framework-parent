package com.wu.framework.inner.layer.stereotype.domain;

import com.wu.framework.inner.layer.stereotype.LayerField;
import lombok.Data;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 转换后的字段
 * @date : 2020/7/4 下午10:17
 */
@Data
public class LayerAnalyzeField {


    private String fieldName;

    @Deprecated
    private String convertedFieldName;
    /**
     * 数据库字段索引类型
     */
    private LayerField.LayerFieldType fieldIndexType;
    /**
     * 字段类型
     */
    private Class clazz;
}
