package com.wu.bionic.language.vocalize;

import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import javazoom.jl.player.Player;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author :  Jia Wei Wu
 * @version 1.0
 * @describe : 音乐
 * @date : 2021/3/2 7:45 下午
 */
@Component
public class Music implements CommandLineRunner {

    private final LazyOperation lazyOperation;

    public Music(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    @Override
    public void run(String... args) throws Exception {
        EasyHashMap easyHashMap = lazyOperation.executeSQLForBean("SELECT * FROM upsert_binary limit 1", EasyHashMap.class);

        byte[] file = easyHashMap.getBytes("file");
        Thread thread = new Thread() {
            private Player player;

            @SneakyThrows
            @Override
            public void run() {
                BufferedInputStream buffer = new BufferedInputStream(new ByteArrayInputStream(file));
                player = new Player(buffer);
                player.play();
            }
        };
        thread.start();

    }
}
