package com.wu.framework.authorization.platform.infrastructure.converter;

import com.wu.framework.authorization.platform.infrastructure.entity.DictionaryDO;
import com.wu.framework.authorization.platform.model.dictionary.Dictionary;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class DictionaryConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static Dictionary toDictionary(DictionaryDO dictionaryDO) {
        if (null != dictionaryDO) {
            Dictionary dictionary = new Dictionary();
            dictionary.setCode(dictionaryDO.getCode());
            dictionary.setCreateTime(dictionaryDO.getCreateTime());
            dictionary.setDescription(dictionaryDO.getDescription());
            dictionary.setId(dictionaryDO.getId());
            dictionary.setName(dictionaryDO.getName());
            dictionary.setType(dictionaryDO.getType());
            dictionary.setUpdateTime(dictionaryDO.getUpdateTime());
            return dictionary;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static DictionaryDO fromDictionary(Dictionary dictionary) {
        if (null != dictionary) {
            DictionaryDO dictionaryDO = new DictionaryDO();
            dictionaryDO.setCode(dictionary.getCode());
            dictionaryDO.setCreateTime(dictionary.getCreateTime());
            dictionaryDO.setDescription(dictionary.getDescription());
            dictionaryDO.setId(dictionary.getId());
            dictionaryDO.setName(dictionary.getName());
            dictionaryDO.setType(dictionary.getType());
            dictionaryDO.setUpdateTime(dictionary.getUpdateTime());
            return dictionaryDO;
        }
        return null;
    }

}