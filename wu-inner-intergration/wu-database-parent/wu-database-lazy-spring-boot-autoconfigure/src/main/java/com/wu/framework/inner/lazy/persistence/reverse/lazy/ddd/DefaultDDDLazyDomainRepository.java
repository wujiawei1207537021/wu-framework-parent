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
public class DefaultDDDLazyDomainRepository extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyDomainRepository(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String className = tableEndpoint.getClassName();
        if (reverseEngineering.isEnableLazyCrud()) {
            // import DO
            addImportClassName(tableEndpoint.getPackageName() +
                    NormalUsedString.DOT + "infrastructure.entity" +
                    NormalUsedString.DOT + className + "DO");

            addImportClassName("com.wu.framework.response.repository.LazyCrudRepository");
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
        }
        if (this.getReverseEngineering().isEnableNormalCrud()) {
            addImportClassName("com.wu.framework.response.Result");
            addImportClassName("com.wu.framework.response.ResultFactory");
            addImportClassName("org.springframework.beans.factory.annotation.Autowired");

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

        String repositoryName = String.format("%sRepository", className);
        setFileName(repositoryName);
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        if (reverseEngineering.isEnableLazyCrud()) {

            addClassNamePart(String.format("public interface %s<T> extends LazyCrudRepository<T,%s,Integer> {" + NormalUsedString.NEWLINE, repositoryName, className, className) + NormalUsedString.NEWLINE);
        } else {
            addClassNamePart(String.format("public interface %s {" + NormalUsedString.NEWLINE, repositoryName) + NormalUsedString.NEWLINE);
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
                            .returnParam(String.format(" Result<%s>", className), comment + "新增后领域对象")
            );
            addClassMethodPart(saveMethodDescribe);
            addClassMethodPart(String.format("    Result<%s> story(%s %s);\n",
                    className,
                    className, methodParamName
            ));
            // batchStory
            addClassMethodPart(generateMethodDescribe("批量新增" + comment
                    ,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sList", methodParamName), "批量新增" + comment)
                            .returnParam(String.format("Result<List<%s>>", className), comment + "新增后领域对象集合")
            ));
            addClassMethodPart(String.format("    Result<List<%s>> batchStory(List<%s> %sList);\n",
                    className,
                    className, methodParamName
            ));
            // findOne
            String findOneMethodDescribe = generateMethodDescribe("查询单个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), "查询单个" + comment)
                            .returnParam(String.format("Result<%s>", className), comment + "DTO对象")
            );
            addClassMethodPart(findOneMethodDescribe);
            addClassMethodPart(String.format("    Result<%s> findOne(%s %s);\n",
                    className,
                    className, methodParamName
            ));
            // findList
            String findListMethodDescribe = generateMethodDescribe("查询多个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), "查询多个" + comment)
                            .returnParam(String.format("Result<List<%s>>", className), comment + "DTO对象")
            );
            addClassMethodPart(findListMethodDescribe);
            addClassMethodPart(String.format("    Result<List<%s>> findList(%s %s);\n",
                    className,
                    className, methodParamName
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
            addClassMethodPart(String.format("    Result<LazyPage<%s>> findPage(int size,int current,%s %s);\n",
                    className,
                    className, methodParamName
            ));
            // remove
            String deleteMethodDescribe = generateMethodDescribe("删除" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), "删除" + comment)
                            .returnParam(String.format("Result<%s>", className), comment)
            );
            addClassMethodPart(deleteMethodDescribe);
            addClassMethodPart(String.format("    Result<%s> remove(%s %s);\n",
                    className,
                    className, methodParamName
            ));
            // exists
            String existsMethodDescribe = generateMethodDescribe("是否存在" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%s", methodParamName), "是否存在" + comment)
                            .returnParam("Result<Boolean>", comment + "是否存在")
            );
            addClassMethodPart(existsMethodDescribe);
            addClassMethodPart(String.format("    Result<Boolean> exists(%s %s);\n",
                    className, methodParamName
            ));
        }
    }

    @Override
    protected String getPackage() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String domain = tableEndpoint.getClassName();
        // 驼峰转换成list
        List<String> domainNames = CamelAndUnderLineConverter.humpToArray(domain);
        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "domain.model." +
                domainNames.stream().map(domainName -> {
                    if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                        return domainName + NormalUsedString.UNDERSCORE;
                    }
                    return domainName;
                }).collect(Collectors.joining(NormalUsedString.DOT))
        ) + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
        return classPackage;
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
     * 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String domain = tableEndpoint.getClassName();
        List<String> domainNames = CamelAndUnderLineConverter.humpToArray(domain);
        return "domain" + File.separator + "model" + File.separator + domainNames.stream().map(domainName -> {
            if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                return domainName + NormalUsedString.UNDERSCORE;
            }
            return domainName;
        }).collect(Collectors.joining(File.separator));
    }

}
