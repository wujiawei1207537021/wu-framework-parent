package com.wu.framework.sql.audit.platform.infrastructure.converter;


import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAudit;
import com.wu.framework.sql.audit.platform.infrastructure.entity.SqlAuditDO;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/24 09:53 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class SqlAuditConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/
    public static SqlAudit toSqlAudit(SqlAuditDO sqlAuditDO) {
        if (null != sqlAuditDO) {
            SqlAudit sqlAudit = new SqlAudit();
            sqlAudit.setSchema(sqlAuditDO.getSchema());
            sqlAudit.setApplicationName(sqlAuditDO.getApplicationName());
            sqlAudit.setDatasource(sqlAuditDO.getDatasource());
            sqlAudit.setExecuteSql(sqlAuditDO.getExecuteSql());
            sqlAudit.setId(sqlAuditDO.getId());
            sqlAudit.setIp(sqlAuditDO.getIp());
            sqlAudit.setInstanceId(sqlAuditDO.getInstanceId());
            sqlAudit.setRequestId(sqlAuditDO.getRequestId());
            sqlAudit.setSqlType(sqlAuditDO.getSqlType());
            sqlAudit.setTime(sqlAuditDO.getTime());
            sqlAudit.setTableList(sqlAuditDO.getTableList());
            return sqlAudit;
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
     * @date 2023/05/24 09:53 上午
     **/
    public static SqlAuditDO fromSqlAudit(SqlAudit sqlAudit) {
        if (null != sqlAudit) {
            SqlAuditDO sqlAuditDO = new SqlAuditDO();
            sqlAuditDO.setSchema(sqlAudit.getSchema());
            sqlAuditDO.setApplicationName(sqlAudit.getApplicationName());
            sqlAuditDO.setDatasource(sqlAudit.getDatasource());
            sqlAuditDO.setExecuteSql(sqlAudit.getExecuteSql());
            sqlAuditDO.setId(sqlAudit.getId());
            sqlAuditDO.setIp(sqlAudit.getIp());
            sqlAuditDO.setRequestId(sqlAudit.getRequestId());
            sqlAuditDO.setSqlType(sqlAudit.getSqlType());
            sqlAuditDO.setInstanceId(sqlAudit.getInstanceId());
            sqlAuditDO.setTime(sqlAudit.getTime());
            sqlAuditDO.setCreateTime(sqlAudit.getCreateTime());
            sqlAuditDO.setUpdateTime(sqlAudit.getUpdateTime());
            sqlAuditDO.setTableList(sqlAudit.getTableList());
            return sqlAuditDO;
        }
        return null;
    }

}