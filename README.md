# wu-framework-parent

<p align="center">
    <a target="_blank" href="https://search.maven.org/search?q=wu-framework-parent%20wu-framework-parent">
        <img src="https://img.shields.io/nexus/s/top.wu2020/wu-framework-parent?server=https%3A%2F%2Foss.sonatype.org&style=flat&logo=log" alt="Maven" />
    </a>
    <a target="_blank" href="https://search.maven.org/search?q=wu-framework-parent%20wu-framework-parent">
        <img src="https://img.shields.io/maven-central/v/top.wu2020/wu-framework-parent" alt="Maven" />
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-11-green.svg" alt="jdk-11" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk-17" />
	</a>
    <br />
        <img src="https://img.shields.io/badge/SpringBoot-v3.x-blue">
    <br />
    <a target="_blank" href='https://gitee.com/wujiawei1207537021/wu-framework-parent'>
		<img src='https://gitee.com/wujiawei1207537021/wu-framework-parent/badge/star.svg' alt='Gitee star'/>
	</a>

</p>

#### 介绍

springboot 版本3.2.1

wu-framework-parent 是一款由Java语言开发的框架，目标不写代码但是却能完成功能。 框架涵盖无赖ORM(
***wu-database-lazy-starter***)、**仿生组件**
、easy框架系列【Easy-Excel、easy-listener、easy-upsert】 授权框架(***wu-framework-authorization***)、Web框架、平台操作组件、层组件抽象、
**ACW模块数据库管理**

#### [仿生](wu-bionic-parent/README.md ':include :type=code')

| 模块                 | 所属层级 | 描述       |
|--------------------|------|----------| 
| wu-bionic-language | 语言模块 | 语言解析，tts |
| wu-bionic-memory   | 记忆内存 | 记忆断点     |
| wu-bionic-think    | 反思   | 反思模块     |

#### [Easy系列](wu-easy-parent/Easy-Component.puml ":include")

#### [Excel导入导出、文件导入导出](wu-easy-parent/wu-easy-excel-starter/README.md)

#### [基于第三方组件的upsert操作](wu-easy-parent/wu-easy-upsert-sink-parent/README.md)

| 模块                                       | 所属层级 | 描述             |
|------------------------------------------|------|----------------|
| wu-easy-upsert-core                      | sink | sink基础模块       |
| wu-easy-upsert-elasticsearch-sink        | sink | es数据存储         |
| wu-easy-upsert-h2-sink                   | sink | h2数据存储         |
| wu-easy-upsert-hbase-sink                | sink | hbase数据存储      |
| wu-easy-upsert-influxdb-sink             | sink | influxdb数据存储   |
| wu-easy-upsert-kafka-sink                | sink | kafka数据存储      |
| wu-easy-upsert-mysql-sink                | sink | mysql数据存储      |
| wu-easy-upsert-pulsar-sink               | sink | pulsar数据存储     |
| wu-easy-upsert-redis-sink                | sink | redis数据存储      |
| wu-easy-upsert-redisearch-sink           | sink | redisearch数据存储 |
| wu-easy-upsert-spring-boot-autoconfigure | sink | sink配置文件       |
| wu-easy-upsert-starter                   | sink | starter包       |

#### [第三方组件监听](wu-easy-parent/wu-easy-listener-parent/README.md)

| 模块                                         | 所属层级     | 描述          |
|--------------------------------------------|----------|-------------| 
| wu-easy-kafka-listener                     | listener | kafka数据监听   |
| wu-easy-listener-core                      | 核心       | 核心模块        |
| wu-easy-listener-spring-boot-autoconfigure | 配置       | 配置          |
| wu-easy-listener-starter                   | listener | starter     |
| wu-easy-mysql-binlog-listener              | listener | binlog数据监听  |
| wu-easy-mysql-listener                     | listener | mysql数据查询监听 |
| wu-easy-pulsar-listener                    | listener | pulsar监听    |
| wu-easy-redis-listener                     | listener | redis数据监听   |
| wu-easy-rocketmq-listener                  | listener | rocket数据监听  |

#### [授权](wu-framework-authorization/README.md)

| 模块                                         | 所属层级 | 描述   |
|--------------------------------------------|------|------| 
| wu-authorization-client-starter            | 客户端  | 客户端  |
| wu-authorization-common                    | 共用组件 | 共用组件 |
| wu-authorization-server-simple             | 模版案例 | 模版案例 |
| wu-authorization-server-starter            | 服务端  | 服务端  |
| wu-authorization-spring-boot-autoconfigure | 配置   | 配置   |

#### [无赖ORM](wu-inner-intergration/wu-database-parent/README.md)

| 模块                                         | 所属层级        | 描述                         |
|--------------------------------------------|-------------|----------------------------| 
| wu-database-h2-lazy-starter                | 核心H2 ORM 操作 | 操作H2数据库操作                  |
| wu-database-layer-stereotype               | 配置          | 数据库注解、配置                   |
| wu-database-lazy-lambda-core               | lambda      | 针对不同数据源                    |
| wu-database-lazy-spring-boot-autoconfigure | 配置          | 配置注解层                      |
| wu-database-lazy-sql-core                  | sql核心处理成    | 处理sql解析、sql执行、sql映射        |
| wu-database-lazy-starter                   | 数据库starter  | 包含mysql、H2、hbase 对应的的Lazy包 |
| wu-database-mysql-lazy-starter             | mysql       | mysql-starter              |
| wu-dynamic-database-starter                | 动态数据源       | 动态数据源                      |
| wu-elasticsearch-starter                   | ES          | ES                         |
| wu-hbase-starter                           | hbase       | hbase                      |

#### [插件](wu-framework-plus/README.md)

| 模块                                           | 所属层级 | 描述                                |
|----------------------------------------------|------|-----------------------------------| 
| wu-database-lazy-mybatis-cure-plus-starter   | 插件   | 针对mybatis框架存在字段不存在、表不存在，进行数据库自动治愈 |
| wu-database-lazy-plus-starter                | 插件   | lazy插件                            |
| wu-database-plus                             | 插件   | 弃用                                |
| wu-database-lazy-seata-cure-plus-starter     | 插件   | seata治愈插件                         |
| wu-springdoc-compatible-swagger-plus-starter | 插件   | 适配就版本swagger2                     |

#### [框架web](wu-framework-web/README.md)

#### [框架层注解](wu-layer-stereotype/README.md)

[EasyController 的使用.md](wu-layer-stereotype/EasyController%20%E7%9A%84%E4%BD%BF%E7%94%A8.md)

#### [内部smart](wu-smart-intergration/README.md)

### smart-acw

| 模块                                                                                  | 所属层级     | 描述            |
|-------------------------------------------------------------------------------------|----------|---------------| 
| wu-smart-acw-client                                                                 | acw项目    | acw项目、客户端、服务端 |
| wu-smart-acw-client-simple                                                          | acw客户端样例 | acw客户端样例      |
| [wu-smart-acw-client-ui](wu-smart-intergration/wu-smart-acw/wu-smart-acw-client-ui) | ui       | 客户端UI         |
| wu-smart-acw-client-core                                                            | core     | core          |
| wu-smart-acw-client-server                                                          | 服务端      | 服务端           |
| [wu-smart-acw-server-ui](wu-smart-intergration/wu-smart-acw/wu-smart-acw-server-ui) | UI       | 服务端UI         |

#### wu-smart-platform

| 模块                                                                                               | 所属层级 | 描述         |
|--------------------------------------------------------------------------------------------------|------|------------| 
| wu-authorization-server-platform-starter                                                         | 平台   | 权限管理平台     |
| [wu-automation-server-platform-starter](wu-smart-platform/wu-automation-server-platform-starter) | 平台   | 自动化平台      |
| wu-data-relay-server-platform-starter                                                            | 平台   | 数据中转       |
| wu-doc-server-platform-starter                                                                   | 平台   | 文件平台       |
| wu-dynamic-iframe-platform-starter                                                               | 平台   | 动态Iframe平台 |
| wu-jvm-server-platform-starter                                                                   | 平台   | JVM 相关信息   |
| wu-log-server-platform-starter                                                                   | 平台   | 日志平台       |
| wu-play-server-platform-starter                                                                  | 平台   | 娱乐模块       |
| wu-sql-audit-server-platform-starter                                                             | 平台   | sql审计平台    |
| [wu-ssh-server-platform-starter](wu-smart-platform/wu-ssh-server-platform-starter)               | 平台   | ssh平台      |
| [wu-tts-server-platform-starter](wu-smart-platform/wu-tts-server-platform-starter)               | 平台   | tts平台      |
| wu-upsert-server-platform-starter                                                                | 平台   | Upsert平台   |


