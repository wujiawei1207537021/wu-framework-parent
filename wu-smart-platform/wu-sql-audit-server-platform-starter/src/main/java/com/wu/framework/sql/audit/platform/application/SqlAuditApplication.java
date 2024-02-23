package com.wu.framework.sql.audit.platform.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.sql.audit.platform.application.command.SqlAuditCommand;
import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAudit;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/24 09:53 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface SqlAuditApplication {


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<SqlAudit> story(SqlAuditCommand sqlAuditCommand);

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<SqlAudit> updateOne(SqlAuditCommand sqlAuditCommand);

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<SqlAudit> findOne(SqlAuditCommand sqlAuditCommand);

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<List<SqlAudit>> findList(SqlAuditCommand sqlAuditCommand);

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<LazyPage<SqlAudit>> findPage(int size, int current, SqlAuditCommand sqlAuditCommand);

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<SqlAudit> remove(SqlAuditCommand sqlAuditCommand);

}