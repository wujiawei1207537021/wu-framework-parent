package com.wu.framework.inner.lazy.database.expand.database.persistence.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * @author 吴佳伟
 * @description 导出数据配置
 * @date 2021/8/12$ 11:46 上午$
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@Data
@ConfigurationProperties(prefix = "spring.datasource")
@Configuration
public class ExportDataConfiguration {

    /**
     * 忽略导出的字段
     */
    private List<String> ignoreExportedFields;


}
