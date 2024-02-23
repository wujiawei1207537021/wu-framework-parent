package com.wu.framework.inner.lazy.persistence.reverse;


import com.wu.framework.inner.lazy.config.LazyOperationConfig;

/**
 * description 逆向工程 顶级接口 记录class的导入
 *
 * @author 吴佳伟
 * @date 2023/02/13 17:10
 */
public interface JavaReverseEngineering {

    /**
     * 创建 class 上下文代码
     *
     * @return class 上下文代码
     */
    String createJavaContextCode();


    /**
     * 创建Java文件
     *
     * @param resourceFilePrefix 文件前缀
     * @return 创建Java文件
     */
    String createJavaFile(String resourceFilePrefix);


    ReverseClassLazyTableEndpoint getReverseClassLazyTableEndpoint();

    LazyOperationConfig.ReverseEngineering getReverseEngineering();
}
