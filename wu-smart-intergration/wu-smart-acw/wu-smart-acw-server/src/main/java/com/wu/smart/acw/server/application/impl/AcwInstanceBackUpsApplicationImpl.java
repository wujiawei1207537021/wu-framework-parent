package com.wu.smart.acw.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperation;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.AcwInstanceBackUpsApplication;
import com.wu.smart.acw.server.application.assembler.AcwInstanceBackUpsDTOAssembler;
import com.wu.smart.acw.server.application.command.AcwInstanceBackUpsCommand;
import com.wu.smart.acw.server.domain.model.database.instance.back.ups.AcwInstanceBackUpsRepository;
import com.wu.smart.acw.server.domain.model.database.instance.back.ups.DatabaseInstanceBackUps;
import com.wu.smart.acw.server.domain.model.database.schema.back.ups.AcwSchemaBackUpsRepository;
import com.wu.smart.acw.server.domain.model.database.schema.back.ups.DatabaseSchemaBackUps;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * describe 数据库备份信息
 *
 * @author Jia wei Wu
 * @date 2023/07/09 11:24 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class AcwInstanceBackUpsApplicationImpl implements AcwInstanceBackUpsApplication {

    @Autowired
    AcwInstanceBackUpsRepository acwInstanceBackUpsRepository;

    @Autowired
    SmartLazyOperation smartLazyOperation;

    @Autowired
    AcwSchemaBackUpsRepository acwSchemaBackUpsRepository;

    @Autowired
    LazyOperationConfig lazyOperationConfig;

    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<DatabaseInstanceBackUps> story(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        DatabaseInstanceBackUps databaseInstanceBackUps = AcwInstanceBackUpsDTOAssembler.INSTANCE.toDatabaseInstanceBackUps(acwInstanceBackUpsCommand);
        return acwInstanceBackUpsRepository.story(databaseInstanceBackUps);
    }

    /**
     * describe 更新数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<DatabaseInstanceBackUps> updateOne(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        DatabaseInstanceBackUps databaseInstanceBackUps = AcwInstanceBackUpsDTOAssembler.INSTANCE.toDatabaseInstanceBackUps(acwInstanceBackUpsCommand);
        return acwInstanceBackUpsRepository.story(databaseInstanceBackUps);
    }

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<DatabaseInstanceBackUps> findOne(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        DatabaseInstanceBackUps databaseInstanceBackUps = AcwInstanceBackUpsDTOAssembler.INSTANCE.toDatabaseInstanceBackUps(acwInstanceBackUpsCommand);
        return acwInstanceBackUpsRepository.findOne(databaseInstanceBackUps);
    }

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<List<DatabaseInstanceBackUps>> findList(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        DatabaseInstanceBackUps databaseInstanceBackUps = AcwInstanceBackUpsDTOAssembler.INSTANCE.toDatabaseInstanceBackUps(acwInstanceBackUpsCommand);
        return acwInstanceBackUpsRepository.findList(databaseInstanceBackUps);
    }

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<LazyPage<DatabaseInstanceBackUps>> findPage(int size, int current, AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        DatabaseInstanceBackUps databaseInstanceBackUps = AcwInstanceBackUpsDTOAssembler.INSTANCE.toDatabaseInstanceBackUps(acwInstanceBackUpsCommand);
        return acwInstanceBackUpsRepository.findPage(size, current, databaseInstanceBackUps);
    }

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<DatabaseInstanceBackUps> remove(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        DatabaseInstanceBackUps databaseInstanceBackUps = AcwInstanceBackUpsDTOAssembler.INSTANCE.toDatabaseInstanceBackUps(acwInstanceBackUpsCommand);
        return acwInstanceBackUpsRepository.remove(databaseInstanceBackUps);
    }

    /**
     * 数据库实例备份
     *
     * @param instanceId
     */
    @Override
    public void backUps(String instanceId) {
        List<String> ignoredDatabase = lazyOperationConfig.getIgnoredDatabase();
        // 切换数据源
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(instanceId);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        List<LazyDatabase> lazyDatabaseList = smartLazyOperation.showDatabases();
        List<DatabaseSchemaBackUps> databaseSchemaBackUpsList = new ArrayList<>();

        // 数据备份
        for (LazyDatabase showDatabase : lazyDatabaseList) {
            String database = showDatabase.getDatabase();
            if (ignoredDatabase.contains(database)) {
                continue;
            }
            List<LazyTableInfo> lazyTableInfos = smartLazyOperation.showTables(database);
            File file = smartLazyOperation.saveSqlFile(database);

            if (null == file) {
                continue;
            }
            // 存储数据库构建信息
            DatabaseSchemaBackUps databaseSchemaBackUps = new DatabaseSchemaBackUps();
            databaseSchemaBackUps.setSchemaName(database);
            databaseSchemaBackUps.setInstanceId(instanceId);
            databaseSchemaBackUps.setPath(file.getPath());
            databaseSchemaBackUps.setId(UUID.randomUUID().toString());
            databaseSchemaBackUps.setTableNum(lazyTableInfos.size());
            databaseSchemaBackUpsList.add(databaseSchemaBackUps);
        }

        DynamicLazyDSContextHolder.clear();
        // 切回当前默认数据源
        DatabaseInstanceBackUps databaseInstanceBackUps = new DatabaseInstanceBackUps();
        // 存储schema 备份记录
        acwSchemaBackUpsRepository.batchStory(databaseSchemaBackUpsList);
        databaseInstanceBackUps.setInstanceId(instanceId);
        databaseInstanceBackUps.setId(UUID.randomUUID().toString());
        databaseInstanceBackUps.setIsDeleted(false);
        databaseInstanceBackUps.setStatus(2);
        databaseInstanceBackUps.setSchemaNum(lazyDatabaseList.size());
        acwInstanceBackUpsRepository.story(databaseInstanceBackUps);


    }
}