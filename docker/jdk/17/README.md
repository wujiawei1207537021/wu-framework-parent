## 打镜像命令

[//]: # (M1芯片问题)
docker build -t 1207537021/jdk-17.0.7-aarch:jdk .

docker build -t 1207537021/jdk-17.0.7-x64:jdk -f Dockerfile-x64 .

docker login --username=1207537021@qq.com registry.cn-hangzhou.aliyuncs.com

```shell

docker build -t registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-framework-parent:jdk-17.0.7-x64 -f Dockerfile-x64 .
#docker push registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-framework-parent:jdk-17.0.7-x64


```



