package com.wu.framework.sql.audit.platform.application.assembler;


import com.wu.framework.sql.audit.platform.application.command.SqlAuditCommand;
import com.wu.framework.sql.audit.platform.application.dto.SqlAuditDTO;
import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAudit;


/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/24 09:53 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/

public class SqlAuditDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/
    public static SqlAudit toSqlAudit(SqlAuditCommand sqlAuditCommand) {
        if (null != sqlAuditCommand) {
            SqlAudit sqlAudit = new SqlAudit();
            sqlAudit.setSchema(sqlAuditCommand.getSchema());
            sqlAudit.setApplicationName(sqlAuditCommand.getApplicationName());
            sqlAudit.setExecuteSql(sqlAuditCommand.getExecuteSql());
            sqlAudit.setId(sqlAuditCommand.getId());
            sqlAudit.setIp(sqlAuditCommand.getIp());
            sqlAudit.setRequestId(sqlAuditCommand.getRequestId());
            sqlAudit.setSqlType(sqlAuditCommand.getSqlType());
            sqlAudit.setInstanceId(sqlAuditCommand.getInstanceId());
            sqlAudit.setTime(sqlAuditCommand.getTime());
            sqlAudit.setTableList(sqlAuditCommand.getTableList());
            return sqlAudit;
        }
        return null;
    }

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/
    public static SqlAuditDTO fromSqlAudit(SqlAudit sqlAudit) {
        if (null != sqlAudit) {
            SqlAuditDTO sqlAuditDTO = new SqlAuditDTO();
            sqlAuditDTO.setSchema(sqlAudit.getSchema());
            sqlAuditDTO.setApplicationName(sqlAudit.getApplicationName());
            sqlAuditDTO.setDatasource(sqlAudit.getDatasource());
            sqlAuditDTO.setExecuteSql(sqlAudit.getExecuteSql());
            sqlAuditDTO.setId(sqlAudit.getId());
            sqlAuditDTO.setIp(sqlAudit.getIp());
            sqlAuditDTO.setRequestId(sqlAudit.getRequestId());
            sqlAuditDTO.setSqlType(sqlAudit.getSqlType());
            sqlAuditDTO.setInstanceId(sqlAudit.getInstanceId());
            sqlAuditDTO.setTime(sqlAudit.getTime());
            sqlAuditDTO.setTableList(sqlAudit.getTableList());
            return sqlAuditDTO;
        }
        return null;
    }

}