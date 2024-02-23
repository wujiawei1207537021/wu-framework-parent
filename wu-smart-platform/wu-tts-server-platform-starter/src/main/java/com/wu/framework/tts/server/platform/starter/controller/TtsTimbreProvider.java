package com.wu.framework.tts.server.platform.starter.controller;

import com.wu.framework.tts.server.platform.starter.application.dto.TtsTimbreDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.timbre.TtsTimbre;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreRemoveCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreStoryCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreUpdateCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreQueryListCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreQueryOneCommand;
import com.wu.framework.tts.server.platform.starter.application.TtsTimbreApplication;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe tts 音色 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "tts 音色提供者")
@EasyController("/tts/timbre")
public class TtsTimbreProvider  {

    @Autowired
    private TtsTimbreApplication ttsTimbreApplication;

    /**
     * describe 新增tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "新增tts 音色")
    @PostMapping("/story")
    public Result<TtsTimbre> story(@RequestBody TtsTimbreStoryCommand ttsTimbreStoryCommand){
        return ttsTimbreApplication.story(ttsTimbreStoryCommand);
    }
    /**
     * describe 批量新增tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "批量新增tts 音色")
    @PostMapping("/batchStory")
    public Result<List<TtsTimbre>> batchStory(@RequestBody List<TtsTimbreStoryCommand> ttsTimbreStoryCommandList){
        return ttsTimbreApplication.batchStory(ttsTimbreStoryCommandList);
    }
    /**
     * describe 更新tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "更新tts 音色")
    @PutMapping("/updateOne")
    public Result<TtsTimbre> updateOne(@RequestBody TtsTimbreUpdateCommand ttsTimbreUpdateCommand){
        return ttsTimbreApplication.updateOne(ttsTimbreUpdateCommand);
    }
    /**
     * describe 查询单个tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "查询单个tts 音色")
    @GetMapping("/findOne")
    public Result<TtsTimbreDTO> findOne(@ModelAttribute TtsTimbreQueryOneCommand ttsTimbreQueryOneCommand){
        return ttsTimbreApplication.findOne(ttsTimbreQueryOneCommand);
    }
    /**
     * describe 查询多个tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "查询多个tts 音色")
    @GetMapping("/findList")
    public Result<List<TtsTimbreDTO>> findList(@ModelAttribute TtsTimbreQueryListCommand ttsTimbreQueryListCommand){
        return ttsTimbreApplication.findList(ttsTimbreQueryListCommand);
    }
    /**
     * describe 分页查询多个tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "分页查询多个tts 音色")
    @GetMapping("/findPage")
    public Result<LazyPage<TtsTimbreDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute TtsTimbreQueryListCommand ttsTimbreQueryListCommand){
        return ttsTimbreApplication.findPage(size,current,ttsTimbreQueryListCommand);
    }
    /**
     * describe 删除tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "删除tts 音色")
    @DeleteMapping("/remove")
    public Result<TtsTimbre> remove(@ModelAttribute TtsTimbreRemoveCommand ttsTimbreRemoveCommand){
        return ttsTimbreApplication.remove(ttsTimbreRemoveCommand);
    }
}