package com.wu.framework.inner.layer.stereotype.analyze;

import com.wu.framework.inner.layer.stereotype.LayerField;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * description 字段名和需要映射属性名
 *
 * @author Jia wei Wu
 * @date 2020/9/17 下午1:28
 */
@Builder()
@Accessors(chain = true)
@Data
public class AnalyzeField {
    /**
     * 字段是否存在
     */
    boolean exist = true;
    /**
     * 字段索引类型(数据库)
     */
    LayerField.LayerFieldType fieldIndexType = LayerField.LayerFieldType.FIELD_TYPE;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 数据库对应字段
     */
    private String convertedFieldName;
    /**
     * 诠释
     */
    private String comment;
    /**
     * 字段class
     */
    private Class clazz;
}

