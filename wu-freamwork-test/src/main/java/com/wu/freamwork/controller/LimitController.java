package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.data.limit.EasyAccessLimit;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.freamwork.domain.DataBaseUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * description limit 限制 控制器
 *
 * @author Jia wei Wu
 * @date 2022/10/09 4:08 下午
 */
@EasyController("/limit")
public class LimitController {


    @EasyAccessLimit(seconds = 1000, maxCount = 2, key = "#id")
    @GetMapping("/sameId")
    @ApiOperation(value = "接口1s内 同一个id 只能访问两次")
    public void sameId(String id) {
        System.out.println("接口只能访问两次");
    }


    @EasyAccessLimit(seconds = 1000, maxCount = 2, key = "#dataBaseUser.username")
    @GetMapping("/sameName")
    @ApiOperation(value = "接口1s内 同一个name 只能访问两次")
    public void sameName(@ModelAttribute DataBaseUser dataBaseUser) {
        System.out.println("接口1s内 同一个name 只能访问两次");
    }

}
