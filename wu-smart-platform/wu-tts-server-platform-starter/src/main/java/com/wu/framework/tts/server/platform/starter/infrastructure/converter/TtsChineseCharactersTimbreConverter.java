package com.wu.framework.tts.server.platform.starter.infrastructure.converter;

import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.timbre.TtsChineseCharactersTimbre;
import com.wu.framework.tts.server.platform.starter.infrastructure.entity.TtsChineseCharactersTimbreDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe tts 中文对应制定音色数据 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface TtsChineseCharactersTimbreConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
    TtsChineseCharactersTimbreConverter INSTANCE = Mappers.getMapper(TtsChineseCharactersTimbreConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
    TtsChineseCharactersTimbre toTtsChineseCharactersTimbre(TtsChineseCharactersTimbreDO ttsChineseCharactersTimbreDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharactersTimbreDO fromTtsChineseCharactersTimbre(TtsChineseCharactersTimbre ttsChineseCharactersTimbre); 
}