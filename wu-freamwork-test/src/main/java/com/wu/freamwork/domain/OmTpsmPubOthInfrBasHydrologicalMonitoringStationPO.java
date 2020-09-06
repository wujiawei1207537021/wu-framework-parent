package com.wu.freamwork.domain;

import java.time.LocalDateTime;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 水文监测站点基础数据
 * @author x
 * @since 2020-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OmTpsmPubOthInfrBasHydrologicalMonitoringStationPO对象", description="水文监测站点基础数据")
public class OmTpsmPubOthInfrBasHydrologicalMonitoringStationPO implements Serializable {

    private static final long serialVersionUID = 1L;



    private Long id;



    private String stationName;



    private String enablingSign;



    private String informationManagementUnit;



    private String pinyinCode;



    private String yearOfStationEstablishment;



    private String floodReportingLevel;



    private String stationType;



    private String yearOfInitialReporting;



    private String exchangeManagementUnit;



    private String stationAddress;



    private String administrativeDivisionCode;



    private String stationCode;



    private String lng;



    private String stationOrientation;



    private String lat;



    private String timestamp;



    private String waterSystemName;



    private Boolean isDeleted;



    private LocalDateTime createTime;



    private LocalDateTime updateTime;


}
