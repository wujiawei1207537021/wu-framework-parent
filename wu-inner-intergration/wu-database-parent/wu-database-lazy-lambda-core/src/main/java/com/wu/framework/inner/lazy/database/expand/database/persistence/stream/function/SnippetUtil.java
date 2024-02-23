package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.LambdaMeta;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.SerializedLambdaMeta;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.toolkit.ReflectionKit;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author Jia wei Wu
 */
public class SnippetUtil {

    public static <T> LambdaMeta extract(Snippet<T, ?> func) {
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            final Method accessible = ReflectionKit.setAccessible(method);
            final Object invoke = accessible.invoke(func);
            return new SerializedLambdaMeta((SerializedLambda) invoke);
        } catch (NoSuchMethodException e) {
            if (func instanceof Proxy) {
//                return new ProxyLambdaMeta((Proxy) func);
                return null;
            }
            String message = "Cannot find method writeReplace, please make sure that the lambda composite class is currently passed in";
            throw new RuntimeException(message);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
