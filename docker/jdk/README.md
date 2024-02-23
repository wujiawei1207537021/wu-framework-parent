# DOCKER JDK IMAGE

## BUILD IMAGE

```linux
docker build -t 1207537021/java-1.8.0-openjdk:jdk .

含有 agent 打包
docker build -t 1207537021/java-1.8.0-openjdk:skywalking .

```

```RUN
docker run -d -it -p 18801:18801 --name jdk-app 1207537021/java-1.8.0-openjdk:jdk

http://127.0.0.1:18801/swagger-ui/index.html

docker run -d -it -p 18801:18801 -e BACKEND_SERVICE=192.168.26.112:11800 -e APP_JAR_NAME=sss --name jdk-skywalking-app 1207537021/java-1.8.0-openjdk:skywalking

```
