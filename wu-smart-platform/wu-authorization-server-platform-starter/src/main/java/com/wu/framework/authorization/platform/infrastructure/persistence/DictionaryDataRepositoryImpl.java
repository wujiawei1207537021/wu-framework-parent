package com.wu.framework.authorization.platform.infrastructure.persistence;


import com.wu.framework.authorization.platform.infrastructure.converter.DictionaryDataConverter;
import com.wu.framework.authorization.platform.infrastructure.entity.DictionaryDataDO;
import com.wu.framework.authorization.platform.model.dictionary.data.DictionaryData;
import com.wu.framework.authorization.platform.model.dictionary.data.DictionaryDataRepository;
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
public class DictionaryDataRepositoryImpl extends
        AbstractLazyCrudRepository<DictionaryDataDO,DictionaryData, Integer> implements DictionaryDataRepository {

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
    public Result<DictionaryData> story(DictionaryData dictionaryData) {
        DictionaryDataDO dictionaryDataDO = DictionaryDataConverter.fromDictionaryData(dictionaryData);
        lazyLambdaStream.upsert(dictionaryDataDO);
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
    public Result<DictionaryData> findOne(DictionaryData dictionaryData) {
        DictionaryDataDO dictionaryDataDO = DictionaryDataConverter.fromDictionaryData(dictionaryData);
        DictionaryData dictionaryDataOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(dictionaryDataDO), DictionaryData.class);
        return ResultFactory.successOf(dictionaryDataOne);
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
    public Result<List<DictionaryData>> findList(DictionaryData dictionaryData) {
        DictionaryDataDO dictionaryDataDO = DictionaryDataConverter.fromDictionaryData(dictionaryData);
        List<DictionaryData> dictionaryDataList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(dictionaryDataDO), DictionaryData.class);
        return ResultFactory.successOf(dictionaryDataList);
    }

    /**
     * describe 根据字典编码获取字典数据
     *
     * @param dictionaryCodeList 字典编码
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    @Override
    public Result<List<DictionaryData>> findListByDictionaryCode(List<String> dictionaryCodeList) {
        List<DictionaryData> dictionaryDataList = lazyLambdaStream.selectList(LazyWrappers.<DictionaryDataDO>lambdaWrapper().in(DictionaryDataDO::getDictionaryCode,dictionaryCodeList), DictionaryData.class);
        return ResultFactory.successOf(dictionaryDataList);
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
    public Result<DictionaryData> delete(DictionaryData dictionaryData) {
        DictionaryDataDO dictionaryDataDO = DictionaryDataConverter.fromDictionaryData(dictionaryData);
        //  lazyLambdaStream.remove(dictionaryDataDO);
        return ResultFactory.successOf();
    }

}