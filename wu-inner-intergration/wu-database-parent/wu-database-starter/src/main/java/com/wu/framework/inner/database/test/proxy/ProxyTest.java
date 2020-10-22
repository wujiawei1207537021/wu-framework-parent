package com.wu.framework.inner.database.test.proxy;

import java.lang.reflect.Proxy;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/6/25 下午8:41
 */
public class ProxyTest {

    public static void main(String[] args) throws Exception {

        InterfaceInvocationHandler interfaceInvocationHandler = new InterfaceInvocationHandler();
        InterfaceClass interfaceClass = (InterfaceClass) Proxy.newProxyInstance(InterfaceClass.class.getClassLoader(),
                new Class[]{InterfaceClass.class}, interfaceInvocationHandler);
        interfaceClass.cc();
        interfaceClass.xx("this is a param");

    }
}
