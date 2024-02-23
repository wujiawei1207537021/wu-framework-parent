# DOCKER JDK IMAGE

## BUILD IMAGE


```shell

mvn clean install 
#docker login --username=1207537021@qq.com registry.cn-hangzhou.aliyuncs.com

docker build -t registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-smart-acw-server:1.2.2-JDK17-SNAPSHOT .
docker push registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-smart-acw-server:1.2.2-JDK17-SNAPSHOT

```

```shell
gu install native-image

gu list

mvn native:build
```

```shell
 mvn clean native:compile -Pnative
```

```RUN
docker run  -d -it -p 18080:18080 -e MAIN_DB_HOST=cloud-mysql:3306 -e MAIN_DB_USERNAME=root -e MAIN_PASSWORD=mysql  --name wu-smart-acw-server registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-smart-acw-server:1.2.2-JDK17-SNAPSHOT


http://127.0.0.1:18080/swagger-ui/index.html

http://127.0.0.1:18080/wu-smart-acw-ui/index.html

```

next
添加表关系维护，自动跳转复杂查询的表数据

