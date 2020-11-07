package com.wu.framework.inner.database;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 单个数据源适配器
 * @date : 2020/8/28 下午11:11
 */

@ConditionalOnBean(value = SimpleCustomDataSource.class)
public class SimpleCustomDataSourceAdapter implements CustomDataSourceAdapter {


    private final CustomDataSource customDataSource;

    public SimpleCustomDataSourceAdapter(SimpleCustomDataSource simpleCustomDataSource) {
        this.customDataSource = simpleCustomDataSource;
    }

    @Override
    public CustomDataSource getCustomDataSource() {
        // 多个数据源

        return customDataSource;
    }
}
