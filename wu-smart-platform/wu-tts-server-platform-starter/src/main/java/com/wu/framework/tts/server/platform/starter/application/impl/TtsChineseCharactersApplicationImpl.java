package com.wu.framework.tts.server.platform.starter.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.tts.server.platform.starter.application.assembler.TtsChineseCharactersDTOAssembler;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.TtsChineseCharactersTimbreQueryTextToBytesCommand;
import com.wu.framework.tts.server.platform.starter.application.dto.TtsChineseCharactersDTO;
import com.wu.framework.tts.server.platform.starter.application.TtsChineseCharactersApplication;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.TtsChineseCharacters;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.TtsChineseCharactersRepository;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersRemoveCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersStoryCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersUpdateCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersQueryListCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersQueryOneCommand;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import org.springframework.util.ObjectUtils;

/**
 * describe tts 中文 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class TtsChineseCharactersApplicationImpl implements TtsChineseCharactersApplication {

    @Autowired
    TtsChineseCharactersRepository ttsChineseCharactersRepository;
    /**
     * describe 新增tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Override
    public Result<TtsChineseCharacters> story(TtsChineseCharactersStoryCommand ttsChineseCharactersStoryCommand) {
        TtsChineseCharacters ttsChineseCharacters = TtsChineseCharactersDTOAssembler.INSTANCE.toTtsChineseCharacters(ttsChineseCharactersStoryCommand);
        return ttsChineseCharactersRepository.story(ttsChineseCharacters);
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

    @Override
    public Result<List<TtsChineseCharacters>> batchStory(List<TtsChineseCharactersStoryCommand> ttsChineseCharactersStoryCommandList) {
        List<TtsChineseCharacters> ttsChineseCharactersList = ttsChineseCharactersStoryCommandList.stream().map( TtsChineseCharactersDTOAssembler.INSTANCE::toTtsChineseCharacters).collect(Collectors.toList());
        return ttsChineseCharactersRepository.batchStory(ttsChineseCharactersList);
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

    @Override
    public Result<TtsChineseCharacters> updateOne(TtsChineseCharactersUpdateCommand ttsChineseCharactersUpdateCommand) {
        TtsChineseCharacters ttsChineseCharacters = TtsChineseCharactersDTOAssembler.INSTANCE.toTtsChineseCharacters(ttsChineseCharactersUpdateCommand);
        return ttsChineseCharactersRepository.story(ttsChineseCharacters);
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

    @Override
    public Result<TtsChineseCharactersDTO> findOne(TtsChineseCharactersQueryOneCommand ttsChineseCharactersQueryOneCommand) {
        TtsChineseCharacters ttsChineseCharacters = TtsChineseCharactersDTOAssembler.INSTANCE.toTtsChineseCharacters(ttsChineseCharactersQueryOneCommand);
        return ttsChineseCharactersRepository.findOne(ttsChineseCharacters).convert(TtsChineseCharactersDTOAssembler.INSTANCE::fromTtsChineseCharacters);
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

    @Override
    public Result<List<TtsChineseCharactersDTO>> findList(TtsChineseCharactersQueryListCommand ttsChineseCharactersQueryListCommand) {
        TtsChineseCharacters ttsChineseCharacters = TtsChineseCharactersDTOAssembler.INSTANCE.toTtsChineseCharacters(ttsChineseCharactersQueryListCommand);
        return ttsChineseCharactersRepository.findList(ttsChineseCharacters)        .convert(ttsChineseCharacterss -> ttsChineseCharacterss.stream().map(TtsChineseCharactersDTOAssembler.INSTANCE::fromTtsChineseCharacters).collect(Collectors.toList())) ;
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

    @Override
    public Result<LazyPage<TtsChineseCharactersDTO>> findPage(int size,int current,TtsChineseCharactersQueryListCommand ttsChineseCharactersQueryListCommand) {
        TtsChineseCharacters ttsChineseCharacters = TtsChineseCharactersDTOAssembler.INSTANCE.toTtsChineseCharacters(ttsChineseCharactersQueryListCommand);
        return ttsChineseCharactersRepository.findPage(size,current,ttsChineseCharacters)        .convert(page -> page.convert(TtsChineseCharactersDTOAssembler.INSTANCE::fromTtsChineseCharacters))            ;
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

    @Override
    public Result<TtsChineseCharacters> remove(TtsChineseCharactersRemoveCommand ttsChineseCharactersRemoveCommand) {
     TtsChineseCharacters ttsChineseCharacters = TtsChineseCharactersDTOAssembler.INSTANCE.toTtsChineseCharacters(ttsChineseCharactersRemoveCommand);
     return ttsChineseCharactersRepository.remove(ttsChineseCharacters);
    }


}