package com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.timbre;

import com.wu.framework.response.Result;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe tts 中文对应制定音色数据 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface TtsChineseCharactersTimbreRepository {


    /**
     * describe 新增tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharactersTimbre> story(TtsChineseCharactersTimbre ttsChineseCharactersTimbre);

    /**
     * describe 批量新增tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<List<TtsChineseCharactersTimbre>> batchStory(List<TtsChineseCharactersTimbre> ttsChineseCharactersTimbreList);

    /**
     * describe 查询单个tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharactersTimbre> findOne(TtsChineseCharactersTimbre ttsChineseCharactersTimbre);

    /**
     * describe 查询多个tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<List<TtsChineseCharactersTimbre>> findList(TtsChineseCharactersTimbre ttsChineseCharactersTimbre);

    /**
     * describe 分页查询多个tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<LazyPage<TtsChineseCharactersTimbre>> findPage(int size,int current,TtsChineseCharactersTimbre ttsChineseCharactersTimbre);

    /**
     * describe 删除tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharactersTimbre> remove(TtsChineseCharactersTimbre ttsChineseCharactersTimbre);

    /**
     * 根据字段、音色查询音频信息
     * @param wordList 文字
     * @param timbreCode 音频编码
     * @return 信息
     */
    List<TtsChineseCharactersTimbre> findListByWordsAndTimbreCode(List<String> wordList, String timbreCode);
}