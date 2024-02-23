//package com.wuframework.log.annotation;
//
//
//import com.wuframework.log.enums.DynamicExecutEnum;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//
//import java.lang.reflect.InvocationTargetException;
//
///**
// * @ Description   :  兼容jdk14的写法
// * @ Author        :  wujiawei
// * @ CreateDate    :  2020/4/10 0010 17:41
// * @ UpdateUser    :  wujiawei
// * @ UpdateDate    :  2020/4/10 0010 17:41
// * @ UpdateRemark  :  修改内容
// * @ Version       :  1.0
// */
//
//
//@Aspect
//public class DynamicAnnotationAOP14 {
//
//    //获取参数
//    private Object o = null;
//
//    private ExecuteDynamic executeDynamic;
//
//
//    @Pointcut("@annotation(dynamicAnnotation)")
//    public void dynamicAnnotationPoint(DynamicAnnotation dynamicAnnotation) {
//        System.out.println("进入切面");
//    }
//
//
//    @Before("dynamicAnnotationPoint(dynamicAnnotation)")
//    public void before(JoinPoint com.wu.bionic.point, DynamicAnnotation dynamicAnnotation) {
////        System.out.println("@Before：模拟权限检查...");
////        System.out.println("@Before：目标方法为：" +
////                com.wu.bionic.point.getSignature().getDeclaringTypeName() +
////                "." + com.wu.bionic.point.getSignature().getName());
////        System.out.println("@Before：参数为：" + Arrays.toString(com.wu.bionic.point.getArgs()));
////        System.out.println("@Before：被织入的目标对象为：" + com.wu.bionic.point.getTarget());
//        // 初始化执行对象
//        try {
//            this.executeDynamic = dynamicAnnotation.executeClass().getDeclaredConstructor().newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        if (dynamicAnnotation.executionTiming().equals(DynamicExecutEnum.ExecutionTiming.BEFORE)) {
//            this.o = com.wu.bionic.point.getArgs();
//            this.executeDynamic.execute(o);
//        }
//    }
//
//
//    @After("dynamicAnnotationPoint(dynamicAnnotation)")
//    public void after(JoinPoint com.wu.bionic.point, DynamicAnnotation dynamicAnnotation) {
////        System.out.println("@After：模拟释放资源...");
////        System.out.println("@After：目标方法为：" +
////                com.wu.bionic.point.getSignature().getDeclaringTypeName() +
////                "." + com.wu.bionic.point.getSignature().getName());
////        System.out.println("@After：参数为：" + Arrays.toString(com.wu.bionic.point.getArgs()));
////        System.out.println("@After：被织入的目标对象为：" + com.wu.bionic.point.getTarget());
//        if (dynamicAnnotation.executionTiming().equals(DynamicExecutEnum.ExecutionTiming.AFTER)) {
//            this.o = com.wu.bionic.point.getArgs();
//            this.executeDynamic.execute(o);
//        }
//
//    }
//
//    @AfterReturning(pointcut = "dynamicAnnotationPoint(dynamicAnnotation)",
//            returning = "returnValue")
//    public void afterReturning(JoinPoint com.wu.bionic.point, DynamicAnnotation dynamicAnnotation, Object returnValue) {
//        // 请求成功或者失败 次数进行更改
////        System.out.println("@AfterReturning：模拟日志记录功能...");
////        System.out.println("@AfterReturning：目标方法为：" +
////                com.wu.bionic.point.getSignature().getDeclaringTypeName() +
////                "." + com.wu.bionic.point.getSignature().getName());
////        System.out.println("@AfterReturning：参数为：" +
////                Arrays.toString(com.wu.bionic.point.getArgs()));
////        System.out.println("@AfterReturning：返回值为：" + returnValue);
////        System.out.println("@AfterReturning：被织入的目标对象为：" + com.wu.bionic.point.getTarget());
////      返回后执行
//        if (dynamicAnnotation.executionTiming().equals(DynamicExecutEnum.ExecutionTiming.AFTER_RETURNING)) {
////           获取参数方式
//            switch (dynamicAnnotation.getParamMode()) {
//                case MODE_IN -> {
//                    o = com.wu.bionic.point.getArgs();
//                    break;
//                }
//                case MODE_OUT -> {
//                    o = returnValue;
//                    break;
//                }
//                default -> {
//                    com.wu.bionic.point.getArgs()[com.wu.bionic.point.getArgs().length] = returnValue;
//                    o = com.wu.bionic.point.getArgs();
//                    break;
//                }
//            }
////           执行模式
//            switch (dynamicAnnotation.executionMode()) {
//                case ALWAYS, SUCCESS -> {
//                    executeDynamic.execute(o);
//                    break;
//                }
//                default -> {
//                    System.out.println("不执行操作");
//                    break;
//                }
//            }
//        }
//    }
//
//    @AfterThrowing(pointcut = "dynamicAnnotationPoint(dynamicAnnotation)",
//            throwing = "exception")
//    public void afterThrowing(JoinPoint com.wu.bionic.point, DynamicAnnotation dynamicAnnotation, Exception exception) {
//        //执行时机
//        if (dynamicAnnotation.executionTiming().equals(DynamicExecutEnum.ExecutionTiming.AFTER_THROWING)) {
////           获取参数方式
//            switch (dynamicAnnotation.getParamMode()) {
//                case MODE_IN -> {
//                    o = com.wu.bionic.point.getArgs();
//                    break;
//                }
//                case MODE_OUT -> {
//                    o = exception;
//                    break;
//                }
//                default -> {
//                    com.wu.bionic.point.getArgs()[com.wu.bionic.point.getArgs().length] = exception;
//                    o = com.wu.bionic.point.getArgs();
//                    break;
//                }
//            }
////           执行模式
//            switch (dynamicAnnotation.executionMode()) {
//                case ALWAYS, FAIL -> {
//                    executeDynamic.execute(o);
//                    break;
//                }
//                default -> {
//                    System.out.println("不执行");
//                    break;
//                }
//            }
//        }
//    }
//}
