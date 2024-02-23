FROM  redhat/ubi8

MAINTAINER wujiawei <1207537021@qq.com>

COPY jdk1.8.0_341  /usr/java/jdk-8
CMD ["/bin/bash"]
ENV LANG=en_US.UTF-8
ENV JAVA_VERSION=1.8.0_341 JAVA_HOME=/usr/java/jdk-8
ENV PATH=/usr/java/jdk-8/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin

RUN ln -sfT "$JAVA_HOME" /usr/java/default; ln -sfT "$JAVA_HOME" /usr/java/latest;
#RUN for bin in "$JAVA_HOME/bin/"*; do base="$(basename "$bin")"; [ ! -e "/usr/bin/$base" ]; alternatives --install "/usr/bin/$base" "$base" "$bin" 20000; done;
RUN java -Xshare:dump; java -version; javac -version
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV APP_JAR_NAME=$APP_NAME \
    JAVA_OPTS="" \
    PARAMS=""


COPY app*.jar /app.jar
ENTRYPOINT ["/bin/sh" ,"-c", "exec java -server -XX:+UnlockExperimentalVMOptions   $JAVA_OPTS -jar /app.jar $PARAMS"]



