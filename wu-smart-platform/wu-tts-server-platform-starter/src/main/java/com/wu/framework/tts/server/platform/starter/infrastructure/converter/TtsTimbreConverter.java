package com.wu.framework.tts.server.platform.starter.infrastructure.converter;

import com.wu.framework.tts.server.platform.starter.domain.model.tts.timbre.TtsTimbre;
import com.wu.framework.tts.server.platform.starter.infrastructure.entity.TtsTimbreDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe tts 音色 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface TtsTimbreConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
    TtsTimbreConverter INSTANCE = Mappers.getMapper(TtsTimbreConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
    TtsTimbre toTtsTimbre(TtsTimbreDO ttsTimbreDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsTimbreDO fromTtsTimbre(TtsTimbre ttsTimbre); 
}