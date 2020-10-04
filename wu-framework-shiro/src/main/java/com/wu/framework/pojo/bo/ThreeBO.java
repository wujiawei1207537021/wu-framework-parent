package com.wu.framework.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ Description   :  三个对象
 * @ Author        :  wujiawei
 * @ CreateDate    :  2019/12/24 0024 9:31
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2019/12/24 0024 9:31
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */
@ApiModel(description = "两个对象")
@Data
public class ThreeBO<F, S, T> {

    @ApiModelProperty(value = "第一个对象")
    private F first;
    @ApiModelProperty(value = "第二个对象")
    private S second;
    @ApiModelProperty(value = "第三个对象")
    private T third;
}
