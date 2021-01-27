package com.wu.freamwork.controller;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.upsert.util.FileUtil;
import com.wu.framework.easy.stereotype.web.EasyController;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2021/1/27 上午10:24
 */
@EnableKafka
@EasyController
public class KafkaController {

    private final UpsertConfig upsertConfig;

    public KafkaController(UpsertConfig upsertConfig) {
        this.upsertConfig = upsertConfig;
    }


    Map<String, List<String>> TIME_VEH = new EasyHashMap<>();

    @KafkaListener(topics = "connect_source_jt809_dnygps", id = "wujiawei", groupId = "wujiawei")
    public void processMessage(ConsumerRecord<String, String> record) throws IOException {
//        确认消费

        JSONObject jsonObject = JSONObject.parseObject(record.value(), JSONObject.class);
        String vehicleNo = jsonObject.getString("vehicleNo");
        String time = vehicleNo +" 时间："+ jsonObject.getString("time");
        List<String> orDefault = TIME_VEH.getOrDefault(time, new ArrayList<>());
        orDefault.add(record.value());
        TIME_VEH.put(time, orDefault);

//        System.out.println("时间重复数据量:" + TIME_VEH.values().stream().filter(strings -> strings.size() > 1).count());
    }

    @PreDestroy
    public void writer() throws Exception {
        BufferedWriter file = FileUtil.createFile(upsertConfig.getCacheFileAddress(), "809数据重复");
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
        file.close();
    }


    @KafkaListener(topics = "connect_source_jt809_dnygps", id = "wujiawei1", groupId = "wujiawei")
    public void processMessage1(ConsumerRecord<String, String> record) throws IOException {
//        确认消费

        JSONObject jsonObject = JSONObject.parseObject(record.value(), JSONObject.class);
        String vehicleNo = jsonObject.getString("vehicleNo");
        String time = vehicleNo +" 时间："+ jsonObject.getString("time");
        List<String> orDefault = TIME_VEH.getOrDefault(time, new ArrayList<>());
        orDefault.add(record.value());
        TIME_VEH.put(time, orDefault);
    }


    @KafkaListener(topics = "connect_source_jt809_dnygps", id = "wujiawei2", groupId = "wujiawei")
    public void processMessage2(ConsumerRecord<String, String> record) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(record.value(), JSONObject.class);
        String vehicleNo = jsonObject.getString("vehicleNo");
        String time = vehicleNo +" 时间："+ jsonObject.getString("time");
        List<String> orDefault = TIME_VEH.getOrDefault(time, new ArrayList<>());
        orDefault.add(record.value());
        TIME_VEH.put(time, orDefault);
    }


    @KafkaListener(topics = "connect_source_jt809_dnygps", id = "wujiawei3", groupId = "wujiawei")
    public void processMessage3(ConsumerRecord<String, String> record) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(record.value(), JSONObject.class);
        String vehicleNo = jsonObject.getString("vehicleNo");
        String time = vehicleNo +" 时间："+ jsonObject.getString("time");
        List<String> orDefault = TIME_VEH.getOrDefault(time, new ArrayList<>());
        orDefault.add(record.value());
        TIME_VEH.put(time, orDefault);
    }

}
