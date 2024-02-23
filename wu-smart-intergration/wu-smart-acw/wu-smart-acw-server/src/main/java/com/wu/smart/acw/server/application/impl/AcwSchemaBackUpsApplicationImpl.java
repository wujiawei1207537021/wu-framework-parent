package com.wu.smart.acw.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.layer.data.translation.NormalTranslation;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperation;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.AcwSchemaBackUpsApplication;
import com.wu.smart.acw.server.application.assembler.AcwSchemaBackUpsDTOAssembler;
import com.wu.smart.acw.server.application.command.AcwSchemaBackUpsCommand;
import com.wu.smart.acw.server.application.dto.AcwSchemaBackUpsDTO;
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
 * @date 2023/07/09 03:49 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class AcwSchemaBackUpsApplicationImpl implements AcwSchemaBackUpsApplication {

    @Autowired
    AcwSchemaBackUpsRepository acwSchemaBackUpsRepository;

    @Autowired
    SmartLazyOperation smartLazyOperation;

    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<DatabaseSchemaBackUps> story(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        DatabaseSchemaBackUps databaseSchemaBackUps = AcwSchemaBackUpsDTOAssembler.INSTANCE.toDatabaseSchemaBackUps(acwSchemaBackUpsCommand);
        return acwSchemaBackUpsRepository.story(databaseSchemaBackUps);
    }

    /**
     * describe 更新数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<DatabaseSchemaBackUps> updateOne(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        DatabaseSchemaBackUps databaseSchemaBackUps = AcwSchemaBackUpsDTOAssembler.INSTANCE.toDatabaseSchemaBackUps(acwSchemaBackUpsCommand);
        return acwSchemaBackUpsRepository.story(databaseSchemaBackUps);
    }

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<DatabaseSchemaBackUps> findOne(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        DatabaseSchemaBackUps databaseSchemaBackUps = AcwSchemaBackUpsDTOAssembler.INSTANCE.toDatabaseSchemaBackUps(acwSchemaBackUpsCommand);
        return acwSchemaBackUpsRepository.findOne(databaseSchemaBackUps);
    }

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<List<DatabaseSchemaBackUps>> findList(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        DatabaseSchemaBackUps databaseSchemaBackUps = AcwSchemaBackUpsDTOAssembler.INSTANCE.toDatabaseSchemaBackUps(acwSchemaBackUpsCommand);
        return acwSchemaBackUpsRepository.findList(databaseSchemaBackUps);
    }

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @NormalTranslation
    @Override
    public Result<LazyPage<AcwSchemaBackUpsDTO>> findPage(int size, int current, AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        DatabaseSchemaBackUps databaseSchemaBackUps = AcwSchemaBackUpsDTOAssembler.INSTANCE.toDatabaseSchemaBackUps(acwSchemaBackUpsCommand);
        return acwSchemaBackUpsRepository.findPage(size, current, databaseSchemaBackUps).convert(databaseSchemaBackUpsLazyPage -> databaseSchemaBackUpsLazyPage.convert(AcwSchemaBackUpsDTOAssembler.INSTANCE::fromAcwSchemaBackUps));
    }

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<DatabaseSchemaBackUps> remove(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        DatabaseSchemaBackUps databaseSchemaBackUps = AcwSchemaBackUpsDTOAssembler.INSTANCE.toDatabaseSchemaBackUps(acwSchemaBackUpsCommand);
        return acwSchemaBackUpsRepository.remove(databaseSchemaBackUps);
    }

    /**
     * 数据库备份
     *
     * @param databaseInstanceBackUpsCommand
     */
    @Override
    public void backUps(AcwSchemaBackUpsCommand databaseInstanceBackUpsCommand) {
        String instanceId = databaseInstanceBackUpsCommand.getInstanceId();
        String schemaName = databaseInstanceBackUpsCommand.getSchemaName();
        // 切换数据源
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(instanceId);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        List<DatabaseSchemaBackUps> databaseSchemaBackUpsList = new ArrayList<>();
        // 数据备份


        List<LazyTableInfo> lazyTableInfos = smartLazyOperation.showTables(schemaName);
        File file = smartLazyOperation.saveSqlFile(schemaName);

        // 存储数据库构建信息
        DatabaseSchemaBackUps databaseSchemaBackUps = new DatabaseSchemaBackUps();
        databaseSchemaBackUps.setSchemaName(schemaName);
        databaseSchemaBackUps.setInstanceId(instanceId);
        databaseSchemaBackUps.setPath(file.getPath());
        databaseSchemaBackUps.setTableNum(lazyTableInfos.size());
        databaseSchemaBackUps.setId(UUID.randomUUID().toString());
        databaseSchemaBackUps.setIsDeleted(false);
        databaseSchemaBackUpsList.add(databaseSchemaBackUps);

        DynamicLazyDSContextHolder.clear();
        // 切回当前默认数据源
        DatabaseInstanceBackUps databaseInstanceBackUps = new DatabaseInstanceBackUps();
        // 存储schema 备份记录
        acwSchemaBackUpsRepository.batchStory(databaseSchemaBackUpsList);


    }
}