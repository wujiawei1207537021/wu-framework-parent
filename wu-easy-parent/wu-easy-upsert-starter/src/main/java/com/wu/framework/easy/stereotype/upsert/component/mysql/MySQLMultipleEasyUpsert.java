package com.wu.framework.easy.stereotype.upsert.component.mysql;


import com.wu.framework.easy.stereotype.dynamic.toolkit.DynamicEasyUpsertDSContextHolder;
import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.component.MySQLEasyUpsertAbstract;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertDS;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.ienum.UserConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * description MySQL多个据源插入数据
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午10:22
 */
@Slf4j
@ConditionalOnClass(name = "com.baomidou.dynamic.datasource.DynamicRoutingDataSource")
@EasyUpsertStrategy(value = EasyUpsertType.MySQL)
public class MySQLMultipleEasyUpsert extends MySQLEasyUpsertAbstract implements IEasyUpsert, InitializingBean {


    private final DataSource dataSource;
    private final UserConvertService userConvertService;
    private final UpsertConfig upsertConfig;
    private String primary;
    private Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

    public MySQLMultipleEasyUpsert(DataSource dataSource, UserConvertService userConvertService, UpsertConfig upsertConfig) {
        super(userConvertService, upsertConfig);
        this.dataSource = dataSource;
        this.upsertConfig = upsertConfig;
        this.userConvertService = userConvertService;

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
        Field primaryDeclaredField = dataSource.getClass().getDeclaredField("primary");
        primaryDeclaredField.setAccessible(true);
        primary = (String) primaryDeclaredField.get(dataSource);
        Field dataSourceMapDeclaredField = dataSource.getClass().getDeclaredField("dataSourceMap");
        dataSourceMapDeclaredField.setAccessible(true);
        dataSourceMap = (Map<String, DataSource>) dataSourceMapDeclaredField.get(dataSource);
    }
}
