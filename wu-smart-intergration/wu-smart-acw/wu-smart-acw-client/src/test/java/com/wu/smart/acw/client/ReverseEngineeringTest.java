package com.wu.smart.acw.client;

import com.wu.smart.acw.client.nocode.provider.controller.CurrentProjectDataSourceProvider;

import java.util.Arrays;
import java.util.stream.Collectors;

class ReverseEngineeringTest {
    public static void main(String[] args) {
        Class<?> clazz = CurrentProjectDataSourceProvider.class;

        // 获取当前class 信息
        System.out.println("class name: "+clazz.getName());
        System.out.println("package name: "+clazz.getPackageName());
        System.out.println("descriptorString: "+clazz.descriptorString());
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            System.out.println("field name: "+field.getName()+" field type: "+field.getType());
        });
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
            System.out.println(
                    "method name: "+method.getName()+
                    " method return type: "+method.getReturnType()+
                    " method parameter : "+ Arrays.stream(method.getParameters()).map(parameter->{
                        return " type :"+parameter.getType()+" name "+parameter.getName();
                    }).collect(Collectors.joining())
            );
        });
        
    }

}