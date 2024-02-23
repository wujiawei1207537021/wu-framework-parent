package com.wu.framework.play.platform.domain.model.translate;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.play.platform.domain.model.translate.Translate;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 翻译数据 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface TranslateRepository {


    /**
     * describe 新增翻译数据
     *
     * @param translate 新增翻译数据     
     * @return {@link  Result<Translate>} 翻译数据新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Translate> story(Translate translate);

    /**
     * describe 批量新增翻译数据
     *
     * @param translateList 批量新增翻译数据     
     * @return {@link Result<List<Translate>>} 翻译数据新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<Translate>> batchStory(List<Translate> translateList);

    /**
     * describe 查询单个翻译数据
     *
     * @param translate 查询单个翻译数据     
     * @return {@link Result<Translate>} 翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Translate> findOne(Translate translate);

    /**
     * describe 查询多个翻译数据
     *
     * @param translate 查询多个翻译数据     
     * @return {@link Result<List<Translate>>} 翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<Translate>> findList(Translate translate);

    /**
     * describe 分页查询多个翻译数据
     *
     * @param size 当前页数
     * @param current 当前页
     * @param translate 分页查询多个翻译数据     
     * @return {@link Result<LazyPage<Translate>>} 分页翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<LazyPage<Translate>> findPage(int size,int current,Translate translate);

    /**
     * describe 删除翻译数据
     *
     * @param translate 删除翻译数据     
     * @return {@link Result<Translate>} 翻译数据     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Translate> remove(Translate translate);

    /**
     * describe 是否存在翻译数据
     *
     * @param translate 是否存在翻译数据     
     * @return {@link Result<Boolean>} 翻译数据是否存在     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Boolean> exists(Translate translate);

}