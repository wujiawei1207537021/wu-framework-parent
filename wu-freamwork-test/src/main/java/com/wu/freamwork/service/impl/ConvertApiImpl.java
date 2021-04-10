package com.wu.freamwork.service.impl;


import com.wu.framework.inner.layer.data.ConvertApi;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/11/7 下午4:41
 */
@Service
public class ConvertApiImpl implements ConvertApi {
    /**
     * description 查询多个字典
     *
     * @param ConvertCodes
     * @param order        控制 code 和name 的顺序
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public Map<String, Map<String, String>> getConvertDataByItems(List<String> ConvertCodes, boolean order) {
        Map<String, Map<String, String>> map = new LinkedHashMap();
        Map<String, String> itemMap = new LinkedHashMap();
        itemMap.put("1", "男");
        itemMap.put("2", "女");
        map.put("SEX", itemMap);
        return map;
    }
}
