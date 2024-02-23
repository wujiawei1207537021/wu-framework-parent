package wu.framework.bean.wu2020;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具
 */
public class ReflexUtils {

    /**
     * 获取目标class中的字段
     * @param targetClass 目标class
     * @param fieldName 字段名称
     * @return 字段
     */
    public static Field findDeclaredField(Class<?> targetClass,String fieldName){
        try {
            return targetClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取目标class中的字段
     * @param targetClass 目标class
     * @param fieldName 字段名称
     * @return 字段
     */
    public static Field findField(Class<?> targetClass,String fieldName){
        try {
            return targetClass.getField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取目标class中的方法
     * @param targetClass 目标class
     * @param method 方法名
     * @param parameterTypes 参数类型
     * @return 方法
     */
    public static Method findDeclaredMethod(Class<?> targetClass, String method,Class<?>... parameterTypes){
        try {
            return targetClass.getDeclaredMethod(method, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取目标class中的方法
     * @param targetClass 目标class
     * @param method 方法名
     * @param parameterTypes 参数类型
     * @return 方法
     */
    public static Method findMethod(Class<?> targetClass, String method,Class<?>... parameterTypes){
        try {
            return targetClass.getMethod(method, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  执行方法
     * @param bean 目标 对象
     * @param declaredMethod 方法
     * @param parameter 参数 参数不能为空否则无法获取参数类型
     * @return 方法
     */
    public static Object invokeDeclaredMethod(Object bean, Method declaredMethod,Object... parameter){
        if(bean==null){
            return null;
        }
        Object invoke = null;
        try {
            invoke = declaredMethod.invoke(bean, parameter);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return invoke;

    }

    /**
     *  执行方法
     * @param bean 目标 对象
     * @param method 方法名
     * @param parameter 参数 参数不能为空否则无法获取参数类型
     * @return 方法
     */
    public static Object invokeDeclaredMethod(Object bean, String method,Object... parameter){
        if(bean==null){
            return null;
        }
        Class<?> targetClass = bean.getClass();
        Method declaredMethod = findDeclaredMethod(targetClass, method);
        return invokeDeclaredMethod(bean,declaredMethod,parameter);

    }

}
