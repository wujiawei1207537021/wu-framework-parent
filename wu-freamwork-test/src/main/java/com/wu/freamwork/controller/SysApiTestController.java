package com.wu.freamwork.controller;

import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.freamwork.domain.SysApiUserTestPO;
import com.wu.freamwork.service.SysApiTestService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/11/7 下午4:35
 */
@EasyController("/sys/api/test/controller")
public class SysApiTestController {


    private final SysApiTestService sysApiTestService;

    public SysApiTestController(SysApiTestService sysApiTestService) {
        this.sysApiTestService = sysApiTestService;
    }
    @GetMapping("/transform")
    public List<SysApiUserTestPO> transformUser(){
        return sysApiTestService.transformUser();
    }
}
