package com.wu.framework.inner.layer.util;


import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.enums.SimulationEnum;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author Jia wei Wu
 */
public class DataTransformUntil {

    // 忽视字段
    public final static List<String> IGNORE_FIELD_LIST = List.of("serialVersionUID");

    /**
     * @param source 原 bean class or Annotation
     * @param target 目标
     *               description 打印两个class相互转换的代码
     * @author Jia wei Wu
     * @date 2021/7/7 3:02 下午
     */
    public static void transform(Class<?> source, Class<?> target) {
        String targetName = lowerFirst(target.getSimpleName());
        String sourceName = lowerFirst(source.getSimpleName());

        if (source.isAnnotation()) {
            Method[] sourceDeclaredMethods = source.getDeclaredMethods();

            Field[] targetDeclaredFields = target.getDeclaredFields();

            System.out.printf("if(null==%s){%n", sourceName);
            System.out.println("            return null;");
            System.out.println("        }");
            System.out.printf("%s %s = new %s();%n", target.getSimpleName(), targetName, target.getSimpleName());
            for (Method sourceDeclaredMethod : sourceDeclaredMethods) {
                String sourceDeclaredMethodName = sourceDeclaredMethod.getName();
                boolean anyMatch = Arrays.stream(targetDeclaredFields).anyMatch(field -> field.getType().equals(sourceDeclaredMethod.getReturnType()) & field.getName().equals(sourceDeclaredMethodName));
                if (anyMatch) {
                    if (IGNORE_FIELD_LIST.contains(sourceDeclaredMethodName)) {
                        continue;
                    }
                    String field1Name = upFirst(sourceDeclaredMethodName);

                    System.out.printf("%s.set%s(%s.%s());%n", targetName, field1Name, sourceName, sourceDeclaredMethodName);
                }
            }

        } else {
            Field[] sourceDeclaredFields = source.getDeclaredFields();
            Field[] targetDeclaredFields = target.getDeclaredFields();
            System.out.printf("if(null==%s){%n", sourceName);
            System.out.println("            return null;");
            System.out.println("        }");
            System.out.printf("%s %s = new %s();%n", target.getSimpleName(), targetName, target.getSimpleName());
            for (Field sourceDeclaredField : sourceDeclaredFields) {
                boolean anyMatch = Arrays.stream(targetDeclaredFields).anyMatch(field -> field.getType().equals(sourceDeclaredField.getType()) & field.getName().equals(sourceDeclaredField.getName()));
                if (anyMatch) {
                    if (IGNORE_FIELD_LIST.contains(sourceDeclaredField.getName())) {
                        continue;
                    }
                    String field1Name = upFirst(sourceDeclaredField.getName());

                    System.out.printf("%s.set%s(%s.get%s());%n", targetName, field1Name, sourceName, field1Name);
                }
            }
        }
        System.out.printf("            return %s;", targetName);

    }

    /**
     * 注解转换成对应的实体类
     *
     * @param source 原始数据
     */
    public static void transformAnnotation(Class<? extends Annotation> source) {

        Method[] sourceDeclaredMethods = source.getDeclaredMethods();
        System.out.printf("public class %sEndPoint{%n", source.getSimpleName());
        for (Method sourceDeclaredMethod : sourceDeclaredMethods) {
            Class<?> returnType = sourceDeclaredMethod.getReturnType();
            String name = sourceDeclaredMethod.getName();
            if (IGNORE_FIELD_LIST.contains(name)) {
                continue;
            }
            System.out.printf(" private %s %s;%n", returnType.getSimpleName(), name);
        }
        System.out.println("}");
    }

    /**
     * @param source 原
     * @param target 目标
     *               description 打印两个class对比字段
     * @author Jia wei Wu
     * @date 2021/7/7 3:02 下午
     */
    public static void contrastField(Class<?> source, Class<?> target) {
        Field[] sourceDeclaredFields = source.getDeclaredFields();
        Field[] targetDeclaredFields = target.getDeclaredFields();
        String targetName = lowerFirst(target.getSimpleName());
        String sourceName = lowerFirst(source.getSimpleName());
        System.out.print("字段名称");
        System.out.printf("        原始数据字段类型(%s)", source.getSimpleName());
        System.out.printf("        目标数据字段类型:(%s)%n", target.getSimpleName());
        // 原始数据字段map
        Map<String/*Field.Name**/, Field> sourceDeclaredFieldMap = Arrays.stream(sourceDeclaredFields).collect(Collectors.toConcurrentMap(Field::getName, Function.identity(), (A, B) -> A));

        // 目标数据字段map
        Map<String/*Field.Name**/, Field> targetDeclaredFieldMap = Arrays.stream(targetDeclaredFields).collect(Collectors.toConcurrentMap(Field::getName, Function.identity(), (A, B) -> A));

        for (Field sourceDeclaredField : sourceDeclaredFields) {
            boolean anyMatch = Arrays.stream(targetDeclaredFields).anyMatch(field -> !field.getType().equals(sourceDeclaredField.getType()) & field.getName().equals(sourceDeclaredField.getName()));
            if (anyMatch) {
                if (IGNORE_FIELD_LIST.contains(sourceDeclaredField.getName())) {
                    continue;
                }
                String fieldName = sourceDeclaredField.getName();

                System.out.printf("%s %s %s %n", fieldName, sourceDeclaredField.getType(), targetDeclaredFieldMap.get(fieldName).getType());
            }
        }

    }

    //首字母大写
    private static String upFirst(String name) {
        return CamelAndUnderLineConverter.capitalizeFirstLetter(name);
    }

    /**
     * @param
     * @return description 首字母小写
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/7/7 3:24 下午
     */
    private static String lowerFirst(String name) {
        return CamelAndUnderLineConverter.lowercaseFirstLetter(name);
    }

    /**
     * description 生成模拟数据
     *
     * @param source 类
     * @author Jia wei Wu
     * @date 2021/10/11 1:51 下午
     */
    public static void simulationData(Class<?> source) {
        simulationData(source, false);
    }

    /**
     * description
     *
     * @param source 数据源
     * @param chain  @Accessors(chain = true)
     * @author Jia wei Wu
     * @date 2021/10/11 3:09 下午
     */
    public static void simulationData(Class<?> source, boolean chain) {
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
            final SimulationEnum.SimulationStrategy orDefault = SimulationEnum.SIMULATION_MAP.getOrDefault(type, new SimulationEnum.DefaultSimulationStrategy());
            if (chain) {
                System.out.printf(".set%s(%s)%n", fieldName, orDefault.text());
            } else {
                System.out.printf("%s.set%s(%s);%n", sourceBeanName, fieldName, orDefault.strategy());
            }
        }
        if (chain) {
            System.out.println(";");
        }
    }

    /**
     * description 生成仿真对象
     *
     * @param source 仿生对象类型
     * @return 仿生对象
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
                Class<?> fieldType = declaredField.getType();
                Object fieldValue = null;
                if (SimulationEnum.SIMULATION_MAP.containsKey(fieldType)) {
                    final SimulationEnum.SimulationStrategy orDefault = SimulationEnum.SIMULATION_MAP.getOrDefault(fieldType, new SimulationEnum.DefaultSimulationStrategy());
                    fieldValue = orDefault.strategy();

                } else {
                    // 判断集合或者map
                    Type genericType = declaredField.getGenericType();
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) genericType;
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        Type rawType = parameterizedType.getRawType();
                        // list 数据字段
                        if (Collection.class.isAssignableFrom((Class<?>) rawType)) {
                            Class<?> actualTypeArgument = (Class<?>) actualTypeArguments[0];
                            // 转换集合
                            fieldValue = simulationBeanList(actualTypeArgument, 10);
                        } else if (rawType instanceof Map) {
                            // map 单层数据
                            Type mapKeyType = actualTypeArguments[0];
                            Type mapValueType = actualTypeArguments[1];
                            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                            objectObjectHashMap.put(simulationBean(mapKeyType.getClass()), simulationBean(mapValueType.getClass()));
                            fieldValue = objectObjectHashMap;
                        }
                    } else {
                        // 深度转换
                        fieldValue = simulationBean(fieldType);
                    }

                }
                Method declaredMethod = source.getDeclaredMethod("set" + upFirst(declaredField.getName()), fieldType);

                declaredMethod.invoke(t, fieldValue);

            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 生成仿真对象集合
     *
     * @param source 数据类型
     * @param length 数据长度
     * @param <T>    范型
     * @return 数据集合
     */
    public static <T> List<T> simulationBeanList(Class<T> source, int length) {
        List<T> sourceList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            T t = simulationBean(source);
            sourceList.add(t);
        }
        return sourceList;
    }

    /**
     * 仿真基础数据类型
     *
     * @param source 数据源类型
     * @param length 数据长度
     * @return 数据
     */
    public static <T> T simulationBaseBean(Class<T> source, Integer length) {
        // 判断是否为基础数据类型
        if (SimulationEnum.SIMULATION_MAP.containsKey(source)) {
            return (T) SimulationEnum.SIMULATION_MAP.get(source).strategy(length);
        } else {
            throw new IllegalArgumentException("仿真基础数据类型错误:不支持的数据类型" + source);
        }
    }

    /**
     * 拷贝数据
     * @param source 原始数据
     * @return 目标数据
     * @param <T> 目标数据类型
     */
    public static <T> T copyBean(Object source) {
        if(source==null){
            return null;
        }
        try {
            Class<?> sourceClass = source.getClass();
            final Object t = sourceClass.getDeclaredConstructor().newInstance();
            // 设置属性
            for (Field declaredField : sourceClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object fieldValue = declaredField.get(source);
                declaredField.set(t,fieldValue);
            }
            return (T) t;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }
}
