### [wu-database-lazy-mybatis-cure-plus-starter](https://gitee.com/wujiawei1207537021/wu-framework-parent/tree/master/wu-framework-plus/wu-database-lazy-mybatis-cure-plus-starter)

### 描述
    针对saas 数据库隔离情况下，每次版本迭代都需要重新修改对应的数据库，对于升级与运维存在一定的难度，那么这个数据库治愈框架来了，使用场景如下
    1.数据库不存在自动创建数据库 
    2.运行时表不存在自动创建表
    3.运行时字段不存在自动根据策略完善表字段

| 模块      | 版本    | 描述                        |
|---------|-------|---------------------------|
| mybatis | 3.5.4 | mybatis-plus-boot-starter |

### 依赖安装

```xml

<dependency>
    <groupId>top.wu2020</groupId>
    <artifactId>wu-database-lazy-mybatis-cure-plus-starter</artifactId>
    <version>latest</version>
</dependency>
```

### 版本功能

    新增支持针对mybatis数据库自愈
    支持运行时表丢失自动创建表
    支持运行时字段存在自动创建字段

#### 配置

    启动类添加扫描迭代数据库模型注解

```java
    @LazyScan(scanBasePackages = {"com.wu.smart.acw.core.domain.uo", "com.wu.framework.inner.lazy.example.**.entity"})
```

    字段添加模型注解

```java
import com.wu.framework.inner.lazy.stereotype.LazyTableField;

@LazyTable() // 表注解
@LazyTableField() //字段 注解 
```

    

