package com.wu.framework.inner.lazy.persistence.conf;


import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractLazyTableEndpoint implements LazyTableEndpoint {

    /**
     * 类名
     */
    private String className;
    /**
     * 类
     */
    private Class clazz;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 描述信息
     */
    private String comment;


    /**
     * 字段
     */
    private List<FieldLazyTableFieldEndpoint> fieldEndpoints;

    /**
     * 字典枚举
     */
    private Map<String, Map<String, String>> iEnumList;

    /**
     * 智能填字段
     */
    private boolean smartFillField;
    /**
     * 完善表
     */
    private boolean perfectTable;
    /**
     * 数据向下钻取
     */
    private boolean dataDrillDown;
    /**
     * 数据库名 schema
     */
    private String schema;


    /**
     * 获取表全名
     * mysql.user
     */
    public String getFullTableName() {
        if (ObjectUtils.isEmpty(schema)) {
            return tableName;
        }
        return schema + NormalUsedString.DOT + tableName;
    }

    /**
     * describe 获取指定注解的字段
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/1 6:40 下午
     **/
    public abstract List<FieldLazyTableFieldEndpoint> specifiedFieldAnnotation(LayerField.LayerFieldType fieldType);
}
