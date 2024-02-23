package com.wu.framework.sql.audit.platform.domain.model.sql.audit;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.repository.LazyCrudRepository;
import com.wu.framework.sql.audit.platform.infrastructure.entity.SqlAuditDO;

import java.util.List;

/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/05/24 09:53 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface SqlAuditRepository extends LazyCrudRepository<SqlAuditDO,SqlAudit,Integer> {


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<SqlAudit> story(SqlAudit sqlAudit);

    /**
     * describe 批量新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<SqlAudit> batchStory(List<SqlAudit> sqlAuditList);

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<SqlAudit> findOne(SqlAudit sqlAudit);

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<List<SqlAudit>> findList(SqlAudit sqlAudit);

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<LazyPage<SqlAudit>> findPage(int size,int current,SqlAudit sqlAudit);

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    Result<SqlAudit> remove(SqlAudit sqlAudit);

}