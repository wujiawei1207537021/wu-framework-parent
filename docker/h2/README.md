# DOCKER JDK IMAGE
账号密码设置为admin/123456


## BUILD IMAGE

```shell

 docker build -t h2:2.2.224 .

```

```RUN

$ docker run -itd --name h2 -p 8082:8082 -p 9092:9092 h2:1.4.199

```
