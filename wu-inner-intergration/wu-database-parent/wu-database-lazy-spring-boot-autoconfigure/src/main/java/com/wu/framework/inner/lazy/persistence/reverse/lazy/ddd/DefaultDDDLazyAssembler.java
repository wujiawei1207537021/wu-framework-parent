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
public class DefaultDDDLazyAssembler extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyAssembler(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
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

        // 添加DTO对象
        addImportClassName(tableEndpoint.getPackageName() + NormalUsedString.DOT + "application.dto" + NormalUsedString.DOT + className + "DTO");

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
        String assemblerName = String.format("%sDTOAssembler", tableEndpoint.getClassName());
        setFileName(assemblerName);
        if (getReverseEngineering().isEnableMapStruct()) {
            addImportClassName("org.mapstruct.factory.Mappers");
            addImportClassName("org.mapstruct.Mapper");
            addClassAnnotationPart("@Mapper");
            addClassNamePart(String.format("public interface %s {" + NormalUsedString.NEWLINE, assemblerName) + NormalUsedString.NEWLINE);
        } else {

            addClassNamePart(String.format("public class %s {" + NormalUsedString.NEWLINE, assemblerName) + NormalUsedString.NEWLINE);
        }


    }

    @Override
    protected String getPackage() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        return String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "application" + NormalUsedString.DOT + "assembler") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
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

    }

    /**
     * description:  添加 class 方法片段
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassMethodPart() {

        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String className = tableEndpoint.getClassName();
        String comment = tableEndpoint.getComment();
        final String lowercaseFirstLetter = CamelAndUnderLineConverter.lowercaseFirstLetter(className);
        String methodParamName = lowercaseFirstLetter;
        if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(lowercaseFirstLetter))) {
            methodParamName = methodParamName + NormalUsedString.UNDERSCORE;
        }
        String finalMethodParamName = methodParamName;
        if (getReverseEngineering().isEnableMapStruct()) {
            //MapStruct 创建的代理对象
            addClassMethodPart(generateMethodDescribe("MapStruct 创建的代理对象", ParamDescribeList.of()) +
                    String.format("    %sDTOAssembler INSTANCE = Mappers.getMapper(%sDTOAssembler.class);",
                            className, className
                    ));
            // command 转换成领域对象
            addClassMethodPart(generateMethodDescribe("应用层存储入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sStoryCommand", methodParamName), "保存" + comment + "对象")
                            .returnParam(className, comment + "领域对象")
            ) +
                    String.format("     %s to%s(%sStoryCommand %sStoryCommand);",
                            className, className, className, methodParamName
                    ));

            addClassMethodPart(generateMethodDescribe("应用层更新入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sUpdateCommand", methodParamName), "更新" + comment + "对象")
                            .returnParam(className, comment + "领域对象")
            ) +
                    String.format("     %s to%s(%sUpdateCommand %sUpdateCommand);",
                            className, className, className, methodParamName
                    ));

            addClassMethodPart(generateMethodDescribe("应用层查询入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryOneCommand", methodParamName), "查询单个" + comment + "对象参数")
                            .returnParam(className, comment + "领域对象")
            ) +
                    String.format("     %s to%s(%sQueryOneCommand %sQueryOneCommand);",
                            className, className, className, methodParamName
                    ));


            addClassMethodPart(generateMethodDescribe("应用层查询入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryListCommand", methodParamName), "查询集合" + comment + "对象参数")
                            .returnParam(className, comment + "领域对象")
            ) +
                    String.format("     %s to%s(%sQueryListCommand %sQueryListCommand);",
                            className, className, className, methodParamName
                    ));

            addClassMethodPart(generateMethodDescribe("应用层删除入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sRemoveCommand", methodParamName), "删除" + comment + "对象参数")
                            .returnParam(className, comment + "领域对象")
            ) +
                    String.format("     %s to%s(%sRemoveCommand %sRemoveCommand);",
                            className, className, className, methodParamName
                    ));
            // 领域对象转换成dto
            addClassMethodPart(generateMethodDescribe("持久层领域对象转换成DTO对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), comment + "领域对象")
                            .returnParam(String.format("%sDTO", className), comment + "DTO对象")
            ) +
                    String.format("     %sDTO from%s(%s %s);"
                            ,
                            className, className, className, methodParamName
                    ));
        } else {
            // 应用层存储入参转换成 领域对象
            addClassMethodPart(generateMethodDescribe("应用层存储入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sStoryCommand", methodParamName), "保存" + comment + "对象")
                            .returnParam(className, comment + "领域对象")) +
                    String.format("    public static %s to%s(%sStoryCommand %sStoryCommand) {\n" +
                                    "        if (null != %sStoryCommand) {\n" +
                                    "        %s %s = new %s(); \n" +
                                    tableEndpoint.getOutLazyTableFieldEndpoints().stream()
                                            .map(lazyTableFieldEndpoint -> {
                                                // set
                                                String name = lazyTableFieldEndpoint.getName();

                                                String capitalizeFirstLetterFieldName = CamelAndUnderLineConverter.capitalizeFirstLetter(name);

                                                String finalCapitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName;
                                                if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(finalCapitalizeFirstLetterFieldName))) {
                                                    capitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName + NormalUsedString.UNDERSCORE;
                                                }
                                                return String.format(
                                                        "           %s.set%s(%sStoryCommand.get%s());\n",
                                                        finalMethodParamName, capitalizeFirstLetterFieldName, finalMethodParamName, capitalizeFirstLetterFieldName
                                                );
                                            }).collect(Collectors.joining()) +
                                    "            return %s;\n" +
                                    "        }\n" +
                                    "        return null;\n" +
                                    "    }\n"
                            ,
                            className, className, className, methodParamName,
                            methodParamName,
                            className, methodParamName, className,
                            methodParamName
                    ));
            //应用层更新入参转换成 领域对象
            addClassMethodPart(generateMethodDescribe("应用层更新入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sUpdateCommand", methodParamName), "更新" + comment + "对象")
                            .returnParam(className, comment + "领域对象")) +
                    String.format("    public static %s to%s(%sUpdateCommand %sUpdateCommand) {\n" +
                                    "        if (null != %sUpdateCommand) {\n" +
                                    "        %s %s = new %s(); \n" +
                                    tableEndpoint.getOutLazyTableFieldEndpoints().stream()
                                            .map(lazyTableFieldEndpoint -> {
                                                // set
                                                String name = lazyTableFieldEndpoint.getName();

                                                String capitalizeFirstLetterFieldName = CamelAndUnderLineConverter.capitalizeFirstLetter(name);

                                                String finalCapitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName;
                                                if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(finalCapitalizeFirstLetterFieldName))) {
                                                    capitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName + NormalUsedString.UNDERSCORE;
                                                }
                                                return String.format(
                                                        "           %s.set%s(%sUpdateCommand.get%s());\n",
                                                        finalMethodParamName, capitalizeFirstLetterFieldName, finalMethodParamName, capitalizeFirstLetterFieldName
                                                );
                                            }).collect(Collectors.joining()) +
                                    "            return %s;\n" +
                                    "        }\n" +
                                    "        return null;\n" +
                                    "    }\n"
                            ,
                            className, className, className, methodParamName,
                            methodParamName,
                            className, methodParamName, className,
                            methodParamName
                    ));
            //应用层查询入参转换成 领域对象
            addClassMethodPart(generateMethodDescribe("应用层查询入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryOneCommand", methodParamName), "查询单个" + comment + "对象参数")
                            .returnParam(className, comment + "领域对象")) +
                    String.format("    public static %s to%s(%sQueryOneCommand %sQueryOneCommand) {\n" +
                                    "        if (null != %sQueryOneCommand) {\n" +
                                    "        %s %s = new %s(); \n" +
                                    tableEndpoint.getOutLazyTableFieldEndpoints().stream()
                                            .map(lazyTableFieldEndpoint -> {
                                                // set
                                                String name = lazyTableFieldEndpoint.getName();

                                                String capitalizeFirstLetterFieldName = CamelAndUnderLineConverter.capitalizeFirstLetter(name);

                                                String finalCapitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName;
                                                if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(finalCapitalizeFirstLetterFieldName))) {
                                                    capitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName + NormalUsedString.UNDERSCORE;
                                                }
                                                return String.format(
                                                        "           %s.set%s(%sQueryOneCommand.get%s());\n",
                                                        finalMethodParamName, capitalizeFirstLetterFieldName, finalMethodParamName, capitalizeFirstLetterFieldName
                                                );
                                            }).collect(Collectors.joining()) +
                                    "            return %s;\n" +
                                    "        }\n" +
                                    "        return null;\n" +
                                    "    }\n"
                            ,
                            className, className, className, methodParamName,
                            methodParamName,
                            className, methodParamName, className,
                            methodParamName
                    ));
            addClassMethodPart(generateMethodDescribe("应用层查询入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryListCommand", methodParamName), "查询集合" + comment + "对象参数")
                            .returnParam(className, comment + "领域对象")) +
                    String.format("    public static %s to%s(%sQueryListCommand %sQueryListCommand) {\n" +
                                    "        if (null != %sQueryListCommand) {\n" +
                                    "        %s %s = new %s(); \n" +
                                    tableEndpoint.getOutLazyTableFieldEndpoints().stream()
                                            .map(lazyTableFieldEndpoint -> {
                                                // set
                                                String name = lazyTableFieldEndpoint.getName();

                                                String capitalizeFirstLetterFieldName = CamelAndUnderLineConverter.capitalizeFirstLetter(name);

                                                String finalCapitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName;
                                                if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(finalCapitalizeFirstLetterFieldName))) {
                                                    capitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName + NormalUsedString.UNDERSCORE;
                                                }
                                                return String.format(
                                                        "           %s.set%s(%sQueryListCommand.get%s());\n",
                                                        finalMethodParamName, capitalizeFirstLetterFieldName, finalMethodParamName, capitalizeFirstLetterFieldName
                                                );
                                            }).collect(Collectors.joining()) +
                                    "            return %s;\n" +
                                    "        }\n" +
                                    "        return null;\n" +
                                    "    }\n"
                            ,
                            className, className, className, methodParamName,
                            methodParamName,
                            className, methodParamName, className,
                            methodParamName
                    ));
            // 应用层删除入参转换成 领域对象"
            addClassMethodPart(generateMethodDescribe("应用层删除入参转换成 领域对象",
                    ParamDescribeList
                            .of()
                            .param(String.format("%sRemoveCommand", methodParamName), "删除" + comment + "对象参数")
                            .returnParam(className, comment + "领域对象")) +
                    String.format("    public static %s to%s(%sRemoveCommand %sRemoveCommand) {\n" +
                                    "        if (null != %sRemoveCommand) {\n" +
                                    "        %s %s = new %s(); \n" +
                                    tableEndpoint.getOutLazyTableFieldEndpoints().stream()
                                            .map(lazyTableFieldEndpoint -> {
                                                // set
                                                String name = lazyTableFieldEndpoint.getName();

                                                String capitalizeFirstLetterFieldName = CamelAndUnderLineConverter.capitalizeFirstLetter(name);

                                                String finalCapitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName;
                                                if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(finalCapitalizeFirstLetterFieldName))) {
                                                    capitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName + NormalUsedString.UNDERSCORE;
                                                }
                                                return String.format(
                                                        "           %s.set%s(%sRemoveCommand.get%s());\n",
                                                        finalMethodParamName, capitalizeFirstLetterFieldName, finalMethodParamName, capitalizeFirstLetterFieldName
                                                );
                                            }).collect(Collectors.joining()) +
                                    "            return %s;\n" +
                                    "        }\n" +
                                    "        return null;\n" +
                                    "    }\n"
                            ,
                            className, className, className, methodParamName,
                            methodParamName,
                            className, methodParamName, className,
                            methodParamName
                    ));
            // 领域对象转换成dto
            addClassMethodPart(generateMethodDescribe("持久层领域对象转换成DTO对象", ParamDescribeList
                    .of()
                    .param(String.format("%s", methodParamName), comment + "领域对象")
                    .returnParam(String.format("%sDTO", className), comment + "DTO对象")
            ) +
                    String.format("    public static %sDTO from%s(%s %s) {\n" +
                                    "        if (null != %s) {\n" +
                                    "        %sDTO %sDTO = new %sDTO(); \n" +
                                    tableEndpoint.getOutLazyTableFieldEndpoints().stream()
                                            .map(lazyTableFieldEndpoint -> {
                                                // set
                                                String name = lazyTableFieldEndpoint.getName();

                                                String capitalizeFirstLetterFieldName = CamelAndUnderLineConverter.capitalizeFirstLetter(name);

                                                String finalCapitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName;
                                                if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(finalCapitalizeFirstLetterFieldName))) {
                                                    capitalizeFirstLetterFieldName = capitalizeFirstLetterFieldName + NormalUsedString.UNDERSCORE;
                                                }
                                                return String.format(
                                                        "           %sDTO.set%s(%s.get%s());\n",
                                                        finalMethodParamName, capitalizeFirstLetterFieldName, finalMethodParamName, capitalizeFirstLetterFieldName
                                                );
                                            }).collect(Collectors.joining()) +
                                    "            return %sDTO;\n" +
                                    "        }\n" +
                                    "        return null;\n" +
                                    "    }\n"
                            ,
                            className, className, className, methodParamName,
                            methodParamName,
                            className, methodParamName, className,
                            methodParamName
                    ));
        }


    }

    /**
     * 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "application" + File.separator + "assembler";
    }

}
