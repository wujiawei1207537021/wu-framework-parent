package com.wu.framework.inner.lazy.database.expand.database.persistence.constant;

import com.wu.framework.inner.lazy.database.domain.Page;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 常量
 * @date : 2021/3/29 7:34 下午
 */
public class LayerOperationMethodCounts {

    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#upsert(List)}
     */
    public static final String UPSERT_LIST = "upsert";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#insertList(List)}
     */
    public static final String INSERT_LIST = "insertList";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#insert(Object)}
     */
    public static final String INSERT = "insert";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#smartUpsert(Object)}
     */
    public static final String ACTIVE_UPSERT = "smartUpsert";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#updateById(Object)}
     */
    public static final String UPDATE_BY_ID = "updateById";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#updateAllByIdList(List)}
     */
    public static final String UPDATE_ALL_BY_ID_LIST = "updateAllByIdList";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#deleteByIdList(List)}
     */
    public static final String DELETE_BY_ID_LIST = "deleteByIdList";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#deleteById(Object)}
     */
    public static final String DELETE_BY_ID = "deleteById";
// /**
//  * {@link com.wu.framework.inner.database.custom.database.persistence.com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#deleteAll(Object)}
//  */      public static final String 
// DELETE_ALL="deleteAll"; "删除所有";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#selectOne(Object)}
     */
    public static final String SELECT_ONE = "selectOne";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#selectAll(Object)}
     */
    public static final String SELECT_ALL = "selectAll";

    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#page(Page, Class, String, Object...)}
     */
    public static final String PAGE = "page";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#executeSQL(String, Class)}
     */
    public static final String EXECUTE_SQL = "executeSQL";

    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#executeSQLForBean(String, Class)}
     */
    public static final String EXECUTE_SQL_FOR_BEAN = "executeSQLForBean";
    /**
     * {@link com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation#miss()}
     */
    public static final String MISS = "miss";
}
