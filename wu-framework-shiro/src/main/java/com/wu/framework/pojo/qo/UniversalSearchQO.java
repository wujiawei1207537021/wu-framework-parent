package com.wu.framework.pojo.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(description = "通用搜索实体")
@Data
public class UniversalSearchQO {
    //TODO  name 属性添加

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "关键字")
    private String keyWord;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "关联id")
    private Integer associationId;

    @ApiModelProperty(value = "是否分页 true 分页 false 不分页 (默认false)")
    private Boolean pagination = false;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
}
