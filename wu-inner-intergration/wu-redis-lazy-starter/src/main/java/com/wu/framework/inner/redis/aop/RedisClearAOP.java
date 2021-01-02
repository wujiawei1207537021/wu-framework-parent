package com.wu.framework.inner.redis.aop;


import com.wu.framework.inner.redis.annotation.RedisClear;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Component
@Aspect
public class RedisClearAOP {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    //    TODO  执行完后执行?
    @Pointcut("@annotation(redisClear)")
    public void redisClearPoint(RedisClear redisClear) {
        System.out.println("清除redis");
    }


    @Before("redisClearPoint(redisClear)")
    public void permissionCheck(JoinPoint point, RedisClear redisClear) {
        System.out.println("@Before：模拟权限检查...");
        System.out.println("@Before：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        System.out.println("@Before：参数为：" + Arrays.toString(point.getArgs()));
        System.out.println("@Before：被织入的目标对象为：" + point.getTarget());
    }


    @After("redisClearPoint(redisClear)")
    public void releaseResource(JoinPoint point, RedisClear redisClear) {
        System.out.println("@After：模拟释放资源...");
        System.out.println("@After：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        System.out.println("@After：参数为：" + Arrays.toString(point.getArgs()));
        System.out.println("@After：被织入的目标对象为：" + point.getTarget());

        String key = redisClear.key();
        // key --> nameAlias
        if (ObjectUtils.isEmpty(key)) {
            if (!ObjectUtils.isEmpty(redisClear.nameAlias())) {
                MethodSignature methodSignature = (MethodSignature) point.getSignature();
                Parameter[] parameters = methodSignature.getMethod().getParameters();
                //step 2判断类型
                if (redisClear.nameClass().equals(String.class)) {
                    //step 1 从参数中获取数据
                    for (int i = 0; i < parameters.length; i++) {
                        if (parameters[i].getName().equals(redisClear.nameAlias())) {
                            key = (String) point.getArgs()[i];
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < parameters.length; i++) {
                        if (parameters[i].getType().equals(redisClear.nameClass())) {
                            try {
                                Field field = parameters[i].getType().getDeclaredField(redisClear.nameAlias());
                                field.setAccessible(true);
                                key = (String) field.get(point.getArgs()[i]);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (ObjectUtils.isEmpty(key)) {
                    System.out.println("参数中无法获取" + redisClear.nameAlias());
                    return;
                }
            } else {
                System.out.println("RedisClear 注解属性key 或nameAlias 必须包含一个");
            }
        }

        if (!ObjectUtils.isEmpty(redisClear.prefix())) {
            key = redisClear.prefix() + key;
        }
        if (!ObjectUtils.isEmpty(redisClear.suffix())) {
            key = key + redisClear.suffix();
        }
        stringRedisTemplate.delete(key);
    }

    @AfterReturning(pointcut = "redisClearPoint(redisClear)",
            returning = "returnValue")
    public void log(JoinPoint point, RedisClear redisClear, Object returnValue) {
        System.out.println("@AfterReturning：模拟日志记录功能...");
        System.out.println("@AfterReturning：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        System.out.println("@AfterReturning：参数为：" +
                Arrays.toString(point.getArgs()));
        System.out.println("@AfterReturning：返回值为：" + returnValue);
        System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
    }


}
