package com.wu.smart.acw.server.infrastructure.converter;

import com.wu.smart.acw.server.domain.model.database.schema.back.ups.DatabaseSchemaBackUps;
import com.wu.smart.acw.server.infrastructure.entity.AcwSchemaBackUpsDO;

/**
 * describe 数据库备份信息 
 *
 * @author Jia wei Wu
 * @date 2023/07/09 03:49 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/

public class DatabaseSchemaBackUpsConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/
    public static DatabaseSchemaBackUps toDatabaseSchemaBackUps(AcwSchemaBackUpsDO acwSchemaBackUpsDO) {
        if (null != acwSchemaBackUpsDO) {
        DatabaseSchemaBackUps databaseSchemaBackUps = new DatabaseSchemaBackUps(); 
           databaseSchemaBackUps.setCreateTime(acwSchemaBackUpsDO.getCreateTime());
           databaseSchemaBackUps.setId(acwSchemaBackUpsDO.getId());
           databaseSchemaBackUps.setInstanceId(acwSchemaBackUpsDO.getInstanceId());
           databaseSchemaBackUps.setIsDeleted(acwSchemaBackUpsDO.getIsDeleted());
           databaseSchemaBackUps.setPath(acwSchemaBackUpsDO.getPath());
           databaseSchemaBackUps.setSchemaName(acwSchemaBackUpsDO.getSchemaName());
           databaseSchemaBackUps.setStatus(acwSchemaBackUpsDO.getStatus());
           databaseSchemaBackUps.setTableNum(acwSchemaBackUpsDO.getTableNum());
           databaseSchemaBackUps.setUpdateTime(acwSchemaBackUpsDO.getUpdateTime());
            return databaseSchemaBackUps;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/
    public static AcwSchemaBackUpsDO fromDatabaseSchemaBackUps(DatabaseSchemaBackUps databaseSchemaBackUps) {
        if (null != databaseSchemaBackUps) {
        AcwSchemaBackUpsDO acwSchemaBackUpsDO = new AcwSchemaBackUpsDO();
           acwSchemaBackUpsDO.setCreateTime(databaseSchemaBackUps.getCreateTime());
           acwSchemaBackUpsDO.setId(databaseSchemaBackUps.getId());
           acwSchemaBackUpsDO.setInstanceId(databaseSchemaBackUps.getInstanceId());
           acwSchemaBackUpsDO.setIsDeleted(databaseSchemaBackUps.getIsDeleted());
           acwSchemaBackUpsDO.setPath(databaseSchemaBackUps.getPath());
           acwSchemaBackUpsDO.setSchemaName(databaseSchemaBackUps.getSchemaName());
           acwSchemaBackUpsDO.setStatus(databaseSchemaBackUps.getStatus());
           acwSchemaBackUpsDO.setTableNum(databaseSchemaBackUps.getTableNum());
           acwSchemaBackUpsDO.setUpdateTime(databaseSchemaBackUps.getUpdateTime());
            return acwSchemaBackUpsDO;
        }
        return null;
    }

}