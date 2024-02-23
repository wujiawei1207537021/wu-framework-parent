## 快速入门

### 简介

#### 特性

- 强大的CRUD操作：内置通过注入 LazyLambdaStream 对象即可实现表单的大部分CRUD操作
- 支持Lambda形式的调用： 通过Lambda表达式，方便的编写各类查询条件
- 内置分页查询：通过构造分页对象查询数据统计分页总数
- 支持多种数据库：支持MySQL

#### 支持数据库

任何能使用 LazyLambdaStream进行CRUD，并且支持标准SQL的数据库，具体支持情况如下

- MySQL

#### 框架架构

![img.png](Lazy ORM log.png)

#### 代码托管

[Gitee](https://gitee.com/wujiawei1207537021/wu-framework-parent/tree/master/wu-inner-intergration/wu-database-parent)｜Github

#### 参与贡献

欢迎各位同学一起参与完善wu-database-lazy-starter

- 贡献代码：代码地址wu-database-lazy-starter，欢迎提交Issue或者Pull Requests

#### 教程、案例、使用者名单

- 暂无

#### 版本功能

- Lazy-ORM 是一款针对懒人快速开发的ORM框架
- 支持实体类反射数据进行数据库CRUD操作
- 新增灵性数据插入更新(自动过滤空值)
- 新增配置导出数据忽略指定字段  
  spring.datasource.ignore-exported-fields: - id
- 新增配置声明导出数据中的特殊字符  
  spring.datasource.special-fields: - ASC
- 新增@LazyScan自动扫描实体创建表
- 新增自动填充表数据
- 新增创建表方法
- 新增更新表字段方法
- 修复数据插入布尔类型、数字类型字段 字符串更改为->原始数据类型
- 新增逆向工程功能生成对应的Java class 支持mybatis 适配

- 使用Spring 进行事物管理
- 修复数据为null 时执行sql 数据为 "null" 问题
- 新增自动过滤null字段的upsert接口
- 新增字段自动关联转译
    - 注解方法入参数通过数据库转译@LazyTableArgsTranslation
    - 注解方法出参数转译@LazyTableTranslation
    - 通过一个字段管理其他表的一条数据@LazyTableTranslationOneField
    - 通过一个字段管理其他表的数据@LazyTableTranslationOneToManyField

### 快速开始

我们将通过一个简单的 Demo 来阐述 wu-database-lazy-starter 的强大功能，在此之前，我们假设您已经：

- 拥有 Java 开发环境以及相应 IDE
- 熟悉 Spring Boot
- 熟悉 Maven

现有一张 User 表，其表结构如下：

| id | name | annual_salary | email          |
|----|------|---------------|----------------|
| 1  | 吴小二  | 18            | test1@lazy.com |
| 2  | 吴三   | 20            | test2@lazy.com |
| 3  | 吴小四  | 28            | test3@lazy.com |
| 4  | 吴小五  | 21            | test4@lazy.com |
| 5  | 吴小六  | 24            | test5@lazy.com |

其对应的数据库 Schema 脚本如下：

```sql
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    annual_salary INT(11) NULL DEFAULT NULL COMMENT '年薪',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

 ```

其对应的数据库 Data 脚本如下：

```sql 
DELETE FROM user;

INSERT INTO user (id, name, annual_salary, email) VALUES
(1, '吴小二', 18, 'test1@lazy.com'),
(2, '吴三', 20, 'test2@lazy.com'),
(3, '吴小四', 28, 'test3@lazy.com'),
(4, '吴小五', 21, 'test4@lazy.com'),
(5, '吴小六', 24, 'test5@lazy.com');

```

#### 初始化工程

创建一个空的 Spring Boot 工程（工程将以 MySQL 作为默认数据库进行演示）

#### 添加依赖

引入 Spring Boot Starter 父工程：

```pom
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.7</version>
    <relativePath/>
</parent>

```

引入 spring-boot-starter、spring-boot-starter-test、wu-database-lazy-starter、mysql 依赖

```pom
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
     <!--懒人依赖-->
    <dependency>
        <groupId>top.wu2020</groupId>
        <artifactId>wu-database-lazy-starter</artifactId>
        <version>1.2.2-JDK17-SNAPSHOT</version>
    </dependency>
     <!--mysql-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
</dependencies>

```

#### 配置

在 application.yml 配置文件中添加 mysql 数据库的相关配置：

```yaml
# DataSource Config
spring:
  datasource:
    username: root
    password: root
    url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver

```

Spring Boot 启动类：

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

```

#### 编码

编写实体类 User.java（此处使用了 Lombok 简化代码）

```java
@Data
public class User {
    private Long id;
    private String name;
    private Integer annualSalary;
    private String email;
}

```

##### 开始使用

****
添加测试类，进行功能测试：

```java
@SpringBootTest
public class SampleTest {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        // 等同于执行sql select * from sys_user
        Collection<User> userList = lazyLambdaStream.select(LazyWrappers.<User>lambdaWrapper()).collection();
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}

```

#### 小结

***
通过以上几个简单的步骤，我们就实现了 User 表的 CRUD 功能，甚至连 XML 文件都不用编写！

### 安装

全新的 wu-database-lazy-starter 1.2.2-JDK17-SNAPSHOT 版本基于 JDK17，提供了 lambda 形式的调用，所以安装集成 MP3.0 要求如下：

- JDK 8+
- Maven or Gradle

#### Spring Boot

Maven：

```pom
    <dependency>
        <groupId>top.wu2020</groupId>
        <artifactId>wu-database-lazy-starter</artifactId>
        <version>1.2.2-JDK17-SNAPSHOT</version>
    </dependency>
```

### 配置

wu-database-lazy-starter 的配置异常的简单，我们仅需要一些简单的配置即可使用 wu-database-lazy-starter 的强大功能！

#### Spring Boot 工程

***

- 配置yaml

```yaml
# DataSource Config
spring:
  datasource:
    username: root
    password: root
    url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver

```

### 注解

::: tip 本文将介绍 wu-database-lazy-starter 注解包相关类详解（更多详细描述可点击查看源码注释）
:::

- 描述
- 使用位置对象

#### @LazyTable

```java 
@LazyTable(tableName="sys_user")
public class User {
    private Long id;
    private String name;
    private Integer annualSalary;
    private String email;
}

```

| 属性             | 类型      | 必须指定 | 默认值     | 描述                                                  |
|----------------|---------|------|---------|-----------------------------------------------------|
| tableName      | String  | 否    | ""      | 表名                                                  |
| schema         | String  | 否    | ""      | schema                                              |
| comment        | String  | 否    | ""      | 表注释                                                 |
| perfectTable   | boolean | 否    | "false" | 完善表                                                 |
| smartFillField | boolean | 否    | false   | 智能填充bean属性 针对数据源 如mysql查询结果、http请求结果中包含的数据字段不再当前对象中 |

#### @LazyTableFieldId

```java 
@LazyTable(tableName="sys_user")
public class User {
    
    @LazyTableFieldId
    private Long id;
    private String name;
    private Integer annualSalary;
    private String email;
}

```

| 属性        | 类型                        | 必须指定 | 默认值                          | 描述                  |
|-----------|---------------------------|------|------------------------------|---------------------|
| value     | String                    | 否    | ""                           | 字段名                 |
| name      | String                    | 否    | ""                           | 字段名                 |
| comment   | String                    | 否    | ""                           | 字段注释                |
| type      | String                    | 否    | ""                           | 字段了类型（varchar、int等） |
| indexType | LayerField.LayerFieldType | 否    | LayerField.LayerFieldType.ID | 索引类型                |
| idType    | IdType                    | 否    | AUTOMATIC_ID                 | 主键自增类型              |

##### LayerFieldType

| 值          | 描述     |
|------------|--------|
| FIELD_TYPE | 字段类型   |
| ID         | 数据库 ID |
| UNIQUE     | 唯一性索引  |
| AUTOMATIC  | 自动的    |

##### IdType

| 值            | 描述        |
|--------------|-----------|
| AUTOMATIC_ID | 主键ID 默认自增 |
| INPUT_ID     | 输入主键      |

#### @LazyTableField

```java 
@LazyTable(tableName="sys_user")
public class User {
    
    private Long id;
    private String name;
    @LazyTableField("salary")
    private Integer annualSalary;
    private String email;
}

```

| 属性         | 类型                        | 必须指定 | 默认值                          | 描述                  |
|------------|---------------------------|------|------------------------------|---------------------|
| value      | String                    | 否    | ""                           | 字段名                 |
| name       | String                    | 否    | ""                           | 字段名                 |
| comment    | String                    | 否    | ""                           | 字段注释                |
| columnType | String                    | 否    | ""                           | 字段了类型（varchar、int等） |
| exist      | boolean                   | 否    | true                         | 是否存在                |
| indexType  | LayerField.LayerFieldType | 否    | LayerField.LayerFieldType.ID | 索引类型                |
| idType     | IdType                    | 否    | AUTOMATIC_ID                 | 主键自增类型              |

### 快速测试

自动导入 wu-database-lazy-starter 测试所需相关配置。

# 示例工程

****
源码：👉 wu-database-lazy-starter-simple(opens new window)

# 使用教程

****

# 添加测试依赖

Maven:

    <dependency>
        <groupId>top.wu2020</groupId>
        <artifactId>wu-database-lazy-starter</artifactId>
        <version>1.2.2-JDK17-SNAPSHOT</version>
    </dependency>

Gradle：

    compile group: 'top.wu2020', name: 'wu-database-lazy-starter', version: '1.2.2-JDK17-SNAPSHOT'

# 编写测试用例

```java 

    @Autowired
    private LazyOperation lazySqlOperation;

    /**
     * 用户信息简单插入
     */
    @ApiOperation("用户信息简单插入")
    @PostMapping("/lazy/upsert")
    public void lazyUpsert() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("小来");
        sysUser.setPassword("1234");
        sysUser.setId(1L);
        // 同执行sql insert into sys_user (user_name,password,id) values("小来","1234","1") ON DUPLICATE KEY UPDATE user_name=values (user_name),password=values (password),id=values (id)
        lazySqlOperation.upsert(sysUser);
    }

```

## 核心功能

### 代码生成器

#### 快速开始

****

##### 安装

    <dependency>
        <groupId>top.wu2020</groupId>
        <artifactId>wu-database-lazy-starter</artifactId>
        <version>1.2.2-JDK17-SNAPSHOT</version>
    </dependency>

::: tip 直接使用maven引入项目，通过配置文件加载生成代码
:::

##### 配置文件

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

##### 使用

启动Spring-boot的启动类即可

##### 成品

![img_1.png](img_1.png)

### CRUD 接口

#### upsert

```java 
    /**
     * 批量更新或插入
     *
     * @param objects
     * @param <T>
     */
    <T> void upsert(Object... objects);

```

#### 参数说明

| 类型        | 参数名     | 描述     |
|-----------|---------|--------|
| Object... | objects | 任意实体对象 |

#### upsert 案例

```java 
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setUsername("username");
        dataBaseUser.setAddress("地址");
        dataBaseUser.setAge(18);
        // 同执行SQL: insert into user (id,username,birthday,sex,age,age_type,address_id) VALUES (null,'username',null,null,18,null,null)  ON DUPLICATE KEY UPDATE 
 //id=values (id),username=values (username),birthday=values (birthday),sex=values (sex),age=values (age),age_type=values (age_type),address_id=values (address_id)
        lazyLambdaStream.upsert(dataBaseUser);

    }
```

#### insert

```java 
    /**
     * 插入 单个/list
     *
     * @param t
     * @param <T>
     */
    <T> void insert(T t);

```

#### 参数说明

| 类型 | 参数名 | 描述   |
|----|-----|------|
| T  | t   | 实体对象 |

#### insert 案例

```java
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setUsername("username");
        dataBaseUser.setAddress("地址");
        dataBaseUser.setAge(18);
        // 同执行SQL: INSERT INTO user(username,birthday,sex,age,age_type,address_id)values(null,'username',null,null,'18',null,null)
        lazyLambdaStream.insert(dataBaseUser);

    }
```

#### smartUpsert

```java 
  /**
     * 更新或者插入单个 去除空值、对比表
     * 多个数据性能会慢，不经常使用
     */
    Object smartUpsert(Object... t);

```

#### 参数说明

| 类型        | 参数名 | 描述     |
|-----------|-----|--------|
| Object... | t   | 任意实体对象 |

#### smartUpsert 案例

```java
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setUsername("username");
        dataBaseUser.setAddress("地址");
        dataBaseUser.setAge(18);
        // 没有表会自动创建表
        /**
         * -- ——————————————————————————
         * -- create table user  用户信息表  
         * -- add by  wujiawei  2022-11-12  
         * -- ——————————————————————————
         * CREATE TABLE IF NOT EXISTS `user` ( 
         * address_id  int(11)      COMMENT 'null'
         * ,age  int(11)      COMMENT ''
         * ,age_type  int(11)      COMMENT ''
         * ,birthday  varchar(255)      COMMENT ''
         * ,id  int(11)   not null   AUTO_INCREMENT COMMENT ''
         * ,sex  varchar(255)      COMMENT ''
         * ,username  varchar(255)      COMMENT ''
         * ,PRIMARY KEY (id) USING BTREE
         *  , UNIQUE KEY `a_a_u` (`age`,`age_type`,`username`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';
         * -- ------end 
         * -- ——————————————————————————
         */
        // 同 执行SQL: insert into user (id,username,age) VALUES (null,'username',18)  ON DUPLICATE KEY UPDATE 
        // id=values (id),username=values (username),age=values (age)
        lazyLambdaStream.smartUpsert(dataBaseUser);

    }
```

#### upsertRemoveNull

```java 
  /**
     * 更新或者插入单个执行 去除空值
     * 多个数据性能会慢，不经常使用
     */
    Object upsertRemoveNull(Object... t);
```

#### 参数说明

| 类型        | 参数名 | 描述     |
|-----------|-----|--------|
| Object... | t   | 任意实体对象 |

#### upsertRemoveNull 案例

```java
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        Address address = new Address();
        address.setId(1L);
        address.setLongitude(1.2d);
        //  执行SQL: insert into address (id,latitude,longitude) VALUES (1,'0.0','1.2')  ON DUPLICATE KEY UPDATE 
        // id=values (id),latitude=values (latitude),longitude=values (longitude)
        lazyLambdaStream.upsertRemoveNull(address);

        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Address addressa = new Address();
            address.setId(1L);
            address.setLongitude(1.2d);
            addresses.add(addressa);
        }
        // 执行SQL: insert into address (id,name,latitude,longitude) VALUES (null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0')  ON DUPLICATE KEY UPDATE
        // id=values (id),name=values (name),latitude=values (latitude),longitude=values (longitude)
        lazyLambdaStream.upsert(addresses);

    }
```

#### lazyPage

```java 
    /**
     * 分页查询
     *
     * @param <T>
     * @return
     */
    <T> Page<T> lazyPage(@NonNull Page lazyPage, @NonNull Class returnType, String sql, Object... params);
```

#### 参数说明

| 类型        | 参数名        | 描述       |
|-----------|------------|----------|
| Page      | lazyPage   | 分页对象     |
| Class     | returnType | 返回数据类型   |
| String    | sql        | 执行的sql语句 |
| Object... | params     | sql执行参数  |

#### lazyPage 案例

```java 
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );

        //  执行SQL: select user.* from user where  user.age  >  18  and  user.sex  =  '男'
        Page<DataBaseUser> dataBaseUserLazyPage = lazyLambdaStream.selectPage(LazyWrappers.<DataBaseUser>lambdaWrapper()
                        .gt(DataBaseUser::getAge, 18)
                        .eq(DataBaseUser::getSex, "男"),
                new Page<>(1, 10)
        );
        System.out.println(dataBaseUserLazyPage);
    }
```

#### executeSQL

```java 
  
    /**
     * @param sql
     * @param t
     * @param params
     * @param <T>
     * @return
     */
    <T> List<T> executeSQL(String sql, Class t, Object... params);

```

#### 参数说明

| 类型        | 参数名    | 描述       |
|-----------|--------|----------|
| Class     | t      | 返回数据类型   |
| String    | sql    | 执行的sql语句 |
| Object... | params | sql执行参数  |

#### executeSQL 案例

```java
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );

        //  执行SQL: select user.* from user where  user.age  >  18  and  user.sex  =  '男'
        List<DataBaseUser> dataBaseUsers = lazyLambdaStream.executeSQL("select user.* from user where  user.age  >  %s  and  user.sex  =  '%s'", DataBaseUser.class, 18, "男");
        System.out.println(dataBaseUsers);
    }
```

#### executeSQLForBean

```java 
      /**
     * description 执行SQL 返回指定类型
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2020/12/29 下午1:44
     */
    <T> T executeSQLForBean(String sql, Class t, Object... params);
```

#### 参数说明

| 类型        | 参数名    | 描述       |
|-----------|--------|----------|
| Class     | t      | 返回数据类型   |
| String    | sql    | 执行的sql语句 |
| Object... | params | sql执行参数  |

#### executeSQLForBean 案例

```java 
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );

        //  执行SQL: select user.* from user where  user.age  >  18  and  user.sex  =  '男' limit 1
        DataBaseUser dataBaseUser = lazyLambdaStream.executeSQLForBean("select user.* from user where  user.age  >  %s  and  user.sex  =  '%s' limit 1", DataBaseUser.class, 18, "男");
        System.out.println(dataBaseUser);
    }
```

#### perfect

```java 
      /**
     * describe 完善表
     *
     * @param entityClasss class 对象数组
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 5:05 下午
     **/
    <T> T perfect(@NonNull Class... entityClasss);
```

#### 参数说明

| 类型       | 参数名          | 描述   |
|----------|--------------|------|
| Class... | entityClasss | 实体对象 |

#### perfect 案例

![img_2.png](img_2.png)

```java 
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        LazyDatabaseJsonMessage.lazyDataSourceType = DataSourceType.MySQL;
        // 完善表结构
        lazyLambdaStream.perfect(Address.class);

    }
```

#### createTable

```java 
    /**
     * describe 创建表
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 7:48 下午
     **/
    <T> T createTable(@NonNull Class... entityClasss);
```

#### 参数说明

| 类型       | 参数名          | 描述   |
|----------|--------------|------|
| Class... | entityClasss | 实体对象 |

#### createTable 案例

```java 
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        LazyDatabaseJsonMessage.lazyDataSourceType = DataSourceType.MySQL;
        // 创建表
        lazyLambdaStream.createTable(Address.class);

    }
```

#### updateTable

```java 
    /**
     * describe 更新表
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 7:48 下午
     **/
    <T> T updateTable(@NonNull Class... entityClasss);
```

#### 参数说明

| 类型       | 参数名          | 描述   |
|----------|--------------|------|
| Class... | entityClasss | 实体对象 |

#### updateTable 案例

```java 
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        LazyDatabaseJsonMessage.lazyDataSourceType = DataSourceType.MySQL;
        // 更新表
        lazyLambdaStream.updateTable(Address.class);

    }
```

#### execute

```java 
    /**
     * 执行操作
     *
     * @param persistenceRepository
     * @return
     */
    List<Object> execute(PersistenceRepository persistenceRepository);
```

#### 参数说明

| 类型                    | 参数名                   | 描述          |
|-----------------------|-----------------------|-------------|
| PersistenceRepository | persistenceRepository | 预执行SQL需要的属性 |

#### execute 案例

```java
// 使用相当灵活、想咋玩就咋玩
```

#### executeOne

```java 
    /**
     * 执行操作
     *
     * @param persistenceRepository
     * @return
     */
    Object executeOne(PersistenceRepository persistenceRepository);
```

#### 参数说明

| 类型                    | 参数名                   | 描述          |
|-----------------------|-----------------------|-------------|
| PersistenceRepository | persistenceRepository | 预执行SQL需要的属性 |

#### executeOne 案例

```java 
        public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );

        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        persistenceRepository.setQueryString("select user.* from user where  user.age  >  18  and  user.sex  =  '男' limit 1");
        persistenceRepository.setExecutionType(LambdaTableType.SELECT);
        persistenceRepository.setResultClass(DataBaseUser.class);
        //  执行SQL: select user.* from user where  user.age  >  18  and  user.sex  =  '男' limit 1
        DataBaseUser dataBaseUser = (DataBaseUser) lazyLambdaStream.executeOne(persistenceRepository);
        System.out.println(dataBaseUser);
    }
```

### 聪明懒人的操作接口

#### saveSqlFile

```java 
    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * description 数据库数据存储到sql文件(删除表后、创建表 数据使用upsert)
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    void saveSqlFile(String nameDatabase);
```

#### 参数说明

| 类型     | 参数名          | 描述            |
|--------|--------------|---------------|
| String | nameDatabase | 数据库名 默认当前连接数据 |

#### saveSoftSqlFile

```java 
    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * description 柔和形 数据库数据存储到sql文件(表存在不删除 数据使用upsert)
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    void saveSoftSqlFile(String nameDatabase);
```

#### 参数说明

| 类型     | 参数名          | 描述            |
|--------|--------------|---------------|
| String | nameDatabase | 数据库名 默认当前连接数据 |

#### saveUpsertSqlFile

```java 
    /**
     * describe  导出增量式更新数据
     *
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * @author Jia wei Wu
     * @date 2022/4/9 22:57
     **/
    void saveUpsertSqlFile(String nameDatabase);
```

#### 参数说明

| 类型     | 参数名          | 描述            |
|--------|--------------|---------------|
| String | nameDatabase | 数据库名 默认当前连接数据 |

#### stuffed

```java 
    /**
     * 自动填充数据
     * SELECT
     * *
     * FROM
     * information_schema.COLUMNS
     * WHERE
     * TABLE_SCHEMA = 'lazy'
     * AND TABLE_NAME = 'sys_user';
     *
     * @param schema 数据库
     * @param table  表
     * @param num    数量
     */
    void stuffed(String schema, String table, Long num);

    /**
     * 自动填充数据
     * SELECT
     * *
     * FROM
     * information_schema.COLUMNS
     * WHERE
     * TABLE_SCHEMA = 'lazy'
     * AND TABLE_NAME = 'sys_user';
     *
     * @param table class 对应数据库结构的class
     * @param num   数量
     */
    void stuffed(Class table, Long num);
```

#### 参数说明

| 类型     | 参数名    | 描述   |
|--------|--------|------|
| String | schema | 数据库名 |
| String | table  | 表名   |
| Long   | num    | 数量   |

#### stuffedAll

```java 
    /**
     * 塞入所有数据
     *
     * @param num
     */
    void stuffedAll(Long num);
```

#### 参数说明

| 类型   | 参数名 | 描述     |
|------|-----|--------|
| Long | num | 存储数据数量 |

#### stuffedJava

```java 
    /**
     * describe 根据表明创建出Java文件
     *
     * @param schema    数据库
     * @param tableName 表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 12:23 上午
     **/
    void stuffedJava(String schema, String tableName);
```

#### 参数说明

| 类型     | 参数名       | 描述  |
|--------|-----------|-----|
| String | schema    | 数据库 |
| String | tableName | 表名  |

## 拓展