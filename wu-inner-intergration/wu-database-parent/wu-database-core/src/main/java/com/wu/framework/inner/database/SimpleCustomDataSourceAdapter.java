package com.wu.framework.inner.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 单个数据源适配器
 * @date : 2020/8/28 下午11:11
 */

@ConditionalOnBean(value = CustomDataSource.class,name = CustomDataSourceAdapter.DEFAULT_DATA_SOURCE)
public class SimpleCustomDataSourceAdapter implements CustomDataSourceAdapter {


    private final CustomDataSource customDataSource;

    public SimpleCustomDataSourceAdapter(@Qualifier(CustomDataSourceAdapter.DEFAULT_DATA_SOURCE) SimpleCustomDataSource simpleCustomDataSource) {
        this.customDataSource = simpleCustomDataSource;
    }

    @Override
    public CustomDataSource getCustomDataSource() {
        // 多个数据源

        return customDataSource;
    }
}
