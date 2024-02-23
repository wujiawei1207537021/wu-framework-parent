package com.wu.smart.acw.server.config.enums;

import com.wu.framework.inner.layer.data.JavaClassType;
import com.wu.framework.inner.layer.stereotype.LayerClass;
import com.wu.smart.acw.core.domain.uo.AcwClassUo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
public enum AnnotationClassEnums {
    /**
     * AllArgsConstructor 注解
     */
    ALL_ARGS_CONSTRUCTOR(lombok.AllArgsConstructor.class),
    BUILDER(lombok.Builder.class),
    CLEANUP(lombok.Cleanup.class),
    CUSTOM_LOG(lombok.CustomLog.class),
    DATA(lombok.Data.class),
    EQUALS_AND_HASH_CODE(lombok.EqualsAndHashCode.class),
    GENERATED(lombok.Generated.class),
    GETTER(lombok.Getter.class),
    NO_ARGS_CONSTRUCTOR(lombok.NoArgsConstructor.class),
    NON_NULL(lombok.NonNull.class),
    REQUIRED_ARGS_CONSTRUCTOR(lombok.RequiredArgsConstructor.class),
    SETTER(lombok.Setter.class),
    SINGULAR(lombok.Singular.class),
    SNEAKY_THROWS(lombok.SneakyThrows.class),
    SYNCHRONIZED(lombok.Synchronized.class),
    TO_STRING(lombok.ToString.class),
    VALUE(lombok.Value.class),
    WITH(lombok.With.class),
    ACCESSORS(lombok.experimental.Accessors.class),
    DELEGATE(lombok.experimental.Delegate.class),
    EXTENSION_METHOD(lombok.experimental.ExtensionMethod.class),
    FIELD_DEFAULTS(lombok.experimental.FieldDefaults.class),
    FIELD_NAME_CONSTANTS(lombok.experimental.FieldNameConstants.class),
    HELPER(lombok.experimental.Helper.class),
    NON_FINAL(lombok.experimental.NonFinal.class),
    PACKAGE_PRIVATE(lombok.experimental.PackagePrivate.class),
    STANDARD_EXCEPTION(lombok.experimental.StandardException.class),
    SUPER_BUILDER(lombok.experimental.SuperBuilder.class),
    TOLERATE(lombok.experimental.Tolerate.class),
    UTILITY_CLASS(lombok.experimental.UtilityClass.class),
    WITH_BY(lombok.experimental.WithBy.class),
    WITHER(lombok.experimental.Wither.class),
    COMMONS_LOG(lombok.extern.apachecommons.CommonsLog.class),
    FLOGGER(lombok.extern.flogger.Flogger.class),
    JACKSONIZED(lombok.extern.jackson.Jacksonized.class),
    LOG(lombok.extern.java.Log.class),
    J_BOSS_LOG(lombok.extern.jbosslog.JBossLog.class),
    LOG4J(lombok.extern.log4j.Log4j.class),
    LOG4J2(lombok.extern.log4j.Log4j2.class),
    SLF4J(lombok.extern.slf4j.Slf4j.class),
    X_SLF4J(lombok.extern.slf4j.XSlf4j.class),
    VAL(lombok.val.class),

    ;

    private Class clazz;

    /**
     * 通过 class 对象创建 ClassUo对象
     *
     * @param clazz
     * @return
     */
    public static AcwClassUo create(Class clazz) {
        final String simpleName = clazz.getSimpleName();
        final String packageName = clazz.getPackage().getName();
        final Class[] classes = clazz.getClasses();
        final List<AcwClassUo> includedClasses = Arrays.stream(classes).map(c -> {
            AcwClassUo acwClassUo = new AcwClassUo();
            acwClassUo.setName(c.getSimpleName()).setPackageName(c.getPackage().getName()).setJavaClassType(JavaClassType.CLASS).setType(LayerClass.LayerType.NULL);
            return acwClassUo;
        }).collect(Collectors.toList());
        final AcwClassUo acwClassUo = new AcwClassUo();

        acwClassUo.setName(simpleName).setPackageName(packageName).setJavaClassType(JavaClassType.CLASS).setType(LayerClass.LayerType.NULL)
                .setIncludedClasses(includedClasses).setProjectId(0L);
        if (clazz.isAnnotation()) {
            acwClassUo.setJavaClassType(JavaClassType.ANNOTATION);
        } else if (clazz.isInterface()) {
            acwClassUo.setJavaClassType(JavaClassType.INTERFACE);
        } else if (clazz.isEnum()) {
            acwClassUo.setJavaClassType(JavaClassType.ENUM);
        }
        return acwClassUo;
    }

}
