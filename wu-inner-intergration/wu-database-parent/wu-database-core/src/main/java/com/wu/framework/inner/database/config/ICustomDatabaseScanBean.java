package com.wu.framework.inner.database.config;


import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 扫描xml
 * @date : 2020/9/5 上午2:29
 */

public interface ICustomDatabaseScanBean {

    List<Class> getScanBeanClasses();

    List<String> getScanXmlPath();

}
