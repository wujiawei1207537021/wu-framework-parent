package com.wu.framework.inner.ftp.controller;

import com.wu.framework.inner.ftp.util.FTPUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description
 * @Author 吴佳伟
 * @Date 2020-06-12 4:24 下午
 */
@Component
public class TestController implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        FTPClient ftpClient = FTPUtils.loginFTP("218.85.66.151", 60022, "zzftp", "mSeG2Zykt20!");
        for (FTPFile ftpFile : ftpClient.listFiles()) {
            System.out.println(ftpFile.getName());
        }

    }
}
