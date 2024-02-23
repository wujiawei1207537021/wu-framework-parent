package com.wu.framework.play.platform.infrastructure.persistence;

import com.wu.framework.play.platform.infrastructure.entity.TranslateDO;
import com.wu.framework.play.platform.infrastructure.converter.TranslateConverter;
import com.wu.framework.play.platform.infrastructure.mapper.TranslateMapper;
import com.wu.framework.play.platform.domain.model.translate.TranslateRepository;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import com.wu.framework.play.platform.domain.model.translate.Translate;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 翻译数据 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class TranslateRepositoryImpl  implements  TranslateRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增翻译数据
     *
     * @param translate 新增翻译数据     
     * @return {@link Result<Translate>} 翻译数据新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Translate> story(Translate translate) {
        TranslateDO translateDO = TranslateConverter.INSTANCE.fromTranslate(translate);
        lazyLambdaStream.upsert(translateDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增翻译数据
     *
     * @param translateList 批量新增翻译数据     
     * @return {@link Result<List<Translate>>} 翻译数据新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<Translate>> batchStory(List<Translate> translateList) {
        List<TranslateDO> translateDOList = translateList.stream().map(TranslateConverter.INSTANCE::fromTranslate).collect(Collectors.toList());
        lazyLambdaStream.upsert(translateDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个翻译数据
     *
     * @param translate 查询单个翻译数据     
     * @return {@link Result<Translate>} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Translate> findOne(Translate translate) {
        TranslateDO translateDO = TranslateConverter.INSTANCE.fromTranslate(translate);
        Translate translateOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(translateDO), Translate.class);
        return ResultFactory.successOf(translateOne);
    }

    /**
     * describe 查询多个翻译数据
     *
     * @param translate 查询多个翻译数据     
     * @return {@link Result<List<Translate>>} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<Translate>> findList(Translate translate) {
        TranslateDO translateDO = TranslateConverter.INSTANCE.fromTranslate(translate);
        List<Translate> translateList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(translateDO), Translate.class);
        return ResultFactory.successOf(translateList);
    }

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

    @Override
    public Result<LazyPage<Translate>> findPage(int size,int current,Translate translate) {
        TranslateDO translateDO = TranslateConverter.INSTANCE.fromTranslate(translate);
        LazyPage<Translate> lazyPage = new LazyPage<>(current,size);
        LazyPage<Translate> translateLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(translateDO),lazyPage, Translate.class);
        return ResultFactory.successOf(translateLazyPage);
    }

    /**
     * describe 删除翻译数据
     *
     * @param translate 删除翻译数据     
     * @return {@link Result<Translate>} 翻译数据     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Translate> remove(Translate translate) {
        TranslateDO translateDO = TranslateConverter.INSTANCE.fromTranslate(translate);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(translateDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在翻译数据
     *
     * @param translate 翻译数据领域对象     
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Boolean> exists(Translate translate) {
        TranslateDO translateDO = TranslateConverter.INSTANCE.fromTranslate(translate);
        Boolean exists=lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(translateDO));
        return ResultFactory.successOf(exists);
    }

}