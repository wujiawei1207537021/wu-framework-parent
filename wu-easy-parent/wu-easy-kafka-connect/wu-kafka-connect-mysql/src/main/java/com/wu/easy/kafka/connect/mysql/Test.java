package com.wu.easy.kafka.connect.mysql;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/10/5 下午5:42
 */
public class Test {

    public static void main(String[] args) {
        SinkConnector sinkConnector = new SinkConnector() {
            @Override
            public void start(Map<String, String> map) {

            }

            @Override
            public Class<? extends Task> taskClass() {
                return null;
            }

            @Override
            public List<Map<String, String>> taskConfigs(int i) {
                return null;
            }

            @Override
            public void stop() {

            }

            @Override
            public ConfigDef config() {
                return null;
            }

            @Override
            public String version() {
                return null;
            }
        };
        sinkConnector.start(null);
    }
}
