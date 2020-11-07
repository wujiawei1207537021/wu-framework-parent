package com.wu.inner.sys.api;

import java.util.List;
import java.util.Map;


public interface ConvertApi {

    /**
     * description 查询多个字典
     *
     * @param dictionaryCodes
     * @param order           控制 code 和name 的顺序
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    Map<String, Map<String, String>> getDictionaryAllDataByCodes(List<String> dictionaryCodes, boolean order);
}
