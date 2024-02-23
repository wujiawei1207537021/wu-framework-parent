#### [Gitee](https://gitee.com/wujiawei1207537021/wu-framework-parent/tree/master/wu-inner-intergration/wu-database-parent)
点赞关注不迷路 [项目地址](https://gitee.com/wujiawei1207537021/wu-framework-parent/tree/master/wu-inner-intergration/wu-database-parent)

#### 版本信息 1.2.2-JDK17-SNAPSHOT

### 新增 inOr查询 防止in查询索引失效
用法

```java
       public void select() {
            LazyUserTest lazyUserTest = new LazyUserTest();
            lazyUserTest.setUsername("紧");
            lazyUserTest.setSex(LazyUserTest.Sex.MAN);
            lazyUserTest.setId(12L);
            lazyLambdaStream.upsert(lazyUserTest);
            lazyLambdaStream.update(lazyUserTest, LazyWrappers.<LazyUserTest>lambdaWrapper().eq(LazyUserTest::getSex, LazyUserTest.Sex.MAN));
        
            List<LazyUserTest> lazyUserTests = lazyLambdaStream.selectList(LazyWrappers.<LazyUserTest>lambdaWrapper()
                    .inOr(LazyUserTest::getAge, List.of(18, 19, 20))
            );
            System.out.println("18、19、20 age user:" + lazyUserTests);
            // 打印信息
            // 执行类型: =====> SELECT  
            //执行sql: =====>  SELECT  lazy_user_test.birthday as birthday , lazy_user_test.sex as sex , lazy_user_test.id as id , lazy_user_test.username as username , lazy_user_test.is_deleted as is_deleted , lazy_user_test.age as age  from lazy_user_test where  (   lazy_user_test.age =  18 or lazy_user_test.age =  19 or lazy_user_test.age =  20  )  and  is_deleted  = false
            // 18、19、20 age user:[]
        }
```
### selectOne 查询

```java
       public void select() {
            LazyUserTest lazyUserTest = new LazyUserTest();
            lazyUserTest.setUsername("紧");
            lazyUserTest.setSex(LazyUserTest.Sex.MAN);
            lazyUserTest.setId(12L);
            lazyLambdaStream.upsert(lazyUserTest);
            lazyLambdaStream.update(lazyUserTest, LazyWrappers.<LazyUserTest>lambdaWrapper().eq(LazyUserTest::getSex, LazyUserTest.Sex.MAN));
        
            LazyUserTest lazyUserTest = lazyLambdaStream.selectOne(LazyWrappers.<LazyUserTest>lambdaWrapper()
                    .eq(LazyUserTest::getId, 12L)
                    .eq(LazyUserTest::getIsDeleted, false)
            );
            System.out.println(" user :" + lazyUserTest);
            // 打印信息
            // 执行类型: =====> SELECT  
            //执行sql: =====>  SELECT  lazy_user_test.birthday as birthday , lazy_user_test.sex as sex , lazy_user_test.id as id , lazy_user_test.username as username , lazy_user_test.is_deleted as is_deleted , lazy_user_test.age as age  from lazy_user_test where   lazy_user_test.id =  12    and  is_deleted  = false
            // 18、19、20 age user:[]
        }
```
### 统计数据
```java
    @Test
    public void  count(){
        Long count = lazyLambdaStream.count(LazyWrappers.<LazyUserTest>lambdaWrapper()
                .eq(LazyUserTest::getIsDeleted, false)
                .eq(LazyUserTest::getSex, LazyUserTest.Sex.MAN)
                .gt(LazyUserTest::getAge, 10)
                .gt(LazyUserTest::getBirthday, LocalDateTime.now()));
        // 如同执行如下sql select count(1) from lazy_user_test where  lazy_user_test.is_deleted  = false  and  lazy_user_test.sex  = 'MAN'  and  lazy_user_test.age  > 10  and  lazy_user_test.birthday  > '2024-02-06 20:21:03'  and  lazy_user_test.is_deleted  = false
        System.out.println("lazyLambdaStream.count:"+count);
    }
```

### 验证数据是否存在
```java

    @Test
    public void  exists(){
        boolean exists = lazyLambdaStream.exists(LazyWrappers.<LazyUserTest>lambdaWrapper()
                .eq(LazyUserTest::getIsDeleted, false)
                .eq(LazyUserTest::getSex, LazyUserTest.Sex.MAN)
                .gt(LazyUserTest::getAge, 10)
                .gt(LazyUserTest::getBirthday, LocalDateTime.now()));
        // 如同执行如下sql select count(1) from lazy_user_test where  lazy_user_test.is_deleted  = false  and  lazy_user_test.sex  = 'MAN'  and  lazy_user_test.age  > 10  and  lazy_user_test.birthday  > '2024-02-06 20:21:03'  and  lazy_user_test.is_deleted  = false
        System.out.println("lazyLambdaStream.exists:"+exists);
    }
```

### 查询某一个字段
```java

    @Test
    public void  selectName(){
        String userName = lazyLambdaStream.selectOne(LazyWrappers.<LazyUserTest>lambdaWrapper()
                .eq(LazyUserTest::getId, 1)
                .eq(LazyUserTest::getIsDeleted, false)
                        .onlyUseAs()
                        .as(LazyUserTest::getUsername,LazyUserTest::getUsername),
                String.class);
        // SELECT  lazy_user_test.username as username  from lazy_user_test where  lazy_user_test.id  = 1  and  lazy_user_test.is_deleted  = false  and  lazy_user_test.is_deleted  = false
        System.out.println("只查询用户ID为1 的用户名称:"+userName);
    }
    
```
### 查询部分字段减少IO
```java
    @Test
    public void  selectNameIdAge(){
        LazyUserTest userNameIdAge = lazyLambdaStream.selectOne(LazyWrappers.<LazyUserTest>lambdaWrapper()
                        .eq(LazyUserTest::getId, 1)
                        .eq(LazyUserTest::getIsDeleted, false)
                        .onlyUseAs()

                        .as(LazyUserTest::getId,LazyUserTest::getId)
                        .as(LazyUserTest::getAge,LazyUserTest::getAge)
                        .as(LazyUserTest::getUsername,LazyUserTest::getUsername)
                ,
                LazyUserTest.class);
        // SELECT  lazy_user_test.id as id , lazy_user_test.username as username , lazy_user_test.age as age  from lazy_user_test where  lazy_user_test.id  = 1  and  lazy_user_test.is_deleted  = false  and  lazy_user_test.is_deleted  = false
        System.out.println("只查询用户ID为1 的用户名称、ID、age:"+userNameIdAge);
    }
```


### 关联查询
```java
List<Menu> menuList = lazyLambdaStream.selectList(LazyWrappers.<RoleMenuDO>lambdaWrapper()
                        .eq(RoleMenuDO::getIsDeleted, false)
                        .eq(RoleMenuDO::getRoleId, role.getId())
                        .internalJoin(LazyWrappers.<RoleMenuDO, MenuDO>lambdaWrapperJoin()
                                .eqo(RoleMenuDO::getIsDeleted, false)
                                .eqo(RoleMenuDO::getRoleId, role.getId())
                                .eq(RoleMenuDO::getMenuId, MenuDO::getId)
                                .eqRighto(MenuDO::getIsDeleted, false)
                        )
                        .onlyUseAs()
                        .as(Menu.class),
                        Menu.class
                );
```

### insert使用

```java
    public void insert() {
        LazyUserTest lazyUserTest = new LazyUserTest();
        lazyUserTest.setUsername("紧");
        lazyUserTest.setSex(LazyUserTest.Sex.MAN);
        lazyUserTest.setId(12L);
        lazyLambdaStream.insert(lazyUserTest);
        // insert into lazy_user_test (id,username,birthday,sex,age,is_deleted) VALUES (12,'紧',null,'MAN',null,null)
    }
```
### upsert使用

```java

    public void upsert() {
        LazyUserTest lazyUserTest = new LazyUserTest();
        lazyUserTest.setUsername("紧");
        lazyUserTest.setSex(LazyUserTest.Sex.MAN);
        lazyUserTest.setId(12L);
        lazyLambdaStream.upsert(lazyUserTest);
        // insert into lazy_user_test (id,username,birthday,sex,age,is_deleted) VALUES (12,'紧',null,'MAN',null,null)  ON DUPLICATE KEY UPDATE username=values (username),birthday=values (birthday),sex=values (sex),age=values (age),is_deleted=values (is_deleted)
    }
    
```


### 前言
	当使用Lazy ORM 框架操作数据库时使用高级映射

### 场景
如查询用户时需要关联出用户的其他信息如地址、角色
### 使用方法
可以再对应的查询结果实体上添加
1. 自动映射用户对应的地址（一对一）

```java
    @LazyTableTranslationOneField(translationSourceName = "addressId", translationTargetName = "id", columnList = "addressName",
            translationTargetTableName = "lazy_table_address", type = LazyTranslationTableEndpoint.Type.COLUMN,
            lazyTranslationAPIClass = LazyTableTranslationOneAPI.class)
    @LazyTableField(exist = false)
```
注解属性：

```markdown
	translationSourceName：当前返回实体（查询出的结果需要根据这个字段的数据进行映射）
	translationTargetName：目标数据库字段
	columnList：查询目标表需要返回那些字段（可以是多个会自动映射成对象）
	translationTargetTableName：目标表名称
	type：指定映射类型包含所有字段、指定字段。默认所有字段
	lazyTranslationAPIClass：需要自定义转译api在这里声明
```


2.一对多（type为LazyTranslationTableEndpoint.Type.ALL 或者columnList枚举出想要的字段）

```java
    @LazyTableTranslationOneToManyField(
            translationSourceName = "id",
            translationTargetName = "automationId",
            translationTargetTableName = "automation_node",
            translationTargetType = AutomationNode.class,
            type = LazyTranslationTableEndpoint.Type.ALL
    )
```
