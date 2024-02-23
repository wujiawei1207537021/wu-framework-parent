package com.wu.framework.authorization.platform.application.impl;

import com.wu.framework.authorization.platform.application.DictionaryDataApplication;
import com.wu.framework.authorization.platform.application.assembler.DictionaryDataDTOAssembler;
import com.wu.framework.authorization.platform.application.command.DictionaryDataCommand;
import com.wu.framework.authorization.platform.model.dictionary.data.DictionaryData;
import com.wu.framework.authorization.platform.model.dictionary.data.DictionaryDataRepository;
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
public class DictionaryDataApplicationImpl implements DictionaryDataApplication {

    @Autowired
    DictionaryDataRepository dictionaryDataRepository;

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
    public Result<DictionaryData> save(DictionaryDataCommand dictionaryDataCommand) {
        DictionaryData dictionaryData = DictionaryDataDTOAssembler.toDictionaryData(dictionaryDataCommand);
        return dictionaryDataRepository.story(dictionaryData);
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
    public Result<DictionaryData> update(DictionaryDataCommand dictionaryDataCommand) {
        DictionaryData dictionaryData = DictionaryDataDTOAssembler.toDictionaryData(dictionaryDataCommand);
        return dictionaryDataRepository.story(dictionaryData);
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
    public Result<DictionaryData> findOne(DictionaryDataCommand dictionaryDataCommand) {
        DictionaryData dictionaryData = DictionaryDataDTOAssembler.toDictionaryData(dictionaryDataCommand);
        return dictionaryDataRepository.findOne(dictionaryData);
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
    public Result<List<DictionaryData>> findList(DictionaryDataCommand dictionaryDataCommand) {
        DictionaryData dictionaryData = DictionaryDataDTOAssembler.toDictionaryData(dictionaryDataCommand);
        return dictionaryDataRepository.findList(dictionaryData);
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
    public Result<DictionaryData> delete(DictionaryDataCommand dictionaryDataCommand) {
        DictionaryData dictionaryData = DictionaryDataDTOAssembler.toDictionaryData(dictionaryDataCommand);
        return dictionaryDataRepository.delete(dictionaryData);
    }

}