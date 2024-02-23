package com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.reverse.JavaReverseEngineering;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;

public final class DefaultDDDLazyFactory {


    //######################################################################################################################

    /**
     * 创建默认的 ddd 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyController(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyController(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyControllerCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                              LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                              String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyController(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 ddd application
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyApplication(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyApplication(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd application
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyApplicationCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                               LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                               String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyApplication(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd application impl
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyApplicationImpl(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyApplicationImpl(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd application impl
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyApplicationImplCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                   LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                   String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyApplicationImpl(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyAssembler
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyAssembler(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyAssembler(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd DefaultDDDLazyAssembler
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyAssemblerCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                             LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                             String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyAssembler(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 ddd defaultDefaultDDDLazyDTO
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDefaultDDDLazyDTO(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyDTO(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd defaultDefaultDDDLazyDTO
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultDDDLazyDTOCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                              LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                              String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultDDDLazyDTO(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyRemoveCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyCommand(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyRemoveCommand(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd defaultDDDLazyCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyCommandCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                           LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                           String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyCommand(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyQueryListCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyQueryListCommand(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyQueryListCommand(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd defaultDDDLazyCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyQueryListCommandCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                    LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                    String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyQueryListCommand(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyQueryOneCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyQueryOneCommand(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyQueryOneCommand(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd DefaultDDDLazyQueryOneCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyQueryOneCommandCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                   LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                   String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyQueryOneCommand(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyStoryCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyStoryCommand(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyStoryCommand(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd DefaultDDDLazyStoryCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyStoryCommandCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyStoryCommand(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyUpdateCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyUpdateCommand(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyUpdateCommand(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd defaultDDDLazyCommand
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyUpdateCommandCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                 LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                 String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyUpdateCommand(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyDomain
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyDomain(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyDomain(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd DefaultDDDLazyDomain
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyDomainCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                          LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                          String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyDomain(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyDomainRepository
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyDomainRepository(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyDomainRepository(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd DefaultDDDLazyDomainRepository
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyDomainRepositoryCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                    LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                    String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyDomainRepository(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyInfrastructureConverter
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyInfrastructureConverter(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyInfrastructureConverter(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd defaultDDDLazyInfrastructureConverter
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyInfrastructureConverterCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                           LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                           String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyInfrastructureConverter(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }   //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyInfrastructureEntity
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyInfrastructureEntity(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyInfrastructureEntity(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd DefaultDDDLazyInfrastructureEntity
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyInfrastructureEntityCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                        LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                        String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyInfrastructureEntity(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }//######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyInfrastructureMapper
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyInfrastructureMapper(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyInfrastructureMapper(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd defaultDDDLazyInfrastructureMapper
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyInfrastructureMapperCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                        LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                        String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyInfrastructureMapper(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyInfrastructureMapperXml
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyInfrastructureMapperXml(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyInfrastructureMapperXml(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd defaultDDDLazyInfrastructureMapperXml
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyInfrastructureMapperXmlCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                           LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                           String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyInfrastructureMapperXml(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 ddd DefaultDDDLazyInfrastructurePersistence
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDDDLazyInfrastructurePersistence(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyInfrastructurePersistence(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 ddd defaultDDDLazyInfrastructurePersistence
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDDDLazyInfrastructurePersistenceCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                             LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                             String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDDDLazyInfrastructurePersistence(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
    //######################################################################################################################

    /**
     * 创建默认的 entity 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDefaultDDDLazyEntity(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyEntity(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 entity 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultDDDLazyEntityCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                 LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                 String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultDDDLazyEntity(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }


    //######################################################################################################################

    /**
     * 创建默认的 mapper 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDefaultDDDLazyMapper(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyMapper(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 mapper 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultDDDLazyMapperCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                 LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                 String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultDDDLazyMapper(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }


    //######################################################################################################################

    /**
     * 创建默认的 mapper xml 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDefaultDDDLazyMapperXml(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyMapperXml(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 mapper xml 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultDDDLazyMapperXmlCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                    LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                    String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultDDDLazyMapperXml(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 mapper xml 控制器
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDefaultDDDLazyService(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyService(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 mapper xml 控制器文件
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultDDDLazyServiceCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                  LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                  String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultDDDLazyService(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }

    //######################################################################################################################

    /**
     * 创建默认的 service impl
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @return JavaReverseEngineering
     */
    public static JavaReverseEngineering defaultDefaultDDDLazyServiceImpl(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        return new DefaultDDDLazyServiceImpl(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    /**
     * 创建默认 service impl
     *
     * @param reverseClassLazyTableEndpoint 表结构数据
     * @param reverseEngineering            逆向工程配置
     * @param resourceFilePrefix            生成文件地址
     */
    public static void defaultDefaultDDDLazyServiceImplCreateJavaFile(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint,
                                                                      LazyOperationConfig.ReverseEngineering reverseEngineering,
                                                                      String resourceFilePrefix) {
        JavaReverseEngineering javaReverseEngineering = defaultDefaultDDDLazyServiceImpl(reverseClassLazyTableEndpoint, reverseEngineering);
        javaReverseEngineering.createJavaFile(resourceFilePrefix);
    }
}
