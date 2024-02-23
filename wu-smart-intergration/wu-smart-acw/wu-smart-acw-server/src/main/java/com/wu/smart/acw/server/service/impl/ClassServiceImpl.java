package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.stereotype.LazyTransactional;
import com.wu.smart.acw.core.domain.uo.ClassUo;
import com.wu.smart.acw.core.domain.uo.FieldUo;
import com.wu.smart.acw.core.domain.uo.MethodUo;
import com.wu.smart.acw.server.config.enums.AnnotationClassEnums;
import com.wu.smart.acw.server.service.ClassService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/15 4:16 下午
 */
@Service
public class ClassServiceImpl implements ClassService {
    private final LazyLambdaStream lazyLambdaStream;

    public ClassServiceImpl(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }


    /**
     * describe save base class
     *
     * @param clazz@return
     * @author Jia wei Wu
     * @date 2022/1/15 4:19 下午
     **/
    @LazyTransactional
    @Override
    public ClassUo save(Class clazz) {
        // 存储class
        final ClassUo classUo = AnnotationClassEnums.create(clazz);
        lazyLambdaStream.insert(classUo);
        final Long classUoId = classUo.getId();

        // 存储class中的属性字段
        List<FieldUo> fieldUoList = new ArrayList<>();
        // 私有属性
        for (Field declaredField : clazz.getDeclaredFields()) {
            final String name = declaredField.getName();
            final Class<?> type = declaredField.getType();
            final ClassUo classUoType = AnnotationClassEnums.create(type);

            final FieldUo fieldUo = new FieldUo();
            fieldUo.setName(name).setClassId(classUoId).setType(classUoType);
            fieldUoList.add(fieldUo);
        }
        // 共有属性
        // final 属性

        lazyLambdaStream.insert(fieldUoList);

        // 存储class对应的方法
        List<MethodUo> methodUoList = new ArrayList<>();


        // 生成公共方法
        for (Method method : clazz.getMethods()) {
            MethodUo methodUo = new MethodUo();
            final Parameter[] parameters = method.getParameters();
            final String inParams = Arrays.stream(parameters).map(parameter -> parameter.getParameterizedType().getTypeName() + NormalUsedString.SPACE + parameter.getName()).collect(Collectors.joining(","));
            final Class<?> returnType = method.getReturnType();
            methodUo.setClassId(classUoId).setName(method.getName()).setInParams(inParams).setOutParams(returnType.getSimpleName()).
                    setBody(Void.class.equals(returnType) ? "{}" : "{return null;}");
            methodUoList.add(methodUo);
        }
        // 生成 私有方法
        for (Method method : clazz.getDeclaredMethods()) {
            MethodUo methodUo = new MethodUo();
            final Parameter[] parameters = method.getParameters();
            final String inParams = Arrays.stream(parameters).map(parameter -> parameter.getParameterizedType().getTypeName() + NormalUsedString.SPACE + parameter.getName()).collect(Collectors.joining(","));
            final Class<?> returnType = method.getReturnType();
            methodUo.setClassId(classUoId).setName(method.getName()).setInParams(inParams).setOutParams(returnType.getSimpleName()).
                    setBody(Void.class.equals(returnType) ? "{}" : "{return null;}");
            methodUoList.add(methodUo);
        }
        // 构造方法
        for (Constructor constructor : clazz.getConstructors()) {
            MethodUo methodUo = new MethodUo();
            final Parameter[] parameters = constructor.getParameters();
            final String inParams = Arrays.stream(parameters).map(parameter -> parameter.getParameterizedType().getTypeName() + NormalUsedString.SPACE + parameter.getName()).collect(Collectors.joining(","));
            final String body = Arrays.stream(parameters).map(parameter -> "this." + parameter.getName() + NormalUsedString.EQUALS + parameter.getName() + NormalUsedString.SEMICOLON).collect(Collectors.joining());

            methodUo.setClassId(classUoId).setName(clazz.getSimpleName()).setInParams(inParams).
                    setBody("{" + body + "}");
            methodUoList.add(methodUo);
        }
        // final 方法
        lazyLambdaStream.insert(methodUoList);
        return null;
    }


}
