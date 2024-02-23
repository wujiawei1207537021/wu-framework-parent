package com.wu.framework.tts.server.platform.starter.application.assembler;

import com.wu.framework.tts.server.platform.starter.application.dto.TtsChineseCharactersTimbreDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.timbre.TtsChineseCharactersTimbre;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.TtsChineseCharactersTimbreRemoveCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.TtsChineseCharactersTimbreStoryCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.TtsChineseCharactersTimbreUpdateCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.TtsChineseCharactersTimbreQueryListCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.TtsChineseCharactersTimbreQueryOneCommand;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe tts 中文对应制定音色数据 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface TtsChineseCharactersTimbreDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
    TtsChineseCharactersTimbreDTOAssembler INSTANCE = Mappers.getMapper(TtsChineseCharactersTimbreDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharactersTimbre toTtsChineseCharactersTimbre(TtsChineseCharactersTimbreStoryCommand ttsChineseCharactersTimbreStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharactersTimbre toTtsChineseCharactersTimbre(TtsChineseCharactersTimbreUpdateCommand ttsChineseCharactersTimbreUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharactersTimbre toTtsChineseCharactersTimbre(TtsChineseCharactersTimbreQueryOneCommand ttsChineseCharactersTimbreQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharactersTimbre toTtsChineseCharactersTimbre(TtsChineseCharactersTimbreQueryListCommand ttsChineseCharactersTimbreQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharactersTimbre toTtsChineseCharactersTimbre(TtsChineseCharactersTimbreRemoveCommand ttsChineseCharactersTimbreRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharactersTimbreDTO fromTtsChineseCharactersTimbre(TtsChineseCharactersTimbre ttsChineseCharactersTimbre);
}