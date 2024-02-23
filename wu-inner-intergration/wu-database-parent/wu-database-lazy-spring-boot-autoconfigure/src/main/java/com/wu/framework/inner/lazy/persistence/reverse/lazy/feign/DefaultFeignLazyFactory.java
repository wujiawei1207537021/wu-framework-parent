package com.wu.framework.inner.lazy.persistence.reverse.lazy.feign;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.reverse.JavaReverseEngineering;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;

public final class DefaultFeignLazyFactory {


    //######################################################################################################################

    /**
     * 创建默认的 api
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultDefaultFeignLazyApi(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultFeignLazyApi(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 api 文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultFeignLazyApiCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultFeignLazyApi(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 apiRpc
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultDefaultFeignLazyApiRpc(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultFeignLazyApiRpc(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 apiRpc 文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultFeignLazyApiRpcCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                   LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                   String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultFeignLazyApiRpc(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 ao
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultDefaultFeignLazyAo(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultFeignLazyAo(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ao 文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultFeignLazyAoCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                               LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                               String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultFeignLazyAo(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 api command
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultFeignLazyAPICommand(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultFeignLazyAPICommand(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 API command 文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultFeignLazyAPICommandCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                       LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                       String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultFeignLazyAPICommand(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 api command
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return
     */
    public static JavaReverseEngineering defaultFeignLazyAPIDTO(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultFeignLazyAPIDTO(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 API dto 文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultFeignLazyAPIDTOCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                   LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                   String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultFeignLazyAPIDTO(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
}
