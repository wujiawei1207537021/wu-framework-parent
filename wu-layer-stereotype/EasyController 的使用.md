# wu-layer-stereotype

wu-layer-stereotype是一个简单的无层(分层)注解使用，具有以下优势

- 使用简单，只需一个注解即可搞定
- 注解支持二次开发

# 快速入门

## 1.导入依赖

框架默认使用spring-boot环境

``` java
        <dependency>
            <groupId>top.wu2020</groupId>
            <artifactId>wu-layer-stereotype</artifactId>
            <version>1.0.5</version>
        </dependency>
```

[最新版本](https://mvnrepository.com/artifact/top.wu2020/wu-layer-stereotype)

## 2.使用

### 注解使用

```java
    @Api(tags = "简单注解使用方式")
    @EasyController("/easy/test")
    public class EasyControllerAPITest {
        @ApiOperation("简单注解使用方式 运行run")
        @PostMapping("/run")
        public void run(){}

    }

```

### 常规注解使用

```java
    @Api(tags = "常规注解使用方式")
    @RestController("/normal/test")
    @RequestMapping
    public class NormalControllerAPITest {

        @ApiOperation("常规注解使用方式 运行run")
        @PostMapping("/run")
        public void run(){}

    }
   
```

### 古老的写法

```java
    @Controller
    @ResponseBody
    @Api(tags = "old常规注解使用方式")
    @RequestMapping("/old/test")
    public class NormalOldControllerAPITest {

        @ApiOperation("常规注解使用方式 运行run")
        @PostMapping("/run")
        public void run(){}

    }
```

### 就想单纯的一个注解打你两个注解😄

![在这里插入图片描述](https://img-blog.csdnimg.cn/5aaf91b31c9941e79e978b954c2f518b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA6L-Y5Zyo5b-Z56KM55qE5ZC05bCP5LqM,size_20,color_FFFFFF,t_70,g_se,x_16)

### 测试代码奉上

```java
/**
 * @EasyController 测试模版
 */
@EasyController("/test")
public class EasyControllerTest {

    @Api(tags = "注解使用方式简单")
    @EasyController("/easy/test")
    public class EasyControllerAPITest {
        @ApiOperation("注解使用方式简单 运行run")
        @PostMapping("/run")
        public void run(){}

    }



    @Api(tags = "注解使用方式常规")
    @RestController
    @RequestMapping("/normal/test")
    public class NormalControllerAPITest {

        @ApiOperation("注解使用方式常规 运行run")
        @PostMapping("/run")
        public void run(){}

    }

    @Controller
    @ResponseBody
    @Api(tags = "注解使用方式old常规")
    @RequestMapping("/old/test")
    public class NormalOldControllerAPITest {

        @ApiOperation("注解使用方式old常规 运行run")
        @PostMapping("/run")
        public void run(){}

    }
}


```


