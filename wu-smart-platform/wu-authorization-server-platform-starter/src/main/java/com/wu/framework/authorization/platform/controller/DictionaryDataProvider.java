package com.wu.framework.authorization.platform.controller;

import com.wu.framework.authorization.platform.application.DictionaryDataApplication;
import com.wu.framework.authorization.platform.application.command.DictionaryDataCommand;
import com.wu.framework.authorization.platform.infrastructure.entity.DictionaryDataDO;
import com.wu.framework.authorization.platform.model.dictionary.data.DictionaryData;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "提供者")
@EasyController("/dictionary/data")
public class DictionaryDataProvider extends AbstractLazyCrudProvider<DictionaryDataDO,DictionaryDataDO, Long> {

    @Autowired
    private DictionaryDataApplication dictionaryDataApplication;

    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @PostMapping("/story")
    public Result<DictionaryData> story(@RequestBody DictionaryDataCommand dictionaryDataCommand) {
        return dictionaryDataApplication.save(dictionaryDataCommand);
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

    @PutMapping("/updateOne")
    public Result<DictionaryData> updateOne(@RequestBody DictionaryDataCommand dictionaryDataCommand) {
        return dictionaryDataApplication.update(dictionaryDataCommand);
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

    @GetMapping("/findOne")
    public Result<DictionaryData> findOne(@ModelAttribute DictionaryDataCommand dictionaryDataCommand) {
        return dictionaryDataApplication.findOne(dictionaryDataCommand);
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

    @GetMapping("/findList")
    public Result<List<DictionaryData>> findList(@ModelAttribute DictionaryDataCommand dictionaryDataCommand) {
        return dictionaryDataApplication.findList(dictionaryDataCommand);
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

    @DeleteMapping("/delete")
    public Result<DictionaryData> delete(@ModelAttribute DictionaryDataCommand dictionaryDataCommand) {
        return dictionaryDataApplication.delete(dictionaryDataCommand);
    }
}