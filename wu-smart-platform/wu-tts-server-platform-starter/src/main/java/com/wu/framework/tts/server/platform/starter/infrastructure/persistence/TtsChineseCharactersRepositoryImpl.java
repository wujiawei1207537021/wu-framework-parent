package com.wu.framework.tts.server.platform.starter.infrastructure.persistence;

import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import com.wu.framework.tts.server.platform.starter.infrastructure.entity.TtsChineseCharactersDO;
import com.wu.framework.tts.server.platform.starter.infrastructure.converter.TtsChineseCharactersConverter;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.TtsChineseCharactersRepository;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.TtsChineseCharacters;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe tts 中文 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class TtsChineseCharactersRepositoryImpl  implements TtsChineseCharactersRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

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
    public Result<TtsChineseCharacters> story(TtsChineseCharacters ttsChineseCharacters) {
        TtsChineseCharactersDO ttsChineseCharactersDO = TtsChineseCharactersConverter.INSTANCE.fromTtsChineseCharacters(ttsChineseCharacters);
        // 查询汉字是否存在
        boolean exists = lazyLambdaStream.exists(LazyWrappers.<TtsChineseCharactersDO>lambdaWrapper().eq(TtsChineseCharactersDO::getIsDeleted, false)
                .eq(TtsChineseCharactersDO::getWord, ttsChineseCharacters.getWord()));
        if(exists){
            // 不允许修改
            RuntimeExceptionFactory.of("不允许修改");
        }
        lazyLambdaStream.upsertRemoveNull(ttsChineseCharactersDO);
        return ResultFactory.successOf();
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
    public Result<List<TtsChineseCharacters>> batchStory(List<TtsChineseCharacters> ttsChineseCharactersList) {
        List<TtsChineseCharactersDO> ttsChineseCharactersDOList = ttsChineseCharactersList.stream().map(TtsChineseCharactersConverter.INSTANCE::fromTtsChineseCharacters).collect(Collectors.toList());
        lazyLambdaStream.upsert(ttsChineseCharactersDOList);
        return ResultFactory.successOf();
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
    public Result<TtsChineseCharacters> findOne(TtsChineseCharacters ttsChineseCharacters) {
        TtsChineseCharactersDO ttsChineseCharactersDO = TtsChineseCharactersConverter.INSTANCE.fromTtsChineseCharacters(ttsChineseCharacters);
        TtsChineseCharacters ttsChineseCharactersOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(ttsChineseCharactersDO), TtsChineseCharacters.class);
        return ResultFactory.successOf(ttsChineseCharactersOne);
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
    public Result<List<TtsChineseCharacters>> findList(TtsChineseCharacters ttsChineseCharacters) {
        TtsChineseCharactersDO ttsChineseCharactersDO = TtsChineseCharactersConverter.INSTANCE.fromTtsChineseCharacters(ttsChineseCharacters);
        List<TtsChineseCharacters> ttsChineseCharactersList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(ttsChineseCharactersDO), TtsChineseCharacters.class);
        return ResultFactory.successOf(ttsChineseCharactersList);
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
    public Result<LazyPage<TtsChineseCharacters>> findPage(int size,int current,TtsChineseCharacters ttsChineseCharacters) {
        TtsChineseCharactersDO ttsChineseCharactersDO = TtsChineseCharactersConverter.INSTANCE.fromTtsChineseCharacters(ttsChineseCharacters);
        LazyPage lazyPage = new LazyPage(current,size);
        LazyPage<TtsChineseCharacters> ttsChineseCharactersLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(ttsChineseCharactersDO),lazyPage, TtsChineseCharacters.class);
        return ResultFactory.successOf(ttsChineseCharactersLazyPage);
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
    public Result<TtsChineseCharacters> remove(TtsChineseCharacters ttsChineseCharacters) {
        TtsChineseCharactersDO ttsChineseCharactersDO = TtsChineseCharactersConverter.INSTANCE.fromTtsChineseCharacters(ttsChineseCharacters);
          lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(ttsChineseCharactersDO));
        return ResultFactory.successOf();
    }

}