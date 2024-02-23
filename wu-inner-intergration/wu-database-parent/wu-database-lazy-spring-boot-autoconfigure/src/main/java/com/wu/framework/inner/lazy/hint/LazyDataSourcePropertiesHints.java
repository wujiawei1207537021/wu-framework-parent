package com.wu.framework.inner.lazy.hint;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @see LazyDataSourceProperties
 */
public class LazyDataSourcePropertiesHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        Constructor<LazyDataSourceProperties> constructor;
        try {
            constructor = LazyDataSourceProperties.class.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        hints.reflection().registerConstructor(constructor, ExecutableMode.INVOKE);
        // setUrl
        Method setUrl = ReflectionUtils.findMethod(LazyDataSourceProperties.class, "setUrl", String.class);
        hints.reflection().registerMethod(setUrl, ExecutableMode.INVOKE);
        // setUsername
        Method setUsername = ReflectionUtils.findMethod(LazyDataSourceProperties.class, "setUsername", String.class);
        hints.reflection().registerMethod(setUsername, ExecutableMode.INVOKE);
        // setPassword
        Method setPassword = ReflectionUtils.findMethod(LazyDataSourceProperties.class, "setPassword", String.class);
        hints.reflection().registerMethod(setPassword, ExecutableMode.INVOKE);
    }
}
