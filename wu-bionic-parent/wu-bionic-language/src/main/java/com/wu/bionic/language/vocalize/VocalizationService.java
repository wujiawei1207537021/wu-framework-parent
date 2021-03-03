package com.wu.bionic.language.vocalize;

import com.sun.istack.NotNull;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
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
 * @author 吴佳伟
 * @date 2021/3/3 下午3:24
 */
@Service
public class VocalizationService implements Vocalization {
    private final LazyOperation lazyOperation;

    public VocalizationService(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
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
    public byte[] textToSpeech(@NotNull String text) {
        List<String> wordList = new ArrayList<>();
        for (char c : text.toCharArray()) {
            wordList.add("'" + String.valueOf(c) + "'");
        }
        List<EasyHashMap> easyHashMaps = lazyOperation.executeSQL(String.format("select voice,word from word where voice is NOT null and word in(%s) ", String.join(",", wordList)), EasyHashMap.class);
        Map<Object, EasyHashMap> word = easyHashMaps.stream().collect(Collectors.toMap(easyHashMap -> easyHashMap.get("word"), easyHashMap -> easyHashMap));
        byte[] bytes = new byte[0];
        for (String s : wordList) {
            EasyHashMap easyHashMap = word.get(s.replace("'",""));
            if (ObjectUtils.isEmpty(easyHashMap)) continue;
            bytes = addBytes(bytes, easyHashMap.getBytes("voice"));
        }
        return bytes;
    }

    @Override
    public void play(String text) {
        byte[] bytes = textToSpeech(text);
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

    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }
}
