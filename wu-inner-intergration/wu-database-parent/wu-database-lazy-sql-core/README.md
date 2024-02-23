### 数据库框架

    1.支持实体类反射数据进行数据库CRUD操作
    2.新增灵性数据插入更新(自动过滤空值)

### 1.0.6

- 新增配置导出数据忽略指定字段  
  spring.datasource.ignore-exported-fields: - id
- 新增配置声明导出数据中的特殊字符  
  spring.datasource.special-fields: - ASC
- 新增@LazyScan自动扫描实体创建表
- 新增自动填充表数据
- 新增创建表方法
- 新增更新表字段方法

### 1.0.7

- 修复数据插入布尔类型、数字类型字段 字符串更改为->原始数据类型
- 新增逆向工程功能生成对应的Java class 支持mybatis 适配

```yaml
spring:
  lazy:
    enable-reverse-engineering: true  # 允许逆向工程
    reverse-engineering:
      reverse-engineering-mvc:
        enable-lazy-operation-mvc: true # 允许创建 api控制层
      enable-mybatis: true      
      enable-lazy: false   # 不允许lazy系列注解
      enable-lombok-accessors: false # 不允许 lombok.accessors
      enable-lombok-data: false  # 不允许 lombok.data
      package-name: com.wu.lazy  # 包名
      enable-swagger: false  # 不允许 swagger
```

### 1.0.8

- 使用Spring 进行事物管理
- 修复数据为null 时执行sql 数据为 "null" 问题
- 新增自动过滤null字段的upsert接口

```java

```

### 1.0.9

- 新增自动创建数据库功能配置文件添加配置spring.lazy.enable-auto-schema=true
    - 配置文件中username、password可以拥有创建数据库的权限
    - 数据库url中配置的数据库会自动创建

```yaml

```