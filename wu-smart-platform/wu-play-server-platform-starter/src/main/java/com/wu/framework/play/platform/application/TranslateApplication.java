package com.wu.framework.play.platform.application;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.play.platform.domain.model.translate.Translate;
import com.wu.framework.play.platform.application.command.translate.TranslateRemoveCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateStoryCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateUpdateCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateQueryListCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateQueryOneCommand;
import com.wu.framework.play.platform.application.dto.TranslateDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 翻译数据 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface TranslateApplication {


    /**
     * describe 新增翻译数据
     *
     * @param translateStoryCommand 新增翻译数据     
     * @return {@link Result<Translate>} 翻译数据新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Translate> story(TranslateStoryCommand translateStoryCommand);

    /**
     * describe 批量新增翻译数据
     *
     * @param translateStoryCommandList 批量新增翻译数据     
     * @return {@link Result<List<Translate>>} 翻译数据新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<Translate>> batchStory(List<TranslateStoryCommand> translateStoryCommandList);

    /**
     * describe 更新翻译数据
     *
     * @param translateUpdateCommand 更新翻译数据     
     * @return {@link Result<Translate>} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Translate> updateOne(TranslateUpdateCommand translateUpdateCommand);

    /**
     * describe 查询单个翻译数据
     *
     * @param translateQueryOneCommand 查询单个翻译数据     
     * @return {@link Result<TranslateDTO>} 翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<TranslateDTO> findOne(TranslateQueryOneCommand translateQueryOneCommand);

    /**
     * describe 查询多个翻译数据
     *
     * @param translateQueryListCommand 查询多个翻译数据     
     * @return {@link Result <List<TranslateDTO>>} 翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result <List<TranslateDTO>> findList(TranslateQueryListCommand translateQueryListCommand);

    /**
     * describe 分页查询多个翻译数据
     *
     * @param translateQueryListCommand 分页查询多个翻译数据     
     * @return {@link Result <LazyPage<TranslateDTO>>} 分页翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result <LazyPage<TranslateDTO>> findPage(int size,int current,TranslateQueryListCommand translateQueryListCommand);

    /**
     * describe 删除翻译数据
     *
     * @param translateRemoveCommand 删除翻译数据     
     * @return {@link Result<Translate>} 翻译数据     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Translate> remove(TranslateRemoveCommand translateRemoveCommand);

}