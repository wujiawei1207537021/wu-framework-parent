package com.wu.framework.inner.lazy.database.expand.database.persistence.factory.config;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.dynamic.LazyDynamicAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.util.DataSourceUrlParsingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * description Lazy schema 自动创建数据库
 *
 * @author Jia wei Wu
 * @date 2022/12/14 4:15 下午
 */
@Slf4j
@AutoConfigureAfter(LazyDataSourceProperties.class)
@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(prefix = LazyOperationConfig.LAZY_OPERATION_CONFIG_PREFIX, value = "enable-auto-schema", havingValue = "true")
@ConditionalOnBean(LazyDataSourceProperties.class)
public class LazySchemaAutoCreateProcessor implements BeanPostProcessor, Ordered, InitializingBean {

    private final LazyDataSourceProperties lazyDataSourceProperties;
    private final LazyDynamicAdapter lazyDynamicAdapter;

    public LazySchemaAutoCreateProcessor(LazyDataSourceProperties lazyDataSourceProperties, LazyDynamicAdapter lazyDynamicAdapter) {
        this.lazyDataSourceProperties = lazyDataSourceProperties;
        this.lazyDynamicAdapter = lazyDynamicAdapter;
    }

    /**
     * 创建数据库
     */
    public void create() {

        String url = lazyDataSourceProperties.getUrl();
        String username = lazyDataSourceProperties.getUsername();
        String password = lazyDataSourceProperties.getPassword();
        Class<? extends DataSource> type = lazyDataSourceProperties.getType();

//        String schema = DataSourceUrlParsingUtil.schema(url);
        String defaultInformationSchemaUrl = SourceFactory.getDefaultInformationSchemaUrl(url);
        if(ObjectUtils.isEmpty(defaultInformationSchemaUrl)){
            return;
        }
        String tempDataSourceId = UUID.randomUUID().toString();
//        // 解析数据库url信息
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(defaultInformationSchemaUrl, username, password, type,tempDataSourceId);

//        String url = lazyDataSourceProperties.getUrl();
        String schema = SourceFactory.getUrlSchema(url);
//        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(lazyDataSourceProperties);
        // 创建数据库连接
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        String format = "CREATE  DATABASE IF NOT EXISTS `%s` CHARACTER SET utf8mb4 ;";
        persistenceRepository.setResultClass(Boolean.class);
        persistenceRepository.setExecutionType(LambdaTableType.CREATE);
        persistenceRepository.setQueryString(String.format(format, schema));
        lazyLambdaStream.executeOne(persistenceRepository);
        log.info("初始化创建数据库:【{}】", schema);
        // 关闭数据库dataSource
        lazyDynamicAdapter.closeDataSource(tempDataSourceId);


    }

    /**
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties
     * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an
     *                   essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            create();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("自动治愈数据库失败");
        }
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
