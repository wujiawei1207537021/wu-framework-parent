package com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.isDeleted;

/**
 * 默认所有操作添加 is_delete 筛选
 */
public interface LazyIsDeletedLineHandler {
    /**
     * 获取 is_deleted 数据
     * <p>
     *
     * @return 租户 ID 值表达式
     */
    Boolean getIsDeleted();

    /**
     * 获取is_deleted 字段名
     * <p>
     * 默认字段名叫: is_deleted
     *
     * @return is_deleted 字段名
     */
    default String getIsDeletedColumn() {
        return "is_deleted";
    }

    /**
     * 根据表名判断是否忽略拼接is_deleted条件
     * <p>
     * 默认都要进行解析并拼接is_deleted条件
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接is_deleted条件
     */
    default boolean ignoreTable(String tableName) {
        return false;
    }
}
