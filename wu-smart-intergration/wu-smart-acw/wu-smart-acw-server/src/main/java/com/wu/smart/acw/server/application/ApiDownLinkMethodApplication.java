package com.wu.smart.acw.server.application;

import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.ApiDownLinkMethodQo;

public interface ApiDownLinkMethodApplication {

    /**
     * describe API 下联 Method 表
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/1 11:00 下午
     **/
    Result save(ApiDownLinkMethodQo apiDownLinkMethodQo);
}
