## `使用灵活EasyHashMap进行数据接入`

        ├── README.md
        ├── pom.xml
        ├── src
        │ └── main
        │     └── java
        │         └── com
        │             └── wu
        │                 └── framework
        │                     └── easy
        │                         └── stereotype
        │                             └── upsert
        │                                 ├── EasySmart.java
        │                                 ├── EasySmartField.java
        │                                 ├── EasyUnique.java
        │                                 ├── IEasyUpsert.java
        │                                 ├── dynamic
        │                                 │ ├── EasyUpsertDS.java
        │                                 │ ├── EasyUpsertStrategy.java
        │                                 │ └── QuickEasyUpsert.java
        │                                 ├── entity
        │                                 │ └── sink
        │                                 │     ├── LocalStorageClassAnnotation.java
        │                                 │     └── Sink.java
        │                                 ├── enums
        │                                 │ └── EasyUpsertType.java
        │                                 ├── exceptions
        │                                 │ └── IllegalDataSourceException.java
        │                                 └── factory
        │                                     └── EasyThreadFactory.java
        ├── target
        │ ├── classes
        │ │ └── com
        │ │     └── wu
        │ │         └── framework
        │ │             └── easy
        │ │                 └── stereotype
        │ │                     └── upsert
        │ │                         ├── EasySmart.class
        │ │                         ├── EasySmartField$JavaSchemaDataType.class
        │ │                         ├── EasySmartField.class
        │ │                         ├── EasyUnique.class
        │ │                         ├── IEasyUpsert.class
        │ │                         ├── dynamic
        │ │                         │ ├── EasyUpsertDS.class
        │ │                         │ ├── EasyUpsertStrategy.class
        │ │                         │ └── QuickEasyUpsert.class
        │ │                         ├── entity
        │ │                         │ └── sink
        │ │                         │     ├── LocalStorageClassAnnotation$1.class
        │ │                         │     ├── LocalStorageClassAnnotation.class
        │ │                         │     └── Sink.class
        │ │                         ├── enums
        │ │                         │ └── EasyUpsertType.class
        │ │                         ├── exceptions
        │ │                         │ └── IllegalDataSourceException.class
        │ │                         └── factory
        │ │                             └── EasyThreadFactory.class
        │ └── generated-sources
        │     └── annotations
        ├── tree.md
        └── wu-easy-upsert-spring-boot-autoconfigure.iml
        
        31 directories, 30 files

### 新增 PULASR INFLXDB H2(均未实现)