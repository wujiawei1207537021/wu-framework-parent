package com.wu.framework.tts.server.platform.starter.application;

import com.wu.framework.response.Result;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.TtsChineseCharactersTimbreQueryTextToBytesCommand;
import com.wu.framework.tts.server.platform.starter.application.dto.TtsChineseCharactersDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.TtsChineseCharacters;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersRemoveCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersStoryCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersUpdateCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersQueryListCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersQueryOneCommand;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe tts 中文 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface TtsChineseCharactersApplication {


    /**
     * describe 新增tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharacters> story(TtsChineseCharactersStoryCommand ttsChineseCharactersStoryCommand);

    /**
     * describe 批量新增tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<List<TtsChineseCharacters>> batchStory(List<TtsChineseCharactersStoryCommand> ttsChineseCharactersStoryCommandList);

    /**
     * describe 更新tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharacters> updateOne(TtsChineseCharactersUpdateCommand ttsChineseCharactersUpdateCommand);

    /**
     * describe 查询单个tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharactersDTO> findOne(TtsChineseCharactersQueryOneCommand ttsChineseCharactersQueryOneCommand);

    /**
     * describe 查询多个tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result <List<TtsChineseCharactersDTO>> findList(TtsChineseCharactersQueryListCommand ttsChineseCharactersQueryListCommand);

    /**
     * describe 分页查询多个tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result <LazyPage<TtsChineseCharactersDTO>> findPage(int size,int current,TtsChineseCharactersQueryListCommand ttsChineseCharactersQueryListCommand);

    /**
     * describe 删除tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharacters> remove(TtsChineseCharactersRemoveCommand ttsChineseCharactersRemoveCommand);


}