# wu-framework-parent

⌚️ 2023年9月22
版本：1.2.0-JDK7-SNAPSHOT

更新内容内容如下：
1. DataTransformUntil 适配注解转换成对象transform()，注解生成对象 transformAnnotation()
2. 新增字段转译 支持rpc、http、枚举
3. 新增Lazy框架查询数据库数据返回树枝自动转译数据
4. @LazyTableFieldId 支持主键自增、主键输入默认LazyTableIdFactory.createId
5. Excel导出支持根据数据类型进行导出和分组，比如用户对象中含有多个角色导出的数据为用户信息合并而后用户中的角色信息变成多个列
6. 适配jdk17 
7. 使用springboot 3.0.7

仓库地址



1.2.1

1. [wu-database-lazy-mybatis-cure-plus-starter](wu-framework-plus%2Fwu-database-lazy-mybatis-cure-plus-starter) 升级mybatis 3.5.4
2. Lazy ORM支持数据自动转译
   - 新增字段自动关联转译
   - 注解方法入参数通过数据库转译@LazyTableArgsTranslation
   - 注解方法出参数转译@LazyTableTranslation
   - 通过一个字段管理其他表的一条数据@LazyTableTranslationOneField
   - 通过一个字段管理其他表的数据@LazyTableTranslationOneToManyField
   - ```java
             @Data
          @LazyTable(tableName = "lazy_table_user", comment = "lazy 框架跨表映射 用户")
          public class LazyTableUser {
          @LazyTableFieldId(value = "id", idType = LazyTableFieldId.IdType.AUTOMATIC_ID)
          private Integer id;
            
              @LazyTableFieldUnique(value = "username")
              private String username;
            
              @LazyTableField(value = "birthday")
              private String birthday;
            
              @LazyTableField("sex")
              private String sex;
            
              @LazyTableTranslationOneField(translationSourceName = "addressId", translationTargetName = "id", columnList = "addressName",
                      translationTargetTableName = "lazy_table_address", type = LazyTranslationTableEndpoint.Type.COLUMN,
                      lazyTranslationAPIClass = LazyTableTranslationOneAPI.class)
              @LazyTableField(exist = false)
              private String address;
            
              @LazyTableFieldUnique(value = "age")
              private Integer age;
            
              @LazyTableFieldUnique()
              private Integer ageType;
            
            
              @LazyTableField(comment = "地址ID")
              private Integer addressId;
            
              /**
               * 是否删除
               */
              @Schema(description = "是否删除", name = "isDeleted")
              @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
              private Boolean isDeleted;
          }

     ```
3. 支持Excel导出适配任意对象含有数组
4. 完善ACW模块
5. 更新日志:
   1. 支持mysql实例添加
   2. 支持mysql 数据库查询统计单个表数量
   3. 支持mysql单表数据库操作（增删改查）
   4. 支持统计单个数据库下所有表条数统计、存储数据
   5. 支持mysql数据库自动填充
   6. 支持通过数据库反向生成ddd代码
   7. 支持数据库备份、数据库实例备份
   8. 支持数据库信息导入



