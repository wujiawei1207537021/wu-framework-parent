package com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters;

import com.wu.framework.response.Result;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe tts 中文 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface TtsChineseCharactersRepository {


    /**
     * describe 新增tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharacters> story(TtsChineseCharacters ttsChineseCharacters);

    /**
     * describe 批量新增tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<List<TtsChineseCharacters>> batchStory(List<TtsChineseCharacters> ttsChineseCharactersList);

    /**
     * describe 查询单个tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharacters> findOne(TtsChineseCharacters ttsChineseCharacters);

    /**
     * describe 查询多个tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<List<TtsChineseCharacters>> findList(TtsChineseCharacters ttsChineseCharacters);

    /**
     * describe 分页查询多个tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<LazyPage<TtsChineseCharacters>> findPage(int size,int current,TtsChineseCharacters ttsChineseCharacters);

    /**
     * describe 删除tts 中文
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    Result<TtsChineseCharacters> remove(TtsChineseCharacters ttsChineseCharacters);

}