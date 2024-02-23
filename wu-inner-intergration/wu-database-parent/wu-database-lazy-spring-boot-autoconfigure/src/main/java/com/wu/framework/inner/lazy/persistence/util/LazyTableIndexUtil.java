package com.wu.framework.inner.lazy.persistence.util;

import com.wu.framework.inner.lazy.persistence.conf.LazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.index.AbstractLazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库索引解析工具
 */
public class LazyTableIndexUtil {

    /**
     * 解析字段上的索引
     *
     * @param lazyTableField
     * @return
     */
    public static LazyTableIndexEndpoint[] analyzeFieldIndex(LazyTableField lazyTableField) {
        List<LazyTableIndexEndpoint> lazyTableIndexEndpointList = new ArrayList<>();
        LazyTableIndex[] lazyTableIndices = lazyTableField.lazyTableIndexs();
        if (ObjectUtils.isEmpty(lazyTableIndices)) {
            return lazyTableIndexEndpointList.toArray(new LazyTableIndexEndpoint[0]);
        } else {
            for (LazyTableIndex lazyTableIndex : lazyTableIndices) {
                AbstractLazyTableIndexEndpoint instance = AbstractLazyTableIndexEndpoint.getInstance();
                instance.setIndexName(lazyTableIndex.indexName());
                instance.setFieldIndexType(lazyTableIndex.indexType());
                lazyTableIndexEndpointList.add(instance);
            }
        }
        return lazyTableIndexEndpointList.toArray(new LazyTableIndexEndpoint[0]);
    }
}
