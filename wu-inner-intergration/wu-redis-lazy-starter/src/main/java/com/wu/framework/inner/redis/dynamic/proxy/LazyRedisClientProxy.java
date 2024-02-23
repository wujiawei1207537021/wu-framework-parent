package com.wu.framework.inner.redis.dynamic.proxy;

import com.wu.framework.inner.redis.config.LazyRedisProxyBeanAutoConfiguration;
import com.wu.framework.inner.redis.dynamic.DynamicRedisAdapter;
import com.wu.framework.inner.redis.dynamic.LazyDynamicRedisEndpoint;
import com.wu.framework.inner.redis.dynamic.toolkit.DynamicLazyRedisContextHolder;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @see LazyRedisProxyBeanAutoConfiguration#lazyRedisLettuce(LazyRedisClientProxy)
 */
public class LazyRedisClientProxy implements InvocationHandler, InitializingBean {

    private final DynamicRedisAdapter dynamicRedisAdapter;

    public LazyRedisClientProxy(DynamicRedisAdapter dynamicRedisAdapter) {
        this.dynamicRedisAdapter = dynamicRedisAdapter;
    }

    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     *
     * @param proxy  the proxy instance that the method was invoked on
     * @param method the {@code Method} instance corresponding to
     *               the interface method invoked on the proxy instance.  The declaring
     *               class of the {@code Method} object will be the interface that
     *               the method was declared in, which may be a superinterface of the
     *               proxy interface that the proxy class inherits the method through.
     * @param args   an array of objects containing the values of the
     *               arguments passed in the method invocation on the proxy instance,
     *               or {@code null} if interface method takes no arguments.
     *               Arguments of primitive types are wrapped in instances of the
     *               appropriate primitive wrapper class, such as
     *               {@code java.lang.Integer} or {@code java.lang.Boolean}.
     * @return the value to return from the method invocation on the
     * proxy instance.  If the declared return type of the interface
     * method is a primitive type, then the value returned by
     * this method must be an instance of the corresponding primitive
     * wrapper class; otherwise, it must be a type assignable to the
     * declared return type.  If the value returned by this method is
     * {@code null} and the interface method's return type is
     * primitive, then a {@code NullPointerException} will be
     * thrown by the method invocation on the proxy instance.  If the
     * value returned by this method is otherwise not compatible with
     * the interface method's declared return type as described above,
     * a {@code ClassCastException} will be thrown by the method
     * invocation on the proxy instance.
     * @throws Throwable the exception to throw from the method
     *                   invocation on the proxy instance.  The exception's type must be
     *                   assignable either to any of the exception types declared in the
     *                   {@code throws} clause of the interface method or to the
     *                   unchecked exception types {@code java.lang.RuntimeException}
     *                   or {@code java.lang.Error}.  If a checked exception is
     *                   thrown by this method that is not assignable to any of the
     *                   exception types declared in the {@code throws} clause of
     *                   the interface method, then an
     *                   {@link UndeclaredThrowableException} containing the
     *                   exception that was thrown by this method will be thrown by the
     *                   method invocation on the proxy instance.
     * @see UndeclaredThrowableException
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        RedisClient redisClient = dynamicRedisAdapter.determineRedisClient();
        LazyDynamicRedisEndpoint lazyDynamicRedisEndpoint = DynamicLazyRedisContextHolder.peek();
        try {
            StatefulRedisConnection<String, String> connect = redisClient.connect();
            RedisCommands<String, String> sync = connect.sync();
            if (lazyDynamicRedisEndpoint != null && lazyDynamicRedisEndpoint.getDatabase() != null) {
                sync.select(lazyDynamicRedisEndpoint.getDatabase());
            }

            return sync;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DynamicLazyRedisContextHolder.clear();
        }
    }

    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
