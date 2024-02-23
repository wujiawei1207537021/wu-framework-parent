package com.wu.framework.inner.lazy.persistence.reverse.lazy.feign;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaKeyword;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
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
public class DefaultFeignLazyApiRpc extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultFeignLazyApiRpc(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        addImportClassName("com.wu.framework.database.lazy.web.plus.stereotype.LazyRpc");

        addImportClassName(this.getReverseClassLazyTableEndpoint().getPackageName() + ".api." + this.getReverseClassLazyTableEndpoint().getClassName() + "Api");
        if (this.getReverseEngineering().isEnableLazyCrud()) {
            addImportClassName("com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider");
            addImportClassName(this.getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "api.ao" + NormalUsedString.DOT + this.getReverseClassLazyTableEndpoint().getClassName() + "Ao");
        } else {
            addImportClassName("com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider");
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

        addClassAnnotationPart(String.format("@LazyRpc(\"/%s\")", path));
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
        String className = this.getReverseClassLazyTableEndpoint().getClassName();
        String controller = className + "ApiRpc";

        setFileName(controller);
        if (this.getReverseEngineering().isEnableLazyCrud()) {
            addClassNamePart(String.format("public class %s extends AbstractLazyCrudProvider<%sAo, Long> implements %sApi  {" +
                            NormalUsedString.NEWLINE,
                    controller, className, className
            ) + NormalUsedString.NEWLINE);
        } else {
            addClassNamePart(String.format("public class %s  implements %syApi  {" + NormalUsedString.NEWLINE,
                    controller, className) + NormalUsedString.NEWLINE);
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
     * 获取当前class 对应的包名
     *
     * @return
     */
    @Override
    protected String getPackage() {
        return String.format("package %s;", this.getReverseClassLazyTableEndpoint().getPackageName() + ".api.rpc") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
    }

    /**
     * 获取 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "api" + File.separator + "rpc";
    }
}
