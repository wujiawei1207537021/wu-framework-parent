
FROM registry.cn-hangzhou.aliyuncs.com/wu-lazy/wu-framework-parent:jdk-17.0.7-x64
MAINTAINER wujiawei <1207537021@qq.com>

ADD h2-2023-09-17.zip  /
COPY h2/  /h2
ENV DATA_DIR /opt/h2-data
RUN mkdir -p ${DATA_DIR} \
CMD ["/bin/bash"]

EXPOSE 8082 9092
ENTRYPOINT java -jar /h2/bin/h2-2.2.224.jar




