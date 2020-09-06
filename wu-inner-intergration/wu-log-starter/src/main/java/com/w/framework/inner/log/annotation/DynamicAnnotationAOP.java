package com.w.framework.inner.log.annotation;


import com.w.framework.inner.log.enums.DynamicExecutEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.lang.reflect.InvocationTargetException;

@Aspect
public class DynamicAnnotationAOP {

    //获取参数
    private Object o = null;

    private ExecuteDynamic executeDynamic;


    @Pointcut("@annotation(dynamicAnnotation)")
    public void dynamicAnnotationPoint(DynamicAnnotation dynamicAnnotation) {
        System.out.println("进入切面");
    }


    @Before("dynamicAnnotationPoint(dynamicAnnotation)")
    public void before(JoinPoint point, DynamicAnnotation dynamicAnnotation) {
//        System.out.println("@Before：模拟权限检查...");
//        System.out.println("@Before：目标方法为：" +
//                point.getSignature().getDeclaringTypeName() +
//                "." + point.getSignature().getName());
//        System.out.println("@Before：参数为：" + Arrays.toString(point.getArgs()));
//        System.out.println("@Before：被织入的目标对象为：" + point.getTarget());

        // 初始化执行对象
        try {
            this.executeDynamic = dynamicAnnotation.executeClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (dynamicAnnotation.executionTiming().equals(DynamicExecutEnum.ExecutionTiming.BEFORE)) {
            this.o = point.getArgs();
            this.executeDynamic.execute(o);
        }
    }


    @After("dynamicAnnotationPoint(dynamicAnnotation)")
    public void after(JoinPoint point, DynamicAnnotation dynamicAnnotation) {
//        System.out.println("@After：模拟释放资源...");
//        System.out.println("@After：目标方法为：" +
//                point.getSignature().getDeclaringTypeName() +
//                "." + point.getSignature().getName());
//        System.out.println("@After：参数为：" + Arrays.toString(point.getArgs()));
//        System.out.println("@After：被织入的目标对象为：" + point.getTarget());
        if (dynamicAnnotation.executionTiming().equals(DynamicExecutEnum.ExecutionTiming.AFTER)) {
            this.o = point.getArgs();
            this.executeDynamic.execute(o);
        }

    }

    @AfterReturning(pointcut = "dynamicAnnotationPoint(dynamicAnnotation)",
            returning = "returnValue")
    public void afterReturning(JoinPoint point, DynamicAnnotation dynamicAnnotation, Object returnValue) {
        // 请求成功或者失败 次数进行更改
//        System.out.println("@AfterReturning：模拟日志记录功能...");
//        System.out.println("@AfterReturning：目标方法为：" +
//                point.getSignature().getDeclaringTypeName() +
//                "." + point.getSignature().getName());
//        System.out.println("@AfterReturning：参数为：" +
//                Arrays.toString(point.getArgs()));
//        System.out.println("@AfterReturning：返回值为：" + returnValue);
//        System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
//      返回后执行
        if (dynamicAnnotation.executionTiming().equals(DynamicExecutEnum.ExecutionTiming.AFTER_RETURNING)) {
//           获取参数方式
            switch (dynamicAnnotation.getParamMode()) {
                case MODE_IN: {
                    o = point.getArgs();
                    break;
                }
                case MODE_OUT: {
                    o = returnValue;
                    break;
                }
                default: {
                    point.getArgs()[point.getArgs().length] = returnValue;
                    o = point.getArgs();
                    break;
                }
            }
//           执行模式
            switch (dynamicAnnotation.executionMode()) {
                case ALWAYS:
                case SUCCESS: {
                    executeDynamic.execute(o);
                    break;
                }
                default: {
                    System.out.println("不执行操作");
                    break;
                }
            }
        }
    }

    @AfterThrowing(pointcut = "dynamicAnnotationPoint(dynamicAnnotation)",
            throwing = "exception")
    public void afterThrowing(JoinPoint point, DynamicAnnotation dynamicAnnotation, Exception exception) {
        //执行时机
        if (dynamicAnnotation.executionTiming().equals(DynamicExecutEnum.ExecutionTiming.AFTER_THROWING)) {
//           获取参数方式
            switch (dynamicAnnotation.getParamMode()) {
                case MODE_IN: {
                    o = point.getArgs();
                    break;
                }
                case MODE_OUT: {
                    o = exception;
                    break;
                }
                default: {
                    point.getArgs()[point.getArgs().length] = exception;
                    o = point.getArgs();
                    break;
                }
            }
//           执行模式
            switch (dynamicAnnotation.executionMode()) {
                case ALWAYS:
                case FAIL: {
                    executeDynamic.execute(o);
                    break;
                }
                default: {
                    System.out.println("不执行");
                    break;
                }
            }
        }
    }
}
