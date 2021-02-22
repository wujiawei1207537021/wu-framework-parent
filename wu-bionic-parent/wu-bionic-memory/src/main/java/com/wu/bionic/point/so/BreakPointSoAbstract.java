package com.wu.bionic.point.so;



import com.wu.bionic.point.config.Scheduling;

import java.lang.reflect.Method;

public abstract class BreakPointSoAbstract {

    /**
     * description 获取断点源头
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/2/3 上午11:08
     */
    public abstract Method getMethod() throws NoSuchMethodException;

    /**
     * description 断点参数
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/2/3 上午11:09
     */
    public abstract Object[] getParams();


    /**
     * description 断点启动  spring 容器管理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/2/3 下午3:36
     */
    public Object invoke() throws Exception {
        Method method = getMethod();
        return method.invoke(Scheduling.applicationContext.getBean(method.getDeclaringClass()), getParams());
    }


}
