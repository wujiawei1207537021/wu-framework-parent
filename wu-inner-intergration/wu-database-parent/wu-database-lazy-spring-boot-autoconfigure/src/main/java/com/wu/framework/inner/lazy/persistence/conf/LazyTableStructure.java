package com.wu.framework.inner.lazy.persistence.conf;

import com.wu.framework.inner.layer.data.schema.Structure;

import java.util.List;

/**
 * describe : 惰性表结构
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/17 8:11 下午
 */
public class LazyTableStructure implements Structure<LazyTableEndpoint, List<List<Object>>> {
    private final LazyTableEndpoint schema;
    private final List<List<Object>> payload;

    public LazyTableStructure(LazyTableEndpoint schema, List<List<Object>> payload) {
        this.schema = schema;
        this.payload = payload;
    }

    @Override
    public LazyTableEndpoint schema() {
        return schema;
    }

    @Override
    public List<List<Object>> payload() {
        return payload;
    }
}
