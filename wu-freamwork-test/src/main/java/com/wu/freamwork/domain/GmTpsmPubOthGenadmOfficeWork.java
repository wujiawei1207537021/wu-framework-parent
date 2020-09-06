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

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 全市统一行政服务软件系统"物理大厅收件的办件信息
 * @author x
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)


public class GmTpsmPubOthGenadmOfficeWork {




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



    private String projectName;



    private String sn;



    private String promiseDay;



    private String declareTime;



    private String receiveDeptName;



    private String receiveDeptCode;



    private String applyType;



    private String proposerAreaCode;



    private String applyName;




}
