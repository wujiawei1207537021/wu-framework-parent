package com.wu.framework.inner.database;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 单个数据源适配器
 * @date : 2020/8/28 下午11:11
 */

@ConditionalOnBean(value = SimpleEasyDataSource.class)
public class SimpleEasyDataSourceAdapter implements EasyDataSourceAdapter {


    private final EasyDataSource easyDataSource;

    public SimpleEasyDataSourceAdapter(SimpleEasyDataSource simpleCustomDataSource) {
        this.easyDataSource = simpleCustomDataSource;
    }

    @Override
    public EasyDataSource getEasyDataSource() {
        // 多个数据源

        return easyDataSource;
    }
}
