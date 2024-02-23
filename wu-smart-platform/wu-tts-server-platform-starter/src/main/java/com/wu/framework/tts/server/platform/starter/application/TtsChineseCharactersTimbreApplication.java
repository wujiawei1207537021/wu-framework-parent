package com.wu.framework.tts.server.platform.starter.application;

import com.wu.framework.response.Result;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.*;
import com.wu.framework.tts.server.platform.starter.application.dto.TtsChineseCharactersTimbreDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.timbre.TtsChineseCharactersTimbre;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe tts 中文对应制定音色数据 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface TtsChineseCharactersTimbreApplication {


    /**
     * describe 新增tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharactersTimbre> story(TtsChineseCharactersTimbreStoryCommand ttsChineseCharactersTimbreStoryCommand);

    /**
     * describe 批量新增tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<List<TtsChineseCharactersTimbre>> batchStory(List<TtsChineseCharactersTimbreStoryCommand> ttsChineseCharactersTimbreStoryCommandList);

    /**
     * describe 更新tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharactersTimbre> updateOne(TtsChineseCharactersTimbreUpdateCommand ttsChineseCharactersTimbreUpdateCommand);

    /**
     * describe 查询单个tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharactersTimbreDTO> findOne(TtsChineseCharactersTimbreQueryOneCommand ttsChineseCharactersTimbreQueryOneCommand);

    /**
     * describe 查询多个tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result <List<TtsChineseCharactersTimbreDTO>> findList(TtsChineseCharactersTimbreQueryListCommand ttsChineseCharactersTimbreQueryListCommand);

    /**
     * describe 分页查询多个tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result <LazyPage<TtsChineseCharactersTimbreDTO>> findPage(int size,int current,TtsChineseCharactersTimbreQueryListCommand ttsChineseCharactersTimbreQueryListCommand);

    /**
     * describe 删除tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharactersTimbre> remove(TtsChineseCharactersTimbreRemoveCommand ttsChineseCharactersTimbreRemoveCommand);

    /**
     *  文本转换成语音文件
     * @param ttsChineseCharactersTimbreQueryTextToBytesCommand 参数
     * @return
     */
    byte[] textToBytes(TtsChineseCharactersTimbreQueryTextToBytesCommand ttsChineseCharactersTimbreQueryTextToBytesCommand);

}