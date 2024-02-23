package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.smart.acw.server.service.ClassService;
import io.swagger.annotations.Api;

/**
 * describe : 类控制器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/15 3:50 下午
 */
@Api(tags = "类控制器")
@EasyController("/class")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }


}
