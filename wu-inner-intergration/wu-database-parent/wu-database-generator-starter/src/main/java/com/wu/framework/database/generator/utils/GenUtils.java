package com.wu.framework.database.generator.utils;

import com.wu.framework.database.generator.config.GeneratorConfig;
import com.wu.framework.database.generator.config.GeneratorEnums;
import com.wu.framework.database.generator.entity.ColumnEntity;
import com.wu.framework.database.generator.entity.EncapsulatedTableInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GenUtils {

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
//        templates.add("template/Entity.java.vm");
//        templates.add("template/Dao.java.vm");
//        templates.add("template/Dao.xml.vm");
//        templates.add("template/Service.java.vm");
//        templates.add("template/ServiceImpl.java.vm");
//        templates.add("template/Controller.java.vm");
//        templates.add("template/menu.sql.vm");
//
//        templates.add("template/index.vue.vm");
//        templates.add("template/add-or-update.vue.vm");

        //使用自定义模板
        templates.add("template/rzx/Entity.java.vm");
        templates.add("template/rzx/Dao.java.vm");
        templates.add("template/rzx/Dao.xml.vm");
        templates.add("template/rzx/Service.java.vm");
        templates.add("template/rzx/Controller.java.vm");

        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(GeneratorConfig generatorConfig, EncapsulatedTableInfo encapsulatedTableInfo, ZipOutputStream zip) {

        boolean hasBigDecimal = false;
        //表信息

        //表名转换成Java类名
        String className = tableToJava(encapsulatedTableInfo.getTableEntity().getTableName(), encapsulatedTableInfo.getTablePrefix());
        encapsulatedTableInfo.getTableEntity().setClassName(className);
        encapsulatedTableInfo.getTableEntity().setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columnList = new ArrayList<>();
        for (ColumnEntity columnEntity : encapsulatedTableInfo.getColumnEntityList()) {
            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
            if (null == encapsulatedTableInfo.getARecord()) {
                columnEntity.setVal(columnEntity.getColumnComment());
            } else {
                Object v = encapsulatedTableInfo.getARecord().get(columnEntity.getColumnName());
                if (null == v) {
                    columnEntity.setVal(columnEntity.getColumnComment());
                } else {
                    columnEntity.setVal(v.toString());
                }

            }
            //列的数据类型，转换成Java类型
            Class typeClazz = GeneratorEnums.typeClazz(columnEntity.getDataType());
            String attrType = typeClazz.getSimpleName();
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(columnEntity.getColumnKey()) && encapsulatedTableInfo.getTableEntity().getPk() == null) {
                encapsulatedTableInfo.getTableEntity().setPk(columnEntity);
            }
            columnList.add(columnEntity);
        }
        encapsulatedTableInfo.getTableEntity().setColumns(columnList);

        //没主键，则第一个字段为主键
        if (encapsulatedTableInfo.getTableEntity().getPk() == null) {
            encapsulatedTableInfo.getTableEntity().setPk(encapsulatedTableInfo.getTableEntity().getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        String mainPath = generatorConfig.getMainPath();
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", encapsulatedTableInfo.getTableEntity().getTableName());
        map.put("comments", encapsulatedTableInfo.getTableEntity().getComments());
        map.put("pk", encapsulatedTableInfo.getTableEntity().getPk());
        map.put("className", encapsulatedTableInfo.getTableEntity().getClassName());
        map.put("classname", encapsulatedTableInfo.getTableEntity().getClassname());
        map.put("pathName", encapsulatedTableInfo.getTableEntity().getClassname().toLowerCase());
        map.put("columns", encapsulatedTableInfo.getTableEntity().getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package", generatorConfig.getPackageName());
        map.put("moduleName", generatorConfig.getModuleName());
        map.put("author", generatorConfig.getAuthor());
        map.put("email", generatorConfig.getEmail());
        map.put("datetime", generatorConfig.getDateTime());
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(template, encapsulatedTableInfo.getTableEntity().getClassName(), encapsulatedTableInfo.getPackageName(), encapsulatedTableInfo.getModuleName()))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + encapsulatedTableInfo.getTableEntity().getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if ("".equals(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return columnToJava(tableName);
    }


    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if ("".equals(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath + "repository" + File.separator + className + "Dao.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Dao.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }

        if (template.contains("index.vue.vm")) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "public/views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + ".vue";
        }

        if (template.contains("add-or-update.vue.vm")) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "public/views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + "-add-or-update.vue";
        }

        return null;
    }
}
