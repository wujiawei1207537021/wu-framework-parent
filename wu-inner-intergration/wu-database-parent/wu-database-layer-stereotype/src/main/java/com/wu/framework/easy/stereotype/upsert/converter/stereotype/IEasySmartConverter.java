package com.wu.framework.easy.stereotype.upsert.converter.stereotype;

import org.springframework.lang.NonNull;

public interface IEasySmartConverter {


    /**
     * @param source 数据源
     * @param target 目标数据
     * @return
     * @describe 转换
     * @author Jia wei Wu
     * @date 2021/3/3 11:23 下午
     **/
    Object converter(@NonNull Object source, @NonNull Object target);
}
