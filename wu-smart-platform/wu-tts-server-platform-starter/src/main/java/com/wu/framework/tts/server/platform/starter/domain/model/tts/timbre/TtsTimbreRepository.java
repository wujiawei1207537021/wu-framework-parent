package com.wu.framework.tts.server.platform.starter.domain.model.tts.timbre;

import com.wu.framework.response.Result;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe tts 音色 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface TtsTimbreRepository {


    /**
     * describe 新增tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsTimbre> story(TtsTimbre ttsTimbre);

    /**
     * describe 批量新增tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<List<TtsTimbre>> batchStory(List<TtsTimbre> ttsTimbreList);

    /**
     * describe 查询单个tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsTimbre> findOne(TtsTimbre ttsTimbre);

    /**
     * describe 查询多个tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<List<TtsTimbre>> findList(TtsTimbre ttsTimbre);

    /**
     * describe 分页查询多个tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<LazyPage<TtsTimbre>> findPage(int size,int current,TtsTimbre ttsTimbre);

    /**
     * describe 删除tts 音色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsTimbre> remove(TtsTimbre ttsTimbre);

}