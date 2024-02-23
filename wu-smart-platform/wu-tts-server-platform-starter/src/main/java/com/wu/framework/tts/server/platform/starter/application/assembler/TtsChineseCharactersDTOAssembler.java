package com.wu.framework.tts.server.platform.starter.application.assembler;

import com.wu.framework.tts.server.platform.starter.application.dto.TtsChineseCharactersDTO;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.TtsChineseCharacters;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersRemoveCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersStoryCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersUpdateCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersQueryListCommand;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.TtsChineseCharactersQueryOneCommand;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe tts 中文 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface TtsChineseCharactersDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
    TtsChineseCharactersDTOAssembler INSTANCE = Mappers.getMapper(TtsChineseCharactersDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharacters toTtsChineseCharacters(TtsChineseCharactersStoryCommand ttsChineseCharactersStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharacters toTtsChineseCharacters(TtsChineseCharactersUpdateCommand ttsChineseCharactersUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharacters toTtsChineseCharacters(TtsChineseCharactersQueryOneCommand ttsChineseCharactersQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharacters toTtsChineseCharacters(TtsChineseCharactersQueryListCommand ttsChineseCharactersQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharacters toTtsChineseCharacters(TtsChineseCharactersRemoveCommand ttsChineseCharactersRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/
     TtsChineseCharactersDTO fromTtsChineseCharacters(TtsChineseCharacters ttsChineseCharacters);
}