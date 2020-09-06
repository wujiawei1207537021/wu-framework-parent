package com.wu.freamwork.domain;









import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 全市统一行政服务软件系统 结果信息
 * @author x
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)


public class GmTpsmPubOthGenadmOfficeWorkResult  {




    private Long id;



    private String applyFrom;



    private String type;



    private String unid;



    private String xsd;



    private String fromAreaCode;



    private String fromAreaName;



    private String toAreaCode;



    private String toAreaName;



    private String time;



    private String sn;



    private String processedUser;



    private String processedTime;



    private String result;



    private String deliveryResult;



    private String processedOpinion;



    private String openWay;



    private String makePublic;



    private String seqNo;



    private String validateDate;



    private String licenseName;



    private String startDate;



    private String certUnit;



    private String certUnitCode;



    private String certContent;



    private String certIfiedTime;



    private String holder;




}
