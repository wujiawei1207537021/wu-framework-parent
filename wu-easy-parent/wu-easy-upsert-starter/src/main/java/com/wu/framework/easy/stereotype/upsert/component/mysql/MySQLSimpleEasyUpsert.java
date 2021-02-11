package com.wu.framework.easy.stereotype.upsert.component.mysql;


import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.component.MySQLEasyUpsertAbstract;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.ienum.UserConvertService;
import com.wu.framework.easy.stereotype.upsert.process.MySQLDataProcess;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Conditional;

import javax.sql.DataSource;


/**
 * description MySQL HikariDataSource 单数据源插入数据
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午10:22
 */
@Slf4j
@ConditionalOnClass(name = "com.zaxxer.hikari.HikariDataSource")
@ConditionalOnMissingBean(MySQLBeanEasyUpsert.class)
@EasyUpsertStrategy(value = EasyUpsertType.MySQL)
public class MySQLSimpleEasyUpsert extends MySQLEasyUpsertAbstract implements IEasyUpsert {


    private final DataSource dataSource;
    private final MySQLDataProcess mySQLDataProcess;

    public MySQLSimpleEasyUpsert(DataSource dataSource, UserConvertService userConvertService, UpsertConfig upsertConfig, MySQLDataProcess mySQLDataProcess) {
        super(userConvertService, upsertConfig, mySQLDataProcess);
        this.dataSource = dataSource;
        this.mySQLDataProcess = mySQLDataProcess;
    }

    @Override
    protected DataSource determineDataSource() {
        return dataSource;
    }


}
