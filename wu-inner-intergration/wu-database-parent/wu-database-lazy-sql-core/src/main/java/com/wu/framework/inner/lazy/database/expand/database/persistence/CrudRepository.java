package com.wu.framework.inner.lazy.database.expand.database.persistence;

import java.util.List;

/**
 * description Repository<T, ID> 持久层接口  控制层自动生成相应的接口
 *
 * @author Jia wei Wu
 * @date 2020/10/10 下午12:29
 * @see com.wu.framework.response.repository.LazyCrudRepository
 */
@Deprecated
public interface CrudRepository<T, ID> extends Repository<T, ID> {

    <T> void save(T t);

    /**
     * 批量更新或插入
     *
     * @param list
     * @param <T>
     */
    <T> void upsertList(List<T> list);

    /**
     * 插入list
     *
     * @param list
     * @param <T>
     */
    <T> void insertList(List<T> list);

    /**
     * 插入 单个
     *
     * @param t
     * @param <T>
     */
    <T> void insert(T t);

    /**
     * 根据ID更新
     *
     * @param t
     * @param <T>
     */
    <T> void updateById(T t);

    /**
     * 根据主键ids更新list
     *
     * @param list
     * @param <T>
     */
    <T> void updateAllByIdList(List<T> list);


    /**
     * 批量删除
     *
     * @param list
     * @param <T>
     */
    <T> void deleteByIdList(List<T> list);

    /**
     * 删除 Serialization
     *
     * @param t
     * @param <T>
     */
    <T> void deleteById(T t);

    /**
     * 删除所有
     *
     * @param t
     * @param <T>
     */
    @Deprecated
    <T> void deleteAll(T t);

    /**
     * 查询
     *
     * @param t
     * @param <T>
     */
    <T> T selectOne(T t);

    /**
     * 查询所有
     *
     * @param t
     * @param <T>
     */
    <T> List<T> selectAll(T t);

    void delete(T t);

    Object count();

    <ID> Object findAllById(Iterable<ID> ids);

    Object findById(ID id);

    Object existsById(ID id);

    <S extends T> void saveAll(Iterable<S> iterable);

    Object findAll();
}
