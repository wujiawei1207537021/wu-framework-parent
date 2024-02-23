# wu-framework-parent

    安装
        brew  install gpg
    创建
        gpg --gen-key
    发送
        gpg --keyserver hkp://pool.sks-keyservers.net --send-keys D63ABA42B8ABC9DFCED412B7BB95A422DD6915C1
        
        host pool.sks-keyservers.net
        
        gpg --keyserver hkp://209.244.105.201 --send-keys D63ABA42B8ABC9DFCED412B7BB95A422DD6915C1

# maven

     mvn clean install
    
     mvn deploy -P oss -X    

     export GPG_TTY=$(tty)

## mvnd

     mvnd clean install -T24
     mvnd deploy -P oss -X -T24
    
     export GPG_TTY=$(tty)

### 1.0.4

     功能: 
        1.添加layer 层的概念
        2.自模块保持版本一直
        3.重构upsert 策略
        4.适配HBase 数据库操作
        5.添加记忆芯片功能(仿生)

### 1.0.5

    使用spring-boot 2.5.2 jdk 1.8
    1.shiro 版本启用 替换为 authorization
    2.后续将持续更新authorization
    3.重构upsert框架
        新增pulsar 组件数据存储
        新增hbase 组件数据存储

### 1.0.6

    使用spring-boot 2.6.0 jdk 11

### 1.0.7

    使用spring-boot 3.0.0  jdk 17
    ACW 模块 auto code write
    