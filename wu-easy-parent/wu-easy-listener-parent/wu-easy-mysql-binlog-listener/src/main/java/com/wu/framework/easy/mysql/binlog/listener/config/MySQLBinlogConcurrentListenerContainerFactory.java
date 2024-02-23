package com.wu.framework.easy.mysql.binlog.listener.config;


import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.wu.framework.easy.listener.core.config.ConcurrentListenerContainerFactory;
import com.wu.framework.easy.listener.properties.SpringBootMysqlBinLogConfigProperties;
import com.wu.framework.easy.mysql.binlog.listener.listener.MySQLBinlogConcurrentMessageListenerContainer;
import com.wu.framework.easy.mysql.binlog.listener.util.DataSourceUrlParsingUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.*;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;


/**
 * @author wujiawei
 */
public class MySQLBinlogConcurrentListenerContainerFactory<K, V> implements
        ConcurrentListenerContainerFactory<MySQLBinlogConcurrentMessageListenerContainer<K, V>, MethodMySQLBinlogBinlogListenerEndpoint, K, V> {

    protected BeanFactory beanFactory;
    /**
     * host_port as key
     */
    private ConcurrentHashMap<String, BinaryLogClient> binaryLogClientConcurrentHashMap = new ConcurrentHashMap();

    @Override
    public MySQLBinlogConcurrentMessageListenerContainer<K, V> createListenerContainer(MethodMySQLBinlogBinlogListenerEndpoint endpoint) {

        final MySQLBinlogConcurrentMessageListenerContainer<K, V> container = new MySQLBinlogConcurrentMessageListenerContainer<K, V>();
        container.setConcurrency(endpoint.getConcurrency());

        BinaryLogClient client;
        // 注解中的数据连接对象
        String username = endpoint.getUsername();
        String password = endpoint.getPassword();
        long serverId = endpoint.getServerId();
        String host = endpoint.getHost();
        int port;
        SpringBootMysqlBinLogConfigProperties binLogProperties = beanFactory.getBean(SpringBootMysqlBinLogConfigProperties.class);
        if (ObjectUtils.isEmpty(host)) {
            // 获取配置文件 初始化连接对象
            username = binLogProperties.getUsername();
            password = binLogProperties.getPassword();
            String url = binLogProperties.getUrl();
            serverId = binLogProperties.getServerId();
            host = DataSourceUrlParsingUtil.host(url);
            port = DataSourceUrlParsingUtil.port(url);
        } else {
            port = Integer.parseInt(endpoint.getPort());
        }

        // 设置默认schema
        String schema = endpoint.getSchema();
        if (ObjectUtils.isEmpty(schema)) {
            String url = binLogProperties.getUrl();
            schema = DataSourceUrlParsingUtil.schema(url);
        }
        endpoint.setSchema(schema);
        String key = host + "_" + port;
        if (binaryLogClientConcurrentHashMap.contains(key)) {
            client = binaryLogClientConcurrentHashMap.get(key);
        } else {
            client = new BinaryLogClient(host, port, username, password);
            client.setServerId(serverId);
            binaryLogClientConcurrentHashMap.put(key, client);
        }


        TableAdapter tableAdapter = beanFactory.getBean(TableAdapter.class);
        container.setBinaryLogClient(client);
        container.setTableAdapter(tableAdapter);

        container.setEndpoint(endpoint);
        return container;
    }

    @Override
    public MySQLBinlogConcurrentMessageListenerContainer<K, V> createContainer(String... tables) {
        return null;
    }

    @Override
    public MySQLBinlogConcurrentMessageListenerContainer<K, V> createContainer(Pattern topicPattern) {
        return null;
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

    }

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} put
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    /**
     * Set the ApplicationEventPublisher that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param applicationEventPublisher event publisher to be used by this object
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
     *
     * @param beanFactory owning BeanFactory (never {@code null}).
     *                    The bean can immediately call methods on the factory.
     * @throws BeansException in case of initialization errors
     * @see BeanInitializationException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
