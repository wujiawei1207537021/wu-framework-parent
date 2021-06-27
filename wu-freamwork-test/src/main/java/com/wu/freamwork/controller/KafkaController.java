package com.wu.freamwork.controller;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.easy.stereotype.upsert.SpringUpsertConfig;
import com.wu.framework.inner.lazy.database.expand.database.persistence.map.EasyHashMap;
import com.wu.framework.inner.layer.util.FileUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author Jiawei Wu
 * @date 2021/1/27 上午10:24
 */
//@EasyController("/public/kafka")
public class KafkaController {


    private final SpringUpsertConfig springUpsertConfig;
    // 车牌+时间
    Map<String, List<String>> TIME_VEH = new EasyHashMap<>();


    public KafkaController(SpringUpsertConfig springUpsertConfig) {
        this.springUpsertConfig = springUpsertConfig;
    }


//    @KafkaListener(groupId = "test2",topicPartitions = {@TopicPartition(topic = "hzl.test.aaa",partitionOffsets = @PartitionOffset(partition = "0",initialOffset = "-1"))})
//    public void cousumerB(String msg) {
//
//    }

    //    @KafkaListener( groupId = "wujiawei", concurrency = "100", topicPartitions = {@TopicPartition(partitionOffsets = @PartitionOffset(partition = "0",initialOffset = "0"),topic = "connect_source_jt809_dnygps")})
    @KafkaListener(groupId = "wujiawei", concurrency = "100", topics = {"kafka_topic"})
    public void processMessage(ConsumerRecord<String, String> record) throws IOException {
//        确认消费
        System.out.println(record.value());
        JSONObject jsonObject = JSONObject.parseObject(record.value(), JSONObject.class);
        String vehicleNo = jsonObject.getString("vehicleNo");
        String key = vehicleNo + " 时间：" + jsonObject.getString("time");
        List<String> orDefault = TIME_VEH.getOrDefault(key, new ArrayList<>());
        orDefault.add(record.value());
        TIME_VEH.put(key, orDefault);

//        System.out.println("时间重复数据量:" + TIME_VEH.values().stream().filter(strings -> strings.size() > 1).count());
    }

    @PreDestroy
    public void writer() throws Exception {
        BufferedWriter file = FileUtil.createFile(springUpsertConfig.getCacheFileAddress(), "809数据重复");
        TIME_VEH.forEach((s, strings) -> {
            if (strings.size() > 1) {
                try {
                    file.newLine();
                    file.write("重复数据" + s);
                    file.newLine();
                    for (String string : strings) {
                        file.write(string);
                        file.newLine();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 数据量大于 30 的数据
        BufferedWriter differentPlatforms = FileUtil.createFile(springUpsertConfig.getCacheFileAddress(), "msgGNSSCenterId重复");
        List<String> msgGNSSCenterId = TIME_VEH.values().stream().filter(strings -> strings.size() > 30).map(strings -> {
            String s = strings.get(0);
            EasyHashMap easyHashMap = JSONObject.parseObject(s, EasyHashMap.class);
            return String.format("msgGNSSCenterId: %s, 车牌 %s  重复次数 %s 原始数据【%s】",
                    easyHashMap.get("msgGNSSCenterId"), easyHashMap.get("vehicleNo"), strings.size(), s);
        }).collect(Collectors.toList());
        for (String s : msgGNSSCenterId) {
            differentPlatforms.newLine();
            ;
            differentPlatforms.write(s);
        }
        differentPlatforms.close();
        file.close();
    }


}
