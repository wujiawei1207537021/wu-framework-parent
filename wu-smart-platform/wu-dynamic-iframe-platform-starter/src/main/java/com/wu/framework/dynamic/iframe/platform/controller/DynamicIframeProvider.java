package com.wu.framework.dynamic.iframe.platform.controller;

import com.wu.framework.dynamic.iframe.platform.application.DynamicIframeApplication;
import com.wu.framework.dynamic.iframe.platform.application.command.DynamicIframeCommand;
import com.wu.framework.dynamic.iframe.platform.model.dynamic.iframe.DynamicIframe;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import io.swagger.v3.oas.annotations.Parameter;
/**
 * describe 动态Iframe 
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "动态Iframe提供者")
@EasyController("/lazy/dynamic/iframe")
public class DynamicIframeProvider  {

    @Autowired
    private DynamicIframeApplication dynamicIframeApplication;

    /**
     * describe 新增动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @PostMapping("/story")
    public Result<DynamicIframe> story(@RequestBody DynamicIframeCommand dynamicIframeCommand){    return dynamicIframeApplication.story(dynamicIframeCommand);    }
    /**
     * describe 更新动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @PutMapping("/updateOne")
    public Result<DynamicIframe> updateOne(@RequestBody DynamicIframeCommand dynamicIframeCommand){    return dynamicIframeApplication.updateOne(dynamicIframeCommand);    }
    /**
     * describe 查询单个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @GetMapping("/findOne")
    public Result<DynamicIframe> findOne(@ModelAttribute DynamicIframeCommand dynamicIframeCommand){    return dynamicIframeApplication.findOne(dynamicIframeCommand);    }
    /**
     * describe 查询多个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @GetMapping("/findList")
    public Result<List<DynamicIframe>> findList(@ModelAttribute DynamicIframeCommand dynamicIframeCommand){    return dynamicIframeApplication.findList(dynamicIframeCommand);    }
    /**
     * describe 分页查询多个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<DynamicIframe>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute DynamicIframeCommand dynamicIframeCommand){    return dynamicIframeApplication.findPage(size,current,dynamicIframeCommand);    }
    /**
     * describe 删除动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @DeleteMapping("/remove")
    public Result<DynamicIframe> remove(@ModelAttribute DynamicIframeCommand dynamicIframeCommand){    return dynamicIframeApplication.remove(dynamicIframeCommand);    }
}