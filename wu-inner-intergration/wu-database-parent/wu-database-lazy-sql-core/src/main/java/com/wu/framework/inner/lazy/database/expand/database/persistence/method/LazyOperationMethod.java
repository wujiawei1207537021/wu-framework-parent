package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

import java.sql.Connection;

/**
 * description 自定义数据库持久层操作方法
 *
 * @author Jia wei Wu
 * @date 2020/7/28 上午8:55
 */
public interface LazyOperationMethod {

    /**
     * description 通过参数获取持久性存储库对象
     *
     * @param sourceParams 参数
     * @return PersistenceRepository 持久层对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    PersistenceRepository analyzePersistenceRepository(Object[] sourceParams);

    /**
     * describe  执行SQL 语句
     *
     * @param connection   当前线程链接对象
     * @param sourceParams 原始参数
     * @return Object 返回对象
     * @author Jia wei Wu
     * @date 2022/1/2 8:10 下午
     **/
    Object execute(Connection connection, Object[] sourceParams) throws Exception;


}
