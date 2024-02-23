//package com.wu.freamwork.controller;
//
//
//import com.wu.framework.inner.layer.web.EasyController;
//import com.wu.framework.inner.template.https.HttpsRestTemplate;
//import com.wu.framework.inner.template.proxy.http.ProxyRestTemplate;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.apache.pulsar.shade.io.swagger.annotations.Api;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import jakarta.annotation.Resource;
//
//import static java.lang.Thread.sleep;
//
///**
// * @author : Jia wei Wu
// * @version : 1.0
// * 测试https
// * @date : 2020/6/17 10:42 下午
// */
//@Tag(name = "Template 模板测试")
//@EasyController("/public/test/template")
//public class TemplateTestController {
//
//    @Resource
//    private HttpsRestTemplate httpsRestTemplate;
//    @Resource
//    private ProxyRestTemplate proxyRestTemplate;
//
//    //    @PostConstruct
//    public void init() {
//        for (int i = 0; i < 1000; i++) {
//            int finalI = i;
//            new Thread(() -> {
//                String url = "https://218.5.80.20:5738/api/Busline/Get";
//                String res = httpsRestTemplate.postForObject(url, null, String.class);
//                System.out.println("第" + finalI + "次" + res);
//            }).run();
//        }
//    }
//
//    //    @PostConstruct
//    public void run() throws InterruptedException {
//        for (int i = 0; i < 100; i++) {
//            new Thread(() -> {
//                try {
//                    sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                init();
//            }).run();
//        }
//    }
//
//    @GetMapping("/proxy")
//    public Object proxyHttp(@RequestParam String url) {
//        return proxyRestTemplate.getForObject(url, String.class);
//    }
//
//}
