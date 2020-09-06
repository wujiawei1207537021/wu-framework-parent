package com.wuframework.shiro.web.aop;


import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.login.LoginService;
import com.wuframework.shiro.annotation.AccessLimit;
import com.wuframework.shiro.config.pro.ShiroProperties;
import com.wuframework.shiro.exceptions.CustomException;
import com.wuframework.shiro.model.UserDetails;
import com.wuframework.shiro.util.ExpiryMap;
import com.wuframework.shiro.util.ReflectiveUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

@Component
@Aspect
public class AccessLimitAOP {


    private ExpiryMap<String, Integer> accessLimitMap = new ExpiryMap<String, Integer>();

    @Resource
    private LoginService loginService;

    @Resource
    private ShiroProperties shiroProperties;

    @Pointcut("@annotation(accessLimit)")
    public void accessLimitPoint(AccessLimit accessLimit) {
        System.out.println("切面限制请求");
    }


    @Before("accessLimitPoint(accessLimit)")
    public void permissionCheck(JoinPoint point, AccessLimit accessLimit) {
//        System.out.println("@Before：模拟权限检查...");
//        System.out.println("@Before：目标方法为：" +
//                point.getSignature().getDeclaringTypeName() +
//                "." + point.getSignature().getName());
//        System.out.println("@Before：参数为：" + Arrays.toString(point.getArgs()));
//        System.out.println("@Before：被织入的目标对象为：" + point.getTarget());

        String key = getKey(accessLimit, point);
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();
        // 没有访问记录
        if (!accessLimitMap.containsKey(key) || accessLimitMap.isInvalid(key)) {
            accessLimitMap.put(key, 0, 1000 * seconds);
        }
        // 有访问记录 判断次数
        if ((accessLimitMap.get(key) + 1) > maxCount) {
            //访问频繁
//            ResultFactory.of(DefaultResultCode.DEFAULT_ERROR, "访问频繁");
            throw new CustomException("访问频繁");
        }

    }


    @After("accessLimitPoint(accessLimit)")
    public void releaseResource(JoinPoint point, AccessLimit accessLimit) {
//        System.out.println("@After：模拟释放资源...");
//        System.out.println("@After：目标方法为：" +
//                point.getSignature().getDeclaringTypeName() +
//                "." + point.getSignature().getName());
//        System.out.println("@After：参数为：" + Arrays.toString(point.getArgs()));
//        System.out.println("@After：被织入的目标对象为：" + point.getTarget());

    }

    @AfterReturning(pointcut = "accessLimitPoint(accessLimit)",
            returning = "returnValue")
    public void log(JoinPoint point, AccessLimit accessLimit, Object returnValue) {
        // 请求成功或者失败 次数进行更改
//        System.out.println("@AfterReturning：模拟日志记录功能...");
//        System.out.println("@AfterReturning：目标方法为：" +
//                point.getSignature().getDeclaringTypeName() +
//                "." + point.getSignature().getName());
//        System.out.println("@AfterReturning：参数为：" +
//                Arrays.toString(point.getArgs()));
//        System.out.println("@AfterReturning：返回值为：" + returnValue);
//        System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
        String key = getKey(accessLimit, point);
        // 请求成功限制
        if (accessLimit.requestSuccessLimit()) {

            if (!ObjectUtils.isEmpty(returnValue) & returnValue.getClass().isAssignableFrom(Result.class)) {
                Result result = (Result) returnValue;
                if (result.getCode().equals(DefaultResultCode.SUCCESS.code)) {
                    accessLimitMap.put(key, accessLimitMap.get(key) + 1, accessLimitMap.getExpiryTime(key) - System.currentTimeMillis());
                }
            }
        } else {
            //请求失败限制
            //正常返回 请求错误
            if (returnValue.getClass().isAssignableFrom(Result.class)) {
                Result result = (Result) returnValue;
                if (!result.getCode().equals(DefaultResultCode.SUCCESS.code)) {
                    accessLimitMap.put(key, accessLimitMap.get(key) + 1, accessLimitMap.getExpiryTime(key) - System.currentTimeMillis());
                }
            }
        }
    }

    @AfterThrowing(pointcut = "accessLimitPoint(accessLimit)",
            throwing = "exception")
    public void afterThrowing(JoinPoint point, AccessLimit accessLimit, Exception exception) {
        String key = getKey(accessLimit, point);
        //异常抛出
        if (!accessLimit.requestSuccessLimit() & !ObjectUtils.isEmpty(exception)) {
            accessLimitMap.put(key, accessLimitMap.get(key) + 1, accessLimitMap.getExpiryTime(key) - System.currentTimeMillis());
        }
    }

    /**
     * 获取键 key
     *
     * @param accessLimit
     * @param point
     * @return
     */
    private String getKey(AccessLimit accessLimit, JoinPoint point) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        boolean needLogin = accessLimit.needLogin();
        String key = requestAttributes.getRequest().getRequestURI();
        //是否需要登录
        if (needLogin) {
            // 验证登录是否有效
            String accessToken = requestAttributes.getRequest().getHeader(shiroProperties.getTokenName());
            UserDetails userDetails = loginService.user(accessToken);
            if (ObjectUtils.isEmpty(userDetails)) {
                ResultFactory.of(DefaultResultCode.DEFAULT_ERROR, "");
                throw new CustomException("验证登录失败");
            }
            key += userDetails.getUsername();
        } else {
            //TODO 不需要登录
        }
        //是否判断参数是否有访问限制
        boolean checkAccessParam = accessLimit.checkAccessParam();
        if (checkAccessParam) {
            Class type = accessLimit.paramType();
            String paramName = accessLimit.paramName();
            Object parameter = null;
            for (Object arg : point.getArgs()) {
                if (arg.getClass().equals(type)) {
                    parameter = arg;
                }
            }
            if(ObjectUtils.isEmpty(parameter)){
                throw new CustomException("AccessLimit 注解使用错误 属性："+paramName+"不存在");
            }
            key += ReflectiveUtil.getBaseClassValByAttributeName(parameter, paramName);
//            System.out.println(key);
        }
        return key;
    }


}
