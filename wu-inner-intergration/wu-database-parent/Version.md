| 模块                                                                                       | 功能描述                                         | 其他  |
|------------------------------------------------------------------------------------------|----------------------------------------------|-----|
| [wu-database-click-house-starter](wu-database-click-house-starter)                       | clickhouse数据库操作模块                            | 弃用  |
| [wu-database-generator-starter](wu-database-generator-starter)                           | 数据库逆向工程模块                                    | 弃用  |
| [wu-database-h2-lazy-starter](wu-database-h2-lazy-starter)                               | h2数据库操作模块                                    | 弃用  |
| [wu-database-layer-stereotype](wu-database-layer-stereotype)                             | 数据库操作配置层模块                                   | 弃用  |
| [wu-database-lazy-datasource-starter](wu-database-lazy-datasource-starter)               | 数据源 模块                                       | 使用中 |
| [wu-database-lazy-lambda-core](wu-database-lazy-lambda-core)                             | 数据库lambda操作核心模块 依赖于wu-database-lazy-sql-core | 使用中 |
| [wu-database-lazy-spring-boot-autoconfigure](wu-database-lazy-spring-boot-autoconfigure) | lazy框架自动注入配置模块                               | 使用中 |
| [wu-database-lazy-sql-core](wu-database-lazy-sql-core)                                   | sql操作核型模块                                    | 使用中 |
| [wu-database-lazy-starter](wu-database-lazy-starter)                                     | lazy框架自动注入容器模块                               | 使用中 |
| [wu-database-mysql-lazy-starter](wu-database-mysql-lazy-starter)                         | mysql数据库操作                                   | 弃用  |
| [wu-dynamic-database-starter](wu-dynamic-database-starter)                               | 数据库动态数据源操作                                   | 弃用  |
| [wu-elasticsearch-starter](wu-elasticsearch-starter)                                     | es数据库操作                                      | 弃用  |
| [wu-hbase-starter](wu-hbase-starter)                                                     | hbase数据库操作                                   | 弃用  |

#### 1.2.0.1-JDK-1.8-SNAPSHOT

    添加isNull lambda表达式
    优化alert语句执行失败导致其他字段也无法执行问题

#### 新增 [wu-database-lazy-datasource-starter](wu-database-lazy-datasource-starter) 模块

    新增 LazyProxyDataSource 数据源代理
    新增 LazyDataSourceConnection 连接对象代理用于获取sql审计中需要的数据
    [fix]  修复自动创建schema后数据源无法销毁问题