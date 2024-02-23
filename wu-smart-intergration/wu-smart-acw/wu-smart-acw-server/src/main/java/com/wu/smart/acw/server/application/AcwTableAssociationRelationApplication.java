package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.application.command.acw.table.association.relation.*;
import com.wu.smart.acw.server.application.dto.AcwTableAssociationRelationDTO;
import com.wu.smart.acw.server.domain.model.acw.table.association.relation.AcwTableAssociationRelation;

import java.util.List;

/**
 * describe 表关联关系
 *
 * @author Jia wei Wu
 * @date 2023/11/01 10:16 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface AcwTableAssociationRelationApplication {


    /**
     * describe 新增表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<AcwTableAssociationRelation> story(AcwTableAssociationRelationStoryCommand acwTableAssociationRelationStoryCommand);

    /**
     * describe 更新表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<AcwTableAssociationRelation> updateOne(AcwTableAssociationRelationUpdateCommand acwTableAssociationRelationUpdateCommand);

    /**
     * describe 查询单个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<AcwTableAssociationRelationDTO> findOne(AcwTableAssociationRelationQueryOneCommand acwTableAssociationRelationQueryOneCommand);

    /**
     * describe 查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<List<AcwTableAssociationRelationDTO>> findList(AcwTableAssociationRelationQueryListCommand acwTableAssociationRelationQueryListCommand);

    /**
     * describe 分页查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<LazyPage<AcwTableAssociationRelationDTO>> findPage(int size, int current, AcwTableAssociationRelationQueryListCommand acwTableAssociationRelationQueryListCommand);

    /**
     * describe 删除表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    Result<AcwTableAssociationRelation> remove(AcwTableAssociationRelationRemoveCommand acwTableAssociationRelationRemoveCommand);

    /**
     * 分析数据库中表与表之间的关系
     *
     * @param acwTableAssociationRelationAnalysisSchemaCommand schema 参数
     * @return
     */
    Result<Void> analysisSchema(AcwTableAssociationRelationAnalysisSchemaCommand acwTableAssociationRelationAnalysisSchemaCommand);
}