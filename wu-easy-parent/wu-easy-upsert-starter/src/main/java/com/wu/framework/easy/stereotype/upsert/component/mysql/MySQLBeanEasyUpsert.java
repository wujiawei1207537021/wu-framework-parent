package com.wu.framework.easy.stereotype.upsert.component.mysql;


import com.wu.framework.easy.stereotype.dynamic.toolkit.DynamicEasyUpsertDSContextHolder;
import com.wu.framework.easy.upsert.autoconfigure.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.component.MySQLEasyUpsertAbstract;
import com.wu.framework.easy.stereotype.upsert.SpringUpsertConfig;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertDS;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.inner.layer.data.UserConvertService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * description MySQL 使用bean 注入数据
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午10:22
 */
@Slf4j
@ConditionalOnBean(value = DataSource.class)
@EasyUpsertStrategy(value = EasyUpsertType.MySQL)
public class MySQLBeanEasyUpsert extends MySQLEasyUpsertAbstract implements IEasyUpsert, ApplicationListener<ContextRefreshedEvent> {


    private String primary;
    private Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

    public MySQLBeanEasyUpsert(UserConvertService userConvertService, SpringUpsertConfig springUpsertConfig) {
        super(userConvertService, springUpsertConfig);
    }

    @Override
    protected DataSource determineDataSource() {
        EasyUpsertDS easyUpsertDS = DynamicEasyUpsertDSContextHolder.peek();
        if (!ObjectUtils.isEmpty(easyUpsertDS) && dataSourceMap.containsKey(easyUpsertDS.name())) {
            return dataSourceMap.get(easyUpsertDS.name());
        }
        return dataSourceMap.get(primary);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        super.afterPropertiesSet();  run after onApplicationEvent when DataSource is already init
        log.info("mysql uses beans to inject ");

    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, DataSource> dataSourceMap = event.getApplicationContext().getBeansOfType(DataSource.class);
        this.primary = dataSourceMap.keySet().iterator().next();
        dataSourceMap.forEach((k, v) -> {
            try {
                mybatisDataSource(k, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        super.afterPropertiesSet();
    }

    /**
     * description mybatis数据源具体拆分
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/11 上午10:37
     */

    public void mybatisDataSource(String key, DataSource dataSource) throws Exception {
        if (dataSource.getClass().getName().equals("com.baomidou.dynamic.datasource.DynamicRoutingDataSource")) {
            Field primaryDeclaredField = dataSource.getClass().getDeclaredField("primary");
            primaryDeclaredField.setAccessible(true);
            primary = (String) primaryDeclaredField.get(dataSource);
            Field dataSourceMapDeclaredField = dataSource.getClass().getDeclaredField("dataSourceMap");
            dataSourceMapDeclaredField.setAccessible(true);
            Map<String, DataSource> mybatisDataSourceMap = (Map<String, DataSource>) dataSourceMapDeclaredField.get(dataSource);
            dataSourceMap.putAll(mybatisDataSourceMap);
        } else {
            dataSourceMap.put(key, dataSource);
        }
    }

}
