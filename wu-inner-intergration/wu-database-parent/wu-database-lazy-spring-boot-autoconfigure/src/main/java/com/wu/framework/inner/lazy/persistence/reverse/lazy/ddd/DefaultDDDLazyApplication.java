package com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaKeyword;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.reverse.ParamDescribeList;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultDDDLazyApplication extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyApplication(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        if (this.getReverseEngineering().isEnableNormalCrud()) {
            addImportClassName("com.wu.framework.response.Result");
            addImportClassName("com.wu.framework.response.ResultFactory");

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

            // import dto
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application" + NormalUsedString.DOT + "dto" + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "DTO");

            // import list
            addImportClassName("java.util.List");
            // import LazyPage
            addImportClassName("com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage");
        }
    }

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
            addClassMethodPart(String.format("    Result<%s> story(%sStoryCommand %sStoryCommand);\n",
                    className,
                    className, methodParamName
            ));
            // batchStory
            addClassMethodPart(generateMethodDescribe("批量新增" + comment
                    ,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sStoryCommandList", methodParamName), "批量新增" + comment)
                            .returnParam(String.format("Result<List<%s>>", className), comment + "新增后领域对象集合")
            ));
            addClassMethodPart(String.format("    Result<List<%s>> batchStory(List<%sStoryCommand> %sStoryCommandList);\n",
                    className,
                    className, methodParamName
            ));
            // update
            String updateMethodDescribe = generateMethodDescribe("更新" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sUpdateCommand", methodParamName), "更新" + comment)
                            .returnParam(String.format("Result<%s>", className), comment + "领域对象")
            );
            addClassMethodPart(updateMethodDescribe);
            addClassMethodPart(String.format("    Result<%s> updateOne(%sUpdateCommand %sUpdateCommand);\n",
                    className,
                    className, methodParamName
            ));
            // findOne
            String findOneMethodDescribe = generateMethodDescribe("查询单个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryOneCommand", methodParamName), "查询单个" + comment)
                            .returnParam(String.format("Result<%sDTO>", className), comment + "DTO对象")
            );
            addClassMethodPart(findOneMethodDescribe);
            addClassMethodPart(String.format("    Result<%sDTO> findOne(%sQueryOneCommand %sQueryOneCommand);\n",
                    className,
                    className, methodParamName
            ));
            // findList
            String findListMethodDescribe = generateMethodDescribe("查询多个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryListCommand", methodParamName), "查询多个" + comment)
                            .returnParam(String.format("Result <List<%sDTO>>", className), comment + "DTO对象")
            );
            addClassMethodPart(findListMethodDescribe);
            addClassMethodPart(String.format("    Result <List<%sDTO>> findList(%sQueryListCommand %sQueryListCommand);\n",
                    className,
                    className, methodParamName
            ));
            // findPage
            String findPageMethodDescribe = generateMethodDescribe("分页查询多个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryListCommand", methodParamName), "分页查询多个" + comment)
                            .returnParam(String.format("Result <LazyPage<%sDTO>>", className), "分页" + comment + "DTO对象")
            );
            addClassMethodPart(findPageMethodDescribe);
            addClassMethodPart(String.format("    Result <LazyPage<%sDTO>> findPage(int size,int current,%sQueryListCommand %sQueryListCommand);\n",
                    className,
                    className, methodParamName
            ));
            // delete
            String deleteMethodDescribe = generateMethodDescribe("删除" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sRemoveCommand", methodParamName), "删除" + comment)
                            .returnParam(String.format("Result<%s>", className), comment));
            addClassMethodPart(deleteMethodDescribe);
            addClassMethodPart(String.format("    Result<%s> remove(%sRemoveCommand %sRemoveCommand);\n",
                    className,
                    className, methodParamName
            ));
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


        String applicationName = String.format("%sApplication", tableEndpoint.getClassName());
        setFileName(applicationName);
        addClassNamePart(String.format("public interface %s {" + NormalUsedString.NEWLINE, applicationName) + NormalUsedString.NEWLINE);


    }

    @Override
    protected String getPackage() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        return String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "application") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
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
        return "application";
    }

}
