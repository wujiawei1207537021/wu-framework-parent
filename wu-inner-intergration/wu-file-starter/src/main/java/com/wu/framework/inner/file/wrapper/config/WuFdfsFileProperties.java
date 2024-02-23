package com.wu.framework.inner.file.wrapper.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * accesspath: ${fdfs.file.accesspath}
 */
@Data
@Component
@ConfigurationProperties(prefix = "fdfs.file")
public class WuFdfsFileProperties extends WuFileProperties {
    /**
     * 文件上传成功后服务器ip
     */
    private String accessPath;
}
