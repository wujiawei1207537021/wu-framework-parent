package com.wu.smart.acw.server.config.enums;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.util.PackageClassUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

class AnnotationClassEnumsTest {

    /**
     * 讲指定包下的class 转换成 枚举对象 AnnotationClassEnums
     *
     * @param args
     * @see AnnotationClassEnums
     */
    public static void main(String[] args) {
        final Set<Class<?>> lombok = PackageClassUtil.getClasses("lombok");
        final List<Class<?>> collect = lombok.stream().filter(clazz -> clazz.isAnnotation()).filter(clazz -> !clazz.getName().contains("$")).distinct().collect(Collectors.toList());
        for (Class<?> aClass : collect) {
            final String name = aClass.getName();
            final String simpleName = aClass.getSimpleName();
            System.out.println(CamelAndUnderLineConverter.humpToLine2(simpleName).toUpperCase(Locale.ROOT) + "(" + name + ".class" + "),");
        }

    }

    @Test
    void main() {

    }

}