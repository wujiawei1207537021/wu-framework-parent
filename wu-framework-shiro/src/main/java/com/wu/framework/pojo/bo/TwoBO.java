package com.wu.framework.pojo.bo;

import lombok.Data;

/**
 * @ Description   :  两个对象
 * @ Author        :  wujiawei
 * @ CreateDate    :  2019/12/24 0024 9:31
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2019/12/24 0024 9:31
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */

@Data
public class TwoBO<F, S> {

    private F first;

    private S second;
}
