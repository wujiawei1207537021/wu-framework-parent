package com.wu.framework.easy.temple.domain;

import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * sys_eqp_opemng_dyn_gps_veh_run
 *
 * @author
 */
@Data
@Document(indexName = "sys_veh_dyn_gps_es_2020.12_m")
public class DynGpsVehRun {

    private String id;
    /**
     * 信息ID
     */
    private String runId;

    /**
     * 号牌号码
     */
    private String plateNum;
    private Long gpsTimestamp;
    private String location;

    /**
     * 经度
     */
    private Double lng;
    /**
     * 维度
     */
    private Double lat;

    /**
     * 速度
     */
    private Double speed;
    /**
     * 方向
     */
    private Long direction;

    /**
     * GPS时间
     */
    private String gpsTime;
    /**
     * 报警状态
     */
    private Long alarm;
    /**
     * GPS时间（日月年）
     */
    private String gpsVdate;

    /**
     * GPS时间（时分秒）
     */
    private String gpsVtime;
    private String businessScopeCode;
    /**
     * 速度车辆行驶记录设备
     */
    private Long ve2;

    /**
     * 车辆当前总里程数
     */
    private Long ve3;

    private Long altitude;
    /**
     * 火星坐标系经度
     */
    private Double marsLng;
    /**
     * 火星坐标系维度
     */
    private Double marsLat;

    private Long isDeleted;

    private String updateTime;


    private String industry;
    private Integer businessType;


    @EasySmartField(name = "@timestamp@")
    private String timestamp = LocalDateTime.now().toString() + "+0800";
}