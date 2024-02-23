FROM centos:7
MAINTAINER wujiawei <1207537021@qq.com>

RUN echo "Asia/Shanghai" > /etc/timezone


RUN yum install -y java-1.8.0-openjdk.x86_64


ENV APP_JAR_NAME=$APP_NAME \
    JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom" \
    PARAMS=""


COPY app*.jar /app.jar



ENTRYPOINT exec java -server -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/heapdump.dump -XX:+UnlockExperimentalVMOptions  --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED $JAVA_OPTS  -jar /app.jar $PARAMS

#java -javaagent:/apache-skywalking-apm-bin/agent/skywalking-agent.jar=agent.service_name=app-service -jar app-service.jar &

# -javaagent:/Users/wujiawei/IdeaProjects/wu-framework-parent/docker/jdk/agent/skywalking-agent.jar -Dskywalking.collector.backend_service=192.168.26.112:11800 -Dskywalking.agent.service_name=Demo03App -Dskywalking.collector.backend_service=192.168.26.112:11800





