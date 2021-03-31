package com.wu.framework.easy.stereotype.upsert.component.mysql;


import com.wu.framework.easy.stereotype.dynamic.toolkit.DynamicEasyUpsertDSContextHolder;
import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.component.MySQLEasyUpsertAbstract;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertDS;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.ienum.UserConvertService;
import com.wu.framework.easy.stereotype.upsert.process.MySQLDataProcess;
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
@ConditionalOnBean(value = DataSource.class)
@EasyUpsertStrategy(value = EasyUpsertType.MySQL)
public class MySQLBeanEasyUpsert extends MySQLEasyUpsertAbstract implements IEasyUpsert, ApplicationListener<ContextRefreshedEvent> {


    private String primary;
    private Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

    public MySQLBeanEasyUpsert(UserConvertService userConvertService, UpsertConfig upsertConfig, MySQLDataProcess mySQLDataProcess) {
        super(userConvertService, upsertConfig, mySQLDataProcess);
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


    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, DataSource> dataSourceMap = event.getApplicationContext().getBeansOfType(DataSource.class);
        this.primary = dataSourceMap.keySet().iterator().next();
        dataSourceMap.forEach((k, v) -> {
            try {
                mybatisDataSource(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * description mybatis数据源具体拆分
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/2/11 上午10:37
     */

    public void mybatisDataSource(DataSource dataSource) throws Exception {
        if (dataSource.getClass().getName().equals("com.baomidou.dynamic.datasource.DynamicRoutingDataSource")) {
            Field primaryDeclaredField = dataSource.getClass().getDeclaredField("primary");
            primaryDeclaredField.setAccessible(true);
            primary = (String) primaryDeclaredField.get(dataSource);
            Field dataSourceMapDeclaredField = dataSource.getClass().getDeclaredField("dataSourceMap");
            dataSourceMapDeclaredField.setAccessible(true);
            Map<String, DataSource> mybatisDataSourceMap = (Map<String, DataSource>) dataSourceMapDeclaredField.get(dataSource);
            dataSourceMap.putAll(mybatisDataSourceMap);
        }

    }
}
