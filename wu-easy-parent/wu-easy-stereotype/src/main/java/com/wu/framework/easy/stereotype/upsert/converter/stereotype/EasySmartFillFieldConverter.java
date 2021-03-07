package com.wu.framework.easy.stereotype.upsert.converter.stereotype;

import com.wu.framework.easy.stereotype.upsert.enums.NormalUsedString;
import com.wu.framework.easy.stereotype.upsert.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :  智能填充数据
 * @date : 2021/3/3 11:18 下午
 */

public class EasySmartFillFieldConverter extends EasySmartFillFieldConverterAbstract implements IEasySmartConverter {


    // Java 基础数据类型
    public static final List<String> BASE_DATA_TYPE = Arrays.asList(
            String.class.getSimpleName(), String[].class.getSimpleName(),
            Integer.class.getSimpleName(), int.class.getSimpleName(), Integer[].class.getSimpleName(), int[].class.getSimpleName(),
            Double.class.getSimpleName(), double.class.getSimpleName(), Double[].class.getSimpleName(), double[].class.getSimpleName(),
            Float.class.getSimpleName(), float.class.getSimpleName(), Float[].class.getSimpleName(), float[].class.getSimpleName(),
            Long.class.getSimpleName(), long.class.getSimpleName(), Long[].class.getSimpleName(), long[].class.getSimpleName(),
            Boolean.class.getSimpleName(), boolean.class.getSimpleName(), Boolean[].class.getSimpleName(), boolean[].class.getSimpleName(),
            Byte.class.getSimpleName(), byte.class.getSimpleName(), byte[].class.getSimpleName(), Byte[].class.getSimpleName());

    /**
     * @param createInfo 创建信息
     * @return
     * @describe 目标类写入属性字段
     * @author Jia wei Wu
     * @date 2021/3/3 10:04 下午
     **/
    @Override
    public String targetClassWriteAttributeFieldList(CreateInfo createInfo) {
        URL resource = EasySmartFillFieldConverter.class.getResource("/");
        // 写入class文件
        String resourceFile = resource.getFile();
        // 工作空间存储
        Package aPackage = createInfo.getAPackage();
        String name = aPackage.getName();
        if (NormalUsedString.DOT_CLASS.equals(createInfo.getFileSuffix())) {
            resourceFile = resourceFile + name.replace(NormalUsedString.DOT, File.separator) + File.separator + createInfo.getClassName();
        } else {
            String target = resourceFile.split("target")[0] + "src/main/java/";

            resourceFile = target + name.replace(NormalUsedString.DOT, File.separator) + File.separator + createInfo.getClassName();
        }
        String classContext = createInfo2String(createInfo);
        try {
            BufferedWriter bufferedWriter = FileUtil.createFile(null, "", createInfo.getFileSuffix(), resourceFile);
            bufferedWriter.write(classContext);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return classContext;
        }
    }

    /**
     * @param createInfo
     * @return
     * @describe 将CreateInfo 转换成String
     * @author Jia wei Wu
     * @date 2021/3/5 8:51 下午
     **/
    public String createInfo2String(CreateInfo createInfo) {
        List<CreateField> createFieldList = createInfo.getCreateFieldList();
        String classPackage = String.format("package %s;", createInfo.getAPackage().getName()) + NormalUsedString.NEWLINE;
        List<String> importClassList = createFieldList.stream().filter(createField -> !BASE_DATA_TYPE.contains(createField.getFieldTypeName())).map(createField -> String.format("import %s; " + NormalUsedString.NEWLINE, createField.getFieldTypeName())).distinct().collect(Collectors.toList());
        String classname = String.format("public  class %s {", createInfo.getClassName()) + NormalUsedString.NEWLINE;
        List<String> attributeFileList = createFieldList.stream().map(createField -> String.format("private %s %s;", createField.getFieldTypeName(), createField.getFieldName())).collect(Collectors.toList());
        // innerClass
        String classContext = classPackage + String.join(NormalUsedString.NEWLINE, importClassList) + NormalUsedString.NEWLINE + classname + String.join(NormalUsedString.NEWLINE, attributeFileList);

        for (CreateInfo innerClass : createInfo.getInnerClassList()) {
            classContext += innerClass2String(innerClass);
        }
        classContext += NormalUsedString.NEWLINE+NormalUsedString.RIGHT_BRACE;
        return classContext;
    }

    public String innerClass2String(CreateInfo innerClass) {
        List<CreateField> createFieldList = innerClass.getCreateFieldList();
        String classname = String.format(NormalUsedString.NEWLINE + "public static class %s {", innerClass.getClassName());
        List<String> attributeFileList = createFieldList.stream().map(createField -> String.format("private %s %s;", createField.getFieldTypeName(), createField.getFieldName())).collect(Collectors.toList());
        String first = classname + NormalUsedString.NEWLINE + String.join(NormalUsedString.NEWLINE, attributeFileList) + NormalUsedString.NEWLINE + NormalUsedString.RIGHT_BRACE;
        for (CreateInfo createInfo : innerClass.getInnerClassList()) {
            first += innerClass2String(createInfo);
        }
        return first;
    }

}
