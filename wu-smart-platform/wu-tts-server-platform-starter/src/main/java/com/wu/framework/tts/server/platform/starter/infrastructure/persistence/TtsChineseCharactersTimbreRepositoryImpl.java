package com.wu.framework.tts.server.platform.starter.infrastructure.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.timbre.TtsChineseCharactersTimbre;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.timbre.TtsChineseCharactersTimbreRepository;
import com.wu.framework.tts.server.platform.starter.infrastructure.converter.TtsChineseCharactersTimbreConverter;
import com.wu.framework.tts.server.platform.starter.infrastructure.entity.TtsChineseCharactersTimbreDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe tts 中文对应制定音色数据
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class TtsChineseCharactersTimbreRepositoryImpl implements TtsChineseCharactersTimbreRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Override
    public Result<TtsChineseCharactersTimbre> story(TtsChineseCharactersTimbre ttsChineseCharactersTimbre) {
        TtsChineseCharactersTimbreDO ttsChineseCharactersTimbreDO = TtsChineseCharactersTimbreConverter.INSTANCE.fromTtsChineseCharactersTimbre(ttsChineseCharactersTimbre);
        lazyLambdaStream.upsert(ttsChineseCharactersTimbreDO);
        return ResultFactory.successOf();
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

    @Override
    public Result<List<TtsChineseCharactersTimbre>> batchStory(List<TtsChineseCharactersTimbre> ttsChineseCharactersTimbreList) {
        List<TtsChineseCharactersTimbreDO> ttsChineseCharactersTimbreDOList = ttsChineseCharactersTimbreList.stream().map(TtsChineseCharactersTimbreConverter.INSTANCE::fromTtsChineseCharactersTimbre).collect(Collectors.toList());
        lazyLambdaStream.upsert(ttsChineseCharactersTimbreDOList);
        return ResultFactory.successOf();
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

    @Override
    public Result<TtsChineseCharactersTimbre> findOne(TtsChineseCharactersTimbre ttsChineseCharactersTimbre) {
        TtsChineseCharactersTimbreDO ttsChineseCharactersTimbreDO = TtsChineseCharactersTimbreConverter.INSTANCE.fromTtsChineseCharactersTimbre(ttsChineseCharactersTimbre);
        TtsChineseCharactersTimbre ttsChineseCharactersTimbreOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(ttsChineseCharactersTimbreDO), TtsChineseCharactersTimbre.class);
        return ResultFactory.successOf(ttsChineseCharactersTimbreOne);
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

    @Override
    public Result<List<TtsChineseCharactersTimbre>> findList(TtsChineseCharactersTimbre ttsChineseCharactersTimbre) {
        TtsChineseCharactersTimbreDO ttsChineseCharactersTimbreDO = TtsChineseCharactersTimbreConverter.INSTANCE.fromTtsChineseCharactersTimbre(ttsChineseCharactersTimbre);
        List<TtsChineseCharactersTimbre> ttsChineseCharactersTimbreList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(ttsChineseCharactersTimbreDO), TtsChineseCharactersTimbre.class);
        return ResultFactory.successOf(ttsChineseCharactersTimbreList);
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

    @Override
    public Result<LazyPage<TtsChineseCharactersTimbre>> findPage(int size, int current, TtsChineseCharactersTimbre ttsChineseCharactersTimbre) {
        TtsChineseCharactersTimbreDO ttsChineseCharactersTimbreDO = TtsChineseCharactersTimbreConverter.INSTANCE.fromTtsChineseCharactersTimbre(ttsChineseCharactersTimbre);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<TtsChineseCharactersTimbre> ttsChineseCharactersTimbreLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(ttsChineseCharactersTimbreDO), lazyPage, TtsChineseCharactersTimbre.class);
        return ResultFactory.successOf(ttsChineseCharactersTimbreLazyPage);
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

    @Override
    public Result<TtsChineseCharactersTimbre> remove(TtsChineseCharactersTimbre ttsChineseCharactersTimbre) {
        TtsChineseCharactersTimbreDO ttsChineseCharactersTimbreDO = TtsChineseCharactersTimbreConverter.INSTANCE.fromTtsChineseCharactersTimbre(ttsChineseCharactersTimbre);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(ttsChineseCharactersTimbreDO));
        return ResultFactory.successOf();
    }

    /**
     * 根据字段、音色查询音频信息
     *
     * @param wordList   文字
     * @param timbreCode 音频编码
     * @return 信息
     */
    @Override
    public List<TtsChineseCharactersTimbre> findListByWordsAndTimbreCode(List<String> wordList, String timbreCode) {
        return lazyLambdaStream.selectList(LazyWrappers.<TtsChineseCharactersTimbreDO>lambdaWrapper()
                .eq(TtsChineseCharactersTimbreDO::getIsDeleted,false)
                .eq(TtsChineseCharactersTimbreDO::getTimbreCode,timbreCode)
                .in(TtsChineseCharactersTimbreDO::getWord,wordList)
                , TtsChineseCharactersTimbre.class
        );
    }
}