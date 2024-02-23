package com.wu.framework.tts.server.platform.starter.controller;

import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.tts.server.platform.starter.application.TtsChineseCharactersTimbreApplication;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.*;
import com.wu.framework.tts.server.platform.starter.application.dto.TtsChineseCharactersTimbreDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.timbre.TtsChineseCharactersTimbre;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe tts 中文对应制定音色数据
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "tts 中文对应制定音色数据提供者")
@EasyController("/tts/chinese/characters/timbre")
public class TtsChineseCharactersTimbreProvider {

    @Autowired
    private TtsChineseCharactersTimbreApplication ttsChineseCharactersTimbreApplication;

    /**
     * describe 新增tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "新增tts 中文对应制定音色数据")
    @PostMapping("/story")
    public Result<TtsChineseCharactersTimbre> story(@RequestBody TtsChineseCharactersTimbreStoryCommand ttsChineseCharactersTimbreStoryCommand) {
        return ttsChineseCharactersTimbreApplication.story(ttsChineseCharactersTimbreStoryCommand);
    }

    /**
     * describe 批量新增tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "批量新增tts 中文对应制定音色数据")
    @PostMapping("/batchStory")
    public Result<List<TtsChineseCharactersTimbre>> batchStory(@RequestBody List<TtsChineseCharactersTimbreStoryCommand> ttsChineseCharactersTimbreStoryCommandList) {
        return ttsChineseCharactersTimbreApplication.batchStory(ttsChineseCharactersTimbreStoryCommandList);
    }

    /**
     * describe 更新tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "更新tts 中文对应制定音色数据")
    @PutMapping("/updateOne")
    public Result<TtsChineseCharactersTimbre> updateOne(@RequestBody TtsChineseCharactersTimbreUpdateCommand ttsChineseCharactersTimbreUpdateCommand) {
        return ttsChineseCharactersTimbreApplication.updateOne(ttsChineseCharactersTimbreUpdateCommand);
    }

    /**
     * describe 查询单个tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "查询单个tts 中文对应制定音色数据")
    @GetMapping("/findOne")
    public Result<TtsChineseCharactersTimbreDTO> findOne(@ModelAttribute TtsChineseCharactersTimbreQueryOneCommand ttsChineseCharactersTimbreQueryOneCommand) {
        return ttsChineseCharactersTimbreApplication.findOne(ttsChineseCharactersTimbreQueryOneCommand);
    }

    /**
     * describe 查询多个tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "查询多个tts 中文对应制定音色数据")
    @GetMapping("/findList")
    public Result<List<TtsChineseCharactersTimbreDTO>> findList(@ModelAttribute TtsChineseCharactersTimbreQueryListCommand ttsChineseCharactersTimbreQueryListCommand) {
        return ttsChineseCharactersTimbreApplication.findList(ttsChineseCharactersTimbreQueryListCommand);
    }

    /**
     * describe 分页查询多个tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "分页查询多个tts 中文对应制定音色数据")
    @GetMapping("/findPage")
    public Result<LazyPage<TtsChineseCharactersTimbreDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                                    @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute TtsChineseCharactersTimbreQueryListCommand ttsChineseCharactersTimbreQueryListCommand) {
        return ttsChineseCharactersTimbreApplication.findPage(size, current, ttsChineseCharactersTimbreQueryListCommand);
    }

    /**
     * describe 删除tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Operation(summary = "删除tts 中文对应制定音色数据")
    @DeleteMapping("/remove")
    public Result<TtsChineseCharactersTimbre> remove(@ModelAttribute TtsChineseCharactersTimbreRemoveCommand ttsChineseCharactersTimbreRemoveCommand) {
        return ttsChineseCharactersTimbreApplication.remove(ttsChineseCharactersTimbreRemoveCommand);
    }

    /**
     * 将文本转换成音频
     *
     * @param ttsChineseCharactersTimbreQueryTextToBytesCommand 查询条件
     * @return
     */
    @EasyFile(fileName = "tts", suffix = "mp3", fileType = EasyFile.FileType.BYTE_TYPE)
    @Operation(summary = "将文本转换成音频")
    @GetMapping("/textToBytes")
    public byte[] textToBytes(@ModelAttribute TtsChineseCharactersTimbreQueryTextToBytesCommand ttsChineseCharactersTimbreQueryTextToBytesCommand) {
        return ttsChineseCharactersTimbreApplication.textToBytes(ttsChineseCharactersTimbreQueryTextToBytesCommand);
    }
}