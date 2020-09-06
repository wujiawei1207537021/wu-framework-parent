package com.w.framework.inner.log.annotation;

/**
 * @ Description   :  日志
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/4/10 0010 11:20
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/4/10 0010 11:20
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


public class ExecuteDynamicLog implements ExecuteDynamic {

    @Override
    public void execute(Object o) {
//        TODO
        System.out.println("日志处理" + o.toString());
    }
}
