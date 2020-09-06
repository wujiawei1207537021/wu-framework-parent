package com.wu.framework.inner.database.config;


import org.springframework.beans.factory.InitializingBean;


/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 数据库连接配置
 * @date : 2020/6/25 下午10:57
 */

public interface ICustomDatabaseConfiguration extends InitializingBean {


    Class getDriver();

    String getUrl();

    String getUsername();

    String getPassword();


    default DDLAuto getDdlAuto() {
        return DDLAuto.NONE;
    }


    enum DDLAuto {
        NONE,
        UPDATE,
        CREATE;
    }


}
