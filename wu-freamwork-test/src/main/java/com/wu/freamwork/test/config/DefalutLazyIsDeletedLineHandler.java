package com.wu.freamwork.test.config;

import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.isDeleted.LazyIsDeletedLineHandler;

public class DefalutLazyIsDeletedLineHandler implements LazyIsDeletedLineHandler {
    /**
     * 获取 is_deleted 数据
     * <p>
     *
     * @return 租户 ID 值表达式
     */
    @Override
    public Boolean getIsDeleted() {
        return false;
    }

    /**
     * 根据表名判断是否忽略拼接is_deleted条件
     * <p>
     * 默认都要进行解析并拼接is_deleted条件
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接is_deleted条件
     */
    @Override
    public boolean ignoreTable(String tableName) {
        return !"lazy_user_test".equals(tableName);
    }
}
