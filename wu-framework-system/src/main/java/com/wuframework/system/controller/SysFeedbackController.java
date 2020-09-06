package com.wuframework.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.response.Result;
import com.wuframework.shiro.annotation.AccessTokenUser;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.db.annotation.RequestPage;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.entity.SysFeedback;
import com.wuframework.system.serivce.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Api(tags = "系统反馈信息")
@CustomController("/system/feedback")
public class SysFeedbackController {

    private FeedbackService feedbackService;

    @Autowired
    public SysFeedbackController(FeedbackService feedbackService, FeedbackService defaultFeedbackService) {
        if (ObjectUtils.isEmpty(feedbackService)) {
            this.feedbackService = defaultFeedbackService;
        } else {
            this.feedbackService = feedbackService;
        }

    }

    @ApiOperation("保存用户反馈信息")
    @PostMapping()
    public Result saveFeedback(@RequestBody SysFeedback sysFeedback, @AccessTokenUser DefaultSysUserDetails defaultSysUserDetails) {
        return feedbackService.saveFeedBack(sysFeedback, defaultSysUserDetails);
    }

    @ApiOperation("更新用户反馈信息")
    @PutMapping()
    public Result updateFeedback(@RequestBody SysFeedback sysFeedback, @AccessTokenUser DefaultSysUserDetails defaultSysUserDetails) {
        return feedbackService.updateFeedback(sysFeedback, defaultSysUserDetails);
    }
    @ApiOperation("查询用户反馈信息")
    @GetMapping()
    public Result selectFeedBackList(@ModelAttribute UniversalSearchQO universalSearchQO, @AccessTokenUser DefaultSysUserDetails defaultSysUserDetails, @RequestPage Page page) {
        return feedbackService.selectFeedBackList(universalSearchQO, page,defaultSysUserDetails);
    }
}
