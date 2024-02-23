package com.wu.framework.inner.lazy.database.expand.database.persistence;

/**
 * describe : DML 语言
 * 数据操纵语言DML主要有三种形式：
 * 1) 插入：INSERT
 * 2) 更新：UPDATE
 * 3) 删除：DELETE
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/4/23 22:58
 */
public interface LazyBaseDMLOperation {

    /**
     * 批量更新或插入
     *
     * @param objects upsert 对象（单个数据、多个数据）
     * @param <T>     数据范型
     */
    <T> void upsert(Object... objects);

    /**
     * 插入 单个/list
     *
     * @param t   插入对象的实体
     * @param <T> 数据范型
     */
    <T> void insert(T t);

    /**
     * 更新或者存储（根据主键、唯一性索引）
     *
     * @param t   更新或保存数据对象
     * @param <T> 数据范型
     */
    <T> void saveOrUpdate(T t);


    /**
     * 更新或者插入单个执行 去除空值
     * 多个数据性能会慢，不经常使用
     */
    Object upsertRemoveNull(Object... t);

    /**
     * 根据ID更新
     *
     * @param t   更新对象
     * @param <T> 数据范型
     */
    @Deprecated
    <T> void updateById(T t);

}
