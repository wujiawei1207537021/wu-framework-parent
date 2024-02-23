package com.wu.framework.sql.audit.platform.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.sql.audit.platform.application.SqlAuditApplication;
import com.wu.framework.sql.audit.platform.application.assembler.SqlAuditDTOAssembler;
import com.wu.framework.sql.audit.platform.application.command.SqlAuditCommand;
import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAudit;
import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/24 09:53 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class SqlAuditApplicationImpl implements SqlAuditApplication {

    @Autowired
    SqlAuditRepository sqlAuditRepository;

    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @Override
    public Result<SqlAudit> story(SqlAuditCommand sqlAuditCommand) {
        SqlAudit sqlAudit = SqlAuditDTOAssembler.toSqlAudit(sqlAuditCommand);
        return sqlAuditRepository.story(sqlAudit);
    }

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @Override
    public Result<SqlAudit> updateOne(SqlAuditCommand sqlAuditCommand) {
        SqlAudit sqlAudit = SqlAuditDTOAssembler.toSqlAudit(sqlAuditCommand);
        return sqlAuditRepository.story(sqlAudit);
    }

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @Override
    public Result<SqlAudit> findOne(SqlAuditCommand sqlAuditCommand) {
        SqlAudit sqlAudit = SqlAuditDTOAssembler.toSqlAudit(sqlAuditCommand);
        return sqlAuditRepository.findOne(sqlAudit);
    }

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @Override
    public Result<List<SqlAudit>> findList(SqlAuditCommand sqlAuditCommand) {
        SqlAudit sqlAudit = SqlAuditDTOAssembler.toSqlAudit(sqlAuditCommand);
        return sqlAuditRepository.findList(sqlAudit);
    }

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @Override
    public Result<LazyPage<SqlAudit>> findPage(int size, int current, SqlAuditCommand sqlAuditCommand) {
        SqlAudit sqlAudit = SqlAuditDTOAssembler.toSqlAudit(sqlAuditCommand);
        return sqlAuditRepository.findPage(size, current, sqlAudit);
    }

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @Override
    public Result<SqlAudit> remove(SqlAuditCommand sqlAuditCommand) {
        SqlAudit sqlAudit = SqlAuditDTOAssembler.toSqlAudit(sqlAuditCommand);
        return sqlAuditRepository.remove(sqlAudit);
    }

}