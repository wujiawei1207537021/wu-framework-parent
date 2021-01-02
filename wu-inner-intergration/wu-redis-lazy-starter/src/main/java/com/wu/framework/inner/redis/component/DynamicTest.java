//package com.wuframework.redis.component;
//
//
//import com.yuntsoft.msa.dynamic.routing.annotation.RedisClear;
//import lombok.Data;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//
//@RestController
//@RequestMapping("/dynamic/test")
//public class DynamicTest {
//
//    @Resource
//    private CustomRedisTemplate customRedisTemplate;
//
//
//    /***
//     * 测试自定义一redis
//     */
//    @GetMapping("/get1")
//    public void setRedis() {
//        customRedisTemplate.opsForValue().set("test1", LocalDateTime.now().toString());
//    }
//
//    /***
//     * 测试自定义redis 切换数据库
//     */
//    @GetMapping("/get2/{db}")
//    public void setRedis2(@PathVariable() Integer db) {
//        customRedisTemplate.setRedisDB(db).opsForValue().set("test2", LocalDateTime.now().toString());
//    }
//
//
//    /***
//     * 切面删除redis
//     */
//    @RedisClear(key = "test1")
//    @GetMapping("/get3/{db}")
//    public void setRedis3(@PathVariable() Integer db) {
//        customRedisTemplate.setRedisDB(db).opsForValue().set("test2", LocalDateTime.now().toString());
//    }
//
//
//    /***
//     * 切面删除redis
//     */
//    @RedisClear(nameAlias = "nameAlias")
//    @GetMapping("/get4/{db}/{nameAlias}")
//    public void setRedis4(@PathVariable() Integer db, @PathVariable String nameAlias) {
//        customRedisTemplate.setRedisDB(db).opsForValue().set("test2", LocalDateTime.now().toString());
//    }
//
//
//    /***
//     * 切面删除redis
//     */
//    @RedisClear(nameAlias = "ss", nameClass = cc.class)
//    @PostMapping("/get5/{db}/")
//    public void setRedis5(@PathVariable() Integer db, @RequestBody cc cc) {
//        customRedisTemplate.setRedisDB(db).opsForValue().set("test2", LocalDateTime.now().toString());
//    }
//
//    @Data
//    public static class cc {
//        private String ss;
//    }
//
//
//}
