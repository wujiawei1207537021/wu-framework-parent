package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.smart.acw.server.application.DemandCodeClassWriteApplication;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * describe : 本地写入class
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/9/19 5:22 下午
 */
@Tag(name = "需求代码写入")
@EasyController("/demand/writer")
public class AcwDemandCodeClassWriteController {

    private final DemandCodeClassWriteApplication demandCodeClassWriteService;


    public AcwDemandCodeClassWriteController(DemandCodeClassWriteApplication demandCodeClassWriteService) {
        this.demandCodeClassWriteService = demandCodeClassWriteService;
    }

}
