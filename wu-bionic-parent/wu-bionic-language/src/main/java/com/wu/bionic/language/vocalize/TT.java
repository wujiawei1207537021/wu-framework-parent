package com.wu.bionic.language.vocalize;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/2 8:35 下午
 */

import sun.audio.AudioPlayer;

import java.io.IOException;

public class TT {
    sun.audio.AudioStream as;

    public void music() throws IOException {
        try {
            java.io.InputStream in = new java.io.FileInputStream("/Users/wujiawei/Desktop/aa.mp3");
            as = new sun.audio.AudioStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Start() {
        AudioPlayer.player.start(as);
    }

    public void Pause() {
        AudioPlayer.player.stop(as);
    }

    public void Continue() {
        AudioPlayer.player.start(as);
    }

    public static void main(String[] args) {
        TT m = new TT();
        try {
            m.music();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        m.Start();
    }

}




