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
public class DefaultDDDLazyInfrastructureConverter extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyInfrastructureConverter(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
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
        // 添加实体对象
        addImportClassName(tableEndpoint.getPackageName() + NormalUsedString.DOT + "infrastructure.entity" + NormalUsedString.DOT + className + "DO");

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
        String converterName = String.format("%sConverter", tableEndpoint.getClassName());
        setFileName(converterName);
        if (getReverseEngineering().isEnableMapStruct()) {
            addImportClassName("org.mapstruct.factory.Mappers");
            addImportClassName("org.mapstruct.Mapper");
            addClassAnnotationPart("@Mapper");
            addClassNamePart(String.format("public interface %s {" + NormalUsedString.NEWLINE, converterName) + NormalUsedString.NEWLINE);
        } else {
            addClassNamePart(String.format("public class %s {" + NormalUsedString.NEWLINE, converterName) + NormalUsedString.NEWLINE);

        }


    }

    @Override
    protected String getPackage() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        return String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "infrastructure" + NormalUsedString.DOT + "converter") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
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
        if (getReverseEngineering().isEnableMapStruct()) {
            //MapStruct 创建的代理对象
            addClassMethodPart(generateMethodDescribe("MapStruct 创建的代理对象", ParamDescribeList.of()) +
                    String.format("    %sConverter INSTANCE = Mappers.getMapper(%sConverter.class);",
                            className, className
                    ));
            // 实体对象 转换成领域对象
            addClassMethodPart(generateMethodDescribe("实体对象 转换成领域对象", ParamDescribeList
                    .of()
                    .param(String.format("%sDO", methodParamName), comment + "实体对象")
                    .returnParam(String.format("%s", className), comment + "领域对象")
            ) +
                    String.format("    %s to%s(%sDO %sDO);",
                            className, className, className, methodParamName
                    ));
            // 领域对象 转换成实体对象
            addClassMethodPart(generateMethodDescribe("领域对象 转换成实体对象", ParamDescribeList
                    .of()
                    .param(String.format("%s", methodParamName), comment + "领域对象")
                    .returnParam(String.format("%sDO", className), comment + "实体对象")
            ) +
                    String.format("     %sDO from%s(%s %s); "
                            ,
                            className, className, className, methodParamName
                    ));
        } else {
            // 实体对象 转换成领域对象
            String finalMethodParamName = methodParamName;
            addClassMethodPart(generateMethodDescribe("实体对象 转换成领域对象", ParamDescribeList
                    .of()
                    .param(String.format("%sDO", methodParamName), comment + "实体对象")
                    .returnParam(String.format("%s", className), comment + "领域对象")
            ) +
                    String.format("    public static %s to%s(%sDO %sDO) {\n" +
                                    "        if (null != %sDO) {\n" +
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
                                                        "           %s.set%s(%sDO.get%s());\n",
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
            // 领域对象 转换成实体对象
            addClassMethodPart(generateMethodDescribe("领域对象 转换成实体对象", ParamDescribeList
                    .of()
                    .param(String.format("%s", methodParamName), comment + "领域对象")
                    .returnParam(String.format("%sDO", className), comment + "实体对象")
            ) +
                    String.format("    public static %sDO from%s(%s %s) {\n" +
                                    "        if (null != %s) {\n" +
                                    "        %sDO %sDO = new %sDO(); \n" +
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
                                                        "           %sDO.set%s(%s.get%s());\n",
                                                        finalMethodParamName, capitalizeFirstLetterFieldName, finalMethodParamName, capitalizeFirstLetterFieldName
                                                );
                                            }).collect(Collectors.joining()) +
                                    "            return %sDO;\n" +
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
        return "infrastructure" + File.separator + "converter";
    }

}
