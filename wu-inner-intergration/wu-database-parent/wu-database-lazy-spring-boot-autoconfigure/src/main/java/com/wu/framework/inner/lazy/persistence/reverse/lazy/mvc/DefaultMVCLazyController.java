package com.wu.framework.inner.lazy.persistence.reverse.lazy.mvc;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaKeyword;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
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
public class DefaultMVCLazyController extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultMVCLazyController(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
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
            addImportClassName(this.getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + this.getReverseClassLazyTableEndpoint().getClassName());
        } else {
            addImportClassName("com.wu.framework.inner.layer.web.EasyController");
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

        if (this.getReverseEngineering().isEnableSwagger()) {
            addClassAnnotationPart(String.format("@Tag(name = \"%s提供者\")", this
                    .getReverseClassLazyTableEndpoint().getComment()));
        }
        addClassAnnotationPart(String.format("@EasyController(\"/%s\")", path));
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
        String controller = this.getReverseClassLazyTableEndpoint().getClassName() + "Provider";

        setFileName(controller);
        if (this.getReverseEngineering().isEnableLazyCrud()) {
            addClassNamePart(String.format("public class %s extends AbstractLazyCrudProvider<%s,%s, Long>  {" +
                            NormalUsedString.NEWLINE,
                    controller,
                    this.getReverseClassLazyTableEndpoint().getClassName(),
                    this.getReverseClassLazyTableEndpoint().getClassName())
                    + NormalUsedString.NEWLINE);
        } else {
            addClassNamePart(String.format("public class %s  {" + NormalUsedString.NEWLINE,
                    controller) + NormalUsedString.NEWLINE);
        }
        super.initClassNamePart();
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
        return "controller";
    }
}
