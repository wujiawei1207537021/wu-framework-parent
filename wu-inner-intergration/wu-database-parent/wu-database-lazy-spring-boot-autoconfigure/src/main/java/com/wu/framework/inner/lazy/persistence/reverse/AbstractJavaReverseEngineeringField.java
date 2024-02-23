package com.wu.framework.inner.lazy.persistence.reverse;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.MysqlColumnTypeEnum;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/13 17:24
 */
public abstract class AbstractJavaReverseEngineeringField extends AbstractJavaReverseEngineeringClassName implements JavaReverseEngineeringField {
    // 字段
    private List<String> classFieldParts = new ArrayList<>();

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
                    // LazyTableField 注解
                    if (OrmArchitecture.LAZY.equals(reverseEngineering.getOrmArchitecture())) {
                        // 自增主键   @LazyTableFieldId(name = "id", comment = "ID")
                        String extra = fieldLazyTableFieldEndpoint.getExtra();
                        String name = fieldLazyTableFieldEndpoint.getColumnName().replace(NormalUsedString.BACKTICK, NormalUsedString.EMPTY);

                        if (NormalUsedString.AUTO_INCREMENT.equalsIgnoreCase(extra)) {
                            lazyTableFieldAnnotation = String.format("    @LazyTableFieldId(name = \"%s\", comment = \"%s\")", name, comment) + NormalUsedString.NEWLINE;
                            addImportClassName(LazyTableFieldId.class.getName());
                        } else {
                            List<String> valueList = new ArrayList<>();
                            // 字段名称
                            valueList.add(String.format("name=\"%s\"", name));
                            // 字段描述
                            valueList.add(String.format("comment=\"%s\"", fieldLazyTableFieldEndpoint.getComment()));
                            // 字段非空
                            if (fieldLazyTableFieldEndpoint.isNotNull()) {
                                valueList.add("notNull=true");
                            }
                            // 是否主键
                            if (fieldLazyTableFieldEndpoint.isKey()) {
                                valueList.add("key=true");
                            }
                            // 是否唯一性索引
                            if (!ObjectUtils.isEmpty(fieldLazyTableFieldEndpoint.getLazyTableIndexEndpoints())) {
                                //lazyTableIndexs = {@LazyTableIndex(indexName = "menu_unique", indexType = LayerField.LayerFieldType.UNIQUE)}
                                valueList.add("lazyTableIndexs = {" + Arrays.stream(fieldLazyTableFieldEndpoint.getLazyTableIndexEndpoints()).map(lazyTableIndexEndpoint -> {
                                    String indexTypeString = "LayerField.LayerFieldType.NORMAL";
                                    switch (lazyTableIndexEndpoint.getFieldIndexType()) {
                                        case ID:
                                            indexTypeString = "LayerField.LayerFieldType.ID";
                                            break;
                                        case NORMAL:
                                            indexTypeString = "LayerField.LayerFieldType.NORMAL";
                                            break;
                                        case UNIQUE:
                                            indexTypeString = "LayerField.LayerFieldType.UNIQUE";
                                            break;
                                        case SPATIAL:
                                            indexTypeString = "LayerField.LayerFieldType.SPATIAL";
                                            break;
                                        case FULLTEXT:
                                            indexTypeString = "LayerField.LayerFieldType.FULLTEXT";
                                            break;
                                        case AUTOMATIC:
                                            indexTypeString = "LayerField.LayerFieldType.AUTOMATIC";
                                            break;
                                        default:
                                            indexTypeString = "LayerField.LayerFieldType.NORMAL";
                                    }
                                    return "@LazyTableIndex(indexName = \"" + lazyTableIndexEndpoint.getIndexName() + "\"," +
                                            " indexType = " + indexTypeString + ")";
                                }).collect(Collectors.joining(",")) + "}");

                            }

                            // 字段默认值
                            if (!ObjectUtils.isEmpty(fieldLazyTableFieldEndpoint.getDefaultValue())) {
                                if (LazyDatabaseJsonMessage.specialFields.contains(fieldLazyTableFieldEndpoint.getDefaultValue())) {
                                    valueList.add(String.format("defaultValue=\"%s\"", fieldLazyTableFieldEndpoint.getDefaultValue()));
                                } else {
                                    valueList.add(String.format("defaultValue=\"'%s'\"", fieldLazyTableFieldEndpoint.getDefaultValue()));
                                }
                                // 默认upset 策略不更新默认值
                                valueList.add("upsertStrategy = LazyFieldStrategy.NEVER");
                            }
                            // 字段类型
                            valueList.add(String.format("columnType=\"%s\"", fieldLazyTableFieldEndpoint.getColumnType()));
                            // 字段额外描述
                            if (!ObjectUtils.isEmpty(extra)) {
                                valueList.add(String.format("extra=\"%s\"", extra.replace("DEFAULT_GENERATED", "")));
                            }


                            lazyTableFieldAnnotation = String.format("    @LazyTableField(%s)", String.join(NormalUsedString.COMMA, valueList)) + NormalUsedString.NEWLINE;
                        }

                    }

                    addImportClassName(mysqlColumnTypeEnum.getJavaType().getName());
                    this.addClassFieldPart(commentFormat + apiModelPropertyAnnotation + lazyTableFieldAnnotation + format);
                }
        );
    }


    /**
     * description:  添加 class 字段片段
     *
     * @param classFieldPart
     * @LazyTableFieldId(comment = "api参数主键")
     * private Long id;
     * </p>
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void addClassFieldPart(String classFieldPart) {
        this.classFieldParts.add(classFieldPart);
    }

    /**
     * description:  获取添加 class 的字段片段
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public List<String> getClassFieldPart() {
        return this.classFieldParts;
    }
}
