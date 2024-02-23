package com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaKeyword;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.persistence.reverse.ParamDescribeList;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultDDDLazyInfrastructurePersistence extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyInfrastructurePersistence(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String className = tableEndpoint.getClassName();
        List<String> domainNames = CamelAndUnderLineConverter.humpToArray(className);
        // import DO
        addImportClassName(tableEndpoint.getPackageName() +
                NormalUsedString.DOT + "infrastructure.entity" +
                NormalUsedString.DOT + className + "DO");

        // import converter
        addImportClassName(tableEndpoint.getPackageName() +
                NormalUsedString.DOT + "infrastructure.converter" +
                NormalUsedString.DOT + className + "Converter");
        // import mapper
        addImportClassName(tableEndpoint.getPackageName() +
                NormalUsedString.DOT + "infrastructure.mapper" +
                NormalUsedString.DOT + className + "Mapper");
        // 导入repository
        addImportClassName(tableEndpoint.getPackageName() +
                NormalUsedString.DOT + "domain.model." +
                domainNames.stream().map(domainName -> {
                    if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                        return domainName + NormalUsedString.UNDERSCORE;
                    }
                    return domainName;
                }).collect(Collectors.joining(NormalUsedString.DOT)) +
                NormalUsedString.DOT + className + "Repository");
        // import annotation
        addImportClassName("org.springframework.stereotype.Repository");
        // import Collectors
        addImportClassName("java.util.stream.Collectors");
        // import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers
        addImportClassName("com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers");
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        if (reverseEngineering.isEnableLazyCrud()) {
            addImportClassName("com.wu.framework.database.lazy.web.plus.AbstractLazyCrudRepository");
            // import 领域对象
            // 驼峰转换成list
            String domainClass = tableEndpoint.getPackageName() + NormalUsedString.DOT + "domain.model." +
                    domainNames.stream().map(domainName -> {
                        if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                            return domainName + NormalUsedString.UNDERSCORE;
                        }
                        return domainName;
                    }).collect(Collectors.joining(NormalUsedString.DOT)) + NormalUsedString.DOT + className;
            addImportClassName(domainClass);
        }
        if (this.getReverseEngineering().isEnableNormalCrud()) {
            addImportClassName("com.wu.framework.response.Result");
            addImportClassName("com.wu.framework.response.ResultFactory");
            addImportClassName("jakarta.annotation.Resource");

            // 添加领域对象
            // 驼峰转换成list
            String domainClass = tableEndpoint.getPackageName() + NormalUsedString.DOT + "domain.model." +
                    domainNames.stream().map(domainName -> {
                        if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                            return domainName + NormalUsedString.UNDERSCORE;
                        }
                        return domainName;
                    }).collect(Collectors.joining(NormalUsedString.DOT)) + NormalUsedString.DOT + className;
            addImportClassName(domainClass);
        }
        // import LazyLambdaStream
        addImportClassName("com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream");
        // import list
        addImportClassName("java.util.List");
        // import LazyPage
        addImportClassName("com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage");

    }

    /**
     * description:  添加 class 上的注解
     * class 上的注解如  @LazyTableFieldId(name = "%s", comment = "%s")
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassAnnotationPart() {

        addClassAnnotationPart("@Repository");
    }

    /**
     * description:  添加 class
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassNamePart() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();

        String className = tableEndpoint.getClassName();
        String repositoryImplName = String.format("%sRepositoryImpl", className);
        setFileName(repositoryImplName);
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        if (reverseEngineering.isEnableLazyCrud()) {
            addClassNamePart(
                    String.format("public class %s extends AbstractLazyCrudRepository<%sDO,%s,Integer> implements  %sRepository<%sDO> {" + NormalUsedString.NEWLINE,
                            repositoryImplName, className, className, className, className
                    )
                            + NormalUsedString.NEWLINE);
        } else {
            addClassNamePart(
                    String.format("public class %s  implements  %sRepository {" + NormalUsedString.NEWLINE,
                            repositoryImplName, className
                    )
                            + NormalUsedString.NEWLINE);
        }


    }

    /**
     * description:  添加 class 方法片段
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassMethodPart() {
        if (this.getReverseEngineering().isEnableNormalCrud()) {
            String comment = this.getReverseClassLazyTableEndpoint().getComment();
            String className = this.getReverseClassLazyTableEndpoint().getClassName();

            final String lowercaseFirstLetter = CamelAndUnderLineConverter.lowercaseFirstLetter(className);
            String methodParamName = lowercaseFirstLetter;
            if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(lowercaseFirstLetter))) {
                methodParamName = methodParamName + NormalUsedString.UNDERSCORE;
            }
            // story
            String saveMethodDescribe = generateMethodDescribe("新增" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), "新增" + comment)
                            .returnParam(String.format("Result<%s>", className), comment + "新增后领域对象")
            );
            addClassMethodPart(saveMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<%s> story(%s %s) {\n" +
                            "        %sDO %sDO = %sConverter" + getMapStructInstance() + ".from%s(%s);\n" +
                            "        lazyLambdaStream.upsert(%sDO);\n" +
                            "        return ResultFactory.successOf();\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    methodParamName
            ));
            // batchStory
            addClassMethodPart(generateMethodDescribe("批量新增" + comment
                    ,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sList", methodParamName), "批量新增" + comment)
                            .returnParam(String.format("Result<List<%s>>", className), comment + "新增后领域对象集合")
            ));
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<List<%s>> batchStory(List<%s> %sList) {\n" +
                            "        List<%sDO> %sDOList = %sList.stream().map(%sConverter" + getMapStructInstance() + "::from%s).collect(Collectors.toList());\n" +
                            "        lazyLambdaStream.upsert(%sDOList);\n" +
                            "        return ResultFactory.successOf();\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, methodParamName, className, className,
                    methodParamName
            ));

            // findOne
            String findOneMethodDescribe = generateMethodDescribe("查询单个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), "查询单个" + comment)
                            .returnParam(String.format("Result<%s>", className), comment + "领域对象")
            );
            addClassMethodPart(findOneMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<%s> findOne(%s %s) {\n" +
                            "        %sDO %sDO = %sConverter" + getMapStructInstance() + ".from%s(%s);\n" +
                            "        %s %sOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(%sDO), %s.class);\n" +
                            "        return ResultFactory.successOf(%sOne);\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    className, methodParamName, methodParamName, className,
                    methodParamName
            ));
            // findList
            String findListMethodDescribe = generateMethodDescribe("查询多个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), "查询多个" + comment)
                            .returnParam(String.format("Result<List<%s>>", className), comment + "领域对象")
            );
            addClassMethodPart(findListMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<List<%s>> findList(%s %s) {\n" +
                            "        %sDO %sDO = %sConverter" + getMapStructInstance() + ".from%s(%s);\n" +
                            "        List<%s> %sList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(%sDO), %s.class);\n" +
                            "        return ResultFactory.successOf(%sList);\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    className, methodParamName, methodParamName, className,
                    methodParamName
            ));
            // findPage
            String findPageMethodDescribe = generateMethodDescribe("分页查询多个" + comment,
                    ParamDescribeList
                            .of()
                            .param("size", "当前页数")
                            .param("current", "当前页")
                            .param(String.format("%s", methodParamName), "分页查询多个" + comment)
                            .returnParam(String.format("Result<LazyPage<%s>>", className), "分页" + comment + "领域对象")
            );
            addClassMethodPart(findPageMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<LazyPage<%s>> findPage(int size,int current,%s %s) {\n" +
                            "        %sDO %sDO = %sConverter" + getMapStructInstance() + ".from%s(%s);\n" +
                            "        LazyPage<%s> lazyPage = new LazyPage<>(current,size);\n" +
                            "        LazyPage<%s> %sLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(%sDO),lazyPage, %s.class);\n" +
                            "        return ResultFactory.successOf(%sLazyPage);\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    className,
                    className, methodParamName, methodParamName, className,
                    methodParamName
            ));
            // remove
            String deleteMethodDescribe = generateMethodDescribe("删除" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), "删除" + comment)
                            .returnParam(String.format("Result<%s>", className), comment)
            );
            addClassMethodPart(deleteMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<%s> remove(%s %s) {\n" +
                            "        %sDO %sDO = %sConverter" + getMapStructInstance() + ".from%s(%s);\n" +
                            "        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(%sDO));\n" +
                            "        return ResultFactory.successOf();\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    methodParamName
            ));
            // exists
            String existsMethodDescribe = generateMethodDescribe("是否存在" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), comment + "领域对象")
                            .returnParam("Result<Boolean>", "是否存在 true 存在，false 不存在")
            );
            addClassMethodPart(existsMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<Boolean> exists(%s %s) {\n" +
                            "        %sDO %sDO = %sConverter" + getMapStructInstance() + ".from%s(%s);\n" +
                            "        Boolean exists=lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(%sDO));\n" +
                            "        return ResultFactory.successOf(exists);\n" +
                            "    }\n",
                    className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    methodParamName
            ));
        }
    }

    /**
     * 获取 mapstruct 对应的instance
     * 如果不支持 mapstruct 那么返回的是. 否则返回的是 INSTANCE
     *
     * @return
     */
    private String getMapStructInstance() {
        if (getReverseEngineering().isEnableMapStruct()) {
            return ".INSTANCE";
        } else {
            return "";
        }
    }

    @Override
    protected String getPackage() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        return String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "infrastructure" + NormalUsedString.DOT + "persistence") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
    }

    /**
     * description:  添加 class 字段片段
     *
     * @LazyTableFieldId(comment = "api参数主键")
     * private Long id;
     * </p>
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassFieldPart() {
        addClassFieldPart("    @Resource\n" +
                "    LazyLambdaStream lazyLambdaStream;\n");
        if (OrmArchitecture.MYBATIS.equals(getReverseEngineering().getOrmArchitecture())) {
            addClassFieldPart(String.format("    @Autowired\n" +
                    "    %sMapper %sMapper;", getReverseClassLazyTableEndpoint().getClassName(), lowercaseFirstLetterTableName()));

        }
    }

    /**
     * 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "infrastructure" + File.separator + "persistence";
    }

}
