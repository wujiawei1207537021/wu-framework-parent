//package com.wu.framework.easy.listener;
//
//import com.wu.framework.easy.listener.core.AbstractEasyListenerAnnotationBeanPostProcessor;
//import com.wu.framework.easy.listener.stereotype.EasyListener;
//import com.wu.framework.easy.listener.stereotype.ListenerProcessorStrategy;
//import com.wu.framework.easy.listener.stereotype.kafka.EasyKafkaListener;
//import com.wu.framework.easy.listener.stereotype.kafka.EasyKafkaListeners;
//import com.wu.framework.easy.listener.stereotype.kafka.PartitionOffset;
//import com.wu.framework.easy.listener.stereotype.kafka.TopicPartition;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.aop.framework.Advised;
//import org.springframework.aop.support.AopUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.*;
//import org.springframework.beans.factory.config.*;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.expression.StandardBeanExpressionResolver;
//import org.springframework.core.MethodIntrospector;
//import org.springframework.core.MethodParameter;
//import org.springframework.core.OrderComparator;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.AnnotatedElementUtils;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.core.convert.converter.GenericConverter;
//import org.springframework.core.log.LogAccessor;
//import org.springframework.format.Formatter;
//import org.springframework.format.FormatterRegistry;
//import org.springframework.format.support.DefaultFormattingConversionService;
//import org.springframework.kafka.annotation.*;
//import org.springframework.kafka.config.*;
//import org.springframework.kafka.listener.KafkaListenerErrorHandler;
//import org.springframework.kafka.retrytopic.RetryTopicBootstrapper;
//import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
//import org.springframework.kafka.retrytopic.RetryTopicConfigurer;
//import org.springframework.kafka.retrytopic.RetryTopicInternalBeanNames;
//import org.springframework.kafka.support.KafkaNull;
//import org.springframework.kafka.support.TopicPartitionOffset;
//import org.springframework.lang.Nullable;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.converter.GenericMessageConverter;
//import org.springframework.messaging.converter.SmartMessageConverter;
//import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
//import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
//import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
//import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
//import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
//import org.springframework.util.Assert;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.Validator;
//
//import java.io.IOException;
//import java.io.StringReader;
//import java.lang.reflect.AnnotatedElement;
//import java.lang.reflect.Method;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.function.BiFunction;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * Bean post-processor that registers methods annotated with {@link EasyKafkaListener}
// * to be invoked by a Kafka message listener container created under the covers
// * by a {@link org.springframework.kafka.config.KafkaListenerContainerFactory}
// * according to the parameters of the annotation.
// *
// * <p>Annotated methods can use flexible arguments as defined by {@link EasyKafkaListener}.
// *
// * <p>This post-processor is automatically registered by Spring's {@link EnableKafka}
// * annotation.
// *
// * <p>Auto-detect any {@link KafkaListenerConfigurer} instances in the container,
// * allowing for customization of the registry to be used, the default container
// * factory or for fine-grained control over endpoints registration. See
// * {@link EnableKafka} Javadoc for complete usage details.
// *
// * @param <K> the key columnType.
// * @param <V> the value columnType.
// * @author Stephane Nicoll
// * @author Juergen Hoeller
// * @author Gary Russell
// * @author Artem Bilan
// * @author Dariusz Szablinski
// * @author Venil Noronha
// * @author Dimitri Penner
// * @author Filip Halemba
// * @author Tomaz Fernandes
// * @see EasyKafkaListener
// * @see KafkaListenerErrorHandler
// * @see EnableKafka
// * @see KafkaListenerConfigurer
// * @see KafkaListenerEndpointRegistrar
// * @see KafkaListenerEndpointRegistry
// * @see org.springframework.kafka.config.KafkaListenerEndpoint
// * @see MethodKafkaListenerEndpoint
// * @see KafkaListenerAnnotationBeanPostProcessor
// */
//@EnableKafka
//@ConditionalOnProperty(prefix = "spring.kafka", name = "bootstrap-servers")
//@ListenerProcessorStrategy(strategy = DynamicListenerType.KAFKA)
//public class EasyKafkaListenerAnnotationBeanPostProcessor<K, V> extends AbstractEasyListenerAnnotationBeanPostProcessor<EasyKafkaListener> implements BeanPostProcessor, Ordered, ApplicationContextAware, InitializingBean, SmartInitializingSingleton {
//
//    /**
//     * The bean name of the default {@link org.springframework.kafka.config.KafkaListenerContainerFactory}.
//     */
//    public static final String DEFAULT_KAFKA_LISTENER_CONTAINER_FACTORY_BEAN_NAME = "kafkaListenerContainerFactory";
//    private static final String GENERATED_ID_PREFIX = "org.springframework.kafka.KafkaListenerEndpointContainer#";
//    private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));
//
//    private final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));
//
//    private final ListenerScope listenerScope = new ListenerScope();
//
//    private final KafkaHandlerMethodFactoryAdapter messageHandlerMethodFactory =
//            new KafkaHandlerMethodFactoryAdapter();
//
//    private final KafkaListenerEndpointRegistrar registrar = new KafkaListenerEndpointRegistrar();
//
//    private final AtomicInteger counter = new AtomicInteger();
//
//    private KafkaListenerEndpointRegistry endpointRegistry;
//
//    private String defaultContainerFactoryBeanName = DEFAULT_KAFKA_LISTENER_CONTAINER_FACTORY_BEAN_NAME;
//
//    private ApplicationContext applicationContext;
//
//    private BeanFactory beanFactory;
//
//    private BeanExpressionResolver resolver = new StandardBeanExpressionResolver();
//
//    private BeanExpressionContext expressionContext;
//
//    private Charset charset = StandardCharsets.UTF_8;
//
//    private AnnotationEnhancer enhancer;
//
//    @Override
//    public int getOrder() {
//        return LOWEST_PRECEDENCE;
//    }
//
//    /**
//     * Set the {@link KafkaListenerEndpointRegistry} that will hold the created
//     * endpoint and manage the lifecycle of the related listener container.
//     *
//     * @param endpointRegistry the {@link KafkaListenerEndpointRegistry} to set.
//     */
//    public void setEndpointRegistry(KafkaListenerEndpointRegistry endpointRegistry) {
//        this.endpointRegistry = endpointRegistry;
//    }
//
//    /**
//     * Set the name of the {@link KafkaListenerContainerFactory} to use by default.
//     * <p>If none is specified, "kafkaListenerContainerFactory" is assumed to be defined.
//     *
//     * @param containerFactoryBeanName the {@link KafkaListenerContainerFactory} bean name.
//     */
//    public void setDefaultContainerFactoryBeanName(String containerFactoryBeanName) {
//        this.defaultContainerFactoryBeanName = containerFactoryBeanName;
//    }
//
//    /**
//     * Return the configured handler factory.
//     *
//     * @return the factory.
//     * @since 2.5.7
//     */
//    public MessageHandlerMethodFactory getMessageHandlerMethodFactory() {
//        return this.messageHandlerMethodFactory;
//    }
//
//    /**
//     * Set the {@link MessageHandlerMethodFactory} to use to configure the message
//     * listener responsible to serve an endpoint detected by this processor.
//     * <p>By default, {@link DefaultMessageHandlerMethodFactory} is used and it
//     * can be configured further to support additional method arguments
//     * or to customize conversion and validation support. See
//     * {@link DefaultMessageHandlerMethodFactory} Javadoc for more details.
//     *
//     * @param messageHandlerMethodFactory the {@link MessageHandlerMethodFactory} instance.
//     */
//    public void setMessageHandlerMethodFactory(MessageHandlerMethodFactory messageHandlerMethodFactory) {
//        this.messageHandlerMethodFactory.setHandlerMethodFactory(messageHandlerMethodFactory);
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//        if (applicationContext instanceof ConfigurableApplicationContext) {
//            setBeanFactory(((ConfigurableApplicationContext) applicationContext).getBeanFactory());
//        } else {
//            setBeanFactory(applicationContext);
//        }
//    }
//
//    /**
//     * Making a {@link BeanFactory} available is optional; if not set,
//     * {@link KafkaListenerConfigurer} beans won't get autodetected and an
//     * {@link #setEndpointRegistry endpoint registry} has to be explicitly configured.
//     *
//     * @param beanFactory the {@link BeanFactory} to be used.
//     */
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) {
//        this.beanFactory = beanFactory;
//        if (beanFactory instanceof ConfigurableListableBeanFactory) {
//            this.resolver = ((ConfigurableListableBeanFactory) beanFactory).getBeanExpressionResolver();
//            this.expressionContext = new BeanExpressionContext((ConfigurableListableBeanFactory) beanFactory,
//                    this.listenerScope);
//        }
//    }
//
//    /**
//     * Set a charset to use when converting byte[] to String in method arguments.
//     * Default UTF-8.
//     *
//     * @param charset the charset.
//     * @since 2.2
//     */
//    public void setCharset(Charset charset) {
//        Assert.notNull(charset, "'charset' cannot be null");
//        this.charset = charset;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        buildEnhancer();
//    }
//
//    @Override
//    public void afterSingletonsInstantiated() {
////        this.registrar.setBeanFactory(this.beanFactory);
////
////        if (this.beanFactory instanceof ListableBeanFactory) {
////            Map<String, KafkaListenerConfigurer> instances =
////                    ((ListableBeanFactory) this.beanFactory).getBeansOfType(KafkaListenerConfigurer.class);
////            for (KafkaListenerConfigurer configurer : instances.values()) {
////                configurer.configureKafkaListeners(this.registrar);
////            }
////        }
////
////        if (this.registrar.getEndpointRegistry() == null) {
////            if (this.endpointRegistry == null) {
////                Assert.state(this.beanFactory != null,
////                        "BeanFactory must be set to find endpoint registry by bean name");
////                this.endpointRegistry = this.beanFactory.getBean(
////                        KafkaListenerConfigUtils.KAFKA_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
////                        KafkaListenerEndpointRegistry.class);
////            }
////            this.registrar.setEndpointRegistry(this.endpointRegistry);
////        }
////
////        if (this.defaultContainerFactoryBeanName != null) {
////            this.registrar.setContainerFactoryBeanName(this.defaultContainerFactoryBeanName);
////        }
////
////        // Set the custom handler method factory once resolved by the configurer
////        MessageHandlerMethodFactory handlerMethodFactory = this.registrar.getMessageHandlerMethodFactory();
////        if (handlerMethodFactory != null) {
////            this.messageHandlerMethodFactory.setHandlerMethodFactory(handlerMethodFactory);
////        } else {
////            addFormatters(this.messageHandlerMethodFactory.defaultFormattingConversionService);
////        }
////
////        // Actually register all listeners
////        this.registrar.afterPropertiesSet();
////        Map<String, ContainerGroupSequencer> sequencers =
////                this.applicationContext.getBeansOfType(ContainerGroupSequencer.class, false, false);
////        sequencers.values().forEach(seq -> seq.initialize());
//    }
//
//    private void buildEnhancer() {
//        if (this.applicationContext != null) {
//            Map<String, AnnotationEnhancer> enhancersMap =
//                    this.applicationContext.getBeansOfType(AnnotationEnhancer.class, false, false);
//            if (enhancersMap.size() > 0) {
//                List<AnnotationEnhancer> enhancers = enhancersMap.values()
//                        .stream()
//                        .sorted(new OrderComparator())
//                        .collect(Collectors.toList());
//                this.enhancer = (attrs, element) -> {
//                    Map<String, Object> newAttrs = attrs;
//                    for (AnnotationEnhancer enh : enhancers) {
//                        newAttrs = enh.apply(newAttrs, element);
//                    }
//                    return attrs;
//                };
//            }
//        }
//    }
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
//        if (!this.nonAnnotatedClasses.contains(bean.getClass())) {
//            Class<?> targetClass = AopUtils.getTargetClass(bean);
//            Collection<EasyKafkaListener> classLevelListeners = findListenerAnnotations(targetClass);
//            final boolean hasClassLevelListeners = classLevelListeners.size() > 0;
//            final List<Method> multiMethods = new ArrayList<>();
//            Map<Method, Set<EasyKafkaListener>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
//                    (MethodIntrospector.MetadataLookup<Set<EasyKafkaListener>>) method -> {
//                        Set<EasyKafkaListener> listenerMethods = findListenerAnnotations(method);
//                        return (!listenerMethods.isEmpty() ? listenerMethods : null);
//                    });
//            if (hasClassLevelListeners) {
//                Set<Method> methodsWithHandler = MethodIntrospector.selectMethods(targetClass,
//                        (ReflectionUtils.MethodFilter) method ->
//                                AnnotationUtils.findAnnotation(method, KafkaHandler.class) != null);
//                multiMethods.addAll(methodsWithHandler);
//            }
//            if (annotatedMethods.isEmpty()) {
//                this.nonAnnotatedClasses.add(bean.getClass());
//                this.logger.trace(() -> "No @EasyKafkaListener annotations found on bean columnType: " + bean.getClass());
//            } else {
//                // Non-empty set of methods
//                for (Map.Entry<Method, Set<EasyKafkaListener>> entry : annotatedMethods.entrySet()) {
//                    Method method = entry.getKey();
//                    for (EasyKafkaListener listener : entry.getValue()) {
//                        processKafkaListener(listener, method, bean, beanName);
//                    }
//                }
//                this.logger.debug(() -> annotatedMethods.size() + " @EasyKafkaListener methods processed on bean '"
//                        + beanName + "': " + annotatedMethods);
//            }
//            if (hasClassLevelListeners) {
//                processMultiMethodListeners(classLevelListeners, multiMethods, bean, beanName);
//            }
//        }
//        return bean;
//    }
//
//    /*
//     * AnnotationUtils.getRepeatableAnnotations does not look at interfaces
//     */
//    private Collection<EasyKafkaListener> findListenerAnnotations(Class<?> clazz) {
//        Set<EasyKafkaListener> listeners = new HashSet<>();
//        EasyKafkaListener ann = AnnotatedElementUtils.findMergedAnnotation(clazz, EasyKafkaListener.class);
//        if (ann != null) {
//            ann = enhance(clazz, ann);
//            listeners.add(ann);
//        }
//        EasyKafkaListeners anns = AnnotationUtils.findAnnotation(clazz, EasyKafkaListeners.class);
//        if (anns != null) {
//            listeners.addAll(Arrays.stream(anns.value())
//                    .map(anno -> enhance(clazz, anno))
//                    .collect(Collectors.toList()));
//        }
//        return listeners;
//    }
//
//    /*
//     * AnnotationUtils.getRepeatableAnnotations does not look at interfaces
//     */
//    private Set<EasyKafkaListener> findListenerAnnotations(Method method) {
//        Set<EasyKafkaListener> listeners = new HashSet<>();
//        EasyKafkaListener ann = AnnotatedElementUtils.findMergedAnnotation(method, EasyKafkaListener.class);
//        if (ann != null) {
//            ann = enhance(method, ann);
//            listeners.add(ann);
//        }
//        EasyKafkaListeners anns = AnnotationUtils.findAnnotation(method, EasyKafkaListeners.class);
//        if (anns != null) {
//            listeners.addAll(Arrays.stream(anns.value())
//                    .map(anno -> enhance(method, anno))
//                    .collect(Collectors.toList()));
//        }
//        return listeners;
//    }
//
//    private EasyKafkaListener enhance(AnnotatedElement element, EasyKafkaListener ann) {
//        if (this.enhancer == null) {
//            return ann;
//        } else {
//            return AnnotationUtils.synthesizeAnnotation(
//                    this.enhancer.apply(AnnotationUtils.getAnnotationAttributes(ann), element), EasyKafkaListener.class, null);
//        }
//    }
//
//    @Override
//    public void processMultiMethodListeners(Collection<EasyKafkaListener> classLevelListeners, List<Method> multiMethods,
//                                            Object bean, String beanName) {
//
//        List<Method> checkedMethods = new ArrayList<>();
//        Method defaultMethod = null;
//        for (Method method : multiMethods) {
//            Method checked = checkProxy(method, bean);
//            KafkaHandler annotation = AnnotationUtils.findAnnotation(method, KafkaHandler.class);
//            if (annotation != null && annotation.isDefault()) {
//                final Method toAssert = defaultMethod;
//                Assert.state(toAssert == null, () -> "Only one @KafkaHandler can be marked 'isDefault', found: "
//                        + toAssert.toString() + " and " + method);
//                defaultMethod = checked;
//            }
//            checkedMethods.add(checked);
//        }
//        for (EasyKafkaListener classLevelListener : classLevelListeners) {
//            MultiMethodKafkaListenerEndpoint<K, V> endpoint =
//                    new MultiMethodKafkaListenerEndpoint<>(checkedMethods, defaultMethod, bean);
//            String beanRef = classLevelListener.beanRef();
//            this.listenerScope.addListener(beanRef, bean);
//            processListener(endpoint, classLevelListener, bean, beanName, resolveTopics(classLevelListener),
//                    resolveTopicPartitions(classLevelListener));
//            this.listenerScope.removeListener(beanRef);
//        }
//    }
//
//    /**
//     * 监听注解
//     *
//     * @param listener
//     * @param method
//     * @param bean
//     * @param beanName
//     */
//    @Override
//    public void processListener(EasyKafkaListener listener, Method method, Object bean, String beanName) {
//        // TODO
//    }
//
//    protected void processKafkaListener(EasyKafkaListener easyKafkaListener, Method method, Object bean, String beanName) {
//        Method methodToUse = checkProxy(method, bean);
//        MethodKafkaListenerEndpoint<K, V> endpoint = new MethodKafkaListenerEndpoint<>();
//        endpoint.setMethod(methodToUse);
//
//        String beanRef = easyKafkaListener.beanRef();
//        this.listenerScope.addListener(beanRef, bean);
//        String[] topics = resolveTopics(easyKafkaListener);
//        TopicPartitionOffset[] tps = resolveTopicPartitions(easyKafkaListener);
//        if (!processMainAndRetryListeners(easyKafkaListener, bean, beanName, methodToUse, endpoint, topics, tps)) {
//            processListener(endpoint, easyKafkaListener, bean, beanName, topics, tps);
//        }
//        this.listenerScope.removeListener(beanRef);
//    }
//
//    private boolean processMainAndRetryListeners(EasyKafkaListener easyKafkaListener, Object bean, String beanName,
//                                                 Method methodToUse, MethodKafkaListenerEndpoint<K, V> endpoint, String[] topics,
//                                                 TopicPartitionOffset[] tps) {
//
//        String[] retryableCandidates = topics;
//        if (retryableCandidates.length == 0 && tps.length > 0) {
//            retryableCandidates = Arrays.stream(tps)
//                    .map(tp -> tp.getTopic())
//                    .distinct()
//                    .collect(Collectors.toList())
//                    .toArray(new String[0]);
//        }
//
//        RetryTopicConfiguration retryTopicConfiguration = new RetryTopicConfigurationProvider(this.beanFactory,
//                this.resolver, this.expressionContext)
//                .findRetryConfigurationFor(retryableCandidates, methodToUse, bean);
//
//        if (retryTopicConfiguration == null) {
//            String[] candidates = retryableCandidates;
//            this.logger.debug(() ->
//                    "No retry topic configuration found for topics " + Arrays.toString(candidates));
//            return false;
//        }
//
//        RetryTopicConfigurer.EndpointProcessor endpointProcessor = endpointToProcess ->
//                this.processKafkaListenerAnnotationForRetryTopic(endpointToProcess, easyKafkaListener, bean, topics, tps);
//
//        KafkaListenerContainerFactory<?> factory =
//                resolveContainerFactory(easyKafkaListener, resolve(easyKafkaListener.containerFactory()), beanName);
//        if (null == factory) {
//            factory = new ConcurrentKafkaListenerContainerFactory<>();
//        }
//        getRetryTopicConfigurer()
//                .processMainAndRetryListeners(endpointProcessor, endpoint, retryTopicConfiguration,
//                        this.registrar, factory, this.defaultContainerFactoryBeanName);
//        return true;
//    }
//
//
//    private RetryTopicConfigurer getRetryTopicConfigurer() {
//        bootstrapRetryTopicIfNecessary();
//        return this.beanFactory.getBean(RetryTopicInternalBeanNames.RETRY_TOPIC_CONFIGURER, RetryTopicConfigurer.class);
//    }
//
//    private void bootstrapRetryTopicIfNecessary() {
//        if (!(this.beanFactory instanceof BeanDefinitionRegistry)) {
//            throw new IllegalStateException("BeanFactory must be an instance of "
//                    + BeanDefinitionRegistry.class.getSimpleName()
//                    + " to bootstrap the RetryTopic functionality. Provided beanFactory: "
//                    + this.beanFactory.getClass().getSimpleName());
//        }
//        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) this.beanFactory;
//        if (!registry.containsBeanDefinition(RetryTopicInternalBeanNames
//                .RETRY_TOPIC_BOOTSTRAPPER)) {
//            registry.registerBeanDefinition(RetryTopicInternalBeanNames
//                            .RETRY_TOPIC_BOOTSTRAPPER,
//                    new RootBeanDefinition(RetryTopicBootstrapper.class));
//            this.beanFactory.getBean(RetryTopicInternalBeanNames
//                    .RETRY_TOPIC_BOOTSTRAPPER, RetryTopicBootstrapper.class).bootstrapRetryTopic();
//        }
//    }
//
//    @Override
//    public Method checkProxy(Method methodArg, Object bean) {
//        Method method = methodArg;
//        if (AopUtils.isJdkDynamicProxy(bean)) {
//            try {
//                // Found a @EasyKafkaListener method on the target class for this JDK proxy ->
//                // is it also present on the proxy itself?
//                method = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
//                Class<?>[] proxiedInterfaces = ((Advised) bean).getProxiedInterfaces();
//                for (Class<?> iface : proxiedInterfaces) {
//                    try {
//                        method = iface.getMethod(method.getName(), method.getParameterTypes());
//                        break;
//                    } catch (@SuppressWarnings("unused") NoSuchMethodException noMethod) {
//                        // NOSONAR
//                    }
//                }
//            } catch (SecurityException ex) {
//                ReflectionUtils.handleReflectionException(ex);
//            } catch (NoSuchMethodException ex) {
//                throw new IllegalStateException(String.format(
//                        "@EasyKafkaListener method '%s' found on bean target class '%s', " +
//                                "but not found in any interface(s) for bean JDK proxy. Either " +
//                                "pull the method up to an interface or switch to subclass (CGLIB) " +
//                                "proxies by setting proxy-target-class/proxyTargetClass " +
//                                "attribute to 'true'", method.getName(),
//                        method.getDeclaringClass().getSimpleName()), ex);
//            }
//        }
//        return method;
//    }
//
//    protected void processListener(MethodKafkaListenerEndpoint<?, ?> endpoint, EasyKafkaListener easyKafkaListener,
//                                   Object bean, String beanName, String[] topics, TopicPartitionOffset[] tps) {
//
//        processKafkaListenerAnnotationBeforeRegistration(endpoint, easyKafkaListener, bean, topics, tps);
//
//        String containerFactory = resolve(easyKafkaListener.containerFactory());
//
//        KafkaListenerContainerFactory<?> listenerContainerFactory = resolveContainerFactory(easyKafkaListener, containerFactory, beanName);
//        if (null == listenerContainerFactory) {
//            listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
//        }
//        this.registrar.registerEndpoint(endpoint, listenerContainerFactory);
//
//        processKafkaListenerEndpointAfterRegistration(endpoint, easyKafkaListener);
//    }
//
//    private void processKafkaListenerAnnotationForRetryTopic(MethodKafkaListenerEndpoint<?, ?> endpoint,
//                                                             EasyKafkaListener easyKafkaListener, Object bean, String[] topics, TopicPartitionOffset[] tps) {
//
//        processKafkaListenerAnnotationBeforeRegistration(endpoint, easyKafkaListener, bean, topics, tps);
//        processKafkaListenerEndpointAfterRegistration(endpoint, easyKafkaListener);
//    }
//
//    private void processKafkaListenerAnnotationBeforeRegistration(MethodKafkaListenerEndpoint<?, ?> endpoint,
//                                                                  EasyKafkaListener easyKafkaListener, Object bean, String[] topics, TopicPartitionOffset[] tps) {
//
//        endpoint.setBean(bean);
//        endpoint.setMessageHandlerMethodFactory(this.messageHandlerMethodFactory);
//        endpoint.setId(getEndpointId(easyKafkaListener));
//        endpoint.setGroupId(getEndpointGroupId(easyKafkaListener, endpoint.getId()));
//        endpoint.setTopicPartitions(tps);
//        endpoint.setTopics(topics);
//        endpoint.setTopicPattern(resolvePattern(easyKafkaListener));
//        endpoint.setClientIdPrefix(resolveExpressionAsString(easyKafkaListener.clientIdPrefix(), "clientIdPrefix"));
//        String group = easyKafkaListener.containerGroup();
//        if (StringUtils.hasText(group)) {
//            Object resolvedGroup = resolveExpression(group);
//            if (resolvedGroup instanceof String) {
//                endpoint.setGroup((String) resolvedGroup);
//            }
//        }
//        String concurrency = easyKafkaListener.concurrency();
//        if (StringUtils.hasText(concurrency)) {
//            endpoint.setConcurrency(resolveExpressionAsInteger(concurrency, "concurrency"));
//        }
//        String autoStartup = easyKafkaListener.autoStartup();
//        if (StringUtils.hasText(autoStartup)) {
//            endpoint.setAutoStartup(resolveExpressionAsBoolean(autoStartup, "autoStartup"));
//        }
//        resolveKafkaProperties(endpoint, easyKafkaListener.properties());
//        endpoint.setSplitIterables(easyKafkaListener.splitIterables());
//    }
//
//    private void processKafkaListenerEndpointAfterRegistration(MethodKafkaListenerEndpoint<?, ?> endpoint, EasyKafkaListener easyKafkaListener) {
//        endpoint.setBeanFactory(this.beanFactory);
//        String errorHandlerBeanName = resolveExpressionAsString(easyKafkaListener.errorHandler(), "errorHandler");
//        if (StringUtils.hasText(errorHandlerBeanName)) {
//            resolveErrorHandler(endpoint, easyKafkaListener);
//        }
//        String converterBeanName = resolveExpressionAsString(easyKafkaListener.contentTypeConverter(), "contentTypeConverter");
//        if (StringUtils.hasText(converterBeanName)) {
//            resolveContentTypeConverter(endpoint, easyKafkaListener);
//        }
//    }
//
//    private void resolveErrorHandler(MethodKafkaListenerEndpoint<?, ?> endpoint, EasyKafkaListener easyKafkaListener) {
//        Object errorHandler = resolveExpression(easyKafkaListener.errorHandler());
//        if (errorHandler instanceof KafkaListenerErrorHandler) {
//            endpoint.setErrorHandler((KafkaListenerErrorHandler) errorHandler);
//        } else {
//            String errorHandlerBeanName = resolveExpressionAsString(easyKafkaListener.errorHandler(), "errorHandler");
//            if (StringUtils.hasText(errorHandlerBeanName)) {
//                endpoint.setErrorHandler(
//                        this.beanFactory.getBean(errorHandlerBeanName, KafkaListenerErrorHandler.class));
//            }
//        }
//    }
//
//    private void resolveContentTypeConverter(MethodKafkaListenerEndpoint<?, ?> endpoint, EasyKafkaListener easyKafkaListener) {
//        Object converter = resolveExpression(easyKafkaListener.contentTypeConverter());
//        if (converter instanceof SmartMessageConverter) {
//            endpoint.setMessagingConverter((SmartMessageConverter) converter);
//        } else {
//            String converterBeanName = resolveExpressionAsString(easyKafkaListener.contentTypeConverter(),
//                    "contentTypeConverter");
//            if (StringUtils.hasText(converterBeanName)) {
//                endpoint.setMessagingConverter(
//                        this.beanFactory.getBean(converterBeanName, SmartMessageConverter.class));
//            }
//        }
//    }
//
//    @Nullable
//    private KafkaListenerContainerFactory<?> resolveContainerFactory(EasyKafkaListener easyKafkaListener,
//                                                                     Object factoryTarget, String beanName) {
//        String containerFactory = easyKafkaListener.containerFactory();
//        if (!StringUtils.hasText(containerFactory)) {
//            return null;
//        }
//
//        KafkaListenerContainerFactory<?> factory = null;
//
//        Object resolved = resolveExpression(containerFactory);
//        if (resolved instanceof KafkaListenerContainerFactory) {
//            return (KafkaListenerContainerFactory<?>) resolved;
//        }
//        String containerFactoryBeanName = resolveExpressionAsString(containerFactory,
//                "containerFactory");
//        if (StringUtils.hasText(containerFactoryBeanName)) {
//            assertBeanFactory();
//            try {
//                factory = this.beanFactory.getBean(containerFactoryBeanName, KafkaListenerContainerFactory.class);
//            } catch (NoSuchBeanDefinitionException ex) {
//                throw new BeanInitializationException(
//                        noBeanFoundMessage(factoryTarget, beanName, containerFactoryBeanName,
//                                KafkaListenerContainerFactory.class), ex);
//            }
//        }
//        return factory;
//    }
//
//    protected void assertBeanFactory() {
//        Assert.state(this.beanFactory != null, "BeanFactory must be set to obtain container factory by bean name");
//    }
//
//    protected String noBeanFoundMessage(Object target, String listenerBeanName, String requestedBeanName,
//                                        Class<?> expectedClass) {
//
//        return "Could not register Kafka listener endpoint on ["
//                + target + "] for bean " + listenerBeanName + ", no '" + expectedClass.getSimpleName() + "' with id '"
//                + requestedBeanName + "' was found in the application context";
//    }
//
//    private void resolveKafkaProperties(MethodKafkaListenerEndpoint<?, ?> endpoint, String[] propertyStrings) {
//        if (propertyStrings.length > 0) {
//            Properties properties = new Properties();
//            for (String property : propertyStrings) {
//                String value = resolveExpressionAsString(property, "property");
//                if (value != null) {
//                    try {
//                        properties.load(new StringReader(value));
//                    } catch (IOException e) {
//                        this.logger.error(e, () -> "Failed to load property " + property + ", continuing...");
//                    }
//                }
//            }
//            endpoint.setConsumerProperties(properties);
//        }
//    }
//
//    private String getEndpointId(EasyKafkaListener easyKafkaListener) {
//        if (StringUtils.hasText(easyKafkaListener.id())) {
//            return resolveExpressionAsString(easyKafkaListener.id(), "id");
//        } else {
//            return GENERATED_ID_PREFIX + this.counter.getAndIncrement();
//        }
//    }
//
//    private String getEndpointGroupId(EasyKafkaListener easyKafkaListener, String id) {
//        String groupId = null;
//        if (StringUtils.hasText(easyKafkaListener.groupId())) {
//            groupId = resolveExpressionAsString(easyKafkaListener.groupId(), "groupId");
//        }
//        if (groupId == null && easyKafkaListener.idIsGroup() && StringUtils.hasText(easyKafkaListener.id())) {
//            groupId = id;
//        }
//        return groupId;
//    }
//
//    private TopicPartitionOffset[] resolveTopicPartitions(EasyKafkaListener easyKafkaListener) {
//        TopicPartition[] topicPartitions = easyKafkaListener.topicPartitions();
//        List<TopicPartitionOffset> result = new ArrayList<>();
//        if (topicPartitions.length > 0) {
//            for (TopicPartition topicPartition : topicPartitions) {
//                result.addAll(resolveTopicPartitionsList(topicPartition));
//            }
//        }
//        return result.toArray(new TopicPartitionOffset[0]);
//    }
//
//    private String[] resolveTopics(EasyKafkaListener easyKafkaListener) {
//        String[] topics = easyKafkaListener.topics();
//        List<String> result = new ArrayList<>();
//        if (topics.length > 0) {
//            for (String topic1 : topics) {
//                Object topic = resolveExpression(topic1);
//                resolveAsString(topic, result);
//            }
//        }
//        return result.toArray(new String[0]);
//    }
//
//    private Pattern resolvePattern(EasyKafkaListener easyKafkaListener) {
//        Pattern pattern = null;
//        String text = easyKafkaListener.topicPattern();
//        if (StringUtils.hasText(text)) {
//            Object resolved = resolveExpression(text);
//            if (resolved instanceof Pattern) {
//                pattern = (Pattern) resolved;
//            } else if (resolved instanceof String) {
//                pattern = Pattern.compile((String) resolved);
//            } else if (resolved != null) {
//                throw new IllegalStateException(
//                        "topicPattern must resolve to a Pattern or String, not " + resolved.getClass());
//            }
//        }
//        return pattern;
//    }
//
//    private List<TopicPartitionOffset> resolveTopicPartitionsList(TopicPartition topicPartition) {
//        Object topic = resolveExpression(topicPartition.topic());
//        Assert.state(topic instanceof String,
//                () -> "topic in @TopicPartition must resolve to a String, not " + topic.getClass());
//        Assert.state(StringUtils.hasText((String) topic), "topic in @TopicPartition must not be empty");
//        String[] partitions = topicPartition.partitions();
//        PartitionOffset[] partitionOffsets = topicPartition.partitionOffsets();
//        Assert.state(partitions.length > 0 || partitionOffsets.length > 0,
//                () -> "At least one 'partition' or 'partitionOffset' required in @TopicPartition for topic '" + topic + "'");
//        List<TopicPartitionOffset> result = new ArrayList<>();
//        for (String partition : partitions) {
//            resolvePartitionAsInteger((String) topic, resolveExpression(partition), result, null, false, false);
//        }
//        if (partitionOffsets.length == 1 && "*".equals(partitionOffsets[0].partition())) {
//            result.forEach(tpo -> {
//                tpo.setOffset(resolveInitialOffset(tpo.getTopic(), partitionOffsets[0]));
//                tpo.setRelativeToCurrent(isRelative(tpo.getTopic(), partitionOffsets[0]));
//            });
//        } else {
//            for (PartitionOffset partitionOffset : partitionOffsets) {
//                Assert.isTrue(!"*".equals(partitionOffset.partition()), () ->
//                        "Partition wildcard '*' is only allowed in a single @PartitionOffset in " + result);
//                resolvePartitionAsInteger((String) topic, resolveExpression(partitionOffset.partition()), result,
//                        resolveInitialOffset(topic, partitionOffset), isRelative(topic, partitionOffset), true);
//            }
//        }
//        Assert.isTrue(result.size() > 0, () -> "At least one partition required for " + topic);
//        return result;
//    }
//
//    private Long resolveInitialOffset(Object topic, PartitionOffset partitionOffset) {
//        Object initialOffsetValue = resolveExpression(partitionOffset.initialOffset());
//        Long initialOffset;
//        if (initialOffsetValue instanceof String) {
//            Assert.state(StringUtils.hasText((String) initialOffsetValue),
//                    () -> "'initialOffset' in @PartitionOffset for topic '" + topic + "' cannot be empty");
//            initialOffset = Long.valueOf((String) initialOffsetValue);
//        } else if (initialOffsetValue instanceof Long) {
//            initialOffset = (Long) initialOffsetValue;
//        } else {
//            throw new IllegalArgumentException(String.format(
//                    "@PartitionOffset for topic '%s' can't resolve '%s' as a Long or String, resolved to '%s'",
//                    topic, partitionOffset.initialOffset(), initialOffsetValue.getClass()));
//        }
//        return initialOffset;
//    }
//
//    private boolean isRelative(Object topic, PartitionOffset partitionOffset) {
//        Object relativeToCurrentValue = resolveExpression(partitionOffset.relativeToCurrent());
//        Boolean relativeToCurrent;
//        if (relativeToCurrentValue instanceof String) {
//            relativeToCurrent = Boolean.valueOf((String) relativeToCurrentValue);
//        } else if (relativeToCurrentValue instanceof Boolean) {
//            relativeToCurrent = (Boolean) relativeToCurrentValue;
//        } else {
//            throw new IllegalArgumentException(String.format(
//                    "@PartitionOffset for topic '%s' can't resolve '%s' as a Boolean or String, resolved to '%s'",
//                    topic, partitionOffset.relativeToCurrent(), relativeToCurrentValue.getClass()));
//        }
//        return relativeToCurrent;
//    }
//
//    @SuppressWarnings("unchecked")
//    private void resolveAsString(Object resolvedValue, List<String> result) {
//        if (resolvedValue instanceof String[]) {
//            for (Object object : (String[]) resolvedValue) {
//                resolveAsString(object, result);
//            }
//        } else if (resolvedValue instanceof String) {
//            result.add((String) resolvedValue);
//        } else if (resolvedValue instanceof Iterable) {
//            for (Object object : (Iterable<Object>) resolvedValue) {
//                resolveAsString(object, result);
//            }
//        } else {
//            throw new IllegalArgumentException(String.format(
//                    "@KafKaListener can't resolve '%s' as a String", resolvedValue));
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    private void resolvePartitionAsInteger(String topic, Object resolvedValue,
//                                           List<TopicPartitionOffset> result, @Nullable Long offset, boolean isRelative, boolean checkDups) {
//
//        if (resolvedValue instanceof String[]) {
//            for (Object object : (String[]) resolvedValue) {
//                resolvePartitionAsInteger(topic, object, result, offset, isRelative, checkDups);
//            }
//        } else if (resolvedValue instanceof String) {
//            Assert.state(StringUtils.hasText((String) resolvedValue),
//                    () -> "partition in @TopicPartition for topic '" + topic + "' cannot be empty");
//            List<TopicPartitionOffset> collected = parsePartitions((String) resolvedValue)
//                    .map(part -> new TopicPartitionOffset(topic, part, offset, isRelative))
//                    .collect(Collectors.toList());
//            if (checkDups) {
//                collected.forEach(tpo -> {
//                    Assert.state(!result.contains(tpo), () ->
//                            String.format("@TopicPartition can't have the same partition configuration twice: [%s]",
//                                    tpo));
//                });
//            }
//            result.addAll(collected);
//        } else if (resolvedValue instanceof Integer[]) {
//            for (Integer partition : (Integer[]) resolvedValue) {
//                result.add(new TopicPartitionOffset(topic, partition));
//            }
//        } else if (resolvedValue instanceof Integer) {
//            result.add(new TopicPartitionOffset(topic, (Integer) resolvedValue));
//        } else if (resolvedValue instanceof Iterable) {
//            for (Object object : (Iterable<Object>) resolvedValue) {
//                resolvePartitionAsInteger(topic, object, result, offset, isRelative, checkDups);
//            }
//        } else {
//            throw new IllegalArgumentException(String.format(
//                    "@KafKaListener for topic '%s' can't resolve '%s' as an Integer or String", topic, resolvedValue));
//        }
//    }
//
//    private String resolveExpressionAsString(String value, String attribute) {
//        Object resolved = resolveExpression(value);
//        if (resolved instanceof String) {
//            return (String) resolved;
//        } else if (resolved != null) {
//            throw new IllegalStateException("The [" + attribute + "] must resolve to a String. "
//                    + "Resolved to [" + resolved.getClass() + "] for [" + value + "]");
//        }
//        return null;
//    }
//
//    private Integer resolveExpressionAsInteger(String value, String attribute) {
//        Object resolved = resolveExpression(value);
//        Integer result = null;
//        if (resolved instanceof String) {
//            result = Integer.parseInt((String) resolved);
//        } else if (resolved instanceof Number) {
//            result = ((Number) resolved).intValue();
//        } else if (resolved != null) {
//            throw new IllegalStateException(
//                    "The [" + attribute + "] must resolve to an Number or a String that can be parsed as an Integer. "
//                            + "Resolved to [" + resolved.getClass() + "] for [" + value + "]");
//        }
//        return result;
//    }
//
//    private Boolean resolveExpressionAsBoolean(String value, String attribute) {
//        Object resolved = resolveExpression(value);
//        Boolean result = null;
//        if (resolved instanceof Boolean) {
//            result = (Boolean) resolved;
//        } else if (resolved instanceof String) {
//            result = Boolean.parseBoolean((String) resolved);
//        } else if (resolved != null) {
//            throw new IllegalStateException(
//                    "The [" + attribute + "] must resolve to a Boolean or a String that can be parsed as a Boolean. "
//                            + "Resolved to [" + resolved.getClass() + "] for [" + value + "]");
//        }
//        return result;
//    }
//
//    private Object resolveExpression(String value) {
//        return this.resolver.evaluate(resolve(value), this.expressionContext);
//    }
//
//    /**
//     * Resolve the specified value if possible.
//     *
//     * @param value the value to resolve
//     * @return the resolved value
//     * @see ConfigurableBeanFactory#resolveEmbeddedValue
//     */
//    private String resolve(String value) {
//        if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
//            return ((ConfigurableBeanFactory) this.beanFactory).resolveEmbeddedValue(value);
//        }
//        return value;
//    }
//
//    private void addFormatters(FormatterRegistry registry) {
//        for (Converter<?, ?> converter : getBeansOfType(Converter.class)) {
//            registry.addConverter(converter);
//        }
//        for (GenericConverter converter : getBeansOfType(GenericConverter.class)) {
//            registry.addConverter(converter);
//        }
//        for (Formatter<?> formatter : getBeansOfType(Formatter.class)) {
//            registry.addFormatter(formatter);
//        }
//    }
//
//    private <T> Collection<T> getBeansOfType(Class<T> type) {
//        if (EasyKafkaListenerAnnotationBeanPostProcessor.this.beanFactory instanceof ListableBeanFactory) {
//            return ((ListableBeanFactory) EasyKafkaListenerAnnotationBeanPostProcessor.this.beanFactory)
//                    .getBeansOfType(type)
//                    .values();
//        } else {
//            return Collections.emptySet();
//        }
//    }
//
//    /**
//     * Parse a list of partitions into a {@link List}. Example: "0-5,10-15".
//     *
//     * @param partsString the comma-delimited list of partitions/ranges.
//     * @return the stream of partition numbers, sorted and de-duplicated.
//     * @since 2.6.4
//     */
//    private Stream<Integer> parsePartitions(String partsString) {
//        String[] partsStrings = partsString.split(",");
//        if (partsStrings.length == 1 && !partsStrings[0].contains("-")) {
//            return Stream.of(Integer.parseInt(partsStrings[0].trim()));
//        }
//        List<Integer> parts = new ArrayList<>();
//        for (String part : partsStrings) {
//            if (part.contains("-")) {
//                String[] startEnd = part.split("-");
//                Assert.state(startEnd.length == 2, "Only one hyphen allowed for a range of partitions: " + part);
//                int start = Integer.parseInt(startEnd[0].trim());
//                int end = Integer.parseInt(startEnd[1].trim());
//                Assert.state(end >= start, "Invalid range: " + part);
//                for (int i = start; i <= end; i++) {
//                    parts.add(i);
//                }
//            } else {
//                parsePartitions(part).forEach(p -> parts.add(p));
//            }
//        }
//        return parts.stream()
//                .sorted()
//                .distinct();
//    }
//
//
//    /**
//     * 处理 EasyListener 监听
//     *
//     * @param listener
//     * @param method
//     * @param bean
//     * @param beanName
//     */
//    @Override
//    public void processEasyListener(EasyListener listener, Method method, Object bean, String beanName) {
//
//
//        Method methodToUse = checkProxy(method, bean);
//        MethodKafkaListenerEndpoint<K, V> endpoint = new MethodKafkaListenerEndpoint<>();
//        endpoint.setMethod(methodToUse);
//
//        String beanRef = "";
//        this.listenerScope.addListener(beanRef, bean);
//
//        endpoint.setId(bean.getClass().getName() + "#" + method.getName());
//
//        List<String> result = new ArrayList<>();
//        if (listener.topics().length > 0) {
//            for (String topic1 : listener.topics()) {
//                Object topic = resolveExpression(topic1);
//                resolveAsString(topic, result);
//            }
//        }
//        String[] topics = result.toArray(new String[0]);
//
//
////        TopicPartitionOffset[] tps = resolveTopicPartitions(easyKafkaListener);
////
////        if (!processMainAndRetryListeners(easyKafkaListener, bean, beanName, methodToUse, endpoint, topics, tps)) {
////            processListener(endpoint, easyKafkaListener, bean, beanName, topics, tps);
////        }
//
//
//        KafkaListenerContainerFactory<?> listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
//
//        this.registrar.registerEndpoint(endpoint, listenerContainerFactory);
//
//        this.listenerScope.removeListener(beanRef);
//
//    }
//
//    /**
//     * 处理注解中指定类型注解的 监听
//     *
//     * @param listener
//     * @param method
//     * @param bean
//     * @param beanName
//     */
//    @Override
//    public void processAssignEasyListener(EasyListener listener, Method method, Object bean, String beanName) {
//        processKafkaListener(listener.kafkaListener(), method, bean, beanName);
//    }
//
//
//    /**
//     * hasStrategy
//     *
//     * @param listener 监听者
//     * @return 返回数据
//     */
//    @Override
//    public Boolean hasStrategy(EasyListener listener) {
//        return !ObjectUtils.isEmpty(listener.kafkaListener().topics());
//    }
//
//    /**
//     * Post processes each set of annotation attributes.
//     *
//     * @since 2.7.2
//     */
//    public interface AnnotationEnhancer extends BiFunction<Map<String, Object>, AnnotatedElement, Map<String, Object>> {
//
//    }
//
//    private static class BytesToStringConverter implements Converter<byte[], String> {
//
//
//        private final Charset charset;
//
//        BytesToStringConverter(Charset charset) {
//            this.charset = charset;
//        }
//
//        @Override
//        public String convert(byte[] source) {
//            return new String(source, this.charset);
//        }
//
//    }
//
//    static class ListenerScope implements Scope {
//
//        private final Map<String, Object> listeners = new HashMap<>();
//
//        ListenerScope() {
//        }
//
//        public void addListener(String key, Object bean) {
//            this.listeners.put(key, bean);
//        }
//
//        public void removeListener(String key) {
//            this.listeners.remove(key);
//        }
//
//        @Override
//        public Object get(String name, ObjectFactory<?> objectFactory) {
//            return this.listeners.get(name);
//        }
//
//        @Override
//        public Object remove(String name) {
//            return null;
//        }
//
//        @Override
//        public void registerDestructionCallback(String name, Runnable callback) {
//        }
//
//        @Override
//        public Object resolveContextualObject(String key) {
//            return this.listeners.get(key);
//        }
//
//        @Override
//        public String getConversationId() {
//            return null;
//        }
//
//    }
//
//    /**
//     * An {@link MessageHandlerMethodFactory} adapter that offers a configurable underlying
//     * instance to use. Useful if the factory to use is determined once the endpoints
//     * have been registered but not created yet.
//     *
//     * @see KafkaListenerEndpointRegistrar#setMessageHandlerMethodFactory
//     */
//    private class KafkaHandlerMethodFactoryAdapter implements MessageHandlerMethodFactory {
//
//        private final DefaultFormattingConversionService defaultFormattingConversionService =
//                new DefaultFormattingConversionService();
//
//        private MessageHandlerMethodFactory handlerMethodFactory;
//
//        @Override
//        public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
//            return getHandlerMethodFactory().createInvocableHandlerMethod(bean, method);
//        }
//
//        private MessageHandlerMethodFactory getHandlerMethodFactory() {
//            if (this.handlerMethodFactory == null) {
//                this.handlerMethodFactory = createDefaultMessageHandlerMethodFactory();
//            }
//            return this.handlerMethodFactory;
//        }
//
//        public void setHandlerMethodFactory(MessageHandlerMethodFactory kafkaHandlerMethodFactory1) {
//            this.handlerMethodFactory = kafkaHandlerMethodFactory1;
//        }
//
//        private MessageHandlerMethodFactory createDefaultMessageHandlerMethodFactory() {
//            DefaultMessageHandlerMethodFactory defaultFactory = new DefaultMessageHandlerMethodFactory();
//            Validator validator = EasyKafkaListenerAnnotationBeanPostProcessor.this.registrar.getValidator();
//            if (validator != null) {
//                defaultFactory.setValidator(validator);
//            }
//            defaultFactory.setBeanFactory(EasyKafkaListenerAnnotationBeanPostProcessor.this.beanFactory);
//            this.defaultFormattingConversionService.addConverter(
//                    new BytesToStringConverter(EasyKafkaListenerAnnotationBeanPostProcessor.this.charset));
//            defaultFactory.setConversionService(this.defaultFormattingConversionService);
//            GenericMessageConverter messageConverter = new GenericMessageConverter(this.defaultFormattingConversionService);
//            defaultFactory.setMessageConverter(messageConverter);
//
//            List<HandlerMethodArgumentResolver> customArgumentsResolver =
//                    new ArrayList<>(EasyKafkaListenerAnnotationBeanPostProcessor.this.registrar.getCustomMethodArgumentResolvers());
//            // Has to be at the end - look at PayloadMethodArgumentResolver documentation
//            customArgumentsResolver.add(new PayloadMethodArgumentResolver(messageConverter, validator) {
//
//                @Override
//                public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception { // NOSONAR
//                    Object resolved = super.resolveArgument(parameter, message);
//                    /*
//                     * Replace KafkaNull list elements with null.
//                     */
//                    if (resolved instanceof List) {
//                        List<?> list = ((List<?>) resolved);
//                        for (int i = 0; i < list.size(); i++) {
//                            if (list.get(i) instanceof KafkaNull) {
//                                list.set(i, null);
//                            }
//                        }
//                    }
//                    return resolved;
//                }
//
//                @Override
//                protected boolean isEmptyPayload(Object payload) {
//                    return payload == null || payload instanceof KafkaNull;
//                }
//            });
//            defaultFactory.setCustomArgumentResolvers(customArgumentsResolver);
//
//            defaultFactory.afterPropertiesSet();
//
//            return defaultFactory;
//        }
//
//    }
//
//}
