package com.wu.framework.tts.server.platform.starter.infrastructure.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.timbre.TtsTimbre;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.timbre.TtsTimbreRepository;
import com.wu.framework.tts.server.platform.starter.infrastructure.converter.TtsTimbreConverter;
import com.wu.framework.tts.server.platform.starter.infrastructure.entity.TtsChineseCharactersDO;
import com.wu.framework.tts.server.platform.starter.infrastructure.entity.TtsChineseCharactersTimbreDO;
import com.wu.framework.tts.server.platform.starter.infrastructure.entity.TtsTimbreDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
/**
 * describe tts 音色 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class TtsTimbreRepositoryImpl  implements TtsTimbreRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

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
    public Result<TtsTimbre> story(TtsTimbre ttsTimbre) {
        TtsTimbreDO ttsTimbreDO = TtsTimbreConverter.INSTANCE.fromTtsTimbre(ttsTimbre);
        lazyLambdaStream.upsert(ttsTimbreDO);
        return ResultFactory.successOf();
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
    public Result<List<TtsTimbre>> batchStory(List<TtsTimbre> ttsTimbreList) {
        List<TtsTimbreDO> ttsTimbreDOList = ttsTimbreList.stream().map(TtsTimbreConverter.INSTANCE::fromTtsTimbre).collect(Collectors.toList());
        lazyLambdaStream.upsert(ttsTimbreDOList);
        return ResultFactory.successOf();
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
    public Result<TtsTimbre> findOne(TtsTimbre ttsTimbre) {
        TtsTimbreDO ttsTimbreDO = TtsTimbreConverter.INSTANCE.fromTtsTimbre(ttsTimbre);
        TtsTimbre ttsTimbreOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(ttsTimbreDO), TtsTimbre.class);
        return ResultFactory.successOf(ttsTimbreOne);
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
    public Result<List<TtsTimbre>> findList(TtsTimbre ttsTimbre) {
        TtsTimbreDO ttsTimbreDO = TtsTimbreConverter.INSTANCE.fromTtsTimbre(ttsTimbre);
        List<TtsTimbre> ttsTimbreList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(ttsTimbreDO), TtsTimbre.class);
        return ResultFactory.successOf(ttsTimbreList);
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
    public Result<LazyPage<TtsTimbre>> findPage(int size,int current,TtsTimbre ttsTimbre) {
        TtsTimbreDO ttsTimbreDO = TtsTimbreConverter.INSTANCE.fromTtsTimbre(ttsTimbre);
        LazyPage lazyPage = new LazyPage(current,size);
        LazyPage<TtsTimbre> ttsTimbreLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(ttsTimbreDO),lazyPage, TtsTimbre.class);
        return ResultFactory.successOf(ttsTimbreLazyPage);
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
    public Result<TtsTimbre> remove(TtsTimbre ttsTimbre) {
        TtsTimbreDO ttsTimbreDO = TtsTimbreConverter.INSTANCE.fromTtsTimbre(ttsTimbre);
          lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(ttsTimbreDO));
        return ResultFactory.successOf();
    }

}