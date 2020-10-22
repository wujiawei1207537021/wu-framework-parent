package com.wu.framework.database.generator.config;

import com.wu.framework.database.generator.utils.DateUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/7/17 下午11:52
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wu.framework.generator")
public class GeneratorConfig {
    private String mainPath = "com.wu";
    private String packageName = "com.wu";
    private String moduleName = "module";
    private String author = "wujiawei";
    private String email;
    private String tablePrefix;
    private String dateTime = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
}
