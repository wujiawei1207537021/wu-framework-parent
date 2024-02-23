package com.wu.framework.tts.server.platform.starter.controller;

import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.TtsChineseCharactersTimbreQueryTextToBytesCommand;
import com.wu.framework.tts.server.platform.starter.application.dto.TtsChineseCharactersDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.TtsChineseCharacters;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersRemoveCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersStoryCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersUpdateCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersQueryListCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersQueryOneCommand;
import com.wu.framework.tts.server.platform.starter.application.TtsChineseCharactersApplication;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe tts 中文 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "tts 中文提供者")
@EasyController("/tts/chinese/characters")
public class TtsChineseCharactersProvider  {

    @Autowired
    private TtsChineseCharactersApplication ttsChineseCharactersApplication;

    /**
     * describe 新增tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "新增tts 中文")
    @PostMapping("/story")
    public Result<TtsChineseCharacters> story(@RequestBody TtsChineseCharactersStoryCommand ttsChineseCharactersStoryCommand){
        return ttsChineseCharactersApplication.story(ttsChineseCharactersStoryCommand);
    }
    /**
     * describe 批量新增tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "批量新增tts 中文")
    @PostMapping("/batchStory")
    public Result<List<TtsChineseCharacters>> batchStory(@RequestBody List<TtsChineseCharactersStoryCommand> ttsChineseCharactersStoryCommandList){
        return ttsChineseCharactersApplication.batchStory(ttsChineseCharactersStoryCommandList);
    }
    /**
     * describe 更新tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "更新tts 中文")
    @PutMapping("/updateOne")
    public Result<TtsChineseCharacters> updateOne(@RequestBody TtsChineseCharactersUpdateCommand ttsChineseCharactersUpdateCommand){
        return ttsChineseCharactersApplication.updateOne(ttsChineseCharactersUpdateCommand);
    }
    /**
     * describe 查询单个tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "查询单个tts 中文")
    @GetMapping("/findOne")
    public Result<TtsChineseCharactersDTO> findOne(@ModelAttribute TtsChineseCharactersQueryOneCommand ttsChineseCharactersQueryOneCommand){
        return ttsChineseCharactersApplication.findOne(ttsChineseCharactersQueryOneCommand);
    }
    /**
     * describe 查询多个tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "查询多个tts 中文")
    @GetMapping("/findList")
    public Result<List<TtsChineseCharactersDTO>> findList(@ModelAttribute TtsChineseCharactersQueryListCommand ttsChineseCharactersQueryListCommand){
        return ttsChineseCharactersApplication.findList(ttsChineseCharactersQueryListCommand);
    }
    /**
     * describe 分页查询多个tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "分页查询多个tts 中文")
    @GetMapping("/findPage")
    public Result<LazyPage<TtsChineseCharactersDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute TtsChineseCharactersQueryListCommand ttsChineseCharactersQueryListCommand){
        return ttsChineseCharactersApplication.findPage(size,current,ttsChineseCharactersQueryListCommand);
    }
    /**
     * describe 删除tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "删除tts 中文")
    @DeleteMapping("/remove")
    public Result<TtsChineseCharacters> remove(@ModelAttribute TtsChineseCharactersRemoveCommand ttsChineseCharactersRemoveCommand){
        return ttsChineseCharactersApplication.remove(ttsChineseCharactersRemoveCommand);
    }

}