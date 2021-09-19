package com.wu.smart.acw.core.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.smart.acw.core.service.DemandCodeClassWriteService;
import io.swagger.annotations.Api;

/**
 * describe : 本地写入class
 *
 * @author : 吴佳伟
 * @version 1.0
 * @date : 2021/9/19 5:22 下午
 */
@Api(tags = "需求代码写入")
@EasyController("/demand/writer")
public class DemandCodeClassWriteController {

    private final DemandCodeClassWriteService demandCodeClassWriteService;


    public DemandCodeClassWriteController(DemandCodeClassWriteService demandCodeClassWriteService) {
        this.demandCodeClassWriteService = demandCodeClassWriteService;
    }

}
