package com.wu.smart.acw.server.application.impl;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.DynamicAdapter;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperation;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.core.domain.uo.AcwSchemaUo;
import com.wu.smart.acw.core.domain.uo.AcwTableColumnUo;
import com.wu.smart.acw.core.domain.uo.AcwTableUo;
import com.wu.smart.acw.server.application.AcwInstanceApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/29 22:21
 */
@Service
public class AcwInstanceApplicationImpl implements AcwInstanceApplication {
    private final LazyLambdaStream lazyLambdaStream;
    private final DynamicAdapter dynamicAdapter;
    private final PerfectLazyOperation perfectLazyOperation;
    private final SmartLazyOperation smartLazyOperation;


    public AcwInstanceApplicationImpl(LazyLambdaStream lazyLambdaStream, DynamicAdapter dynamicAdapter, PerfectLazyOperation perfectLazyOperation, SmartLazyOperation smartLazyOperation) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.dynamicAdapter = dynamicAdapter;
        this.perfectLazyOperation = perfectLazyOperation;
        this.smartLazyOperation = smartLazyOperation;
    }

    /**
     * 查询出所有的数据服务器
     *
     * @param acwInstanceUo
     * @return
     */
    @Override
    public Result<List<AcwInstanceUo>> list(AcwInstanceUo acwInstanceUo) {
        final Collection<AcwInstanceUo> acwInstanceUos = lazyLambdaStream.of(AcwInstanceUo.class)
                .selectList(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                        .eq(AcwInstanceUo::getIsDeleted, false)
                        .orderByAsc(AcwInstanceUo::getSort));
        return ResultFactory.successOf(acwInstanceUos.stream().toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Void> save(AcwInstanceUo acwInstanceUo) {

        String host = acwInstanceUo.getHost();
        Integer port = acwInstanceUo.getPort();
        LazyDataSourceType dataSourceType = acwInstanceUo.getLazyDataSourceType();
       if(ObjectUtils.isEmpty(acwInstanceUo.getUrl())){
           String url = SourceFactory.getUrl(dataSourceType, host, port);
           acwInstanceUo.setUrl(url);
       }else {
           acwInstanceUo.setUrl(acwInstanceUo.getUrl());
       }

        if (ObjectUtils.isEmpty(acwInstanceUo.getId())) {
            acwInstanceUo.setId(UUID.randomUUID().toString());
        }
        if (ObjectUtils.isEmpty(acwInstanceUo.getDriverClassName())) {
            acwInstanceUo.setDriverClassName(dataSourceType.getDriver());
        }
        lazyLambdaStream.upsert(acwInstanceUo);
        if (!ObjectUtils.isEmpty(acwInstanceUo.getInitializeToLocal()) && acwInstanceUo.getInitializeToLocal()) {
            // 初始化表信息
            initSchema(acwInstanceUo);
        }
        return ResultFactory.successOf();
    }

    /**
     * 测试连接
     *
     * @param acwInstanceUo
     * @return
     */
    @Override
    public Result testConnection(AcwInstanceUo acwInstanceUo) {

        String driverClassName = acwInstanceUo.getDriverClassName();
        String host = acwInstanceUo.getHost();
        Integer port = acwInstanceUo.getPort();
        LazyDataSourceType dataSourceType = acwInstanceUo.getLazyDataSourceType();
        String url = SourceFactory.getUrl(dataSourceType, host, port);

        if (ObjectUtils.isEmpty(driverClassName)) {
            driverClassName = dataSourceType.getDriver();
        }
        String username = acwInstanceUo.getUsername();
        String password = acwInstanceUo.getPassword();

        try {

            Class.forName(driverClassName);

            Connection conn = DriverManager.getConnection(url, username, password);//获取连接对象

            conn.close();
            return ResultFactory.successOf();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return ResultFactory.errorOf(e.getMessage());

        }
    }


    /**
     * describe 初始化schema
     *
     * @param acwInstanceUo 数据库实例信息
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/11/27 19:02
     **/
    private void initSchema(AcwInstanceUo acwInstanceUo) {
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(acwInstanceUo.getUrl());
        lazyDataSourceProperties.setUsername(acwInstanceUo.getUsername());
        lazyDataSourceProperties.setPassword(acwInstanceUo.getPassword());
        lazyDataSourceProperties.setAlias(acwInstanceUo.getId());
        dynamicAdapter.loading(lazyDataSourceProperties);
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        // 初始化数据库信息
        List<LazyDatabase> lazyDatabases = smartLazyOperation.showDatabases();
        List<AcwSchemaUo> acwSchemaUos = lazyDatabases.stream().map(lazyDatabase -> {
            AcwSchemaUo acwSchemaUo = new AcwSchemaUo();
            acwSchemaUo.setSchemaName(lazyDatabase.getDatabase());
            acwSchemaUo.setInstanceId(acwInstanceUo.getId());
            acwSchemaUo.setIsDeleted(false);
            acwSchemaUo.setInitializeToLocal(true);
            return acwSchemaUo;
        }).collect(Collectors.toList());


        // 表信息
        Collection<AcwTableUo> acwTableUoList = lazyLambdaStream.select(LazyWrappers.<LazyTableInfo>lambdaWrapper()
                        .notNull(LazyTableInfo::getTableName)
                ).
                stream(AcwTableUo.class).collect(Collectors.toList());


        // 表字段信息
        Collection<AcwTableColumnUo> acwTableColumnUos = lazyLambdaStream.of(LazyColumn.class).
                select(LazyWrappers.<LazyTableInfo>lambdaWrapper())
                .stream(AcwTableColumnUo.class).collect(Collectors.toList());


        DynamicLazyDSContextHolder.peek();
        // 存储schema 信息
        lazyLambdaStream.upsert(acwSchemaUos);
        Map<String, Long> databaseSchemaUoMap = lazyLambdaStream
                .selectList(LazyWrappers.<AcwSchemaUo>lambdaWrapper().eq(AcwSchemaUo::getInstanceId, acwInstanceUo.getId())
                ).stream().collect(Collectors.toMap(AcwSchemaUo::getSchemaName, AcwSchemaUo::getId, (A, B) -> A));
        // 存储表信息
        acwTableUoList = acwTableUoList.stream().peek(databaseTableUo -> {
            databaseTableUo.setInstanceId(acwInstanceUo.getId());
            databaseTableUo.setInstanceName(acwInstanceUo.getInstanceName());
            databaseTableUo.setSchemaNameId(databaseSchemaUoMap.getOrDefault(databaseTableUo.getTableName(), 0L));
            databaseTableUo.setSchemaName(databaseTableUo.getTableName());
        }).collect(Collectors.toList());

        lazyLambdaStream.upsert(acwTableUoList);

        // 存储字段
        Map<String, Long> databaseTableUoMap = lazyLambdaStream.of(AcwTableUo.class).
                selectList(
                        LazyWrappers.<AcwTableUo>lambdaWrapper().
                                eq(AcwTableUo::getInstanceId, acwInstanceUo.getId())
                ).stream().collect(Collectors.toMap(AcwTableUo::getTableName, AcwTableUo::getId, (A, B) -> A));
        acwTableColumnUos = acwTableColumnUos.
                stream().
                peek(databaseTableColumnUo ->
                        databaseTableColumnUo.setTableId(databaseTableUoMap.getOrDefault(databaseTableColumnUo.getTableName(), 0L))).
                collect(Collectors.toList());

        lazyLambdaStream.upsert(acwTableColumnUos);
    }

    @Override
    public Result delete(String id) {
        Integer count = lazyLambdaStream.of(AcwInstanceUo.class).delete(LazyWrappers.<AcwInstanceUo>lambdaWrapper().eq(AcwInstanceUo::getId, id));
        return ResultFactory.successOf(count);
    }

    /**
     * 重新加载数据源
     *
     * @return
     */
    @Override
    public Result loading() {
        Collection<AcwInstanceUo> acwInstanceUos = lazyLambdaStream.of(AcwInstanceUo.class).selectList(null);
        for (AcwInstanceUo acwInstanceUo : acwInstanceUos) {
            LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
            lazyDataSourceProperties.setUrl(acwInstanceUo.getUrl());
            lazyDataSourceProperties.setUsername(acwInstanceUo.getUsername());
            lazyDataSourceProperties.setPassword(acwInstanceUo.getPassword());
            lazyDataSourceProperties.setAlias(acwInstanceUo.getId());
            lazyDataSourceProperties.setType(MysqlDataSource.class);
            dynamicAdapter.loading(lazyDataSourceProperties);
        }
        return ResultFactory.successOf();
    }

    /**
     * 重新初始化数据库服务器
     *
     * @param id 服务器ID
     * @return
     */
    @Override
    public Result reload(String id) {
        // 查询数据库实例是否存在
        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                .eq(AcwInstanceUo::getId, id)
                .eq(AcwInstanceUo::getIsDeleted, false));
        // 获取实例信息
        if (ObjectUtils.isEmpty(acwInstanceUo)) {
            return ResultFactory.errorOf("服务器不存在");
        }
        acwInstanceUo.setInitializeToLocal(true);
        lazyLambdaStream.upsert(acwInstanceUo);
        // 初始化实例
        initSchema(acwInstanceUo);
        return ResultFactory.successOf();
    }

    /**
     * 数据库实例备份
     *
     * @param instanceId
     */
    @Override
    public void backUps(String instanceId) {
        // 切换数据源
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(instanceId);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        // 数据备份
        for (LazyDatabase showDatabase : smartLazyOperation.showDatabases()) {
            smartLazyOperation.saveSqlFile(showDatabase.getDatabase());
        }
        DynamicLazyDSContextHolder.clear();
    }

    /**
     * describe  查询出分页的数据
     *
     * @param size          分页大小
     * @param current       当前页数
     * @param acwInstanceUo 查询参数对象
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<LazyPage<AcwInstanceUo>> retrievePage(int size, int current, AcwInstanceUo acwInstanceUo) {
        LazyPage<AcwInstanceUo> lazyPage = new LazyPage<AcwInstanceUo>(current, size);
        LazyPage<AcwInstanceUo> resultLazyPage = lazyLambdaStream.selectPage(
                LazyWrappers.lambdaWrapperBean(acwInstanceUo)
                        .eq(AcwInstanceUo::getIsDeleted, false)
                        .orderByAsc(AcwInstanceUo::getSort), lazyPage);
        return ResultFactory.successOf(resultLazyPage);
    }

    /**
     * 查询出所有的数据页面使用
     *
     * @param acwInstanceUo
     * @return
     */
    @Override
    public Result<List<AcwInstanceUo>> retrieveAll(AcwInstanceUo acwInstanceUo) {
        List<AcwInstanceUo> acwInstanceUoList = lazyLambdaStream
                .selectList(
                        LazyWrappers.lambdaWrapperBean(acwInstanceUo)
                                .eq(AcwInstanceUo::getIsDeleted, false)
                                .orderByAsc(AcwInstanceUo::getSort));
        return ResultFactory.successOf(acwInstanceUoList);
    }


}
