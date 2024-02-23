package com.wu.framework.inner.layer.data.clazz;

import com.wu.framework.inner.layer.util.FileUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/10/10 15:14
 */
public class xx {


    public static void main(String[] args) {
        String classPath = FileUtil.readLocalClassFolder(xx.class);
        System.out.println("classPath: " + classPath);
        String readLocalSrcMainClassPath = FileUtil.readLocalSrcMainClassPath(FileUtil.class);
        System.out.println("readLocalSrcMainClassPath: " + readLocalSrcMainClassPath);

        String classContent = FileUtil.readFileContent(readLocalSrcMainClassPath);
        System.out.println("class 文件内容:" + classContent);


        Map<Integer, String> lineContent = FileUtil.readFileLineContent(readLocalSrcMainClassPath);
        lineContent.forEach((line, content) -> {
            System.out.println(line + ":" + content);
        });


        Class<xx> xxClass = xx.class;
        String typeName = xxClass.getTypeName();


        Method[] declaredMethods = xxClass.getDeclaredMethods();
        Method[] methods = xxClass.getMethods();
        Field[] declaredFields = xxClass.getDeclaredFields();
        Annotation[] annotations = xxClass.getAnnotations();
        Method method = null;
        int parameterCount = method.getParameterCount();
        Parameter[] parameters = method.getParameters();
        Parameter parameter = null;
        String name = parameter.getName();
        Class<?> type = parameter.getType();

    }
}
