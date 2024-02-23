package com.wu.smart.acw.server.infrastructure.converter;

import com.wu.smart.acw.server.domain.model.database.instance.back.ups.DatabaseInstanceBackUps;
import com.wu.smart.acw.server.infrastructure.entity.AcwInstanceBackUpsDO;

/**
 * describe 数据库备份信息 
 *
 * @author Jia wei Wu
 * @date 2023/07/09 11:24 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/

public class DatabaseInstanceBackUpsConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/
    public static DatabaseInstanceBackUps toDatabaseInstanceBackUps(AcwInstanceBackUpsDO acwInstanceBackUpsDO) {
        if (null != acwInstanceBackUpsDO) {
        DatabaseInstanceBackUps databaseInstanceBackUps = new DatabaseInstanceBackUps(); 
           databaseInstanceBackUps.setCreateTime(acwInstanceBackUpsDO.getCreateTime());
           databaseInstanceBackUps.setId(acwInstanceBackUpsDO.getId());
           databaseInstanceBackUps.setInstanceId(acwInstanceBackUpsDO.getInstanceId());
           databaseInstanceBackUps.setIsDeleted(acwInstanceBackUpsDO.getIsDeleted());
           databaseInstanceBackUps.setPath(acwInstanceBackUpsDO.getPath());
           databaseInstanceBackUps.setSchemaNum(acwInstanceBackUpsDO.getSchemaNum());
           databaseInstanceBackUps.setStatus(acwInstanceBackUpsDO.getStatus());
           databaseInstanceBackUps.setUpdateTime(acwInstanceBackUpsDO.getUpdateTime());
            return databaseInstanceBackUps;
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
     * @date 2023/07/09 11:24 上午
     **/
    public static AcwInstanceBackUpsDO fromDatabaseInstanceBackUps(DatabaseInstanceBackUps databaseInstanceBackUps) {
        if (null != databaseInstanceBackUps) {
        AcwInstanceBackUpsDO acwInstanceBackUpsDO = new AcwInstanceBackUpsDO();
           acwInstanceBackUpsDO.setCreateTime(databaseInstanceBackUps.getCreateTime());
           acwInstanceBackUpsDO.setId(databaseInstanceBackUps.getId());
           acwInstanceBackUpsDO.setInstanceId(databaseInstanceBackUps.getInstanceId());
           acwInstanceBackUpsDO.setIsDeleted(databaseInstanceBackUps.getIsDeleted());
           acwInstanceBackUpsDO.setPath(databaseInstanceBackUps.getPath());
           acwInstanceBackUpsDO.setSchemaNum(databaseInstanceBackUps.getSchemaNum());
           acwInstanceBackUpsDO.setStatus(databaseInstanceBackUps.getStatus());
           acwInstanceBackUpsDO.setUpdateTime(databaseInstanceBackUps.getUpdateTime());
            return acwInstanceBackUpsDO;
        }
        return null;
    }

}