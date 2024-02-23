package com.wu.framework.inner.ftp.config;

import com.wu.framework.inner.ftp.connection.SimpleFtpConnectionFactory;
import com.wu.framework.inner.ftp.core.FtpTemplate;
import org.springframework.context.annotation.Bean;

public class FtpConnectionConfig {

    @Bean
    public FtpTemplate<String, String> ftpTemplate(FtpConfig ftpConfig) {
        return new SimpleFtpConnectionFactory(ftpConfig).build();
    }
}
