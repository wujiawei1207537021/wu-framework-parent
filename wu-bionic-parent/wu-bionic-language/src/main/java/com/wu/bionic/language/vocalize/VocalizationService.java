package com.wu.bionic.language.vocalize;


import com.wu.framework.easy.stereotype.upsert.converter.stereotype.ChineseCharacters;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import javazoom.jl.player.Player;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2021/3/3 下午3:24
 */
@Service
public class VocalizationService implements Vocalization {
    private final LazyLambdaStream lazyLambdaStream;


    public VocalizationService(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

    /**
     * description 文本转换成语音
     *
     * @param text@return
     * @exception/throws
     * @author Jia Wei Wu
     * @date 2021/3/3 下午3:23
     */
    @Override
    public byte[] textToByte(String text) {
        List<String> wordList = new ArrayList<>();
        for (char c : text.toCharArray()) {
            wordList.add("'" + c + "'");
        }
        List<ChineseCharacters> easyHashMaps = lazyLambdaStream.executeSQL(String.format("select voice,word from chinese_characters where voice is NOT null and word in(%s) ", String.join(",", wordList)), ChineseCharacters.class);

        Map<Object, ChineseCharacters> word = easyHashMaps.stream().collect(Collectors.toMap(easyHashMap -> easyHashMap.getWord(), easyHashMap -> easyHashMap));
        byte[] bytes = new byte[0];
        for (String s : wordList) {
            ChineseCharacters easyHashMap = word.get(s.replace("'", ""));
            if (ObjectUtils.isEmpty(easyHashMap)) {
                continue;
            }
            byte[] easyHashMapBytes = easyHashMap.getVoice();
//             easyHashMapBytes=subByte(easyHashMapBytes,100,easyHashMapBytes.length-100);
            bytes = addBytes(bytes, easyHashMapBytes);
        }
        return bytes;
    }

    @Override
    public void play(String text) {
        byte[] bytes = textToByte(text);
        Thread thread = new Thread() {
            private Player player;

            @SneakyThrows
            @Override
            public void run() {
                BufferedInputStream buffer = new BufferedInputStream(new ByteArrayInputStream(bytes));
                player = new Player(buffer);
                player.play();
            }
        };
        thread.start();
    }

    @Override
    public List<ChineseCharacters> voiceData() {
        final List<ChineseCharacters> charactersList = lazyLambdaStream.executeSQL("select * from word limit 10", ChineseCharacters.class);
        return charactersList;
    }

    /**
     * 截取byte数组   不改变原数组
     *
     * @param b      原数组
     * @param off    偏差值（索引）
     * @param length 长度
     * @return 截取后的数组
     */
    public byte[] subByte(byte[] b, int off, int length) {
        byte[] b1 = new byte[length];
        System.arraycopy(b, off, b1, 0, length);
        return b1;
    }

}
