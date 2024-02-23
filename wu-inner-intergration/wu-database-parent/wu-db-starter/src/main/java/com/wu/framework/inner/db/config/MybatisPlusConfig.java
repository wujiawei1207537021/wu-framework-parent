package com.wu.framework.inner.db.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import com.wu.framework.inner.db.component.web.interceptors.MySQLPageInterceptor;
import org.springframework.context.annotation.Bean;


public class MybatisPlusConfig {


    /**
     * 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    //    @Bean
    public MySQLPageInterceptor mySQLPageInterceptor() {
        PageHelperProperties properties = new PageHelperProperties();
        properties.setPageSizeZero("true");// 分页尺寸为0时查询所有纪录不再执行分页
        properties.setReasonable("true");// 页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setSupportMethodsArguments("true");// 支持通过 Mapper 接口参数来传递分页参数

        MySQLPageInterceptor mySQLPageInterceptor = new MySQLPageInterceptor();
        mySQLPageInterceptor.setProperties(properties.getProperties());
        return mySQLPageInterceptor;
    }

    /***
     * plus 的性能优化
     * @return
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        final PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->
        performanceInterceptor.setMaxTime(100000);*/
        /*<!--SQL是否格式化 默认false-->*/
        performanceInterceptor.setFormat(false);
        return performanceInterceptor;
    }

    //    @Bean
//    public SqlSessionFactory sqlSessionFactoryBean(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setTypeAliasesPackage(Constant.PAGEPARAMETER_FIRST);
//
//
//        // 添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
//        return factory.getObject();
//    }

    /**
     * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
     * 配置文件和mybatis-boot的配置文件同步
     *
     * @return
     */
//    @Bean
//    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
//        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
//        mybatisPlus.setDataSource(dataSource);
//        mybatisPlus.setVfs(SpringBootVFS.class);
//        if (StringUtils.hasText(this.properties.getConfigLocation())) {
//            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
//        }
//        mybatisPlus.setConfiguration(properties.getConfiguration());
//
//        // 配置分页插件，详情请查阅官方文档
//        PageHelperProperties properties = new PageHelperProperties();
//        properties.setPageSizeZero("true");// 分页尺寸为0时查询所有纪录不再执行分页
//        properties.setReasonable("true");// 页码<=0 查询第一页，页码>=总页数查询最后一页
//        properties.setSupportMethodsArguments("true");// 支持通过 Mapper 接口参数来传递分页参数
//
//        MySQLPageInterceptor pageInterceptor = new MySQLPageInterceptor();
//        pageInterceptor.setProperties(properties.getProperties());
//        //  添加插件
//        this.interceptors = new Interceptor[]{pageInterceptor};
//        if (!ObjectUtils.isEmpty(this.interceptors)) {
//            mybatisPlus.setPlugins(this.interceptors);
//        }
//        MybatisConfiguration mc = new MybatisConfiguration();
//        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
//        mybatisPlus.setConfiguration(mc);
//        if (this.databaseIdProvider != null) {
//            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
//        }
//        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
//            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
//        }
//        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
//            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
//        }
//        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
//            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
//        }
//        return mybatisPlus;
//    }


}


