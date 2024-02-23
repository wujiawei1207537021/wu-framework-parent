package com.wu.framework.authorization.platform.application.assembler;


import com.wu.framework.authorization.platform.application.command.DictionaryDataCommand;
import com.wu.framework.authorization.platform.application.dto.DictionaryDataDTO;
import com.wu.framework.authorization.platform.model.dictionary.data.DictionaryData;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/

public class DictionaryDataDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static DictionaryData toDictionaryData(DictionaryDataCommand dictionaryDataCommand) {
        if (null != dictionaryDataCommand) {
            DictionaryData dictionaryData = new DictionaryData();
            dictionaryData.setCode(dictionaryDataCommand.getCode());
            dictionaryData.setCreateTime(dictionaryDataCommand.getCreateTime());
            dictionaryData.setDescription(dictionaryDataCommand.getDescription());
            dictionaryData.setDictionaryCode(dictionaryDataCommand.getDictionaryCode());
            dictionaryData.setId(dictionaryDataCommand.getId());
            dictionaryData.setName(dictionaryDataCommand.getName());
            dictionaryData.setPcode(dictionaryDataCommand.getPcode());
            dictionaryData.setSortValue(dictionaryDataCommand.getSortValue());
            dictionaryData.setUpdateTime(dictionaryDataCommand.getUpdateTime());
            return dictionaryData;
        }
        return null;
    }

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static DictionaryDataDTO fromDictionaryData(DictionaryData dictionaryData) {
        if (null != dictionaryData) {
            DictionaryDataDTO dictionaryDataDTO = new DictionaryDataDTO();
            dictionaryDataDTO.setCode(dictionaryData.getCode());
            dictionaryDataDTO.setCreateTime(dictionaryData.getCreateTime());
            dictionaryDataDTO.setDescription(dictionaryData.getDescription());
            dictionaryDataDTO.setDictionaryCode(dictionaryData.getDictionaryCode());
            dictionaryDataDTO.setId(dictionaryData.getId());
            dictionaryDataDTO.setName(dictionaryData.getName());
            dictionaryDataDTO.setPcode(dictionaryData.getPcode());
            dictionaryDataDTO.setSortValue(dictionaryData.getSortValue());
            dictionaryDataDTO.setUpdateTime(dictionaryData.getUpdateTime());
            return dictionaryDataDTO;
        }
        return null;
    }

}