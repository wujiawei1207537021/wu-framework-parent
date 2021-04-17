### 快速对接模块

    默认使用数据库链接(配置数据源)
    支持kafka 入库操作（没有配置数据源 配置Kafka数据源）
    
    功能:
    1.支持配置schema 
    2.配置扫描class创建schema 
    3.配置包路径扫描schema
    4.支持不配置schema 在使用Kafka进行数据发送时创建schema(添加缓存处理)
    5.Mybatis多数据源切换 @EasyUpsertDS
    6.Kafka与MySQL 自由切换 @EasyUpsertDS
    7.@QuickEasyUpsert 无代码数据保存
    8.支持ES数据批量插入
    9.新增数据库自动建表
    10.新增复杂数据快速插入
    11.支持手动注入mysql数据源

### MYSQL 数据源 com.zaxxer.hikari.HikariDataSource &com.baomidou.dynamic.datasource.DynamicRoutingDataSource

### log

    com.supconit.its.eslog.util.DataAccessLogHelper,\
    com.supconit.its.eslog.producer.ElasticLogProducer,\
    com.supconit.its.eslog.config.LogKafkaConfig,\