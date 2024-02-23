package com.wu.framework.authorization.platform.model.dictionary.data;

import com.wu.framework.authorization.platform.infrastructure.entity.DictionaryDataDO;
import com.wu.framework.response.Result;
import com.wu.framework.response.repository.LazyCrudRepository;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface DictionaryDataRepository extends LazyCrudRepository<DictionaryDataDO,DictionaryData, Integer> {


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<DictionaryData> story(DictionaryData dictionaryData);

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<DictionaryData> findOne(DictionaryData dictionaryData);

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<DictionaryData>> findList(DictionaryData dictionaryData);

    /**
     * describe 根据字典编码获取字典数据
     *
     * @param dictionaryCodeList 字典编码
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<DictionaryData>> findListByDictionaryCode(List<String> dictionaryCodeList);

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<DictionaryData> delete(DictionaryData dictionaryData);

}