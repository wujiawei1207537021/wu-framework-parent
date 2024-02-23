package com.wu.smart.acw.server.rpc;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyRpc;
import com.wu.framework.inner.layer.data.dictionary.api.ConvertApi;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@LazyRpc
public class ConvertApiRpc implements ConvertApi {

    /**
     * description 查询多个字典
     *
     * @param convertItems 字典大项
     * @param order        控制 code 和name 的顺序
     * @return Map<String, Map < String, String>> 字典大项
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public Map<String, Map<String, String>> getConvertDataByItems(List<String> convertItems, boolean order) {
        return new LinkedHashMap<>();
    }
}
