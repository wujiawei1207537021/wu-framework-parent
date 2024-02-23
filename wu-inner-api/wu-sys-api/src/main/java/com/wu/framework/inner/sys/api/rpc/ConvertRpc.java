package com.wu.framework.inner.sys.api.rpc;

import com.wu.framework.inner.sys.api.ConvertApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.util.List;
import java.util.Map;

@ConditionalOnMissingBean(ConvertApi.class)
public class ConvertRpc implements ConvertApi {
    /**
     * description 查询多个字典
     *
     * @param convertItems
     * @param order        控制 code 和name 的顺序
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public Map<String, Map<String, String>> getConvertDataByItems(List<String> convertItems, boolean order) {
        return null;
    }
}
