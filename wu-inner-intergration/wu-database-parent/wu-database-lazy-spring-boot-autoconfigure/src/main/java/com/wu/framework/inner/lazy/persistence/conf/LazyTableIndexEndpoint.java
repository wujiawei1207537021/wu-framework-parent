package com.wu.framework.inner.lazy.persistence.conf;

import com.wu.framework.inner.layer.stereotype.LayerField;

/**
 * 表索引
 */
public interface LazyTableIndexEndpoint {

    /**
     * 索引名称 当索引类型不是NONE 时有效
     */
    String getIndexName();

    void setIndexName(String indexName);

    /**
     * 数据库字段索引类型
     */
    LayerField.LayerFieldType getFieldIndexType();

    void setFieldIndexType(LayerField.LayerFieldType unique);
}
