package com.wu.framework.inner.lazy.database.expand.database.persistence.constant;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 常量
 * @date : 2021/3/29 7:34 下午
 */
public class LayerOperationMethodCounts {

    /**
     * {@link LazySqlOperation#upsert(List)}
     */
    public static final String UPSERT_LIST = "upsert";
    /**
     * {@link LazySqlOperation#insertList(List)}
     */
    public static final String INSERT_LIST = "insertList";
    /**
     * {@link LazySqlOperation#insert(Object)}
     */
    public static final String INSERT = "insert";
    /**
     * {@link LazySqlOperation#smartUpsert(Object)}
     */
    public static final String ACTIVE_UPSERT = "smartUpsert";
    /**
     * {@link LazySqlOperation#updateById(Object)}
     */
    public static final String UPDATE_BY_ID = "updateById";
    /**
     * {@link LazySqlOperation#updateAllByIdList(List)}
     */
    public static final String UPDATE_ALL_BY_ID_LIST = "updateAllByIdList";
    /**
     * {@link LazySqlOperation#deleteByIdList(List)}
     */
    public static final String DELETE_BY_ID_LIST = "deleteByIdList";
    /**
     * {@link LazySqlOperation#deleteById(Object)}
     */
    public static final String DELETE_BY_ID = "deleteById";
// /**
//  * {@link com.wu.framework.inner.database.custom.database.persistence.com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation#deleteAll(Object)}
//  */      public static final String 
// DELETE_ALL="deleteAll"; "删除所有";
    /**
     * {@link LazySqlOperation#selectOne(Object)}
     */
    public static final String SELECT_ONE = "selectOne";
    /**
     * {@link LazySqlOperation#selectAll(Object)}
     */
    public static final String SELECT_ALL = "selectAll";

    /**
     * {@link LazySqlOperation#page(LazyPage, Class, String, Object...)}
     */
    public static final String PAGE = "page";
    /**
     * {@link LazySqlOperation#executeSQL(String, Class)}
     */
    public static final String EXECUTE_SQL = "executeSQL";

    /**
     * {@link LazySqlOperation#executeSQLForBean(String, Class)}
     */
    public static final String EXECUTE_SQL_FOR_BEAN = "executeSQLForBean";
    /**
     * {@link LazySqlOperation#miss()}
     */
    public static final String MISS = "miss";
}
