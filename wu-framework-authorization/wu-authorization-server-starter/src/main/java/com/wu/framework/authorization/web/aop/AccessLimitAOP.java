package com.wu.framework.authorization.web.aop;


//@Aspect
public class AccessLimitAOP {


//    private ExpiryMap<String, Integer> accessLimitMap = new ExpiryMap<String, Integer>();
//
//    @Resource
//    private ILoginService ILoginService;
//
//    @Resource
//    private ShiroProperties shiroProperties;
//
//    @Pointcut("@annotation(accessLimit)")
//    public void accessLimitPoint(AccessLimit accessLimit) {
//        System.out.println("切面限制请求");
//    }
//
//
//    @Before(methodName = "accessLimitPoint(accessLimit)", argNames = "com.wu.bionic.point,accessLimit")
//    public void permissionCheck(JoinPoint com.wu.bionic.point, AccessLimit accessLimit) {
////        System.out.println("@Before：模拟权限检查...");
////        System.out.println("@Before：目标方法为：" +
////                com.wu.bionic.point.getSignature().getDeclaringTypeName() +
////                "." + com.wu.bionic.point.getSignature().getName());
////        System.out.println("@Before：参数为：" + Arrays.toString(com.wu.bionic.point.getArgs()));
////        System.out.println("@Before：被织入的目标对象为：" + com.wu.bionic.point.getTarget());
//
//        String key = getKey(accessLimit, com.wu.bionic.point);
//        int seconds = accessLimit.seconds();
//        int maxCount = accessLimit.maxCount();
//        // 没有访问记录
//        if (!accessLimitMap.containsKey(key) || accessLimitMap.isInvalid(key)) {
//            accessLimitMap.put(key, 0, 1000 * seconds);
//        }
//        // 有访问记录 判断次数
//        if ((accessLimitMap.get(key) + 1) > maxCount) {
//            //访问频繁
////            ResultFactory.of(DefaultResultCode.DEFAULT_ERROR, "访问频繁");
//            throw new CustomException("访问频繁");
//        }
//
//    }
//
//
//    @After(methodName = "accessLimitPoint(accessLimit)", argNames = "com.wu.bionic.point,accessLimit")
//    public void releaseResource(JoinPoint com.wu.bionic.point, AccessLimit accessLimit) {
////        System.out.println("@After：模拟释放资源...");
////        System.out.println("@After：目标方法为：" +
////                com.wu.bionic.point.getSignature().getDeclaringTypeName() +
////                "." + com.wu.bionic.point.getSignature().getName());
////        System.out.println("@After：参数为：" + Arrays.toString(com.wu.bionic.point.getArgs()));
////        System.out.println("@After：被织入的目标对象为：" + com.wu.bionic.point.getTarget());
//
//    }
//
//    @AfterReturning(pointcut = "accessLimitPoint(accessLimit)",
//            returning = "returnValue", argNames = "com.wu.bionic.point,accessLimit,returnValue")
//    public void log(JoinPoint com.wu.bionic.point, AccessLimit accessLimit, Object returnValue) {
//        // 请求成功或者失败 次数进行更改
////        System.out.println("@AfterReturning：模拟日志记录功能...");
////        System.out.println("@AfterReturning：目标方法为：" +
////                com.wu.bionic.point.getSignature().getDeclaringTypeName() +
////                "." + com.wu.bionic.point.getSignature().getName());
////        System.out.println("@AfterReturning：参数为：" +
////                Arrays.toString(com.wu.bionic.point.getArgs()));
////        System.out.println("@AfterReturning：返回值为：" + returnValue);
////        System.out.println("@AfterReturning：被织入的目标对象为：" + com.wu.bionic.point.getTarget());
//        String key = getKey(accessLimit, com.wu.bionic.point);
//        // 请求成功限制
//        if (accessLimit.requestSuccessLimit()) {
//
//            if (!ObjectUtils.isEmpty(returnValue) & returnValue.getClass().isAssignableFrom(Result.class)) {
//                Result result = (Result) returnValue;
//                if (result.getCode().equals(DefaultResultCode.SUCCESS.code)) {
//                    accessLimitMap.put(key, accessLimitMap.get(key) + 1, accessLimitMap.getExpiryTime(key) - System.currentTimeMillis());
//                }
//            }
//        } else {
//            //请求失败限制
//            //正常返回 请求错误
//            if (returnValue.getClass().isAssignableFrom(Result.class)) {
//                Result result = (Result) returnValue;
//                if (!result.getCode().equals(DefaultResultCode.SUCCESS.code)) {
//                    accessLimitMap.put(key, accessLimitMap.get(key) + 1, accessLimitMap.getExpiryTime(key) - System.currentTimeMillis());
//                }
//            }
//        }
//    }
//
//    @AfterThrowing(pointcut = "accessLimitPoint(accessLimit)",
//            throwing = "exception", argNames = "com.wu.bionic.point,accessLimit,exception")
//    public void afterThrowing(JoinPoint com.wu.bionic.point, AccessLimit accessLimit, Exception exception) {
//        String key = getKey(accessLimit, com.wu.bionic.point);
//        //异常抛出
//        if (!accessLimit.requestSuccessLimit() & !ObjectUtils.isEmpty(exception)) {
//            accessLimitMap.put(key, accessLimitMap.get(key) + 1, accessLimitMap.getExpiryTime(key) - System.currentTimeMillis());
//        }
//    }
//
//    /**
//     * 获取键 key
//     *
//     * @param accessLimit
//     * @param com.wu.bionic.point
//     * @return
//     */
//    private String getKey(AccessLimit accessLimit, JoinPoint com.wu.bionic.point) {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        boolean needLogin = accessLimit.needLogin();
//        String key = requestAttributes.getRequest().getRequestURI();
//        //是否需要登录
//        if (needLogin) {
//            // 验证登录是否有效
//            String accessToken = requestAttributes.getRequest().getHeader(shiroProperties.getTokenName());
//            UserDetails userDetails = ILoginService.user(accessToken);
//            if (ObjectUtils.isEmpty(userDetails)) {
//                ResultFactory.of(DefaultResultCode.DEFAULT_ERROR, "");
//                throw new CustomException("验证登录失败");
//            }
//            key += userDetails.getUsername();
//        } else {
//            //TODO 不需要登录
//        }
//        //是否判断参数是否有访问限制
//        boolean checkAccessParam = accessLimit.checkAccessParam();
//        if (checkAccessParam) {
//            Class type = accessLimit.paramType();
//            String paramName = accessLimit.paramName();
//            Object parameter = null;
//            for (Object arg : com.wu.bionic.point.getArgs()) {
//                if (arg.getClass().equals(type)) {
//                    parameter = arg;
//                }
//            }
//            if (ObjectUtils.isEmpty(parameter)) {
//                throw new CustomException("AccessLimit 注解使用错误 属性：" + paramName + "不存在");
//            }
//            key += ReflectiveUtil.getBaseClassValByAttributeName(parameter, paramName);
////            System.out.println(key);
//        }
//        return key;
//    }


}
