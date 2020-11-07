package com.wu.framework.easy.temple.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:33
 */
@Data
public class UseExcel {

    @JSONField(name = "id")
    private Integer id;

    @JSONField(name = "当前时间")
    private LocalDateTime currentTime;

    @JSONField(name = "描述")
    private String desc;

    @JSONField(name = "类型")
    private String type;
}
