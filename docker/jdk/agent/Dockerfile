FROM 1207537021/java-1.8.0-openjdk:jdk

ENV BACKEND_SERVICE cloud-skywalking:11800
ENV SERVICE_NAME  demo-app

COPY apache-skywalking-apm-bin-es7-8.7.0/skywalking-agent.jar /agent/

ENTRYPOINT exec java -server $JAVA_OPTS -javaagent:/agent/skywalking-agent.jar -Dskywalking.collector.backend_service=$BACKEND_SERVICE -Dskywalking.agent.service_name=$SERVICE_NAME -jar /app.jar $PARAMS


