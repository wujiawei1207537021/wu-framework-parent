package com.wu.framework.authorization.platform.application.impl;

import com.wu.framework.authorization.platform.application.DictionaryApplication;
import com.wu.framework.authorization.platform.application.assembler.DictionaryDTOAssembler;
import com.wu.framework.authorization.platform.application.command.DictionaryCommand;
import com.wu.framework.authorization.platform.model.dictionary.Dictionary;
import com.wu.framework.authorization.platform.model.dictionary.DictionaryRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class DictionaryApplicationImpl implements DictionaryApplication {

    @Autowired
    DictionaryRepository dictionaryRepository;

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
    public Result<Dictionary> save(DictionaryCommand dictionaryCommand) {
        Dictionary dictionary = DictionaryDTOAssembler.toDictionary(dictionaryCommand);
        return dictionaryRepository.story(dictionary);
    }

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Dictionary> update(DictionaryCommand dictionaryCommand) {
        Dictionary dictionary = DictionaryDTOAssembler.toDictionary(dictionaryCommand);
        return dictionaryRepository.story(dictionary);
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
    public Result<Dictionary> findOne(DictionaryCommand dictionaryCommand) {
        Dictionary dictionary = DictionaryDTOAssembler.toDictionary(dictionaryCommand);
        return dictionaryRepository.findOne(dictionary);
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
    public Result<List<Dictionary>> findList(DictionaryCommand dictionaryCommand) {
        Dictionary dictionary = DictionaryDTOAssembler.toDictionary(dictionaryCommand);
        return dictionaryRepository.findList(dictionary);
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
    public Result<Dictionary> delete(DictionaryCommand dictionaryCommand) {
        Dictionary dictionary = DictionaryDTOAssembler.toDictionary(dictionaryCommand);
        return dictionaryRepository.delete(dictionary);
    }

}