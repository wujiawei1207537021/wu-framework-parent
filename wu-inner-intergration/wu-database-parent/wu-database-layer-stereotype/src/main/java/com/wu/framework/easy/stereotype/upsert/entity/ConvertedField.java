package com.wu.framework.easy.stereotype.upsert.entity;

import com.wu.framework.inner.layer.stereotype.LayerField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * description 字段名和需要映射属性名
 *
 * @author Jia wei Wu
 * @date 2020/9/17 下午1:28
 */
@Accessors(chain = true)
@Data
public class ConvertedField {
    /**
     * 字段是否存在
     */
    boolean exist = true;
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
    private LayerField.LayerFieldType fieldIndexType = LayerField.LayerFieldType.FILE_TYPE;
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

    /**
     * @return
     * @params 创建SQL column
     * "`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
     * @author Jiawei Wu
     * @date 2020/12/31 9:19 下午
     **/
    public String createColumn() {
        return convertedFieldName + " " + type + " COMMENT '" + comment + "', \n";
    }
}

