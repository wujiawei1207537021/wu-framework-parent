package com.wu.framework.easy.stereotype.upsert.component.mysql;


import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.ienum.UserDictionaryService;
import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.component.MySQLEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import javax.sql.DataSource;

/**
 * description MySQL单数据源插入数据
 *
 * @author 吴佳伟
 * @date 2020/9/11 上午10:22
 */
@Slf4j
//@ConditionalOnBean(DataSource.class)
@ConditionalOnClass(name = "javax.sql.DataSource")
@ConditionalOnMissingBean(MySQLMultipleEasyUpsert.class)
@EasyUpsertStrategy(value = EasyUpsertType.MySQL)
public class MySQLSimpleEasyUpsert extends MySQLEasyUpsert implements IEasyUpsert, InitializingBean {


    private final DataSource dataSource;
    private final UserDictionaryService userDictionaryService;
    private final UpsertConfig upsertConfig;

    public MySQLSimpleEasyUpsert(DataSource dataSource, UserDictionaryService userDictionaryService, UpsertConfig upsertConfig) {
        super(userDictionaryService,upsertConfig);
        this.dataSource = dataSource;
        this.upsertConfig=upsertConfig;
        this.userDictionaryService=userDictionaryService;
    }

    @Override
    protected DataSource determineDataSource() {
        return dataSource;
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
