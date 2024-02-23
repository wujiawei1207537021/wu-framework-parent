## BUILD IMAGE

```linux
docker build -t docker-registry.laihui.com/library/nginx:1.13.0-alpine .
docker push docker-registry.laihui.com/library/nginx:1.13.0-alpine
```
###     配置信息
    /etc/nginx/conf.d

### 镜像导出

docker save > nginx:1.13.0-alpine.tar nginx:1.13.0-alpine

### 镜像导入

docker load -i nginx:1.13.0-alpine.tar docker tag nginx:1.13.0-alpine docker-registry.laihui.com/library/nginx:
1.13.0-alpine

docker login docker-registry.laihui.com -u admin -p Harbor12345

docker push docker-registry.laihui.com/library/nginx:1.13.0-alpine

###     
```linux
docker build -t docker-registry.laihui.com/base/nginx:1.13.0 .
docker push docker-registry.laihui.com/base/nginx:1.13.0
```
