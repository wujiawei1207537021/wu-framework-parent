package com.wu.framework.inner.lazy.persistence.util;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.MysqlColumnTypeEnum;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasySmartFillFieldConverter;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wu.framework.inner.lazy.persistence.analyze.SQLAnalyze.CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/8 2:35 下午
 */
public class LazyTableUtil {

    public static final Logger log = LoggerFactory.getLogger(LazyTableUtil.class);

    public static String getTableName(Class clazz) {
        LazyTable lazyTable = AnnotatedElementUtils.findMergedAnnotation(clazz, LazyTable.class);
        if (null == lazyTable || ObjectUtils.isEmpty(lazyTable.tableName())) {

            String simpleName = clazz.getSimpleName();
            // 天然适应Uo
            if (simpleName.endsWith("Uo")) {
                simpleName = simpleName.substring(0, simpleName.length() - 2);
            }
            return CamelAndUnderLineConverter.humpToLine2(simpleName);
        }
        if (!ObjectUtils.isEmpty(lazyTable.schema())) {
            return lazyTable.schema() + NormalUsedString.DOT + lazyTable.tableName();
        }
        return lazyTable.tableName();
    }

    /**
     * 将 class 解析中注解 LazyTable 解析成 ClassLazyTableEndpoint
     *
     * @param clazz
     * @return
     */
    public static ClassLazyTableEndpoint analyzeLazyTable(Class clazz) {
        if (!CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.containsKey(clazz)) {
            synchronized (CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP) {
                LazyTable lazyTable = AnnotatedElementUtils.findMergedAnnotation(clazz, LazyTable.class);

                String className = clazz.getName();
                String tableName = getTableName(clazz);
                final String packageName = clazz.getPackage().getName();
                String comment = "";
                boolean smartFillField = false;
                final ClassLazyTableEndpoint classLazyTableEndpoint = new ClassLazyTableEndpoint();
                if (null != lazyTable) {
                    classLazyTableEndpoint.setSchema(lazyTable.schema());
                    smartFillField = lazyTable.smartFillField();
                }
                classLazyTableEndpoint.setPackageName(packageName);
                classLazyTableEndpoint.setComment(comment);
                classLazyTableEndpoint.setClassName(className);
                classLazyTableEndpoint.setClazz(clazz);
                classLazyTableEndpoint.setTableName(tableName);
                classLazyTableEndpoint.setFieldEndpoints(LazyTableFieldUtil.analyzeFieldOnAnnotation(clazz, null));
                classLazyTableEndpoint.setSmartFillField(smartFillField);
                log.info("Initialize {} annotation parameters  className:[{}],tableName:[{}],comment:[{}]", clazz,
                        className, tableName, comment);
                CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.put(clazz, classLazyTableEndpoint);
            }
        }
        return CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.get(clazz);
    }

    /**
     * describe 创建 Java class
     *
     * @param tableEndpoint ClassLazyTableEndpoint 参数
     * @return void void
     * @author Jia wei Wu
     * @date 2022/1/23 7:55 下午
     **/
    public static void createJava(ClassLazyTableEndpoint tableEndpoint) {
        createJava(tableEndpoint, new LazyOperationConfig.ReverseEngineering());
    }

    /**
     * describe 创建 Java class
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     * @return void void
     * @author Jia wei Wu
     * @date 2022/1/23 3:50 下午
     **/
    public static void createJava(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        URL resource = EasySmartFillFieldConverter.class.getResource(NormalUsedString.SLASH);
        // 写入class文件
        String resourceFile = resource.getFile();
        // 工作空间存储
        final String packageName = tableEndpoint.getPackageName();

        String target = resourceFile.split("target")[0] + "src/main/java/";

        resourceFile = target + packageName.replace(NormalUsedString.DOT, File.separator) + File.separator + tableEndpoint.getClassName();

        String classContext = createJavaString(tableEndpoint, reverseEngineering);
        try {
            BufferedWriter bufferedWriter = FileUtil.createFile(null, "", NormalUsedString.DOT_JAVA, resourceFile);
            bufferedWriter.write(classContext);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * describe 创建Java 字符串
     *
     * @param tableEndpoint      tableEndpoint
     * @param reverseEngineering 逆行工程
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 4:43 下午
     **/
    public static String createJavaString(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {

        List<FieldLazyTableFieldEndpoint> fieldEndpoints = tableEndpoint.getFieldEndpoints();

        final StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName()) + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        List<String> importClass = new ArrayList<>();

        String classAnnotationString = NormalUsedString.NEWLINE;

        if (reverseEngineering.isEnableLombokData()) {
            classAnnotationString += "@Data" +
                    NormalUsedString.NEWLINE;
            importClass.add(Data.class.getName());
        }
        if (reverseEngineering.isEnableLombokAccessors()) {
            classAnnotationString += "@Accessors(chain = true)" +
                    NormalUsedString.NEWLINE;
            importClass.add("lombok.experimental.Accessors");
        }

        if (reverseEngineering.isEnableLazy()) {
            classAnnotationString += String.format("@LazyTable(tableName = \"%s\",schema = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getSchema()) +
                    NormalUsedString.NEWLINE;
            importClass.add(LazyTable.class.getName());
            importClass.add(LazyTableField.class.getName());
        }
        if (reverseEngineering.isEnableSwagger()) {
            classAnnotationString += String.format("@ApiModel(value = \"%s\",description = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getComment()) +
                    NormalUsedString.NEWLINE;
            importClass.add("io.swagger.annotations.ApiModel");
            importClass.add("io.swagger.annotations.ApiModelProperty");
        }

        String classname = String.format("public class %s {", tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;


        final String attributeFileList = fieldEndpoints.stream().map(
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
                        apiModelPropertyAnnotation = String.format("    @ApiModelProperty(name =\"%s\",example = \"%s\")", fieldLazyTableFieldEndpoint.getName(), "") + NormalUsedString.NEWLINE;
                    }


                    String lazyTableFieldAnnotation = NormalUsedString.EMPTY;
                    // LazyTableField 注解
                    if (reverseEngineering.isEnableLazy()) {
                        lazyTableFieldAnnotation = String.format("    @LazyTableField(name =\"%s\",comment = \"%s\")", fieldLazyTableFieldEndpoint.getColumnName(), fieldLazyTableFieldEndpoint.getComment()) + NormalUsedString.NEWLINE;
                    }

                    importClass.add(mysqlColumnTypeEnum.getJavaType().getName());
                    return commentFormat + apiModelPropertyAnnotation + lazyTableFieldAnnotation + format;
                }
        ).collect(Collectors.joining());

        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(classPackage);
        classContext.append(importString);
        classContext.append(classAnnotationString);
        classContext.append(classname);
        classContext.append(attributeFileList);
        classContext.append(NormalUsedString.NEWLINE + NormalUsedString.RIGHT_BRACE);

        return classContext.toString();
    }
}
