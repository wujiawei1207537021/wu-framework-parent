package com.wu.framework.inner.lazy.persistence.util;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaKeyword;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.MysqlColumnTypeEnum;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.config.enums.WebArchitecture;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.clazz.AbstractLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyFactory;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.feign.DefaultFeignLazyFactory;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.mvc.DefaultMVCLazyFactory;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/8 2:35 下午
 */
public class LazyTableUtil {


    public static final Logger log = LoggerFactory.getLogger(LazyTableUtil.class);
    // 防止多线程并发
    private static final ConcurrentMap<Class<?>, LazyTableEndpoint> CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP = new ConcurrentHashMap<>();

    public static String getTableName(Class clazz) {
        Assert.notNull(clazz, "表对应的class 不能是空");
        LazyTable lazyTable = AnnotatedElementUtils.findMergedAnnotation(clazz, LazyTable.class);
        if (null == lazyTable || ObjectUtils.isEmpty(lazyTable.tableName())) {

            String simpleName = clazz.getSimpleName();
            // 适应前缀过滤
            for (String ddlIgnoredTableSuffix : LazyDatabaseJsonMessage.ddlIgnoredTablePrefix) {
                if (simpleName.startsWith(ddlIgnoredTableSuffix)) {
                    simpleName = simpleName.substring(ddlIgnoredTableSuffix.length());
                    break;
                }
            }
            // 适应后缀过滤
            for (String ddlIgnoredTableSuffix : LazyDatabaseJsonMessage.ddlIgnoredTableSuffix) {
                if (simpleName.endsWith(ddlIgnoredTableSuffix)) {
                    simpleName = simpleName.substring(0, simpleName.length() - ddlIgnoredTableSuffix.length());
                    break;
                }
            }
            return CamelAndUnderLineConverter.humpToLine2(simpleName);
        }
        return lazyTable.tableName();
    }

    /**
     * 获取当前使用的 表信息
     *
     * @return 表信息集合
     */
    public static List<LazyTableEndpoint> getCurrentLazyTableEndpointList() {
        return new ArrayList<>(CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.values());
    }

    /**
     * 将 class 解析中注解 LazyTable 解析成 ClassLazyTableEndpoint
     *
     * @param clazz
     * @return
     */
    public static LazyTableEndpoint analyzeLazyTable(Class<?> clazz) {
        if (!CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.containsKey(clazz)) {
            synchronized (CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP) {
                LazyTable lazyTable = AnnotatedElementUtils.findMergedAnnotation(clazz, LazyTable.class);

                String className = clazz.getName();
                String tableName = getTableName(clazz);
                Package clazzPackage = clazz.getPackage();
                if (null == clazzPackage) {
                    System.out.println(className);
                }
                final String packageName = clazzPackage.getName();
                String comment = "";
                boolean smartFillField = false;
                boolean exist = true;

                AbstractLazyTableEndpoint lazyTableEndpoint = AbstractLazyTableEndpoint.getInstance();
                if (null != lazyTable) {
                    lazyTableEndpoint.setSchema(lazyTable.schema());
                    smartFillField = lazyTable.smartFillField();
                    comment = lazyTable.comment();
                    // 判断一手当前实体对象是否被弃用
                    if (AnnotatedElementUtils.hasAnnotation(clazz, Deprecated.class)) {
                        comment += "（即将弃用）";
                    }
                    LazyTable.Engine engine = lazyTable.engine();
                    lazyTableEndpoint.setEngine(engine);
                    exist = lazyTable.exist();
                }
                List<LazyTableFieldEndpoint> analyzeFieldOnAnnotation = LazyTableFieldUtil.analyzeFieldOnAnnotation(clazz, null);
                lazyTableEndpoint.setPackageName(packageName);
                lazyTableEndpoint.setComment(comment);
                lazyTableEndpoint.setClassName(className);
                lazyTableEndpoint.setClazz(clazz);
                lazyTableEndpoint.setTableName(tableName);
                lazyTableEndpoint.setFieldEndpoints(analyzeFieldOnAnnotation);
                lazyTableEndpoint.setSmartFillField(smartFillField);
                lazyTableEndpoint.setExist(exist);
                log.info("Initialize {} annotation parameters  className:[{}],tableName:[{}],comment:[{}]", clazz,
                        className, tableName, comment);
                CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.put(clazz, lazyTableEndpoint);
            }
        }
        return CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.get(clazz);
    }

    /**
     * describe 创建 Java class
     *
     * @param reverseClassLazyTableEndpoint ClassLazyTableEndpoint 参数
     * @return void void
     * @author Jia wei Wu
     * @date 2022/1/23 7:55 下午
     **/
    public static void createJava(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint) {
        createJava(reverseClassLazyTableEndpoint, new LazyOperationConfig.ReverseEngineering());
    }

    /**
     * describe 创建 Java class
     *
     * @param reverseClassLazyTableEndpoint ClassLazyTableEndpoint 参数
     * @param reverseEngineering            逆向工程参数
     * @return void void
     * @author Jia wei Wu
     * @date 2022/1/23 3:50 下午
     **/
    public static void createJava(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        // 写入class文件
        String resourceFilePrefix = reverseEngineering.getResourceFilePrefix();
        // 工作空间存储
        final String packageName = reverseClassLazyTableEndpoint.getPackageName();

        String target = resourceFilePrefix.split("target")[0] + "src/main/java/";

        resourceFilePrefix = target + packageName.replace(NormalUsedString.DOT, File.separator) + File.separator;


        if (WebArchitecture.WEB_MVC.equals(reverseEngineering.getWebArchitecture())) {
//            createJavaEntityFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaServiceFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaServiceImplFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaMapperFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaMapperXmlFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaControllerWithLazyFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultMVCLazyFactory.defaultMVCLazyControllerCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultMVCLazyFactory.defaultDefaultMVCLazyServiceCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultMVCLazyFactory.defaultDefaultMVCLazyServiceImplCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultMVCLazyFactory.defaultDefaultMVCLazyMapperCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultMVCLazyFactory.defaultDefaultMVCLazyMapperXmlCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultMVCLazyFactory.defaultDefaultMVCLazyEntityCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);

        } else if (WebArchitecture.DDD_ARCHITECTURE.equals(reverseEngineering.getWebArchitecture())) {
//            createJavaControllerFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaApplicationFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaApplicationImplFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaAssemblerFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaCommandFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaDomainFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaDomainRepositoryFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaInfrastructureConverterFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaInfrastructureEntityFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaInfrastructureMapperFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaInfrastructurePersistenceFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
//            createJavaMapperXmlFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);

            DefaultDDDLazyFactory.defaultDDDLazyControllerCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyApplicationCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyApplicationImplCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyAssemblerCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDefaultDDDLazyDTOCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyCommandCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyQueryOneCommandCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyQueryListCommandCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyStoryCommandCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyUpdateCommandCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyDomainCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyDomainRepositoryCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyInfrastructureConverterCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyInfrastructureEntityCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);

            DefaultDDDLazyFactory.defaultDDDLazyInfrastructureMapperCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyInfrastructureMapperXmlCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultDDDLazyFactory.defaultDDDLazyInfrastructurePersistenceCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);

        } else if (WebArchitecture.FEIGN_API.equals(reverseEngineering.getWebArchitecture())) {
            DefaultFeignLazyFactory.defaultDefaultFeignLazyApiCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultFeignLazyFactory.defaultDefaultFeignLazyApiRpcCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultFeignLazyFactory.defaultDefaultFeignLazyAoCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultFeignLazyFactory.defaultDefaultFeignLazyAPICommandCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
            DefaultFeignLazyFactory.defaultDefaultFeignLazyAPIDTOCreateJavaFile(reverseClassLazyTableEndpoint, reverseEngineering, resourceFilePrefix);
        }

    }


    /**
     * @param tableEndpoint
     * @param reverseEngineering
     * @return
     */
    private static StringBuilder createJavaInfrastructurePersistence(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;",
                tableEndpoint.getPackageName() + NormalUsedString.DOT + "infrastructure.persistence")
                + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);

        String classAnnotationString = NormalUsedString.NEWLINE;
        classAnnotationString += "@Service" + NormalUsedString.NEWLINE;
        List<String> importClass = new ArrayList<>();
        importClass.add("org.springframework.stereotype.Service");

        String classname;
        String repositoryImpl = tableEndpoint.getClassName() + "RepositoryImpl";
        String repository = tableEndpoint.getClassName() + "Repository";
        // 驼峰转换成list
        List<String> domainNames = CamelAndUnderLineConverter.humpToArray(tableEndpoint.getClassName());

        importClass.add(
                tableEndpoint.getPackageName() + NormalUsedString.DOT + "domain.model." +
                        domainNames.stream().map(domainName -> {
                            if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                                return domainName + NormalUsedString.UNDERSCORE;
                            }
                            return domainName;
                        }).collect(Collectors.joining(NormalUsedString.DOT)) +
                        NormalUsedString.DOT + repository
        );

        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
//            @Service
//public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {}

            String mapper = tableEndpoint.getClassName() + "Mapper";

            classname = String.format("public class %s extends  ServiceImpl<%s, %s> implements %s {" + NormalUsedString.NEWLINE + "}",
                    repositoryImpl, mapper, tableEndpoint.getClassName(), repository) + NormalUsedString.NEWLINE;

            importClass.add(Service.class.getName());
            importClass.add("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");

            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "service" + NormalUsedString.DOT + repository);
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "mapper" + NormalUsedString.DOT + mapper);

        } else {
            classname = String.format("public class %s implements %s {" + NormalUsedString.NEWLINE + "}", repositoryImpl, repository) + NormalUsedString.NEWLINE;
        }

        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(importString);
        classContext.append(generateClassDescribe(tableEndpoint));

        classContext.append(classAnnotationString);
        classContext.append(classname);

        return classContext;
    }


    /**
     * @param tableEndpoint
     * @param reverseEngineering
     * @return
     */
    private static StringBuilder createJavaInfrastructureMapper(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        StringBuilder classContext = new StringBuilder();
        String mapper = tableEndpoint.getClassName() + "Mapper";
        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "infrastructure.mapper") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);

        String classAnnotationString = NormalUsedString.NEWLINE;
        String classname;
        List<String> importClass = new ArrayList<>();
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {

            classAnnotationString += "@Mapper" + NormalUsedString.NEWLINE;

            classname = String.format("public interface %s extends BaseMapper<%s> {" + NormalUsedString.NEWLINE + "}",
                    mapper, tableEndpoint.getClassName() + "DO") + NormalUsedString.NEWLINE;

            importClass.add("org.apache.ibatis.annotations.Mapper");
            importClass.add("com.baomidou.mybatisplus.core.mapper.BaseMapper");
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "infrastructure.entity" + NormalUsedString.DOT + tableEndpoint.getClassName());

        } else {
            classname = String.format("public interface %s {" + NormalUsedString.NEWLINE + "}", mapper) + NormalUsedString.NEWLINE;
        }
        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;
        classContext.append(importString);

        classContext.append(generateClassDescribe(tableEndpoint));
        classContext.append(classAnnotationString);
        classContext.append(classname);

        return classContext;
    }


    /**
     * @param tableEndpoint
     * @param reverseEngineering
     * @return
     */
    private static StringBuilder createJavaInfrastructureEntity(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        StringBuilder classContext = new StringBuilder();
        String entity = tableEndpoint.getClassName();
        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "infrastructure.entity") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);

        List<String> importClass = new ArrayList<>();
        String classAnnotationString = NormalUsedString.NEWLINE;
        String classname;
        classAnnotationString += "@Data" + NormalUsedString.NEWLINE;
        importClass.add("lombok.Data");
        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;
        classContext.append(importString);

        classname = String.format("public class %sDO {" + NormalUsedString.NEWLINE, entity) + NormalUsedString.NEWLINE;

        String attributeFileList = generateAttributeFileList(tableEndpoint, reverseEngineering, importClass);

        classContext.append(generateClassDescribe(tableEndpoint));
        classContext.append(classAnnotationString);
        classContext.append(classname);
        classContext.append(attributeFileList);
        classContext.append(NormalUsedString.NEWLINE + NormalUsedString.RIGHT_BRACE);

        return classContext;
    }

    /**
     * description 创建mybatis xml文件
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/26 10:48 上午
     */
    public static StringBuilder createJavaMapperXml(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        String className = tableEndpoint.getClassName();
        String packageName = tableEndpoint.getPackageName();
        String mapper = tableEndpoint.getClassName() + "Mapper";
        //   <?xml version="1.0" encoding="UTF-8"?>
        //<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
        //<mapper namespace="com.xx.dao">
        //      <resultMap id="BaseResultMap" type="com.xx.entity">
        //          <id column="id" property="id" />
        //          <result column="code" property="code" />
        //      </resultMap>
        // </mapper>
        StringBuilder classContext = new StringBuilder();
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append(String.format("<mapper namespace=\"%s\">", packageName + NormalUsedString.DOT + "mapper" + NormalUsedString.DOT + mapper));
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append(String.format("<resultMap id=\"BaseResultMap\" type=\"%s\">",
                packageName + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + className));

        for (LazyTableFieldEndpoint fieldEndpoint : tableEndpoint.getFieldEndpoints()) {
            classContext.append(NormalUsedString.NEWLINE);
            String name = fieldEndpoint.getName();
            String columnName = fieldEndpoint.getColumnName();
            if ("id".equals(name)) {
                classContext.append("<id column=\"id\" property=\"id\" />");
            } else {
                classContext.append(String.format("<result column=\"%s\" property=\"%s\" />", columnName, name));
            }
        }
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append("</resultMap>");
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append("</mapper>");
        return classContext;
    }

    /**
     * 创建 Java的 controller
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.framework.inner.lazy.database.smart.database.persistence.
     */
    private static void createJavaControllerFile(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {
        StringBuilder classContext = createJavaController(tableEndpoint, reverseEngineering);
        String controller = tableEndpoint.getClassName() + "Provider";
        try {
            BufferedWriter bufferedWriter = FileUtil.createFileBufferedWriter(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "controller" + File.separator + controller);
            bufferedWriter.write(classContext.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建 Java的 controller
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     */
    public static StringBuilder createJavaController(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "controller") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);

        String classAnnotationString = NormalUsedString.NEWLINE;
        classAnnotationString += String.format("@Tag(name = \"%s提供者\")", tableEndpoint.getComment()) + NormalUsedString.NEWLINE;

        List<String> importClass = new ArrayList<>();
        if (reverseEngineering.isEnableSwagger()) {
            importClass.add("io.swagger.v3.oas.annotations.tags.Tag");
        }

        String classname;
        String controller = tableEndpoint.getClassName() + "Provider";

        classAnnotationString += String.format("@EasyController(\"/%s\")", tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;

        importClass.add("com.wu.framework.inner.layer.web.EasyController");


        classname = String.format("public class %s  {" + NormalUsedString.NEWLINE + "}",
                controller) + NormalUsedString.NEWLINE;

        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(importString);
        classContext.append(generateClassDescribe(tableEndpoint));

        classContext.append(classAnnotationString);
        classContext.append(classname);
        return classContext;

    }

    /**
     * 创建 Java的 Mapper
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.framework.inner.lazy.database.smart.database.persistence.
     */
    private static void createJavaMapperFile(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {
        StringBuilder classContext = createJavaMapper(tableEndpoint, reverseEngineering);
        String mapper = tableEndpoint.getClassName() + "Mapper";
        try {
            BufferedWriter bufferedWriter = FileUtil.createFileBufferedWriter(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "mapper" + File.separator + mapper);
            bufferedWriter.write(classContext.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建 Java的 Mapper
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     */
    public static StringBuilder createJavaMapper(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "mapper") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);


        List<String> importClass = new ArrayList<>();
        String classAnnotationString = NormalUsedString.NEWLINE;

        String classname;
        String mapper = tableEndpoint.getClassName() + "Mapper";
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {

            classAnnotationString += "@Mapper" + NormalUsedString.NEWLINE;

            classname = String.format("public interface %s extends BaseMapper<%s> {" + NormalUsedString.NEWLINE + "}",
                    mapper, tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;

            importClass.add("org.apache.ibatis.annotations.Mapper");
            importClass.add("com.baomidou.mybatisplus.core.mapper.BaseMapper");
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());

        } else {
            classname = String.format("public interface %s  {" + NormalUsedString.NEWLINE + "}",
                    mapper) + NormalUsedString.NEWLINE;
        }
        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(importString);
        classContext.append(generateClassDescribe(tableEndpoint));
        classContext.append(classAnnotationString);
        classContext.append(classname);

        return classContext;

    }

    /**
     * 创建 Java的service实现类
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.framework.inner.lazy.database.smart.database.persistence.
     */
    private static void createJavaServiceImplFile(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {
        StringBuilder classContext = createJavaServiceImpl(tableEndpoint, reverseEngineering);
        String serviceImpl = tableEndpoint.getClassName() + "ServiceImpl";
        try {
            BufferedWriter bufferedWriter = FileUtil.createFileBufferedWriter(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "service" + File.separator + "impl" + File.separator + serviceImpl);
            bufferedWriter.write(classContext.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建 Java的service实现类
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     */
    public static StringBuilder createJavaServiceImpl(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "service.impl") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);

        String classAnnotationString = NormalUsedString.NEWLINE;
        classAnnotationString += "@Service" + NormalUsedString.NEWLINE;
        List<String> importClass = new ArrayList<>();
        importClass.add("org.springframework.stereotype.Service");

        String classname;
        String serviceImpl = tableEndpoint.getClassName() + "ServiceImpl";
        String service = tableEndpoint.getClassName() + "Service";

        importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "service." + service);

        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
//            @Service
//public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {}

            String mapper = tableEndpoint.getClassName() + "Mapper";

            classname = String.format("public class %s extends  ServiceImpl<%s, %s> implements %s {" + NormalUsedString.NEWLINE + "}",
                    serviceImpl, mapper, tableEndpoint.getClassName(), service) + NormalUsedString.NEWLINE;

            importClass.add(Service.class.getName());
            importClass.add("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");

            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "service" + NormalUsedString.DOT + service);
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "mapper" + NormalUsedString.DOT + mapper);

        } else {
            classname = String.format("public class %s implements %s {" + NormalUsedString.NEWLINE + "}", serviceImpl, service) + NormalUsedString.NEWLINE;
        }

        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(importString);
        classContext.append(generateClassDescribe(tableEndpoint));

        classContext.append(classAnnotationString);
        classContext.append(classname);

        return classContext;
    }

    /**
     * 创建 Java的service
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.framework.inner.lazy.database.smart.database.persistence.
     */
    public static void createJavaServiceFile(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {
        String serviceName = String.format("%sService", tableEndpoint.getClassName());

        StringBuilder classContext = createJavaService(tableEndpoint, reverseEngineering);

        try {
            BufferedWriter bufferedWriter = FileUtil.createFileBufferedWriter(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "service" + File.separator + serviceName);
            bufferedWriter.write(classContext.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建 Java的service
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     */
    public static StringBuilder createJavaService(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        final StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "service") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);

        List<String> importClass = new ArrayList<>();
        String classname;
        String serviceName = String.format("%sService", tableEndpoint.getClassName());
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
            classname = String.format("public interface %s extends IService<%s> {" + NormalUsedString.NEWLINE + "}", serviceName, tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;
            importClass.add("com.baomidou.mybatisplus.extension.service.IService");
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());
        } else {
            classname = String.format("public interface %sService  {" + NormalUsedString.NEWLINE + "}", tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;
        }

        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(importString);

        classContext.append(generateClassDescribe(tableEndpoint));

        classContext.append(classname);

        return classContext;
    }

    /**
     * describe 创建Java 实体 字符串
     *
     * @param tableEndpoint      tableEndpoint
     * @param reverseEngineering 逆行工程
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 4:43 下午
     **/
    public static StringBuilder createJavaEntityFile(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {

        StringBuilder javaEntity = createJavaEntity(tableEndpoint, reverseEngineering);
        try {
            BufferedWriter bufferedWriter = FileUtil.createFileBufferedWriter(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "entity" + File.separator + tableEndpoint.getClassName());
            bufferedWriter.write(javaEntity.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return javaEntity;
    }

    /**
     * describe 创建Java 实体 字符串
     *
     * @param tableEndpoint      tableEndpoint
     * @param reverseEngineering 逆行工程
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 4:43 下午
     **/
    public static StringBuilder createJavaEntity(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        List<LazyTableFieldEndpoint> fieldEndpoints = tableEndpoint.getFieldEndpoints();

        final StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        List<String> importClass = new ArrayList<>();

        List<String> classAnnotation = new ArrayList<>();
        classAnnotation.add(NormalUsedString.NEWLINE);


        if (reverseEngineering.isEnableLombokData()) {
            classAnnotation.add("@Data");
            classAnnotation.add(NormalUsedString.NEWLINE);
            importClass.add(Data.class.getName());
        }
        if (reverseEngineering.isEnableLombokAccessors()) {
            classAnnotation.add("@Accessors(chain = true)");
            classAnnotation.add(NormalUsedString.NEWLINE);
            importClass.add("lombok.experimental.Accessors");
        }
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
            if (reverseEngineering.isEnableSchema()) {
                classAnnotation.add(String.format("@TableName(value = \"%s\",schema = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getSchema()));
                classAnnotation.add(NormalUsedString.NEWLINE);
            } else {
                classAnnotation.add(String.format("@TableName(value = \"%s\")", tableEndpoint.getTableName()));
                classAnnotation.add(NormalUsedString.NEWLINE);
            }

            importClass.add("com.baomidou.mybatisplus.annotation.TableName");
        }

        if (OrmArchitecture.LAZY.equals(reverseEngineering.getOrmArchitecture())
        ) {
            if (reverseEngineering.isEnableSchema()) {
                classAnnotation.add(String.format("@LazyTable(tableName = \"%s\",schema = \"%s\",comment = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getSchema(), tableEndpoint.getComment()));
                classAnnotation.add(NormalUsedString.NEWLINE);
            } else {
                classAnnotation.add(String.format("@LazyTable(tableName = \"%s\",comment = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getComment()));
                classAnnotation.add(NormalUsedString.NEWLINE);
            }

            // 索引
            if (tableEndpoint.getFieldEndpoints().stream().anyMatch(lazyTableFieldEndpoint -> ObjectUtils.isEmpty(lazyTableFieldEndpoint.getLazyTableIndexEndpoints()))) {
                importClass.add(LazyTableIndex.class.getName());
                importClass.add(LayerField.LayerFieldType.class.getName().replace(NormalUsedString.DOLLAR, NormalUsedString.DOT));
            }
            importClass.add(LazyTable.class.getName());
            importClass.add(LazyTableField.class.getName());
        }
        if (reverseEngineering.isEnableSwagger()) {
            classAnnotation.add(String.format("@Schema(title = \"%s\",description = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getComment()));
            classAnnotation.add(NormalUsedString.NEWLINE);
            importClass.add("io.swagger.v3.oas.annotations.media.Schema");
            importClass.add("io.swagger.v3.oas.annotations.media.Schema");
        }

        String classname = String.format("public class %s {", tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;


        String attributeFileList = generateAttributeFileList(tableEndpoint, reverseEngineering, importClass);

        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        String classAnnotationString = String.join(NormalUsedString.NEWLINE, classAnnotation);
        classContext.append(classPackage);
        classContext.append(importString);
        classContext.append(generateClassDescribe(tableEndpoint));
        classContext.append(classAnnotationString);
        classContext.append(classname);
        classContext.append(attributeFileList);
        classContext.append(NormalUsedString.NEWLINE + NormalUsedString.RIGHT_BRACE);
        return classContext;
    }

    /**
     * 创建表注解
     *
     * @param tableEndpoint      表信息
     * @param reverseEngineering 逆向工程配置
     * @param importClass        导入class
     * @return
     */
    protected static void generateClassAnnotation(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, List<String> importClass, List<String> classAnnotation) {

    }

    /**
     * 创建字段
     *
     * @param tableEndpoint      表信息
     * @param reverseEngineering 逆向工程配置
     * @param importClass        导入class
     * @return
     */
    protected static String generateAttributeFileList(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, List<String> importClass) {
        List<LazyTableFieldEndpoint> fieldEndpoints = tableEndpoint.getFieldEndpoints();
        String attributeFileList = fieldEndpoints.stream().map(
                fieldLazyTableFieldEndpoint -> {

                    final String comment = fieldLazyTableFieldEndpoint.getComment();
                    final String commentFormat = NormalUsedString.NEWLINE + String.format("    /**\n" +
                            "     * \n" +
                            "     * %s\n" +
                            "     */\n", comment);
                    final MysqlColumnTypeEnum mysqlColumnTypeEnum =
                            MysqlColumnTypeEnum.MYSQL_COLUMN_TYPE_ENUM_MAP.getOrDefault(fieldLazyTableFieldEndpoint.getDataType(), MysqlColumnTypeEnum.VARCHAR);
                    final String format = String.format("    private %s %s;", mysqlColumnTypeEnum.getJavaType().getSimpleName(), fieldLazyTableFieldEndpoint.getName());

                    // ApiModelProperty 注解
                    String apiModelPropertyAnnotation = NormalUsedString.EMPTY;
                    if (reverseEngineering.isEnableSwagger()) {
                        apiModelPropertyAnnotation = String.format("    @Schema(description =\"%s\",name =\"%s\",example = \"%s\")", fieldLazyTableFieldEndpoint.getComment(), fieldLazyTableFieldEndpoint.getName(), "") + NormalUsedString.NEWLINE;
                    }


                    String lazyTableFieldAnnotation = NormalUsedString.EMPTY;
                    // LazyTableField 注解
                    if (reverseEngineering.isEnableLazy()
                            || OrmArchitecture.LAZY.equals(reverseEngineering.getOrmArchitecture())
                    ) {
                        // 自增主键   @LazyTableFieldId(name = "id", comment = "ID")
                        String extra = fieldLazyTableFieldEndpoint.getExtra();
                        String name = fieldLazyTableFieldEndpoint.getColumnName().replace(NormalUsedString.BACKTICK, NormalUsedString.EMPTY);

                        if (NormalUsedString.AUTO_INCREMENT.equalsIgnoreCase(extra)) {
                            lazyTableFieldAnnotation = String.format("    @LazyTableFieldId(name = \"%s\", comment = \"%s\")", name, comment) + NormalUsedString.NEWLINE;
                            importClass.add(LazyTableFieldId.class.getName());
                        } else {
                            List<String> valueList = new ArrayList<>();
                            // 字段名称
                            valueList.add(String.format("name=\"%s\"", name));
                            // 字段描述
                            valueList.add(String.format("comment=\"%s\"", fieldLazyTableFieldEndpoint.getComment()));
                            // 字段非空
                            if (fieldLazyTableFieldEndpoint.isNotNull()) {
                                valueList.add("notNull=true");
                            }
                            // 是否主键
                            if (fieldLazyTableFieldEndpoint.isKey()) {
                                valueList.add("key=true");
                            }
                            // 字段默认值
                            if (!ObjectUtils.isEmpty(fieldLazyTableFieldEndpoint.getDefaultValue())) {
                                if (LazyDatabaseJsonMessage.specialFields.contains(fieldLazyTableFieldEndpoint.getDefaultValue())) {
                                    valueList.add(String.format("defaultValue=\"%s\"", fieldLazyTableFieldEndpoint.getDefaultValue()));
                                } else {
                                    valueList.add(String.format("defaultValue=\"'%s'\"", fieldLazyTableFieldEndpoint.getDefaultValue()));
                                }

                            }
                            // 字段类型
                            valueList.add(String.format("columnType=\"%s\"", fieldLazyTableFieldEndpoint.getColumnType()));
                            // 字段额外描述
                            if (!ObjectUtils.isEmpty(extra)) {
                                valueList.add(String.format("extra=\"%s\"", extra.replace("DEFAULT_GENERATED", "")));
                            }


                            lazyTableFieldAnnotation = String.format("    @LazyTableField(%s)", String.join(NormalUsedString.COMMA, valueList)) + NormalUsedString.NEWLINE;
                        }

                    }

                    importClass.add(mysqlColumnTypeEnum.getJavaType().getName());
                    return commentFormat + apiModelPropertyAnnotation + lazyTableFieldAnnotation + format;
                }
        ).collect(Collectors.joining());
        return attributeFileList;
    }

    /**
     * description 生成类注释
     *
     * @param tableEndpoint 表结构数据
     * @return String 返回类描述信息
     * @date 2022/2/15 1:41 下午
     */
    public static String generateClassDescribe(ClassLazyTableEndpoint tableEndpoint) {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final int localDateTimeHour = localDateTime.getHour();
        String describe = String.format("/**\n" +
                        " * describe %s \n" +
                        " *\n" +
                        " * @author Jia wei Wu\n" +
                        " * @date %s %s\n" +
                        " **/\n", tableEndpoint.getComment(),
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")),
                localDateTimeHour < 8 ? "夜间" : localDateTimeHour < 12 ? "上午" : localDateTimeHour < 18 ? "下午" : "晚上");
        return describe;
    }

    /**
     * description:  获取暂存表结构
     *
     * @return
     * @author 吴佳伟
     * @date: 16.1.23 01:08
     */
    public static ConcurrentMap<Class<?>, LazyTableEndpoint> getTableCache() {
        return CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP;
    }
}
