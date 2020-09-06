package com.wuframework.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.response.Result;
import com.wuframework.shiro.annotation.AccessTokenUser;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.db.annotation.RequestPage;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.entity.SysUserMessage;
import com.wuframework.system.serivce.SysUserMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Api(tags = "系统用户通知查询")
@CustomController("/system/user/message")
public class SysUserMessageController {

    private SysUserMessageService sysUserMessageService;

    public SysUserMessageController(SysUserMessageService sysUserMessageService,SysUserMessageService defaultSysUserMessageService) {
       if(ObjectUtils.isEmpty(sysUserMessageService)){
           this.sysUserMessageService = defaultSysUserMessageService;
       }else {
           this.sysUserMessageService = sysUserMessageService;
       }
    }

    @ApiOperation("用户通知查询")
    @GetMapping()
    public Result queryMessage(@AccessTokenUser DefaultSysUserDetails defaultSysUserDetails, @ModelAttribute UniversalSearchQO universalSearchQO, @RequestPage Page page){
        return sysUserMessageService.queryMessage(defaultSysUserDetails,universalSearchQO,page);
    }

    @ApiOperation("用户消息添加")
    @PostMapping()
    public Result saveMessage(@RequestBody SysUserMessage sysUserMessage){
        return sysUserMessageService.saveMessage(sysUserMessage);
    }
}
