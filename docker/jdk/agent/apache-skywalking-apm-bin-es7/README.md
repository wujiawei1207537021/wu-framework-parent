### 镜像构建

docker build . -t skywalking-agent-sidecar:8.3.0

### 镜像导出

docker save >skywalking-agent-sidecar-8.3.0.tar skywalking-agent-sidecar:8.3.0

### 镜像导入

docker load -i skywalking-agent-sidecar-8.3.0.tar docker tag skywalking-agent-sidecar:8.3.0
docker-registry.laihui.com/library/skywalking-agent-sidecar:8.3.0

docker login docker-registry.laihui.com -u admin -p Harbor12345

docker push docker-registry.laihui.com/library/skywalking-agent-sidecar:8.3.0

###            
