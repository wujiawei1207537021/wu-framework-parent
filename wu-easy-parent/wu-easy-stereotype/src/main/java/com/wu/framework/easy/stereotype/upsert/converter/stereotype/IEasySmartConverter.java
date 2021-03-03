package com.wu.framework.easy.stereotype.upsert.converter.stereotype;

import org.springframework.lang.NonNull;

public interface IEasySmartConverter {

    /**
     * @param
     * @return
     * @describe 智能填充数据
     * @author Jia wei Wu
     * @date 2021/3/3 9:23 下午
     **/
    void smartFillField(@NonNull Object source, @NonNull Object target);
}
