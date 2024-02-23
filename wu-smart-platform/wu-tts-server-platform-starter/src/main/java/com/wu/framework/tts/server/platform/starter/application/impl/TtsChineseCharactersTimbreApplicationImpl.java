package com.wu.framework.tts.server.platform.starter.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.tts.server.platform.starter.application.assembler.TtsChineseCharactersTimbreDTOAssembler;
import com.wu.framework.tts.server.platform.starter.application.command.tts.chinese.characters.timbre.*;
import com.wu.framework.tts.server.platform.starter.application.dto.TtsChineseCharactersTimbreDTO;
import com.wu.framework.tts.server.platform.starter.application.TtsChineseCharactersTimbreApplication;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.timbre.TtsChineseCharactersTimbre;
import com.wu.framework.tts.server.platform.starter.domain.model.tts.chinese.characters.timbre.TtsChineseCharactersTimbreRepository;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import org.springframework.util.ObjectUtils;

/**
 * describe tts 中文对应制定音色数据 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class TtsChineseCharactersTimbreApplicationImpl implements TtsChineseCharactersTimbreApplication {

    @Autowired
    TtsChineseCharactersTimbreRepository ttsChineseCharactersTimbreRepository;
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
    public Result<TtsChineseCharactersTimbre> story(TtsChineseCharactersTimbreStoryCommand ttsChineseCharactersTimbreStoryCommand) {
        TtsChineseCharactersTimbre ttsChineseCharactersTimbre = TtsChineseCharactersTimbreDTOAssembler.INSTANCE.toTtsChineseCharactersTimbre(ttsChineseCharactersTimbreStoryCommand);
        return ttsChineseCharactersTimbreRepository.story(ttsChineseCharactersTimbre);
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
    public Result<List<TtsChineseCharactersTimbre>> batchStory(List<TtsChineseCharactersTimbreStoryCommand> ttsChineseCharactersTimbreStoryCommandList) {
        List<TtsChineseCharactersTimbre> ttsChineseCharactersTimbreList = ttsChineseCharactersTimbreStoryCommandList.stream().map( TtsChineseCharactersTimbreDTOAssembler.INSTANCE::toTtsChineseCharactersTimbre).collect(Collectors.toList());
        return ttsChineseCharactersTimbreRepository.batchStory(ttsChineseCharactersTimbreList);
    }
    /**
     * describe 更新tts 中文对应制定音色数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/18 07:22 晚上
     **/

    @Override
    public Result<TtsChineseCharactersTimbre> updateOne(TtsChineseCharactersTimbreUpdateCommand ttsChineseCharactersTimbreUpdateCommand) {
        TtsChineseCharactersTimbre ttsChineseCharactersTimbre = TtsChineseCharactersTimbreDTOAssembler.INSTANCE.toTtsChineseCharactersTimbre(ttsChineseCharactersTimbreUpdateCommand);
        return ttsChineseCharactersTimbreRepository.story(ttsChineseCharactersTimbre);
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
    public Result<TtsChineseCharactersTimbreDTO> findOne(TtsChineseCharactersTimbreQueryOneCommand ttsChineseCharactersTimbreQueryOneCommand) {
        TtsChineseCharactersTimbre ttsChineseCharactersTimbre = TtsChineseCharactersTimbreDTOAssembler.INSTANCE.toTtsChineseCharactersTimbre(ttsChineseCharactersTimbreQueryOneCommand);
        return ttsChineseCharactersTimbreRepository.findOne(ttsChineseCharactersTimbre).convert(TtsChineseCharactersTimbreDTOAssembler.INSTANCE::fromTtsChineseCharactersTimbre);
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
    public Result<List<TtsChineseCharactersTimbreDTO>> findList(TtsChineseCharactersTimbreQueryListCommand ttsChineseCharactersTimbreQueryListCommand) {
        TtsChineseCharactersTimbre ttsChineseCharactersTimbre = TtsChineseCharactersTimbreDTOAssembler.INSTANCE.toTtsChineseCharactersTimbre(ttsChineseCharactersTimbreQueryListCommand);
        return ttsChineseCharactersTimbreRepository.findList(ttsChineseCharactersTimbre)        .convert(ttsChineseCharactersTimbres -> ttsChineseCharactersTimbres.stream().map(TtsChineseCharactersTimbreDTOAssembler.INSTANCE::fromTtsChineseCharactersTimbre).collect(Collectors.toList())) ;
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
    public Result<LazyPage<TtsChineseCharactersTimbreDTO>> findPage(int size,int current,TtsChineseCharactersTimbreQueryListCommand ttsChineseCharactersTimbreQueryListCommand) {
        TtsChineseCharactersTimbre ttsChineseCharactersTimbre = TtsChineseCharactersTimbreDTOAssembler.INSTANCE.toTtsChineseCharactersTimbre(ttsChineseCharactersTimbreQueryListCommand);
        return ttsChineseCharactersTimbreRepository.findPage(size,current,ttsChineseCharactersTimbre)        .convert(page -> page.convert(TtsChineseCharactersTimbreDTOAssembler.INSTANCE::fromTtsChineseCharactersTimbre))            ;
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
    public Result<TtsChineseCharactersTimbre> remove(TtsChineseCharactersTimbreRemoveCommand ttsChineseCharactersTimbreRemoveCommand) {
     TtsChineseCharactersTimbre ttsChineseCharactersTimbre = TtsChineseCharactersTimbreDTOAssembler.INSTANCE.toTtsChineseCharactersTimbre(ttsChineseCharactersTimbreRemoveCommand);
     return ttsChineseCharactersTimbreRepository.remove(ttsChineseCharactersTimbre);
    }
    /**
     * 文本转换成语音文件
     *
     * @param ttsChineseCharactersTimbreQueryTextToBytesCommand 参数
     * @return
     */
    @Override
    public byte[] textToBytes(TtsChineseCharactersTimbreQueryTextToBytesCommand ttsChineseCharactersTimbreQueryTextToBytesCommand) {

        String text = ttsChineseCharactersTimbreQueryTextToBytesCommand.getText();

        String timbreCode = ttsChineseCharactersTimbreQueryTextToBytesCommand.getTimbreCode();
        List<String> wordList = new ArrayList<>();
        for (char c : text.toCharArray()) {
            wordList.add( c+"");
        }
        List<TtsChineseCharactersTimbre> ttsChineseCharactersTimbreList= ttsChineseCharactersTimbreRepository.findListByWordsAndTimbreCode(wordList,timbreCode);


        Map<Object, TtsChineseCharactersTimbre> word = ttsChineseCharactersTimbreList.stream().collect(Collectors.toMap(TtsChineseCharactersTimbre::getWord, Function.identity()));
        byte[] bytes = new byte[0];
        for (String s : wordList) {
            TtsChineseCharactersTimbre ttsChineseCharactersTimbre = word.get(s.replace("'", ""));
            if (ObjectUtils.isEmpty(ttsChineseCharactersTimbre)) {
                continue;
            }
            byte[] timbreVoiceBytes = ttsChineseCharactersTimbre.getVoice();
//             easyHashMapBytes=subByte(easyHashMapBytes,100,easyHashMapBytes.length-100);
            bytes = addBytes(bytes, timbreVoiceBytes);
        }
        return bytes;
    }


    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

}