package com.wu.framework.pojo.qo;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ Description   :  通用搜索实体
 * @ Author        :  wujiawei
 * @ CreateDate    :  2019/12/24 0024 9:25
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2019/12/24 0024 9:25
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */

@Data
public class UniversalSearchQO {
    //TODO  methodName 属性添加


    private Integer id;


    private String keyWord;


    private String type;


    private Integer associationId;


    private Boolean pagination = false;


    private Integer status;


    private LocalDateTime startTime;


    private LocalDateTime endTime;
}
