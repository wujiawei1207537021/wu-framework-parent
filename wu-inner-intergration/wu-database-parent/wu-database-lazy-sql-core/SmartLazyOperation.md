### SmartLazyOperation 使用

### 使用依赖

```xml
        <dependency>
            <groupId>top.wu2020</groupId>
            <artifactId>wu-database-lazy-starter</artifactId>
            <version>latest</version>
        </dependency>
```

### 配置文件

```yml

spring:
  lazy:
    enable-reverse-engineering: true  # 允许逆向工程
    reverse-engineering:
      enable-lazy: false   # 不允许lazy系列注解
      enable-lombok-accessors: false # 不允许 lombok.accessors
      enable-lombok-data: false  # 不允许 lombok.data
      package-name: com.wu.lazy  # 包名
      enable-swagger: false  # 不允许 swagger
```

### 使用

```java
package com.wu.freamwork;

import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperation;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * describe : SmartLazyOperation 使用 测试
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/23 5:25 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class SmartLazyOperationTest {

    @Autowired
    SmartLazyOperation smartLazyOperation;

    @Autowired
    PerfectLazyOperation perfectLazyOperation;

    /**
     * 导出 sql文件
     */
    @Test
    void saveSqlFileLazy() {
        smartLazyOperation.saveSqlFile("lazy");
    }

    /**
     * 填充数据 lazy.sys_user
     */
    @Test
    void stuffedLazySysUser() {
        smartLazyOperation.stuffed("lazy", "sys_user", 10L);
    }

    /**
     * 填充所有数据
     */
    @Test
    void stuffedAll() {
        smartLazyOperation.stuffedAll(10L);
    }

    /**
     * describe stuffedDataBaseUser 填充数据 DataBaseUser
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 5:31 下午
     **/
    @Test
    void stuffedDataBaseUser() {
        smartLazyOperation.stuffed(DataBaseUser.class, 10L);
    }

    /**
     * 生成 lazy.sys_user 对应的Java 实体
     */
    @Test
    void stuffedLazySysUserJava() {
        smartLazyOperation.stuffedJava("lazy", "sys_user");
    }


    /**
     * 创建多有的表
     */
    @Test
    void stuffedJava() {
        for (LazyTableInfo showTable : perfectLazyOperation.showTables("lazy")) {
            smartLazyOperation.stuffedJava(showTable.getTableSchema(), showTable.getTableName());
        }
    }


}


```