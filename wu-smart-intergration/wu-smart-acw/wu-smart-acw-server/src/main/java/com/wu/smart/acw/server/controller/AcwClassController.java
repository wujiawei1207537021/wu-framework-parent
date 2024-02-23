package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.smart.acw.server.application.ClassApplication;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * describe : 类控制器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/15 3:50 下午
 */
@Tag(name = "类控制器")
@EasyController("/class")
public class AcwClassController {

    private final ClassApplication classService;

    public AcwClassController(ClassApplication classService) {
        this.classService = classService;
    }


}
