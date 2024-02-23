package com.wu.framework.authorization.platform.controller;

import com.wu.framework.authorization.platform.application.DictionaryApplication;
import com.wu.framework.authorization.platform.application.command.DictionaryCommand;
import com.wu.framework.authorization.platform.infrastructure.entity.DictionaryDO;
import com.wu.framework.authorization.platform.model.dictionary.Dictionary;
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
@EasyController("/dictionary")
public class DictionaryProvider extends AbstractLazyCrudProvider<DictionaryDO,DictionaryDO, Long> {

    @Autowired
    private DictionaryApplication dictionaryApplication;

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
    public Result<Dictionary> story(@RequestBody DictionaryCommand dictionaryCommand) {
        return dictionaryApplication.save(dictionaryCommand);
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
    public Result<Dictionary> updateOne(@RequestBody DictionaryCommand dictionaryCommand) {
        return dictionaryApplication.update(dictionaryCommand);
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
    public Result<Dictionary> findOne(@ModelAttribute DictionaryCommand dictionaryCommand) {
        return dictionaryApplication.findOne(dictionaryCommand);
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
    public Result<List<Dictionary>> findList(@ModelAttribute DictionaryCommand dictionaryCommand) {
        return dictionaryApplication.findList(dictionaryCommand);
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
    public Result<Dictionary> delete(@ModelAttribute DictionaryCommand dictionaryCommand) {
        return dictionaryApplication.delete(dictionaryCommand);
    }
}