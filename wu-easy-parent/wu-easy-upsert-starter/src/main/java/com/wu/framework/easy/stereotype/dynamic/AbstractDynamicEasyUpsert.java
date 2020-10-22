package com.wu.framework.easy.stereotype.dynamic;


import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertDS;

/**
 * description EasyUpsert获取IEasyUpsert和注解CustomDS抽象类
 *
 * @author 吴佳伟
 * @date 2020/9/11 下午1:06
 */
public abstract class AbstractDynamicEasyUpsert {

    public abstract EasyUpsertDS determineEasyUpsertDS();

    public abstract IEasyUpsert determineIEasyUpsert();

}
