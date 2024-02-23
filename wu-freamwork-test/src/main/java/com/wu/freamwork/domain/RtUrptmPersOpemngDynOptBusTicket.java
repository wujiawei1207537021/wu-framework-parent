package com.wu.freamwork.domain;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/7/10 下午2:26
 */

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 'baseinfo.rn_rmm_infr_view_common_facility' is not BASE TABLE
 *
 * @author xx
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)


public class RtUrptmPersOpemngDynOptBusTicket {


    private Integer id;


    private String ticketId;


    private String elTicket;


    private String ticketCode;


    private String ticketNumber;


    private Integer sellType;


    private String busCodeId;


    private String orderNo;


    private String busCode;


    private String lineCode;


    private String departTime;


    private String customerName;


    private Integer certType;


    private String certCode;


    private String customerPhone;


    private String startDepotCode;


    private String arrivalDepotCode;


    private BigDecimal ticketPrice;


    private Integer ticketState;


    private Integer seatCode;


    private Integer seatLevel;


    private Integer ticketDistance;


    private Integer ticketType;


    private String sellTime;


    private String sellCompanyCode;


    private String sellStationCode;


    private String ticketCompanyCode;


    private String ticketStationCode;


    private BigDecimal returnMoney;


    private BigDecimal returnFee;


    private String returnTime;


    private String checkTime;


    private Integer isDeleted;


    private LocalDateTime createTime;


    private LocalDateTime updateTime;


}
