package com.wu.smart.acw.server.infrastructure.converter;


import com.wu.smart.acw.server.domain.model.acw.table.association.relation.AcwTableAssociationRelation;
import com.wu.smart.acw.server.infrastructure.entity.AcwTableAssociationRelationDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe 表关联关系 
 *
 * @author Jia wei Wu
 * @date 2023/11/01 10:16 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface AcwTableAssociationRelationConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
    AcwTableAssociationRelationConverter INSTANCE = Mappers.getMapper(AcwTableAssociationRelationConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
    AcwTableAssociationRelation toAcwTableAssociationRelation(AcwTableAssociationRelationDO acwTableAssociationRelationDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/
     AcwTableAssociationRelationDO fromAcwTableAssociationRelation(AcwTableAssociationRelation acwTableAssociationRelation); 
}