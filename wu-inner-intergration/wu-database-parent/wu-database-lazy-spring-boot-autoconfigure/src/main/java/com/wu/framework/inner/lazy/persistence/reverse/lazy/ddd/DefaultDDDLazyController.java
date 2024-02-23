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
public class DefaultDDDLazyController extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyController(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        if (this.getReverseEngineering().isEnableSwagger()) {
            addImportClassName("io.swagger.v3.oas.annotations.tags.Tag");
            addImportClassName("io.swagger.v3.oas.annotations.Operation");
            // import Parameter
            addImportClassName("io.swagger.v3.oas.annotations.Parameter");
        }
        if (this.getReverseEngineering().isEnableLazyCrud()) {
            addImportClassName("com.wu.framework.inner.layer.web.EasyController");
            addImportClassName("com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider");
            addImportClassName(this.getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "infrastructure.entity" + NormalUsedString.DOT + this.getReverseClassLazyTableEndpoint().getClassName() + "DO");
        } else {
            addImportClassName("com.wu.framework.inner.layer.web.EasyController");
        }
        if (this.getReverseEngineering().isEnableNormalCrud()) {
            addImportClassName("org.springframework.web.bind.annotation.*");
            addImportClassName("com.wu.framework.response.Result");
            addImportClassName("com.wu.framework.response.ResultFactory");
            addImportClassName("org.springframework.beans.factory.annotation.Autowired");
            addImportClassName("jakarta.annotation.Resource");

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

            // import application
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application" + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "Application");
            // import dto
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "application" + NormalUsedString.DOT + "dto" + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "DTO");

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
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String domain = tableEndpoint.getClassName();
        List<String> domainNames = CamelAndUnderLineConverter.humpToArray(domain);
        String path = domainNames.stream().map(domainName -> {
            if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                return domainName + NormalUsedString.UNDERSCORE;
            }
            return domainName;
        }).collect(Collectors.joining(NormalUsedString.SLASH));
        addClassAnnotationPart(String.format("@Tag(name = \"%s提供者\")", this
                .getReverseClassLazyTableEndpoint().getComment()));
        addClassAnnotationPart(String.format("@EasyController(\"%s/%s\")", this.getReverseEngineering().getApiUrlPrefix(), path));
        super.initClassAnnotationPart();
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
        // 添加application对象

        String lowercaseFirstLetterTableName = lowercaseFirstLetterTableName();
        addClassFieldPart(String.format("    @Resource\n" +
                "    private %sApplication %sApplication;\n", getReverseClassLazyTableEndpoint().getClassName(), lowercaseFirstLetterTableName));

    }

    /**
     * description:  添加 class 方法片段
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassMethodPart() {
        // 添加crud 的方法区间
        /**
         *
         *      * describe  新增
         *      *
         *      * @param s 实体对象
         *      * @return Result<S>
         *      * @author Jia wei Wu
         *      * @date 2022/1/30 19:00
         *
         *@PostMapping("/story")
         *     <S extends T > Result < S > story(@RequestBody S s);
         */
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
                            .param(String.format("%sStoryCommand", methodParamName), "新增" + comment)
                            .returnParam(String.format("Result<%s>", className), comment + "新增后领域对象")
            );
            addClassMethodPart(saveMethodDescribe);
            if (getReverseEngineering().isEnableSwagger()) {
                addClassMethodPart(String.format("    @Operation(summary = \"新增%s\")", comment));
            }

            addClassMethodPart("    @PostMapping(\"/story\")");
            addClassMethodPart(String.format("    public Result<%s> story(@RequestBody %sStoryCommand %sStoryCommand){%n" +
                            "        return %sApplication.story(%sStoryCommand);%n" +
                            "    }",
                    className,
                    className, methodParamName,
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
            if (getReverseEngineering().isEnableSwagger()) {
                addClassMethodPart(String.format("    @Operation(summary = \"批量新增%s\")", comment));
            }

            addClassMethodPart("    @PostMapping(\"/batchStory\")");
            addClassMethodPart(String.format("    public Result<List<%s>> batchStory(@RequestBody List<%sStoryCommand> %sStoryCommandList){%n" +
                            "        return %sApplication.batchStory(%sStoryCommandList);%n" +
                            "    }",
                    className,
                    className, methodParamName,
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
            if (getReverseEngineering().isEnableSwagger()) {

                addClassMethodPart(String.format("    @Operation(summary = \"更新%s\")", comment));
            }
            addClassMethodPart("    @PutMapping(\"/updateOne\")");
            addClassMethodPart(String.format("    public Result<%s> updateOne(@RequestBody %sUpdateCommand %sUpdateCommand){%n" +
                            "        return %sApplication.updateOne(%sUpdateCommand);%n" +
                            "    }",
                    className,
                    className, methodParamName,
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
            if (getReverseEngineering().isEnableSwagger()) {

                addClassMethodPart(String.format("    @Operation(summary = \"查询单个%s\")", comment));
            }
            addClassMethodPart("    @GetMapping(\"/findOne\")");
            addClassMethodPart(String.format("    public Result<%sDTO> findOne(@ModelAttribute %sQueryOneCommand %sQueryOneCommand){%n" +
                            "        return %sApplication.findOne(%sQueryOneCommand);%n" +
                            "    }",
                    className,
                    className, methodParamName,
                    methodParamName, methodParamName
            ));
            // findList
            String findListMethodDescribe = generateMethodDescribe("查询多个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryListCommand", methodParamName), "查询多个" + comment)
                            .returnParam(String.format("Result<List<%sDTO>>", className), comment + "DTO对象")
            );
            addClassMethodPart(findListMethodDescribe);
            if (getReverseEngineering().isEnableSwagger()) {

                addClassMethodPart(String.format("    @Operation(summary = \"查询多个%s\")", comment));
            }
            addClassMethodPart("    @GetMapping(\"/findList\")");
            addClassMethodPart(String.format("    public Result<List<%sDTO>> findList(@ModelAttribute %sQueryListCommand %sQueryListCommand){%n" +
                            "        return %sApplication.findList(%sQueryListCommand);%n" +
                            "    }",
                    className,
                    className, methodParamName,
                    methodParamName, methodParamName
            ));
            // findPage
            String findPageMethodDescribe = generateMethodDescribe("分页查询多个" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sQueryListCommand", methodParamName), "分页查询多个" + comment)
                            .returnParam(String.format("Result<LazyPage<%sDTO>>", className), "分页" + comment + "DTO对象")
            );
            addClassMethodPart(findPageMethodDescribe);
            if (getReverseEngineering().isEnableSwagger()) {
                addClassMethodPart(String.format("    @Operation(summary = \"分页查询多个%s\")", comment));
            }
            addClassMethodPart("    @GetMapping(\"/findPage\")");
            addClassMethodPart(String.format("    public Result<LazyPage<%sDTO>> findPage(@Parameter(description =\"分页大小\") @RequestParam(defaultValue = \"10\", value = \"size\") int size,\n" +
                            "                           @Parameter(description =\"当前页数\") @RequestParam(defaultValue = \"1\", value = \"current\") int current,@ModelAttribute %sQueryListCommand %sQueryListCommand){%n" +
                            "        return %sApplication.findPage(size,current,%sQueryListCommand);%n" +
                            "    }",
                    className,
                    className, methodParamName,
                    methodParamName, methodParamName
            ));
            // remove
            String deleteMethodDescribe = generateMethodDescribe("删除" + comment,
                    ParamDescribeList
                            .of()
                            .param(String.format("%sRemoveCommand", methodParamName), "删除" + comment)
                            .returnParam(String.format("Result<%s>", className), comment)
            );
            addClassMethodPart(deleteMethodDescribe);
            addClassMethodPart(String.format("    @Operation(summary = \"删除%s\")", comment));
            addClassMethodPart("    @DeleteMapping(\"/remove\")");
            addClassMethodPart(String.format("    public Result<%s> remove(@ModelAttribute %sRemoveCommand %sRemoveCommand){%n" +
                            "        return %sApplication.remove(%sRemoveCommand);%n" +
                            "    }",
                    className,
                    className, methodParamName,
                    methodParamName, methodParamName
            ));
        }


    }

    /**
     * description:  添加 class
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassNamePart() {
        String className = this.getReverseClassLazyTableEndpoint().getClassName();
        String controller = className + "Provider";

        setFileName(controller);
        if (this.getReverseEngineering().isEnableLazyCrud()) {
            addClassNamePart(String.format("public class %s extends AbstractLazyCrudProvider<%sDO,%sDO, Long>  {" +
                            NormalUsedString.NEWLINE,
                    controller, className, className) + NormalUsedString.NEWLINE);
        } else {
            addClassNamePart(String.format("public class %s  {" + NormalUsedString.NEWLINE,
                    controller) + NormalUsedString.NEWLINE);
        }
        super.initClassNamePart();
    }


    /**
     * 获取 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "controller";
    }
}
