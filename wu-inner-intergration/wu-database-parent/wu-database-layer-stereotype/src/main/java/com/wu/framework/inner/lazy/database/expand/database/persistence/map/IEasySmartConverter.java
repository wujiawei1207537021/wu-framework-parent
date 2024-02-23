package com.wu.framework.inner.lazy.database.expand.database.persistence.map;

import com.wu.framework.inner.layer.stereotype.Layer;
import com.wu.framework.inner.layer.stereotype.LayerDefault;
import org.springframework.lang.NonNull;

public interface IEasySmartConverter extends Layer, LayerDefault {


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
