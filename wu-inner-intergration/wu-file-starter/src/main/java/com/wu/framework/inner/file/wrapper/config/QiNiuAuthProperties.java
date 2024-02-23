package com.wu.framework.inner.file.wrapper.config;


import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "qiniu.file")
public class QiNiuAuthProperties {

    /**
     * 访问密钥
     */
    private String accessKey;
    /**
     * 密钥
     */
    private String secretKey;
    /**
     * 空间名
     */
    private String bucket;

    /**
     * 文件上传成功后服务器ip
     */
    private String accessPath;

    public String create() {
        return Auth.create(this.accessKey, this.secretKey).uploadToken(bucket);
    }
}
