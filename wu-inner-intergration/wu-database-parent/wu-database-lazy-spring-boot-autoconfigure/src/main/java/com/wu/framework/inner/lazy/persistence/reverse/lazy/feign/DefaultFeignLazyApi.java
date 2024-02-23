package com.wu.framework.inner.lazy.persistence.reverse.lazy.feign;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaKeyword;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.reverse.ParamDescribeList;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;

import java.util.Arrays;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultFeignLazyApi extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultFeignLazyApi(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {

        addImportClassName("org.springframework.cloud.openfeign.FeignClient");
        addImportClassName("org.springframework.web.bind.annotation.GetMapping");
        addImportClassName("org.springframework.web.bind.annotation.PostMapping");
        addImportClassName("org.springframework.web.bind.annotation.RequestBody");
        addImportClassName("org.springframework.web.bind.annotation.RequestParam");

        if (this.getReverseEngineering().isEnableSwagger()) {
            addImportClassName("io.swagger.v3.oas.annotations.tags.Tag");
            addImportClassName("io.swagger.v3.oas.annotations.Operation");
            // import Parameter
            addImportClassName("io.swagger.v3.oas.annotations.Parameter");
        }

        if (this.getReverseEngineering().isEnableLazyCrud()) {
            addImportClassName("com.wu.framework.response.repository.LazyCrudProvider");
            addImportClassName(this.getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "api.ao" + NormalUsedString.DOT + this.getReverseClassLazyTableEndpoint().getClassName() + "Ao");
        }
        if (this.getReverseEngineering().isEnableNormalCrud()) {
            addImportClassName("org.springframework.web.bind.annotation.*");
            addImportClassName("com.wu.framework.response.Result");
            addImportClassName("com.wu.framework.response.ResultFactory");

            // import APICommand
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "command" + NormalUsedString.DOT + humpToDOT() + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "APICommand");
            // import APIDTO
            addImportClassName(getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "dto" + NormalUsedString.DOT + humpToDOT() + NormalUsedString.DOT + getReverseClassLazyTableEndpoint().getClassName() + "APIDTO");
            addImportClassName("java.util.List");


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
        String serverName = "default-feign-server-provider";
        String className = this.getReverseClassLazyTableEndpoint().getClassName();

        addClassAnnotationPart(
                String.format("@FeignClient(contextId = \"%sApi\", name = \"%s\", url = \"${feign.url.%s: }\")",
                        className, serverName, serverName)
        );
        if (this.getReverseEngineering().isEnableSwagger()) {
            addClassAnnotationPart(String.format("@Tag(name = \"%sFeign提供者\")",
                            this.getReverseClassLazyTableEndpoint().getComment() != null ? this.getReverseClassLazyTableEndpoint().getComment() :
                                    this.getReverseClassLazyTableEndpoint().getClassName()
                    )
            );
        }

        super.initClassAnnotationPart();
    }

    /**
     * description:  添加 class
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassNamePart() {
        String apiName = this.getReverseClassLazyTableEndpoint().getClassName() + "Api";

        setFileName(apiName);
        if (this.getReverseEngineering().isEnableLazyCrud()) {
            addClassNamePart(String.format("public interface %s extends LazyCrudProvider<%sAo, Long>  {" +
                            NormalUsedString.NEWLINE,
                    apiName, this.getReverseClassLazyTableEndpoint().getClassName()) + NormalUsedString.NEWLINE);
        } else {
            addClassNamePart(String.format("public interface %s  {" + NormalUsedString.NEWLINE,
                    apiName) + NormalUsedString.NEWLINE);
        }
        super.initClassNamePart();
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
            String saveMethodDescribe = generateMethodDescribe("新增" + comment, ParamDescribeList.of()
                    .param(String.format("%sAPICommand", methodParamName), "新增" + comment + "参数")
                    .returnParam(String.format("Result<%sAPICommand>", className), "返回结果")
            );
            addClassMethodPart(saveMethodDescribe);
            if (getReverseEngineering().isEnableSwagger()) {
                addClassMethodPart(String.format("    @Operation(summary = \"新增%s\")", comment));
            }

            addClassMethodPart("    @PostMapping(\"/story\")");
            addClassMethodPart(String.format("     Result<%sAPICommand> story(@RequestBody %sAPICommand %sAPICommand);",
                    className,
                    className, methodParamName
            ));
            // batchStory
            addClassMethodPart(generateMethodDescribe("批量新增" + comment, ParamDescribeList.of()
                    .param(String.format("%sAPICommandList", methodParamName), "批量新增" + comment + "参数")
                    .returnParam(String.format("Result<List<%sAPICommand>>", className), "返回结果")
            ));
            if (getReverseEngineering().isEnableSwagger()) {
                addClassMethodPart(String.format("    @Operation(summary = \"批量新增%s\")", comment));
            }

            addClassMethodPart("    @PostMapping(\"/batchStory\")");
            addClassMethodPart(String.format("     Result<List<%sAPICommand>> batchStory(@RequestBody List<%sAPICommand> %sAPICommandList);",
                    className, className, methodParamName
            ));

            // update
            String updateMethodDescribe = generateMethodDescribe("更新" + comment, ParamDescribeList.of()
                    .param(String.format("%sAPICommand", methodParamName), "更新" + comment + "参数")
                    .returnParam(String.format("Result<%sAPICommand>", className), "返回结果"));
            addClassMethodPart(updateMethodDescribe);
            if (getReverseEngineering().isEnableSwagger()) {

                addClassMethodPart(String.format("    @Operation(summary = \"更新%s\")", comment));
            }
            addClassMethodPart("    @PutMapping(\"/updateOne\")");
            addClassMethodPart(String.format("     Result<%sAPICommand> updateOne(@RequestBody %sAPICommand %sAPICommand);",
                    className, className, methodParamName
            ));
            // findOne
            String findOneMethodDescribe = generateMethodDescribe("查询单个" + comment, ParamDescribeList.of()
                    .param(String.format("%sAPICommand", methodParamName), "查询单个" + comment + "参数")
                    .returnParam(String.format("Result<%sAPIDTO>", className), "返回结果"));
            addClassMethodPart(findOneMethodDescribe);
            if (getReverseEngineering().isEnableSwagger()) {

                addClassMethodPart(String.format("    @Operation(summary = \"查询单个%s\")", comment));
            }
            addClassMethodPart("    @GetMapping(\"/findOne\")");
            addClassMethodPart(String.format("     Result<%sAPIDTO> findOne(@ModelAttribute %sAPICommand %sAPICommand);",
                    className, className, methodParamName
            ));
            // findList
            String findListMethodDescribe = generateMethodDescribe("查询多个" + comment, ParamDescribeList.of()
                    .param(String.format("%sAPICommand", methodParamName), "查询多个" + comment + "参数")
                    .returnParam(String.format("Result<List<%sAPIDTO>>", className), "返回结果"));
            addClassMethodPart(findListMethodDescribe);
            if (getReverseEngineering().isEnableSwagger()) {

                addClassMethodPart(String.format("    @Operation(summary = \"查询多个%s\")", comment));
            }
            addClassMethodPart("    @GetMapping(\"/findList\")");
            addClassMethodPart(String.format("     Result<List<%sAPIDTO>> findList(@ModelAttribute %sAPICommand %sAPICommand);",
                    className, className, methodParamName
            ));
            // remove
            String deleteMethodDescribe = generateMethodDescribe("删除" + comment, ParamDescribeList.of()
                    .param(String.format("%sAPICommand", methodParamName), "删除" + comment + "参数")
                    .returnParam(String.format("Result<%sAPICommand>", className), "返回结果"));
            addClassMethodPart(deleteMethodDescribe);
            addClassMethodPart(String.format("    @Operation(summary = \"删除%s\")", comment));
            addClassMethodPart("    @DeleteMapping(\"/remove\")");
            addClassMethodPart(String.format("     Result<%sAPICommand> remove(@ModelAttribute %sAPICommand %sAPICommand);",
                    className, className, methodParamName
            ));
        }

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
     * 获取 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "api";
    }
}
