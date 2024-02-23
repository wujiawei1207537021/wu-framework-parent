package com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaKeyword;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
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
public class DefaultDDDLazyApplicationImpl extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyApplicationImpl(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        addImportClassName("com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication");
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String application = tableEndpoint.getClassName() + "Application";

        addImportClassName(tableEndpoint.getPackageName() + NormalUsedString.DOT + "application." + application);
        if (this.getReverseEngineering().isEnableNormalCrud()) {
            addImportClassName("org.springframework.web.bind.annotation.*");
            addImportClassName("com.wu.framework.response.Result");
            addImportClassName("com.wu.framework.response.ResultFactory");
            addImportClassName("org.springframework.beans.factory.annotation.Autowired");

            String className = tableEndpoint.getClassName();
            // 添加领域对象
            // 驼峰转换成list
            List<String> domainNames = CamelAndUnderLineConverter.humpToArray(className);
            String domainClass = tableEndpoint.getPackageName() + NormalUsedString.DOT + "domain.model." +
                    domainNames.stream().map(domainName -> {
                        if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                            return domainName + NormalUsedString.UNDERSCORE;
                        }
                        return domainName;
                    }).collect(Collectors.joining(NormalUsedString.DOT)) + NormalUsedString.DOT + className;
            addImportClassName(domainClass);

            // import command
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application.command" + NormalUsedString.DOT + humpToDOT() + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "RemoveCommand");
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application.command" + NormalUsedString.DOT + humpToDOT() + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "StoryCommand");
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application.command" + NormalUsedString.DOT + humpToDOT() + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "UpdateCommand");
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application.command" + NormalUsedString.DOT + humpToDOT() + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "QueryListCommand");
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application.command" + NormalUsedString.DOT + humpToDOT() + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "QueryOneCommand");

            // import application
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application" + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "Application");
            // 导入 DTOAssembler
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application.assembler" + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "DTOAssembler");

            // import dto
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application" + NormalUsedString.DOT + "dto" + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "DTO");

            // import java.util.stream.Collectors;
            addImportClassName("java.util.stream.Collectors");
            addImportClassName("jakarta.annotation.Resource");


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
            // import list
            addImportClassName("java.util.List");
            // import LazyPage
            addImportClassName("com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage");
        }
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

        // TODO @Application 注解

        addClassAnnotationPart("@LazyApplication");
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
        String applicationImpl = className + "ApplicationImpl";
        setFileName(applicationImpl);
        String application = className + "Application";
        String classname = String.format("public class %s implements %s {" + NormalUsedString.NEWLINE, applicationImpl, application) + NormalUsedString.NEWLINE;
        addClassNamePart(classname);

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
            String saveMethodDescribe = generateMethodDescribe("新增" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sStoryCommand", methodParamName), "新增" + comment)
                            .returnParam(String.format("Result<%s>", className), comment + "新增后领域对象")
            );
            // story
            addClassMethodPart(saveMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<%s> story(%sStoryCommand %sStoryCommand) {\n" +
                            "        %s %s = %sDTOAssembler" + getMapStructInstance() + "to%s(%sStoryCommand);\n" +
                            "        return %sRepository.story(%s);\n" +
                            "    }",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    methodParamName, methodParamName
            ));
            // batchStory
            addClassMethodPart(generateMethodDescribe("批量新增" + comment
                    ,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sStoryCommandList", methodParamName), "批量新增" + comment)
                            .returnParam(String.format("Result<List<%s>>", className), comment + "新增后领域对象集合")
            ));
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<List<%s>> batchStory(List<%sStoryCommand> %sStoryCommandList) {\n" +
                            "        List<%s> %sList = %sStoryCommandList.stream().map( %sDTOAssembler" + getMapLambdaStructInstance() + "to%s).collect(Collectors.toList());\n" +
                            "        return %sRepository.batchStory(%sList);\n" +
                            "    }",
                    className, className, methodParamName,
                    className, methodParamName, methodParamName, className, className,
                    methodParamName, methodParamName
            ));

            // update
            String updateMethodDescribe = generateMethodDescribe("更新" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sUpdateCommand", methodParamName), "更新" + comment)
                            .returnParam(String.format("Result<%s>", className), comment + "领域对象")
            );
            addClassMethodPart(updateMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<%s> updateOne(%sUpdateCommand %sUpdateCommand) {\n" +
                            "        %s %s = %sDTOAssembler" + getMapStructInstance() + "to%s(%sUpdateCommand);\n" +
                            "        return %sRepository.story(%s);\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    methodParamName, methodParamName
            ));
            // findOne
            String findOneMethodDescribe = generateMethodDescribe("查询单个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryOneCommand", methodParamName), "查询单个" + comment)
                            .returnParam(String.format("Result<%sDTO>", className), comment + "DTO对象")
            );
            addClassMethodPart(findOneMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<%sDTO> findOne(%sQueryOneCommand %sQueryOneCommand) {\n" +
                            "        %s %s = %sDTOAssembler" + getMapStructInstance() + "to%s(%sQueryOneCommand);\n" +
                            "        return %sRepository.findOne(%s).convert(%sDTOAssembler" + getMapLambdaStructInstance() + "from%s);\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    methodParamName, methodParamName, className, className
            ));
            // findList
            String findListMethodDescribe = generateMethodDescribe("查询多个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryListCommand", methodParamName), "查询多个" + comment)
                            .returnParam(String.format("Result<List<%sDTO>>", className), comment + "DTO对象")
            );
            addClassMethodPart(findListMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<List<%sDTO>> findList(%sQueryListCommand %sQueryListCommand) {\n" +
                            "        %s %s = %sDTOAssembler" + getMapStructInstance() + "to%s(%sQueryListCommand);\n" +
                            "        return %sRepository.findList(%s)" +
                            "        .convert(%ss -> %ss.stream().map(%sDTOAssembler" + getMapLambdaStructInstance() + "from%s).collect(Collectors.toList())) " +
                            ";\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    methodParamName, methodParamName,
                    methodParamName, methodParamName, className, className
            ));
            // findPage
            String findPageMethodDescribe = generateMethodDescribe("分页查询多个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryListCommand", methodParamName), "分页查询多个" + comment)
                            .returnParam(String.format("Result<LazyPage<%sDTO>>", className), "分页" + comment + "DTO对象")
            );
            addClassMethodPart(findPageMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<LazyPage<%sDTO>> findPage(int size,int current,%sQueryListCommand %sQueryListCommand) {\n" +
                            "        %s %s = %sDTOAssembler" + getMapStructInstance() + "to%s(%sQueryListCommand);\n" +
                            "        return %sRepository.findPage(size,current,%s)" +
                            "        .convert(page -> page.convert(%sDTOAssembler" + getMapLambdaStructInstance() + "from%s)) " +
                            "           ;\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    methodParamName, methodParamName,
                    className, className
            ));
            // remove
            String deleteMethodDescribe = generateMethodDescribe("删除" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sRemoveCommand", methodParamName), "删除" + comment)
                            .returnParam(String.format("Result<%s>", className), comment));
            addClassMethodPart(deleteMethodDescribe);
            addClassMethodPart("    @Override");
            addClassMethodPart(String.format("    public Result<%s> remove(%sRemoveCommand %sRemoveCommand) {\n" +
                            "     %s %s = %sDTOAssembler" + getMapStructInstance() + "to%s(%sRemoveCommand);\n" +
                            "     return %sRepository.remove(%s);\n" +
                            "    }\n",
                    className, className, methodParamName,
                    className, methodParamName, className, className, methodParamName,
                    methodParamName, methodParamName
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
            return ".INSTANCE.";
        } else {
            return ".";
        }
    }

    /**
     * 获取 mapstruct 对应的instance
     * 如果不支持 mapstruct 那么返回的是. 否则返回的是 INSTANCE:
     *
     * @return
     */
    private String getMapLambdaStructInstance() {
        if (getReverseEngineering().isEnableMapStruct()) {
            return ".INSTANCE::";
        } else {
            return "::";
        }
    }

    @Override
    protected String getPackage() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();

        return String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "application.impl") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
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
        addClassFieldPart(String.format("    @Resource\n" +
                "    %sRepository %sRepository;", getReverseClassLazyTableEndpoint().getClassName(), lowercaseFirstLetterTableName()));
    }

    /**
     * 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "application" + File.separator + "impl";
    }

}
