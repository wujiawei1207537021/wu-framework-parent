package com.wu.smart.acw.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;

import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import com.wu.smart.acw.server.application.AcwTableAssociationRelationApplication;
import com.wu.smart.acw.server.application.assembler.AcwTableAssociationRelationDTOAssembler;
import com.wu.smart.acw.server.application.command.acw.table.association.relation.*;
import com.wu.smart.acw.server.application.dto.AcwTableAssociationRelationDTO;
import com.wu.smart.acw.server.domain.model.acw.table.association.relation.AcwTableAssociationRelation;
import com.wu.smart.acw.server.domain.model.acw.table.association.relation.AcwTableAssociationRelationRepository;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 表关联关系 
 *
 * @author Jia wei Wu
 * @date 2023/11/01 10:16 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class AcwTableAssociationRelationApplicationImpl implements AcwTableAssociationRelationApplication {

    @Autowired
    AcwTableAssociationRelationRepository acwTableAssociationRelationRepository;
    /**
     * describe 新增表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<AcwTableAssociationRelation> story(AcwTableAssociationRelationStoryCommand acwTableAssociationRelationStoryCommand) {
        AcwTableAssociationRelation acwTableAssociationRelation = AcwTableAssociationRelationDTOAssembler.INSTANCE.toAcwTableAssociationRelation(acwTableAssociationRelationStoryCommand);
        return acwTableAssociationRelationRepository.story(acwTableAssociationRelation);
    }
    /**
     * describe 更新表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<AcwTableAssociationRelation> updateOne(AcwTableAssociationRelationUpdateCommand acwTableAssociationRelationUpdateCommand) {
        AcwTableAssociationRelation acwTableAssociationRelation = AcwTableAssociationRelationDTOAssembler.INSTANCE.toAcwTableAssociationRelation(acwTableAssociationRelationUpdateCommand);
        return acwTableAssociationRelationRepository.story(acwTableAssociationRelation);
    }

    /**
     * describe 查询单个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<AcwTableAssociationRelationDTO> findOne(AcwTableAssociationRelationQueryOneCommand acwTableAssociationRelationQueryOneCommand) {
        AcwTableAssociationRelation acwTableAssociationRelation = AcwTableAssociationRelationDTOAssembler.INSTANCE.toAcwTableAssociationRelation(acwTableAssociationRelationQueryOneCommand);
        return acwTableAssociationRelationRepository.findOne(acwTableAssociationRelation).convert(AcwTableAssociationRelationDTOAssembler.INSTANCE::fromAcwTableAssociationRelation);
    }

    /**
     * describe 查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<List<AcwTableAssociationRelationDTO>> findList(AcwTableAssociationRelationQueryListCommand acwTableAssociationRelationQueryListCommand) {
        AcwTableAssociationRelation acwTableAssociationRelation = AcwTableAssociationRelationDTOAssembler.INSTANCE.toAcwTableAssociationRelation(acwTableAssociationRelationQueryListCommand);
        return acwTableAssociationRelationRepository.findList(acwTableAssociationRelation)        .convert(acwTableAssociationRelations -> acwTableAssociationRelations.stream().map(AcwTableAssociationRelationDTOAssembler.INSTANCE::fromAcwTableAssociationRelation).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<LazyPage<AcwTableAssociationRelationDTO>> findPage(int size,int current,AcwTableAssociationRelationQueryListCommand acwTableAssociationRelationQueryListCommand) {
        AcwTableAssociationRelation acwTableAssociationRelation = AcwTableAssociationRelationDTOAssembler.INSTANCE.toAcwTableAssociationRelation(acwTableAssociationRelationQueryListCommand);
        return acwTableAssociationRelationRepository.findPage(size,current,acwTableAssociationRelation)        .convert(page -> page.convert(AcwTableAssociationRelationDTOAssembler.INSTANCE::fromAcwTableAssociationRelation))            ;
    }

    /**
     * describe 删除表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<AcwTableAssociationRelation> remove(AcwTableAssociationRelationRemoveCommand acwTableAssociationRelationRemoveCommand) {
     AcwTableAssociationRelation acwTableAssociationRelation = AcwTableAssociationRelationDTOAssembler.INSTANCE.toAcwTableAssociationRelation(acwTableAssociationRelationRemoveCommand);
     return acwTableAssociationRelationRepository.remove(acwTableAssociationRelation);
    }

    /**
     * 分析数据库中表与表之间的关系
     * @param acwTableAssociationRelationAnalysisSchemaCommand schema 参数
     * @return
     */
    @Override
    public Result<Void> analysisSchema(AcwTableAssociationRelationAnalysisSchemaCommand acwTableAssociationRelationAnalysisSchemaCommand) {
        AcwTableAssociationRelation acwTableAssociationRelation = AcwTableAssociationRelationDTOAssembler.INSTANCE.toAcwTableAssociationRelation(acwTableAssociationRelationAnalysisSchemaCommand);
        return acwTableAssociationRelationRepository.analysisSchema(acwTableAssociationRelation);
    }
}