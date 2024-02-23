package com.wu.framework.inner.lazy.persistence.reverse.lazy.feign;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.MysqlColumnTypeEnum;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultFeignLazyAPICommand extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultFeignLazyAPICommand(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        List<String> importClass = this.getImportClassNames();
        List<String> classAnnotation = getClassAnnotationParts();
        if (reverseEngineering.isEnableLombokData()) {
            classAnnotation.add("@Data");
            importClass.add(Data.class.getName());
        }
        if (reverseEngineering.isEnableLombokAccessors()) {
            classAnnotation.add("@Accessors(chain = true)");
            importClass.add("lombok.experimental.Accessors");
        }
        if (reverseEngineering.isEnableSwagger()) {
            classAnnotation.add(String.format("@Schema(title = \"%s\",description = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getComment()));
            importClass.add("io.swagger.v3.oas.annotations.media.Schema");
            importClass.add("io.swagger.v3.oas.annotations.media.Schema");
        }
    }

    /**
     * description:  添加 class 上的注解
     * class 上的注解如  @LazyTableFieldId(name = "%s", comment = "%s")
     *
     * @author 吴佳伟
     * @date 13.2.23 17:13
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
        className += "APICommand";
        setFileName(className);

        String classnamePart = String.format("public class %s {", className) + NormalUsedString.NEWLINE;
        addClassNamePart(classnamePart);
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
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        List<LazyTableFieldEndpoint> fieldEndpoints = tableEndpoint.getOutLazyTableFieldEndpoints();
        fieldEndpoints.forEach(
                fieldLazyTableFieldEndpoint -> {

                    final String comment = fieldLazyTableFieldEndpoint.getComment();
                    final String commentFormat = NormalUsedString.NEWLINE + String.format("    /**\n" +
                            "     * \n" +
                            "     * %s\n" +
                            "     */\n", comment);
                    final MysqlColumnTypeEnum mysqlColumnTypeEnum =
                            MysqlColumnTypeEnum.MYSQL_COLUMN_TYPE_ENUM_MAP.getOrDefault(fieldLazyTableFieldEndpoint.getDataType(), MysqlColumnTypeEnum.VARCHAR);
                    final String format = String.format("    private %s %s;", mysqlColumnTypeEnum.getJavaType().getSimpleName(), fieldLazyTableFieldEndpoint.getName());

                    // ApiModelProperty 注解
                    String apiModelPropertyAnnotation = NormalUsedString.EMPTY;
                    if (reverseEngineering.isEnableSwagger()) {
                        apiModelPropertyAnnotation = String.format("    @Schema(description =\"%s\",name =\"%s\",example = \"%s\")", fieldLazyTableFieldEndpoint.getComment(), fieldLazyTableFieldEndpoint.getName(), "") + NormalUsedString.NEWLINE;
                    }


                    String lazyTableFieldAnnotation = NormalUsedString.EMPTY;

                    addImportClassName(mysqlColumnTypeEnum.getJavaType().getName());
                    this.addClassFieldPart(commentFormat + apiModelPropertyAnnotation + lazyTableFieldAnnotation + format);
                }
        );
    }

    /**
     * 获取当前class 对应的包名
     *
     * @return
     */
    @Override
    protected String getPackage() {
        return String.format("package %s;", this.getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "api.command") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
    }

    /**
     * 获取 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "api" + File.separator + "command";
    }
}
