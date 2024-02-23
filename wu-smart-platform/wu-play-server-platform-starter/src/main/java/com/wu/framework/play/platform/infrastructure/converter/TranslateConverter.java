package com.wu.framework.play.platform.infrastructure.converter;

import com.wu.framework.play.platform.domain.model.translate.Translate;
import com.wu.framework.play.platform.infrastructure.entity.TranslateDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 翻译数据 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface TranslateConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    TranslateConverter INSTANCE = Mappers.getMapper(TranslateConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param translateDO 翻译数据实体对象     
     * @return {@link Translate} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    Translate toTranslate(TranslateDO translateDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param translate 翻译数据领域对象     
     * @return {@link TranslateDO} 翻译数据实体对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     TranslateDO fromTranslate(Translate translate); 
}