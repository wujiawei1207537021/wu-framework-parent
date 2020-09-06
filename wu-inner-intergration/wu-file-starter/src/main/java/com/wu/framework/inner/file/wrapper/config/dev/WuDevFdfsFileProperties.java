package com.wu.framework.inner.file.wrapper.config.dev;


import com.wu.framework.inner.file.wrapper.config.WuFileProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "dev.fdfs.file")
public class WuDevFdfsFileProperties extends WuFileProperties {
    /**
     * 文件上传成功后服务器ip
     */
    private String accessPath;

}
