package com.wu.framework.easy.stereotype.upsert.component.mysql;


import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.component.MySQLEasyUpsertAbstract;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.ienum.UserDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import javax.sql.DataSource;

/**
 * description MySQL单数据源插入数据
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午10:22
 */
@Slf4j
@ConditionalOnBean(DataSource.class)
@ConditionalOnMissingBean(MySQLMultipleEasyUpsert.class)
@EasyUpsertStrategy(value = EasyUpsertType.MySQL)
public class MySQLSimpleEasyUpsert extends MySQLEasyUpsertAbstract implements IEasyUpsert {


    private final DataSource dataSource;

    public MySQLSimpleEasyUpsert(DataSource dataSource, UserDictionaryService userDictionaryService, UpsertConfig upsertConfig) {
        super(userDictionaryService, upsertConfig);
        this.dataSource = dataSource;
    }

    @Override
    protected DataSource determineDataSource() {
        return dataSource;
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
