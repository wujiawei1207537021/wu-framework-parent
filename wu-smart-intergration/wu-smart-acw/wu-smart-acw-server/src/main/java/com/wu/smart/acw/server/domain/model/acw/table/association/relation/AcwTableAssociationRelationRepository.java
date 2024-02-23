package com.wu.smart.acw.server.domain.model.acw.table.association.relation;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;

import java.util.List;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 表关联关系
 *
 * @author Jia wei Wu
 * @date 2023/11/01 10:16 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface AcwTableAssociationRelationRepository {


    /**
     * describe 新增表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<AcwTableAssociationRelation> story(AcwTableAssociationRelation acwTableAssociationRelation);

    /**
     * describe 批量新增表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<List<AcwTableAssociationRelation>> batchStory(List<AcwTableAssociationRelation> acwTableAssociationRelationList);

    /**
     * describe 查询单个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<AcwTableAssociationRelation> findOne(AcwTableAssociationRelation acwTableAssociationRelation);

    /**
     * describe 查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<List<AcwTableAssociationRelation>> findList(AcwTableAssociationRelation acwTableAssociationRelation);

    /**
     * describe 分页查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<LazyPage<AcwTableAssociationRelation>> findPage(int size, int current, AcwTableAssociationRelation acwTableAssociationRelation);

    /**
     * describe 删除表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<AcwTableAssociationRelation> remove(AcwTableAssociationRelation acwTableAssociationRelation);

    /**
     * 分析数据库中表与表之间的关系
     *
     * @param acwTableAssociationRelation
     * @return
     */
    Result<Void> analysisSchema(AcwTableAssociationRelation acwTableAssociationRelation);
}