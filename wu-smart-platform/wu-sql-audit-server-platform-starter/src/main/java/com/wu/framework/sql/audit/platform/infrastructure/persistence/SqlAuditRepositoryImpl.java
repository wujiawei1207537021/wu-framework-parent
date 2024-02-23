package com.wu.framework.sql.audit.platform.infrastructure.persistence;

import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAudit;
import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAuditRepository;
import com.wu.framework.sql.audit.platform.infrastructure.converter.SqlAuditConverter;
import com.wu.framework.sql.audit.platform.infrastructure.entity.SqlAuditDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/05/24 09:53 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class SqlAuditRepositoryImpl extends AbstractLazyCrudRepository<SqlAuditDO,SqlAudit,Integer> implements SqlAuditRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

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
    public Result<SqlAudit> story(SqlAudit sqlAudit) {
        SqlAuditDO sqlAuditDO = SqlAuditConverter.fromSqlAudit(sqlAudit);
        lazyLambdaStream.upsert(sqlAuditDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @Override
    public Result<SqlAudit> batchStory(List<SqlAudit> sqlAuditList) {
        List<SqlAuditDO> sqlAuditDOList = sqlAuditList.stream().map(SqlAuditConverter::fromSqlAudit).collect(Collectors.toList());
        lazyLambdaStream.upsert(sqlAuditDOList);
        return ResultFactory.successOf();
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
    public Result<SqlAudit> findOne(SqlAudit sqlAudit) {
        SqlAuditDO sqlAuditDO = SqlAuditConverter.fromSqlAudit(sqlAudit);
        SqlAudit sqlAuditOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(sqlAuditDO), SqlAudit.class);
        return ResultFactory.successOf(sqlAuditOne);
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
    public Result<List<SqlAudit>> findList(SqlAudit sqlAudit) {
        SqlAuditDO sqlAuditDO = SqlAuditConverter.fromSqlAudit(sqlAudit);
        List<SqlAudit> sqlAuditList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(sqlAuditDO), SqlAudit.class);
        return ResultFactory.successOf(sqlAuditList);
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
    public Result<LazyPage<SqlAudit>> findPage(int size,int current,SqlAudit sqlAudit) {
        SqlAuditDO sqlAuditDO = SqlAuditConverter.fromSqlAudit(sqlAudit);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<SqlAudit> sqlAuditLazyPage = lazyLambdaStream.selectPage(
                LazyWrappers.lambdaWrapperBean(sqlAuditDO)
                        .orderByDesc(SqlAuditDO::getCreateTime)
                , lazyPage, SqlAudit.class);
        return ResultFactory.successOf(sqlAuditLazyPage);
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
    public Result<SqlAudit> remove(SqlAudit sqlAudit) {
        SqlAuditDO sqlAuditDO = SqlAuditConverter.fromSqlAudit(sqlAudit);
        //  lazyLambdaStream.delete(sqlAuditDO);
        return ResultFactory.successOf();
    }

}