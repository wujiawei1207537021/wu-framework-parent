package com.wu.framework.authorization.platform.infrastructure.converter;

import com.wu.framework.authorization.platform.infrastructure.entity.DictionaryDataDO;
import com.wu.framework.authorization.platform.model.dictionary.data.DictionaryData;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class DictionaryDataConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static DictionaryData toDictionaryData(DictionaryDataDO dictionaryDataDO) {
        if (null != dictionaryDataDO) {
            DictionaryData dictionaryData = new DictionaryData();
            dictionaryData.setCode(dictionaryDataDO.getCode());
            dictionaryData.setCreateTime(dictionaryDataDO.getCreateTime());
            dictionaryData.setDescription(dictionaryDataDO.getDescription());
            dictionaryData.setDictionaryCode(dictionaryDataDO.getDictionaryCode());
            dictionaryData.setId(dictionaryDataDO.getId());
            dictionaryData.setName(dictionaryDataDO.getName());
            dictionaryData.setPcode(dictionaryDataDO.getPcode());
            dictionaryData.setSortValue(dictionaryDataDO.getSortValue());
            dictionaryData.setUpdateTime(dictionaryDataDO.getUpdateTime());
            return dictionaryData;
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
    public static DictionaryDataDO fromDictionaryData(DictionaryData dictionaryData) {
        if (null != dictionaryData) {
            DictionaryDataDO dictionaryDataDO = new DictionaryDataDO();
            dictionaryDataDO.setCode(dictionaryData.getCode());
            dictionaryDataDO.setCreateTime(dictionaryData.getCreateTime());
            dictionaryDataDO.setDescription(dictionaryData.getDescription());
            dictionaryDataDO.setDictionaryCode(dictionaryData.getDictionaryCode());
            dictionaryDataDO.setId(dictionaryData.getId());
            dictionaryDataDO.setName(dictionaryData.getName());
            dictionaryDataDO.setPcode(dictionaryData.getPcode());
            dictionaryDataDO.setSortValue(dictionaryData.getSortValue());
            dictionaryDataDO.setUpdateTime(dictionaryData.getUpdateTime());
            return dictionaryDataDO;
        }
        return null;
    }

}