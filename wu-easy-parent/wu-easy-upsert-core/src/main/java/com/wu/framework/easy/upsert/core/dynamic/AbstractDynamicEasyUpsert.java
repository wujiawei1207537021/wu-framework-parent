package com.wu.framework.easy.upsert.core.dynamic;


import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertDS;

/**
 * description EasyUpsert获取IEasyUpsert和注解CustomDS抽象类
 *
 * @author Jia wei Wu
 * @date 2020/9/11 下午1:06
 */
public abstract class AbstractDynamicEasyUpsert {



    public abstract IEasyUpsert determineIEasyUpsert();

}
