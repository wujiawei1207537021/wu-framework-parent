package com.wu.framework.inner.layer.data.dictionary.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultConvertApi implements ConvertApi{
    /**
     * description 查询多个字典
     *
     * @param convertItems 字典大项
     * @param order        控制 code 和name 的顺序
     * @return Map<String, Map < String, String>> 字典大项
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public Map<String, Map<String, String>> getConvertDataByItems(List<String> convertItems, boolean order) {
        return new ConcurrentHashMap<>();
    }
}
