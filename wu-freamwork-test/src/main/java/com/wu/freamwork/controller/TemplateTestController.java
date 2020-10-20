package com.wu.freamwork.controller;


import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.inner.template.https.HttpsRestTemplate;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

import static java.lang.Thread.sleep;

/**
 * @author : 吴佳伟
 * @version : 1.0
 * @describe: 测试https
 * @date : 2020/6/17 10:42 下午
 */
@Api(tags = "Template 模板测试")
@EasyController("/test/template")
public class TemplateTestController {

    @Resource
    private HttpsRestTemplate httpsRestTemplate;

    //    @PostConstruct
    public void init() {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            new Thread(() -> {
                String url = "https://218.5.80.20:5738/api/Busline/Get";
                String res = httpsRestTemplate.postForObject(url, null, String.class);
                System.out.println("第" + finalI + "次" + res);
            }).run();
        }
    }

    //    @PostConstruct
    public void run() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                init();
            }).run();
        }
    }

}
