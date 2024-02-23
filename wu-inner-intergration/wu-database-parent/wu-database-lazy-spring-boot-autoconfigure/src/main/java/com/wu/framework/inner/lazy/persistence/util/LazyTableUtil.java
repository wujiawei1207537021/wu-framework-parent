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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                    comment = lazyTable.comment();
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
        String resourceFilePrefix = resource.getFile();
        // 工作空间存储
        final String packageName = tableEndpoint.getPackageName();

        String target = resourceFilePrefix.split("target")[0] + "src/main/java/";

        resourceFilePrefix = target + packageName.replace(NormalUsedString.DOT, File.separator) + File.separator;

        createJavaEntity(tableEndpoint, reverseEngineering, resourceFilePrefix);
        createJavaService(tableEndpoint, reverseEngineering, resourceFilePrefix);
        createJavaServiceImpl(tableEndpoint, reverseEngineering, resourceFilePrefix);
        createJavaMapper(tableEndpoint, reverseEngineering, resourceFilePrefix);
        createJavaController(tableEndpoint, reverseEngineering, resourceFilePrefix);

    }

    /**
     * 创建 Java的 controller
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.framework.inner.lazy.database.smart.database.persistence.
     */
    private static void createJavaController(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {
        final StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "controller") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);

        String classAnnotationString = NormalUsedString.NEWLINE;
        classAnnotationString += String.format("@Api(tags = \"%s提供者\")", tableEndpoint.getComment()) + NormalUsedString.NEWLINE;

        List<String> importClass = new ArrayList<>();
        if (reverseEngineering.isEnableSwagger()) {
            importClass.add("io.swagger.annotations.Api");
        }

        String classname;
        String controller = tableEndpoint.getClassName() + "Provider";
        if (reverseEngineering.getReverseEngineeringMvc().isEnableLazyOperationMvc()) {

            classAnnotationString += String.format("@EasyController(\"/%s\")", tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;

//              protected DictionaryDataProvider(LazyLambdaStream lazyLambdaStream) {
//        super(lazyLambdaStream);
//    }


            classname = String.format("public class %s extends AbstractLazyCrudProvider<%s, Long>  {" +
                            NormalUsedString.NEWLINE +
                            String.format("    protected %s(LazyLambdaStream lazyLambdaStream) {\n" +
                                    "        super(lazyLambdaStream);\n" +
                                    "    }", controller) +
                            "}",
                    controller, tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;

            importClass.add("com.wu.framework.inner.layer.web.EasyController");
            importClass.add("com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider");
            importClass.add("com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream");

            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());

        } else {
            classname = String.format("public class %s  {" + NormalUsedString.NEWLINE + "}",
                    controller) + NormalUsedString.NEWLINE;
        }
        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(importString);
        classContext.append(generateClassDescribe(tableEndpoint));

        classContext.append(classAnnotationString);
        classContext.append(classname);

        try {
            BufferedWriter bufferedWriter = FileUtil.createFile(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "controller" + File.separator + controller);
            bufferedWriter.write(classContext.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建 Java的 Mapper
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.framework.inner.lazy.database.smart.database.persistence.
     */
    private static void createJavaMapper(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {
        final StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "mapper") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);


        List<String> importClass = new ArrayList<>();
        String classAnnotationString = NormalUsedString.NEWLINE;
        classAnnotationString += "@Mapper" + NormalUsedString.NEWLINE;

        String classname;
        String mapper = tableEndpoint.getClassName() + "Mapper";
        if (reverseEngineering.isEnableMyBatis()) {


            classname = String.format("public interface %s extends BaseMapper<%s> {" + NormalUsedString.NEWLINE + "}",
                    mapper, tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;

            importClass.add("org.apache.ibatis.annotations.Mapper");
            importClass.add("com.baomidou.mybatisplus.core.mapper.BaseMapper");
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());

        } else {
            classname = String.format("public interface %s  {" + NormalUsedString.NEWLINE + "}",
                    mapper) + NormalUsedString.NEWLINE;
        }
        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(importString);
        classContext.append(generateClassDescribe(tableEndpoint));
        classContext.append(classAnnotationString);
        classContext.append(classname);

        try {
            BufferedWriter bufferedWriter = FileUtil.createFile(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "mapper" + File.separator + mapper);
            bufferedWriter.write(classContext.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建 Java的service实现类
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.framework.inner.lazy.database.smart.database.persistence.
     */
    private static void createJavaServiceImpl(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {

        final StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "service.impl") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);

        String classAnnotationString = NormalUsedString.NEWLINE;
        classAnnotationString += "@Service" + NormalUsedString.NEWLINE;
        List<String> importClass = new ArrayList<>();
        String classname;
        String serviceImpl = tableEndpoint.getClassName() + "ServiceImpl";
        String service = tableEndpoint.getClassName() + "Service";
        if (reverseEngineering.isEnableMyBatis()) {
//            @Service
//public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {}

            String mapper = tableEndpoint.getClassName() + "Mapper";

            classname = String.format("public class %s extends  ServiceImpl<%s, %s> implements %s {" + NormalUsedString.NEWLINE + "}",
                    serviceImpl, mapper, tableEndpoint.getClassName(), service) + NormalUsedString.NEWLINE;

            importClass.add(Service.class.getName());
            importClass.add("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");

            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "service" + NormalUsedString.DOT + service);
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "mapper" + NormalUsedString.DOT + mapper);

        } else {
            classname = String.format("public class %s implements %s {" + NormalUsedString.NEWLINE + "}", serviceImpl, service) + NormalUsedString.NEWLINE;
        }

        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(importString);
        classContext.append(generateClassDescribe(tableEndpoint));

        classContext.append(classAnnotationString);
        classContext.append(classname);

        try {
            BufferedWriter bufferedWriter = FileUtil.createFile(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "service" + File.separator + "impl" + File.separator + serviceImpl);
            bufferedWriter.write(classContext.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建 Java的service
     *
     * @param tableEndpoint      ClassLazyTableEndpoint 参数
     * @param reverseEngineering 逆向工程参数
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.framework.inner.lazy.database.smart.database.persistence.
     */
    public static void createJavaService(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {
        final StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "service") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        classContext.append(classPackage);

        List<String> importClass = new ArrayList<>();
        String classname;
        String serviceName = String.format("%sService", tableEndpoint.getClassName());
        if (reverseEngineering.isEnableMyBatis()) {
            classname = String.format("public interface %s extends IService<%s> {" + NormalUsedString.NEWLINE + "}", serviceName, tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;
            importClass.add("com.baomidou.mybatisplus.extension.service.IService");
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());
        } else {
            classname = String.format("public interface %sService  {" + NormalUsedString.NEWLINE + "}", tableEndpoint.getClassName()) + NormalUsedString.NEWLINE;
        }

        String importString = importClass.stream().distinct().map(clazzName -> "import " + clazzName + NormalUsedString.SEMICOLON).collect(Collectors.joining(NormalUsedString.NEWLINE)) + NormalUsedString.NEWLINE;

        classContext.append(importString);

        classContext.append(generateClassDescribe(tableEndpoint));

        classContext.append(classname);

        try {
            BufferedWriter bufferedWriter = FileUtil.createFile(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "service" + File.separator + serviceName);
            bufferedWriter.write(classContext.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * describe 创建Java 实体 字符串
     *
     * @param tableEndpoint      tableEndpoint
     * @param reverseEngineering 逆行工程
     * @param resourceFilePrefix 文件地址前缀 如 com.wu.
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 4:43 下午
     **/
    public static String createJavaEntity(ClassLazyTableEndpoint tableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering, String resourceFilePrefix) {

        List<FieldLazyTableFieldEndpoint> fieldEndpoints = tableEndpoint.getFieldEndpoints();

        final StringBuilder classContext = new StringBuilder();

        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

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
        if (reverseEngineering.isEnableMyBatis()) {
            classAnnotationString += String.format("@TableName(value = \"%s\",schema = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getSchema()) +
                    NormalUsedString.NEWLINE;
            importClass.add("com.baomidou.mybatisplus.annotation.TableName");
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
                        apiModelPropertyAnnotation = String.format("    @ApiModelProperty(value =\"%s\",name =\"%s\",example = \"%s\")", fieldLazyTableFieldEndpoint.getComment(), fieldLazyTableFieldEndpoint.getName(), "") + NormalUsedString.NEWLINE;
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
        classContext.append(generateClassDescribe(tableEndpoint));
        classContext.append(classAnnotationString);
        classContext.append(classname);
        classContext.append(attributeFileList);
        classContext.append(NormalUsedString.NEWLINE + NormalUsedString.RIGHT_BRACE);

        try {
            BufferedWriter bufferedWriter = FileUtil.createFile(null, "", NormalUsedString.DOT_JAVA,
                    resourceFilePrefix + "entity" + File.separator + tableEndpoint.getClassName());
            bufferedWriter.write(classContext.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classContext.toString();
    }

    /**
     * description 生成类注释
     *
     * @param tableEndpoint 表结构数据
     * @return String 返回类描述信息
     * @date 2022/2/15 1:41 下午
     */
    public static String generateClassDescribe(ClassLazyTableEndpoint tableEndpoint) {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final int localDateTimeHour = localDateTime.getHour();
        String describe = String.format("/**\n" +
                        " * describe %s \n" +
                        " *\n" +
                        " * @author Jia wei Wu\n" +
                        " * @date %s %s\n" +
                        " **/", tableEndpoint.getComment(),
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")),
                localDateTimeHour < 8 ? "夜间" : localDateTimeHour < 12 ? "上午" : localDateTimeHour < 18 ? "下午" : "晚上");
        return describe;
    }

}
