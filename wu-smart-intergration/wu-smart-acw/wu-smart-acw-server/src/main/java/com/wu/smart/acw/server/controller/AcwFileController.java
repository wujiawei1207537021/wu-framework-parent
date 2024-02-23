package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/10/9 20:58
 */
@Tag(name = "ACW-文件上传")
@EasyController("file")
public class AcwFileController {

    @ApiOperation(value = "新增文件")
    @PostMapping("/save")
    public Result save(@RequestBody MultipartFile multipartFile) {
        return ResultFactory.successOf();
    }
}
