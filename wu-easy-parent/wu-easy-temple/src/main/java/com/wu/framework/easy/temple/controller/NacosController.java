package com.wu.framework.easy.temple.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.wu.framework.easy.stereotype.web.EasyController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/12/30 8:02 下午
 */
@EasyController("config")
public class NacosController {


    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @GetMapping(value = "/get")
    public boolean get() {
        return useLocalCache;
    }

//    @NacosInjected
//    private NamingService namingService;
//
//    @GetMapping(value = "/getInstance")
//    public List<Instance> getInstance(@RequestParam String serviceName) throws NacosException {
//        return namingService.getAllInstances(serviceName);
//    }
}
