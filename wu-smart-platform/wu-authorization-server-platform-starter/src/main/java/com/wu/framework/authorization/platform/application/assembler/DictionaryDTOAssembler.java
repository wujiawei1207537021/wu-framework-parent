package com.wu.framework.authorization.platform.application.assembler;

import com.wu.framework.authorization.platform.application.command.DictionaryCommand;
import com.wu.framework.authorization.platform.application.dto.DictionaryDTO;
import com.wu.framework.authorization.platform.model.dictionary.Dictionary;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/

public class DictionaryDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static Dictionary toDictionary(DictionaryCommand dictionaryCommand) {
        if (null != dictionaryCommand) {
            Dictionary dictionary = new Dictionary();
            dictionary.setCode(dictionaryCommand.getCode());
            dictionary.setCreateTime(dictionaryCommand.getCreateTime());
            dictionary.setDescription(dictionaryCommand.getDescription());
            dictionary.setId(dictionaryCommand.getId());
            dictionary.setName(dictionaryCommand.getName());
            dictionary.setType(dictionaryCommand.getType());
            dictionary.setUpdateTime(dictionaryCommand.getUpdateTime());
            return dictionary;
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
    public static DictionaryDTO fromDictionary(Dictionary dictionary) {
        if (null != dictionary) {
            DictionaryDTO dictionaryDTO = new DictionaryDTO();
            dictionaryDTO.setCode(dictionary.getCode());
            dictionaryDTO.setCreateTime(dictionary.getCreateTime());
            dictionaryDTO.setDescription(dictionary.getDescription());
            dictionaryDTO.setId(dictionary.getId());
            dictionaryDTO.setName(dictionary.getName());
            dictionaryDTO.setType(dictionary.getType());
            dictionaryDTO.setUpdateTime(dictionary.getUpdateTime());
            return dictionaryDTO;
        }
        return null;
    }

}