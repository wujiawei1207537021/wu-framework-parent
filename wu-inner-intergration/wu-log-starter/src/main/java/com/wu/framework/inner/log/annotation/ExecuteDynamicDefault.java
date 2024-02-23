package com.wu.framework.inner.log.annotation;

/**
 * @ Description   :  默认实现执行
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/4/10 0010 11:20
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/4/10 0010 11:20
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


public class ExecuteDynamicDefault implements ExecuteDynamic {

    @Override
    public void execute(Object o) {
        System.out.println("默认什么也不执行,走个过场" + o.toString());
    }
}
