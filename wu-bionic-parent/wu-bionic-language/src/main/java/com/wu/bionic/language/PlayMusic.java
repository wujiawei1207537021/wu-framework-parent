package com.wu.bionic.language;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author : Jia Wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/2/28 9:44 下午
 */
public class PlayMusic {


    public static void main(String[] args) throws Exception {
//        File file = new File("https://mp32.9ku.com/upload/128/2021/01/26/1015576.mp3");
        String path="/Users/wujiawei/Desktop/aa.mp3";
        FileInputStream fileau = new FileInputStream("/Users/wujiawei/Desktop/aa.mp3");
        AudioStream as = new AudioStream(fileau);
        AudioPlayer.player.start(as);
    }

}