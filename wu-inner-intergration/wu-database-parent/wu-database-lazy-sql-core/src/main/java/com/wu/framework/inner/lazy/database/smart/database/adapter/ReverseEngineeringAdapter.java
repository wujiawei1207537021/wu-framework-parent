package com.wu.framework.inner.lazy.database.smart.database.adapter;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperation;
import com.wu.framework.inner.lazy.database.util.LazyDataSourceUtils;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * describe : 逆向工程 适配器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/23 8:58 下午
 */
@ConditionalOnBean(DataSource.class)
@ConditionalOnProperty(prefix = LazyOperationConfig.LAZY_OPERATION_CONFIG_PREFIX, name = "enable-reverse-engineering", havingValue = "true")
public class ReverseEngineeringAdapter implements InitializingBean {

    private final DataSource dataSource;
    private final SmartLazyOperation smartLazyOperation;

    public ReverseEngineeringAdapter(DataSource dataSource, SmartLazyOperation smartLazyOperation) {
        this.dataSource = dataSource;
        this.smartLazyOperation = smartLazyOperation;
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
        Connection connection = LazyDataSourceUtils.getConnection(dataSource);
        String catalog = connection.getCatalog();
        catalog = ObjectUtils.isEmpty(catalog) ? connection.getSchema() : catalog;
        // 适配 spring mvc
        try {
            if (catalog == null) {
                return;
            }
            // 多线程
            smartLazyOperation.showTables(catalog).stream().parallel().forEach(showTable -> smartLazyOperation.stuffedJava(showTable.getTableSchema(), showTable.getTableName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
