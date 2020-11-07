package com.wu.freamwork.controller;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.easy.stereotype.web.EasyController;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/10/26 下午2:14
 */
@EasyController("/json")
public class JsonController {


    @PostMapping("/compare")
    public void compareJsonKey(@RequestBody Json json) {
        JSONObject newJsonObject = JSONObject.parseObject(json.getNewJson());
        JSONObject oldJsonObject = JSONObject.parseObject(json.getOldJson());
        List<String> newKeyList = new ArrayList<>(newJsonObject.keySet());
        List<String> oldKeyList = new ArrayList<>(oldJsonObject.keySet());
        // 新增
        List<String> add = newKeyList.stream().filter(key -> !oldKeyList.contains(key)).collect(Collectors.toList());
        // 删除
        List<String> delete = oldKeyList.stream().filter(key -> !newKeyList.contains(key)).collect(Collectors.toList());
        System.out.println("新增字段:" + add);
        System.out.println("删除字段:" + delete);

    }

    @Data
    public static class Json {
        private String newJson;
        private String oldJson;
    }


}
