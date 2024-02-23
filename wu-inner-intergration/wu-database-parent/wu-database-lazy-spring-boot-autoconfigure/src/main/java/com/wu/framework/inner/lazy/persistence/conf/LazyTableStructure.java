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
public class LazyTableStructure implements Structure<LazyTableEndpoint, List<List<LazyTableFieldEndpoint>>> {
    private final LazyTableEndpoint schema;
    /**
     * 当前对象的属性数据
     */
    private final List<List<LazyTableFieldEndpoint>> payload;

    public LazyTableStructure(LazyTableEndpoint schema, List<List<LazyTableFieldEndpoint>> payload) {
        this.schema = schema;
        this.payload = payload;
    }

    @Override
    public LazyTableEndpoint schema() {
        return schema;
    }

    @Override
    public List<List<LazyTableFieldEndpoint>> payload() {
        return payload;
    }
}
