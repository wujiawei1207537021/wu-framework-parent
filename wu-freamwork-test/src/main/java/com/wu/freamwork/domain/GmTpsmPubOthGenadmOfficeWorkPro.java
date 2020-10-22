package com.wu.freamwork.domain;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2020/7/9 上午11:31
 */


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 全市统一行政服务软件系统"过程信息
 *
 * @author x
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)


public class GmTpsmPubOthGenadmOfficeWorkPro {


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


    private String code;


    private String name;


    private String sname;


    private String nextName;


    private String nextUser;


    private String processedUser;


    private String startTime;


    private String modified;


    private String opinionType;


    private String processedOpinion;


    private String promiseendTime;


    private String remark;


}

