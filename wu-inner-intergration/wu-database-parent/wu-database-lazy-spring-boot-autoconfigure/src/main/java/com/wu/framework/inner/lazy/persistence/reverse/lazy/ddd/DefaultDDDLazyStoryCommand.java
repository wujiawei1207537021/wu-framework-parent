package com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.MysqlColumnTypeEnum;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultDDDLazyStoryCommand extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyStoryCommand(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        List<String> importClass = this.getImportClassNames();
        List<String> classAnnotation = getClassAnnotationParts();
        if (reverseEngineering.isEnableLombokData()) {
            classAnnotation.add("@Data");
            importClass.add(Data.class.getName());
        }
        if (reverseEngineering.isEnableLombokAccessors()) {
            importClass.add("lombok.experimental.Accessors");
        }
        if (reverseEngineering.isEnableSwagger()) {
            addImportClassName("io.swagger.v3.oas.annotations.media.Schema");
            addImportClassName("io.swagger.v3.oas.annotations.media.Schema");
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
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        if (reverseEngineering.isEnableLombokAccessors()) {
            addClassAnnotationPart("@Accessors(chain = true)");
        }
        if (reverseEngineering.isEnableSwagger()) {
            addClassAnnotationPart(String.format("@Schema(title = \"%s\",description = \"%s\")", tableEndpoint.getTableName() + "_story_command", tableEndpoint.getComment()));
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
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();


        String commandName = String.format("%sStoryCommand", tableEndpoint.getClassName());
        setFileName(commandName);
        addClassNamePart(String.format("public class %s {" + NormalUsedString.NEWLINE, commandName) + NormalUsedString.NEWLINE);


    }

    @Override
    protected String getPackage() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        return String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "application" + NormalUsedString.DOT + "command" + NormalUsedString.DOT + humpToDOT()) + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
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
        List<LazyTableFieldEndpoint> fieldEndpoints = tableEndpoint.getInLazyTableFieldEndpoints();
        fieldEndpoints.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(LazyTableFieldEndpoint::getName))), ArrayList::new)
        ).forEach(
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
     * 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "application" + File.separator + "command" + File.separator + humpToSeparator();
    }

}
