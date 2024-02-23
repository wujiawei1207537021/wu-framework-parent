package com.wu.framework.tts.server.platform.starter.application;

import com.wu.framework.response.Result;
import com.wu.framework.tts.server.platform.starter.application.dto.TtsTimbreDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.timbre.TtsTimbre;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreRemoveCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreStoryCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreUpdateCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreQueryListCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreQueryOneCommand;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe tts 音色 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface TtsTimbreApplication {


    /**
     * describe 新增tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsTimbre> story(TtsTimbreStoryCommand ttsTimbreStoryCommand);

    /**
     * describe 批量新增tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<List<TtsTimbre>> batchStory(List<TtsTimbreStoryCommand> ttsTimbreStoryCommandList);

    /**
     * describe 更新tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsTimbre> updateOne(TtsTimbreUpdateCommand ttsTimbreUpdateCommand);

    /**
     * describe 查询单个tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsTimbreDTO> findOne(TtsTimbreQueryOneCommand ttsTimbreQueryOneCommand);

    /**
     * describe 查询多个tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result <List<TtsTimbreDTO>> findList(TtsTimbreQueryListCommand ttsTimbreQueryListCommand);

    /**
     * describe 分页查询多个tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result <LazyPage<TtsTimbreDTO>> findPage(int size,int current,TtsTimbreQueryListCommand ttsTimbreQueryListCommand);

    /**
     * describe 删除tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsTimbre> remove(TtsTimbreRemoveCommand ttsTimbreRemoveCommand);

}