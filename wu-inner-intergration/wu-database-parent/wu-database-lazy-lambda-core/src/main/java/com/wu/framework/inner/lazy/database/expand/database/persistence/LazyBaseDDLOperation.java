package com.wu.framework.inner.lazy.database.expand.database.persistence;

import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.stereotype.proxy.ProxyLazyBaseLayerStrategicApproach;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;

/**
 * <img width="100" height="200" src="https://gitee.com/wujiawei1207537021/wu-framework-parent/raw/wu-framework/1.2.0-JDK1.8-SNAPSHOT/wu-inner-intergration/wu-database-parent/wu-database-lazy-lambda-core/src/main/resources/annex/online-ddl.png" alt="">
 *
 * <p>
 * describe : DDL 语言
 * 数据定义语言DDL用来创建数据库中的各种对象-----表、视图、
 * 索引、同义词、聚簇等如：
 * CREATE TABLE/VIEW/INDEX/SYN/CLUSTER
 * |         |          |          |          |
 * 表    视图    索引   同义词    簇
 * <p>
 * DDL操作是隐性提交的！不能rollback
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/4/23 22:58
 */
public interface LazyBaseDDLOperation {


    /**
     * describe 完善表
     *
     * @param entityClasses class 对象数组
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 5:05 下午
     **/
    @ProxyLazyBaseLayerStrategicApproach(proxyClassName = "com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyOperationMethodPerfect")
    <T> T perfect(@NonNull Class<T>... entityClasses);

    /**
     * describe 完善表
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/6 13:59
     **/
    @ProxyLazyBaseLayerStrategicApproach(proxyClassName = "com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyOperationMethodPerfect")
    <T> T perfect(LazyTableEndpoint... lazyTableEndpointList);

    /**
     * describe 创建表
     *
     * @param entityClasses class 对象数组
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 7:48 下午
     **/
    @ProxyLazyBaseLayerStrategicApproach(proxyClassName = "com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyOperationMethodCreateTable")
    <T> T createTable(@NonNull Class<T>... entityClasses);


    /**
     * describe 更新表
     *
     * @param entityClasses class 对象数组
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 7:49 下午
     **/
    @ProxyLazyBaseLayerStrategicApproach(proxyClassName = "com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyOperationMethodUpdateTable")
    <T> T updateTable(@NonNull Class<T>... entityClasses);

    /**
     * describe 创建视图
     *
     * @param tableClazz 表对应的实体
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/5/1 21:25
     **/
    @ProxyLazyBaseLayerStrategicApproach(proxyClassName = "com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyOperationMethodPerfect")
    void createView(Class<?> tableClazz);

    /**
     * describe 创建索引
     *
     * @param tableClazz 表对应的实体
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/5/1 21:25
     **/
    @ProxyLazyBaseLayerStrategicApproach(proxyClassName = "com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyOperationMethodPerfect")
    void createIndex(Class<?> tableClazz);


    /**
     * 执行生sql 脚本
     *
     * @param resources 脚本资源
     * @return Object
     */
    @ProxyLazyBaseLayerStrategicApproach(proxyClassName = "com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyOperationMethodScriptRunner")
    Object scriptRunner(Resource... resources);


    /**
     * 执行生sql 脚本
     *
     * @param scripts 执行脚本
     * @return Object
     */
    @ProxyLazyBaseLayerStrategicApproach(proxyClassName = "com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyOperationMethodStringScriptRunner")
    Object stringScriptRunner(String... scripts);
}
