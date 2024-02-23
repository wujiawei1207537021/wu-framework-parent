package com.wu.framework.inner.lazy.persistence.conf.index;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableIndexEndpoint;
import lombok.Data;

@Data
public abstract class AbstractLazyTableIndexEndpoint implements LazyTableIndexEndpoint {

    /**
     * 索引名称 当索引类型不是NONE 时有效
     */
    private String indexName = "";

    /**
     * 数据库字段索引类型
     */
    private LayerField.LayerFieldType fieldIndexType = LayerField.LayerFieldType.NONE;

    /**
     * 获取索引端点对象
     *
     * @return
     */
    public static AbstractLazyTableIndexEndpoint getInstance() {
        return new FieldLazyTableIndexEndpoint();
    }
}
