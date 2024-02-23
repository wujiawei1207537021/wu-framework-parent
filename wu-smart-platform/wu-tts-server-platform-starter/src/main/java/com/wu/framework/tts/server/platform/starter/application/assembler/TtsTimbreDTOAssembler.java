package com.wu.framework.tts.server.platform.starter.application.assembler;

import com.wu.framework.tts.server.platform.starter.application.dto.TtsTimbreDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.timbre.TtsTimbre;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreRemoveCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreStoryCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreUpdateCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreQueryListCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.timbre.TtsTimbreQueryOneCommand;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe tts 音色 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface TtsTimbreDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
    TtsTimbreDTOAssembler INSTANCE = Mappers.getMapper(TtsTimbreDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsTimbre toTtsTimbre(TtsTimbreStoryCommand ttsTimbreStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsTimbre toTtsTimbre(TtsTimbreUpdateCommand ttsTimbreUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsTimbre toTtsTimbre(TtsTimbreQueryOneCommand ttsTimbreQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsTimbre toTtsTimbre(TtsTimbreQueryListCommand ttsTimbreQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsTimbre toTtsTimbre(TtsTimbreRemoveCommand ttsTimbreRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsTimbreDTO fromTtsTimbre(TtsTimbre ttsTimbre);
}