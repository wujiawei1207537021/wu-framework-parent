package com.wu.framework.inner.db.config.pro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wu.db")
public class DBPageConfigPro {


    /**
     * 分页索引别名
     */
    private String pageAlias = "current";
    /**
     * 分页大小别名
     */
    private String sizeAlias = "size";

    private Integer pageNum = 1;

    private Integer pageSize = 10;


}
