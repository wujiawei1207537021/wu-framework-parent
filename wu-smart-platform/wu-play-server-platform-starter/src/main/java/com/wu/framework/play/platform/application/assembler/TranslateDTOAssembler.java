package com.wu.framework.play.platform.application.assembler;

import com.wu.framework.play.platform.domain.model.translate.Translate;
import com.wu.framework.play.platform.application.command.translate.TranslateRemoveCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateStoryCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateUpdateCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateQueryListCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateQueryOneCommand;
import com.wu.framework.play.platform.application.dto.TranslateDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 翻译数据 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface TranslateDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    TranslateDTOAssembler INSTANCE = Mappers.getMapper(TranslateDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param translateStoryCommand 保存翻译数据对象     
     * @return {@link Translate} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Translate toTranslate(TranslateStoryCommand translateStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param translateUpdateCommand 更新翻译数据对象     
     * @return {@link Translate} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Translate toTranslate(TranslateUpdateCommand translateUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param translateQueryOneCommand 查询单个翻译数据对象参数     
     * @return {@link Translate} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Translate toTranslate(TranslateQueryOneCommand translateQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param translateQueryListCommand 查询集合翻译数据对象参数     
     * @return {@link Translate} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Translate toTranslate(TranslateQueryListCommand translateQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param translateRemoveCommand 删除翻译数据对象参数     
     * @return {@link Translate} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Translate toTranslate(TranslateRemoveCommand translateRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param translate 翻译数据领域对象     
     * @return {@link TranslateDTO} 翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     TranslateDTO fromTranslate(Translate translate);
}