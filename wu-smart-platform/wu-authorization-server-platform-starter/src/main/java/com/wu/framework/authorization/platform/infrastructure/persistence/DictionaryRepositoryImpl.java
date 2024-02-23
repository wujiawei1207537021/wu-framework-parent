package com.wu.framework.authorization.platform.infrastructure.persistence;

import com.wu.framework.authorization.platform.infrastructure.converter.DictionaryConverter;
import com.wu.framework.authorization.platform.infrastructure.entity.DictionaryDO;
import com.wu.framework.authorization.platform.model.dictionary.Dictionary;
import com.wu.framework.authorization.platform.model.dictionary.DictionaryRepository;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class DictionaryRepositoryImpl extends AbstractLazyCrudRepository<DictionaryDO,Dictionary, Integer> implements DictionaryRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Dictionary> story(Dictionary dictionary) {
        DictionaryDO dictionaryDO = DictionaryConverter.fromDictionary(dictionary);
        lazyLambdaStream.upsert(dictionaryDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Dictionary> findOne(Dictionary dictionary) {
        DictionaryDO dictionaryDO = DictionaryConverter.fromDictionary(dictionary);
        Dictionary dictionaryOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(dictionaryDO), Dictionary.class);
        return ResultFactory.successOf(dictionaryOne);
    }

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<Dictionary>> findList(Dictionary dictionary) {
        DictionaryDO dictionaryDO = DictionaryConverter.fromDictionary(dictionary);
        List<Dictionary> dictionaryList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(dictionaryDO), Dictionary.class);
        return ResultFactory.successOf(dictionaryList);
    }

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Dictionary> delete(Dictionary dictionary) {
        DictionaryDO dictionaryDO = DictionaryConverter.fromDictionary(dictionary);
        //  lazyLambdaStream.remove(dictionaryDO);
        return ResultFactory.successOf();
    }

}