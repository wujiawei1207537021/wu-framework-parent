package com.wu.smart.acw.server.application.assembler;


import com.wu.smart.acw.server.application.command.acw.table.association.relation.*;
import com.wu.smart.acw.server.application.dto.AcwTableAssociationRelationDTO;
import com.wu.smart.acw.server.domain.model.acw.table.association.relation.AcwTableAssociationRelation;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 表关联关系 
 *
 * @author Jia wei Wu
 * @date 2023/11/01 10:16 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface AcwTableAssociationRelationDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
    AcwTableAssociationRelationDTOAssembler INSTANCE = Mappers.getMapper(AcwTableAssociationRelationDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
     AcwTableAssociationRelation toAcwTableAssociationRelation(AcwTableAssociationRelationStoryCommand acwTableAssociationRelationStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
     AcwTableAssociationRelation toAcwTableAssociationRelation(AcwTableAssociationRelationUpdateCommand acwTableAssociationRelationUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
     AcwTableAssociationRelation toAcwTableAssociationRelation(AcwTableAssociationRelationQueryOneCommand acwTableAssociationRelationQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
     AcwTableAssociationRelation toAcwTableAssociationRelation(AcwTableAssociationRelationQueryListCommand acwTableAssociationRelationQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
     AcwTableAssociationRelation toAcwTableAssociationRelation(AcwTableAssociationRelationRemoveCommand acwTableAssociationRelationRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
     AcwTableAssociationRelationDTO fromAcwTableAssociationRelation(AcwTableAssociationRelation acwTableAssociationRelation);

    /**
     * 解析数据库表结构对象转换成领域对象
     * @param acwTableAssociationRelationAnalysisSchemaCommand 解析数据库表结构对象
     * @return
     */
    AcwTableAssociationRelation toAcwTableAssociationRelation(AcwTableAssociationRelationAnalysisSchemaCommand acwTableAssociationRelationAnalysisSchemaCommand);
}