package com.wu.smart.acw.server.application;

import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.ApiQo;

public interface ApiApplication {

    /**
     * describe 新增API
     *
     * @param
     * @param apiQo
     * @return
     * @author Jia wei Wu
     * @date 2022/1/1 10:18 下午
     **/
    Result save(ApiQo apiQo);
}
