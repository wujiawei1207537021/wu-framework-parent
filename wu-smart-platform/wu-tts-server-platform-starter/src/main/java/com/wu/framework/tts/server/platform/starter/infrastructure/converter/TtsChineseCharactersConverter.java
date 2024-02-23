package com.wu.framework.tts.server.platform.starter.infrastructure.converter;

import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.TtsChineseCharacters;
import com.wu.framework.tts.server.platform.starter.infrastructure.entity.TtsChineseCharactersDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe tts 中文 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface TtsChineseCharactersConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
    TtsChineseCharactersConverter INSTANCE = Mappers.getMapper(TtsChineseCharactersConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
    TtsChineseCharacters toTtsChineseCharacters(TtsChineseCharactersDO ttsChineseCharactersDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharactersDO fromTtsChineseCharacters(TtsChineseCharacters ttsChineseCharacters); 
}