package com.wu.framework.inner.layer.util;


import com.wu.framework.inner.layer.enums.SimulationEnum;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


/**
 * @author wujiawei
 */
public class DataTransformUntil {

    // 忽视字段
    public final static List<String> IGNORE_FIELD_LIST = Arrays.asList("serialVersionUID");

    /**
     * @param source 原
     * @param target 目标
     * @return void
     * description 打印两个class相互转换的代码
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/7/7 3:02 下午
     */
    public static void transform(Class source, Class target) {
        Field[] sourceDeclaredFields = source.getDeclaredFields();
        Field[] targetDeclaredFields = target.getDeclaredFields();
        String targetName = lowerFirst(target.getSimpleName());
        String sourceName = lowerFirst(source.getSimpleName());
        System.out.println(String.format("%s %s = new %s();", target.getSimpleName(), targetName, target.getSimpleName()));
        for (Field sourceDeclaredField : sourceDeclaredFields) {
            boolean anyMatch = Arrays.stream(targetDeclaredFields).anyMatch(field -> field.getType().equals(sourceDeclaredField.getType()) & field.getName().equals(sourceDeclaredField.getName()));
            if (anyMatch) {
                if (IGNORE_FIELD_LIST.contains(sourceDeclaredField.getName())) {
                    continue;
                }
                String field1Name = upFirst(sourceDeclaredField.getName());

                System.out.println(String.format("%s.set%s(%s.get%s());", targetName, field1Name, sourceName, field1Name));
            }
        }

    }


    //首字母大写
    private static String upFirst(String name) {
        //     name = name.substring(0, 1).toUpperCase() + name.substring(1);
//        return  name;
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * @param
     * @return description 首字母小写
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/7/7 3:24 下午
     */
    private static String lowerFirst(String name) {
        char[] cs = name.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }

    /**
     * description 生成模拟数据
     *
     * @param source 类
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/10/11 1:51 下午
     */
    public static void simulationData(Class source) {
        simulationData(source, false);
    }

    /**
     * description
     *
     * @param source 数据源
     * @param chain  @Accessors(chain = true)
     * @return void
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/10/11 3:09 下午
     */
    public static void simulationData(Class source, boolean chain) {
        final String sourceBeanName = lowerFirst(source.getSimpleName());
        System.out.printf("%s %s = new %s();%n", source.getSimpleName(), sourceBeanName, source.getSimpleName());

        if (chain) {
            System.out.println(sourceBeanName);
        }

        for (Field declaredField : source.getDeclaredFields()) {
            if (IGNORE_FIELD_LIST.contains(declaredField.getName())) {
                continue;
            }
            String fieldName = upFirst(declaredField.getName());
            final Class<?> type = declaredField.getType();
            final SimulationEnum.SimulationStrategy orDefault = SimulationEnum.SIMULATION_MAP.getOrDefault(type,
                    new SimulationEnum.DefaultSimulationStrategy());
            if (chain) {
                System.out.printf(".set%s(%s)%n", fieldName, orDefault.text());
            } else {
                System.out.printf("%s.set%s(%s);%n",
                        sourceBeanName, fieldName, orDefault.strategy());
            }
        }
        if (chain) {
            System.out.println(";");
        }
    }

    /**
     * description 生成仿真对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/10/11 3:49 下午
     */
    public static <T> T simulationBean(Class<T> source) {
        try {
            // 判断是否为基础数据类型
            if (SimulationEnum.SIMULATION_MAP.containsKey(source)) {
                return simulationBaseBean(source, null);
            }
            final T t = source.getDeclaredConstructor().newInstance();
            for (Field declaredField : source.getDeclaredFields()) {
                if (IGNORE_FIELD_LIST.contains(declaredField.getName())) {
                    continue;
                }
                final Class<?> fieldType = declaredField.getType();
                final SimulationEnum.SimulationStrategy orDefault = SimulationEnum.SIMULATION_MAP.getOrDefault(fieldType,
                        new SimulationEnum.DefaultSimulationStrategy());
                // boolean 与Boolean
                Method declaredMethod;
                if (fieldType.equals(boolean.class)) {
                    if (declaredField.getName().startsWith("is")) {
                        declaredMethod = source.getDeclaredMethod(declaredField.getName().replace("is", "set"), fieldType);
                    } else {
                        declaredMethod = source.getDeclaredMethod("is" + upFirst(declaredField.getName()), fieldType);
                    }
                } else {
                    declaredMethod = source.getDeclaredMethod("set" + upFirst(declaredField.getName()), fieldType);
                }
                declaredMethod.invoke(t, orDefault.strategy());
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 仿真基础数据类型
     *
     * @param source 数据源类型
     * @param length 数据长度
     * @return
     */
    public static <T> T simulationBaseBean(Class<T> source, Integer length) {
        // 判断是否为基础数据类型
        if (SimulationEnum.SIMULATION_MAP.containsKey(source)) {
            return (T) SimulationEnum.SIMULATION_MAP.get(source).strategy(length);
        } else {
            throw new IllegalArgumentException("仿真基础数据类型错误:不支持的数据类型" + source);
        }
    }
}
