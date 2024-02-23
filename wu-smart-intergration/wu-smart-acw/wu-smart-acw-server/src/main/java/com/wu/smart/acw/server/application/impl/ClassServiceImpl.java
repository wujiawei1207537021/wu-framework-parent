package com.wu.smart.acw.server.application.impl;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.smart.acw.core.domain.uo.AcwClassUo;
import com.wu.smart.acw.core.domain.uo.AcwFieldUo;
import com.wu.smart.acw.core.domain.uo.AcwMethodUo;
import com.wu.smart.acw.server.application.ClassApplication;
import com.wu.smart.acw.server.config.enums.AnnotationClassEnums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ClassServiceImpl implements ClassApplication {
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
    @Transactional
    @Override
    public AcwClassUo save(Class clazz) {
        // 存储class
        final AcwClassUo acwClassUo = AnnotationClassEnums.create(clazz);
        lazyLambdaStream.insert(acwClassUo);
        final Long classUoId = acwClassUo.getId();

        // 存储class中的属性字段
        List<AcwFieldUo> acwFieldUoList = new ArrayList<>();
        // 私有属性
        for (Field declaredField : clazz.getDeclaredFields()) {
            final String name = declaredField.getName();
            final Class<?> type = declaredField.getType();
            final AcwClassUo acwClassUoType = AnnotationClassEnums.create(type);

            final AcwFieldUo acwFieldUo = new AcwFieldUo();
            acwFieldUo.setName(name).setClassId(classUoId).setType(acwClassUoType);
            acwFieldUoList.add(acwFieldUo);
        }
        // 共有属性
        // final 属性

        lazyLambdaStream.insert(acwFieldUoList);

        // 存储class对应的方法
        List<AcwMethodUo> acwMethodUoList = new ArrayList<>();


        // 生成公共方法
        for (Method method : clazz.getMethods()) {
            AcwMethodUo acwMethodUo = new AcwMethodUo();
            final Parameter[] parameters = method.getParameters();
            final String inParams = Arrays.stream(parameters).map(parameter -> parameter.getParameterizedType().getTypeName() + NormalUsedString.SPACE + parameter.getName()).collect(Collectors.joining(","));
            final Class<?> returnType = method.getReturnType();
            acwMethodUo.setClassId(classUoId).setName(method.getName()).setInParams(inParams).setOutParams(returnType.getSimpleName()).
                    setBody(Void.class.equals(returnType) ? "{}" : "{return null;}");
            acwMethodUoList.add(acwMethodUo);
        }
        // 生成 私有方法
        for (Method method : clazz.getDeclaredMethods()) {
            AcwMethodUo acwMethodUo = new AcwMethodUo();
            final Parameter[] parameters = method.getParameters();
            final String inParams = Arrays.stream(parameters).map(parameter -> parameter.getParameterizedType().getTypeName() + NormalUsedString.SPACE + parameter.getName()).collect(Collectors.joining(","));
            final Class<?> returnType = method.getReturnType();
            acwMethodUo.setClassId(classUoId).setName(method.getName()).setInParams(inParams).setOutParams(returnType.getSimpleName()).
                    setBody(Void.class.equals(returnType) ? "{}" : "{return null;}");
            acwMethodUoList.add(acwMethodUo);
        }
        // 构造方法
        for (Constructor constructor : clazz.getConstructors()) {
            AcwMethodUo acwMethodUo = new AcwMethodUo();
            final Parameter[] parameters = constructor.getParameters();
            final String inParams = Arrays.stream(parameters).map(parameter -> parameter.getParameterizedType().getTypeName() + NormalUsedString.SPACE + parameter.getName()).collect(Collectors.joining(","));
            final String body = Arrays.stream(parameters).map(parameter -> "this." + parameter.getName() + NormalUsedString.EQUALS + parameter.getName() + NormalUsedString.SEMICOLON).collect(Collectors.joining());

            acwMethodUo.setClassId(classUoId).setName(clazz.getSimpleName()).setInParams(inParams).
                    setBody("{" + body + "}");
            acwMethodUoList.add(acwMethodUo);
        }
        // final 方法
        lazyLambdaStream.insert(acwMethodUoList);
        return null;
    }


}
