package com.wu.framework.inner.lazy.database.expand.database.persistence.config;

import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jia wei Wu
 * description 导出数据配置
 * @date 2021/8/12$ 11:46 上午$
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@Data
@ConfigurationProperties(prefix = "spring.datasource")
@Configuration
public class ExportDataConfiguration implements InitializingBean {

    /**
     * 忽略导出的字段
     */
    private List<String> ignoreExportedFields;

    /**
     * 特殊字段
     */
    private List<String> specialFields = new ArrayList<>();

    public void setSpecialFields(List<String> specialFields) {
        this.specialFields.addAll(specialFields);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LazyDatabaseJsonMessage.specialFields.addAll(this.specialFields);
    }
}
