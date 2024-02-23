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

```java

```

```yaml

```