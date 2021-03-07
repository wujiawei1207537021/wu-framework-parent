package com.wu.bionic.language.vocalize;

import com.wu.framework.easy.stereotype.upsert.converter.stereotype.Word;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;

import java.util.List;

/**
 * @author : Jia Wei Wu
 * @version 1.0
 * @describe : 发声
 * @date : 2021/3/2 7:41 下午
 */

public interface Vocalization {

    /**
     * description 文本转换成语音
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia Wei Wu
     * @date 2021/3/3 下午3:23
     */
    byte[] textToByte(String text);

    void play(String text);

    List<Word> voiceData();
}
