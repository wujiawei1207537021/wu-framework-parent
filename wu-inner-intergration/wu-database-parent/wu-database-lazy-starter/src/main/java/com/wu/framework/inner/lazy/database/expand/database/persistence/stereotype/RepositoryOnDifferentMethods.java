package com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype;

import com.wu.framework.inner.lazy.database.expand.database.persistence.constant.LayerOperationMethodCounts;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyOperationProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 获取不同方法上指定执行类
 * @date : 2020/7/3 下午10:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Indexed
public @interface RepositoryOnDifferentMethods {


    String methodName() default LayerOperationMethodCounts.MISS;


    /**
     * @param
     * @return
     * @describe 代理类
     * @author Jia wei Wu
     * @date 2021/3/28 10:16 下午
     **/
    Class proxyClass() default LazyOperationProxy.class;


}
