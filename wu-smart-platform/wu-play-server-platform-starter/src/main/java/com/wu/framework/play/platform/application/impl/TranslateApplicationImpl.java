package com.wu.framework.play.platform.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.play.platform.application.TranslateApplication;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.play.platform.domain.model.translate.Translate;
import com.wu.framework.play.platform.application.command.translate.TranslateRemoveCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateStoryCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateUpdateCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateQueryListCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateQueryOneCommand;
import com.wu.framework.play.platform.application.assembler.TranslateDTOAssembler;
import com.wu.framework.play.platform.application.dto.TranslateDTO;
import java.util.stream.Collectors;
import jakarta.annotation.Resource;
import com.wu.framework.play.platform.domain.model.translate.TranslateRepository;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 翻译数据 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class TranslateApplicationImpl implements TranslateApplication {

    @Resource
    TranslateRepository translateRepository;
    /**
     * describe 新增翻译数据
     *
     * @param translateStoryCommand 新增翻译数据     
     * @return {@link Result<Translate>} 翻译数据新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Translate> story(TranslateStoryCommand translateStoryCommand) {
        Translate translate = TranslateDTOAssembler.INSTANCE.toTranslate(translateStoryCommand);
        return translateRepository.story(translate);
    }
    /**
     * describe 批量新增翻译数据
     *
     * @param translateStoryCommandList 批量新增翻译数据     
     * @return {@link Result<List<Translate>>} 翻译数据新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<Translate>> batchStory(List<TranslateStoryCommand> translateStoryCommandList) {
        List<Translate> translateList = translateStoryCommandList.stream().map( TranslateDTOAssembler.INSTANCE::toTranslate).collect(Collectors.toList());
        return translateRepository.batchStory(translateList);
    }
    /**
     * describe 更新翻译数据
     *
     * @param translateUpdateCommand 更新翻译数据     
     * @return {@link Result<Translate>} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Translate> updateOne(TranslateUpdateCommand translateUpdateCommand) {
        Translate translate = TranslateDTOAssembler.INSTANCE.toTranslate(translateUpdateCommand);
        return translateRepository.story(translate);
    }

    /**
     * describe 查询单个翻译数据
     *
     * @param translateQueryOneCommand 查询单个翻译数据     
     * @return {@link Result<TranslateDTO>} 翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<TranslateDTO> findOne(TranslateQueryOneCommand translateQueryOneCommand) {
        Translate translate = TranslateDTOAssembler.INSTANCE.toTranslate(translateQueryOneCommand);
        return translateRepository.findOne(translate).convert(TranslateDTOAssembler.INSTANCE::fromTranslate);
    }

    /**
     * describe 查询多个翻译数据
     *
     * @param translateQueryListCommand 查询多个翻译数据     
     * @return {@link Result<List<TranslateDTO>>} 翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<TranslateDTO>> findList(TranslateQueryListCommand translateQueryListCommand) {
        Translate translate = TranslateDTOAssembler.INSTANCE.toTranslate(translateQueryListCommand);
        return translateRepository.findList(translate)        .convert(translates -> translates.stream().map(TranslateDTOAssembler.INSTANCE::fromTranslate).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个翻译数据
     *
     * @param translateQueryListCommand 分页查询多个翻译数据     
     * @return {@link Result<LazyPage<TranslateDTO>>} 分页翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<LazyPage<TranslateDTO>> findPage(int size,int current,TranslateQueryListCommand translateQueryListCommand) {
        Translate translate = TranslateDTOAssembler.INSTANCE.toTranslate(translateQueryListCommand);
        return translateRepository.findPage(size,current,translate)        .convert(page -> page.convert(TranslateDTOAssembler.INSTANCE::fromTranslate))            ;
    }

    /**
     * describe 删除翻译数据
     *
     * @param translateRemoveCommand 删除翻译数据     
     * @return {@link Result<Translate>} 翻译数据     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Translate> remove(TranslateRemoveCommand translateRemoveCommand) {
     Translate translate = TranslateDTOAssembler.INSTANCE.toTranslate(translateRemoveCommand);
     return translateRepository.remove(translate);
    }

}