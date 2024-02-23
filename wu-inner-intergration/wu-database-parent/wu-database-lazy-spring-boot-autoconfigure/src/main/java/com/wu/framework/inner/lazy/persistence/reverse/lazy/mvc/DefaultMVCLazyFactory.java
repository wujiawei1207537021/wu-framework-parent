package com.wu.framework.inner.lazy.persistence.reverse.lazy.mvc;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.reverse.JavaReverseEngineering;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;

public final class DefaultMVCLazyFactory {


    //######################################################################################################################

    /**
     * 创建默认的mvc 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultMVCLazyController(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultMVCLazyController(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认mvc 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultMVCLazyControllerCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                              LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                              String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultMVCLazyController(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 entity 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultDefaultMVCLazyEntity(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultMVCLazyEntity(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 entity 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultMVCLazyEntityCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                 LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                 String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultMVCLazyEntity(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }


    //######################################################################################################################

    /**
     * 创建默认的 mapper 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultDefaultMVCLazyMapper(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultMVCLazyMapper(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 mapper 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultMVCLazyMapperCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                 LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                 String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultMVCLazyMapper(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }


    //######################################################################################################################

    /**
     * 创建默认的 mapper xml 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultDefaultMVCLazyMapperXml(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultMVCLazyMapperXml(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 mapper xml 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultMVCLazyMapperXmlCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                    LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                    String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultMVCLazyMapperXml(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 mapper xml 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultDefaultMVCLazyService(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultMVCLazyService(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 mapper xml 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultMVCLazyServiceCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                  LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                  String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultMVCLazyService(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 service impl
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultDefaultMVCLazyServiceImpl(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultMVCLazyServiceImpl(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 service impl
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultMVCLazyServiceImplCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                      LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                      String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultMVCLazyServiceImpl(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
}
