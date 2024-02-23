package com.wu.framework.tts.server.platform.starter.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.tts.server.platform.starter.application.TtsTimbreApplication;
import com.wu.framework.tts.server.platform.starter.application.assembler.TtsTimbreDTOAssembler;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.*;
import com.wu.framework.tts.server.platform.starter.application.dto.TtsTimbreDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.timbre.TtsTimbre;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.timbre.TtsTimbreRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe tts 音色
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class TtsTimbreApplicationImpl implements TtsTimbreApplication {

    @Autowired
    TtsTimbreRepository ttsTimbreRepository;

    /**
     * describe 新增tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Override
    public Result<TtsTimbre> story(TtsTimbreStoryCommand ttsTimbreStoryCommand) {
        TtsTimbre ttsTimbre = TtsTimbreDTOAssembler.INSTANCE.toTtsTimbre(ttsTimbreStoryCommand);
        return ttsTimbreRepository.story(ttsTimbre);
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

    @Override
    public Result<List<TtsTimbre>> batchStory(List<TtsTimbreStoryCommand> ttsTimbreStoryCommandList) {
        List<TtsTimbre> ttsTimbreList = ttsTimbreStoryCommandList.stream().map(TtsTimbreDTOAssembler.INSTANCE::toTtsTimbre).collect(Collectors.toList());
        return ttsTimbreRepository.batchStory(ttsTimbreList);
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

    @Override
    public Result<TtsTimbre> updateOne(TtsTimbreUpdateCommand ttsTimbreUpdateCommand) {
        TtsTimbre ttsTimbre = TtsTimbreDTOAssembler.INSTANCE.toTtsTimbre(ttsTimbreUpdateCommand);
        return ttsTimbreRepository.story(ttsTimbre);
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

    @Override
    public Result<TtsTimbreDTO> findOne(TtsTimbreQueryOneCommand ttsTimbreQueryOneCommand) {
        TtsTimbre ttsTimbre = TtsTimbreDTOAssembler.INSTANCE.toTtsTimbre(ttsTimbreQueryOneCommand);
        return ttsTimbreRepository.findOne(ttsTimbre).convert(TtsTimbreDTOAssembler.INSTANCE::fromTtsTimbre);
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

    @Override
    public Result<List<TtsTimbreDTO>> findList(TtsTimbreQueryListCommand ttsTimbreQueryListCommand) {
        TtsTimbre ttsTimbre = TtsTimbreDTOAssembler.INSTANCE.toTtsTimbre(ttsTimbreQueryListCommand);
        return ttsTimbreRepository.findList(ttsTimbre).convert(ttsTimbres -> ttsTimbres.stream().map(TtsTimbreDTOAssembler.INSTANCE::fromTtsTimbre).collect(Collectors.toList()));
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

    @Override
    public Result<LazyPage<TtsTimbreDTO>> findPage(int size, int current, TtsTimbreQueryListCommand ttsTimbreQueryListCommand) {
        TtsTimbre ttsTimbre = TtsTimbreDTOAssembler.INSTANCE.toTtsTimbre(ttsTimbreQueryListCommand);
        return ttsTimbreRepository.findPage(size, current, ttsTimbre).convert(page -> page.convert(TtsTimbreDTOAssembler.INSTANCE::fromTtsTimbre));
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

    @Override
    public Result<TtsTimbre> remove(TtsTimbreRemoveCommand ttsTimbreRemoveCommand) {
        TtsTimbre ttsTimbre = TtsTimbreDTOAssembler.INSTANCE.toTtsTimbre(ttsTimbreRemoveCommand);
        return ttsTimbreRepository.remove(ttsTimbre);
    }

}