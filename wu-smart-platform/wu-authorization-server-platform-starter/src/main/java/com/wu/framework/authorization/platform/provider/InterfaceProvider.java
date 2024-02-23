package com.wu.framework.authorization.platform.provider;


import com.wu.framework.authorization.platform.model.interface_.Interface;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * describe : 接口提供者
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 18:34
 */
@Tag(name = "接口提供者")
//@EasyController("/interface")
public class InterfaceProvider extends AbstractLazyCrudProvider<Interface,Interface, Long> implements CommandLineRunner {
    private final ApplicationContext applicationContext;
    private final LazyLambdaStream lazyLambdaStream;

    protected InterfaceProvider(LazyLambdaStream lazyLambdaStream, ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe 扫描当前项目中接口
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 00:09
     **/
    public List<Interface> scanInterface() {
        final String applicationName = applicationContext.getApplicationName();
        final Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(RequestMapping.class);

        List<Interface> interfaceList = new ArrayList<>();

        beansWithAnnotation.values().stream().map(Object::getClass).forEach(interfaceProviderClass -> {
            final RequestMapping mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(interfaceProviderClass, RequestMapping.class);
            final String[] parentPath = mergedAnnotation.value();
            String[] tag = new String[0];
            if (AnnotatedElementUtils.hasAnnotation(interfaceProviderClass, Api.class)) {
                final Api api = AnnotatedElementUtils.getMergedAnnotation(interfaceProviderClass, Api.class);
                tag = new String[]{api.tags()};
            }

            final Method[] methods = interfaceProviderClass.getMethods();

            for (Method method : methods) {
                final boolean hasAnnotation = AnnotatedElementUtils.hasAnnotation(method, RequestMapping.class);
                if (hasAnnotation) {
                    final RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
                    final Interface anInterface = new Interface();
                    anInterface.setApplicationName(applicationName);
                    anInterface.setPath(Arrays.asList(requestMapping.path()));
                    if (ObjectUtils.isEmpty(requestMapping.method())) {
                        anInterface.setRequestMethods(Arrays.asList(RequestMethod.values()));
                    } else {
                        anInterface.setRequestMethods(Arrays.asList(requestMapping.method()));
                    }
                    anInterface.setParentPath(Arrays.asList(parentPath));
                    final boolean hasApiOperation = AnnotatedElementUtils.hasAnnotation(method, ApiOperation.class);
                    if (hasApiOperation) {
                        final ApiOperation apiOperation = AnnotatedElementUtils.findMergedAnnotation(method, ApiOperation.class);
                        anInterface.setDescription(apiOperation.value());
                        final List<String> tags = new ArrayList<>();
                        if (!ObjectUtils.isEmpty(tag)) {
                            tags.addAll(Arrays.asList(tag));
                        }
                        tags.addAll(Arrays.asList(apiOperation.tags()));
                        anInterface.setTag(tags);
                    } else {
                        anInterface.setTag(Arrays.asList(tag));
                    }
                    interfaceList.add(anInterface);
                }
            }
        });
        return interfaceList;
    }

    @Override
    public void run(String... args) {
        try {
            final List<Interface> interfaceList = scanInterface();
            interfaceList.stream().parallel().forEach(lazyLambdaStream::upsert);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
