package com.wu.framework.easy.stereotype.upsert.converter.stereotype;

import com.wu.framework.easy.stereotype.upsert.enums.NormalUsedString;
import com.wu.framework.easy.stereotype.upsert.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :  智能填充数据
 * @date : 2021/3/3 11:18 下午
 */

public class EasySmartFillFieldConverter extends EasySmartFillFieldConverterAbstract implements IEasySmartConverter {
    /**
     * @param createFieldList 创建的字段
     * @param targetClass     目标类
     * @return
     * @describe 目标类写入属性字段
     * @author Jia wei Wu
     * @date 2021/3/3 10:04 下午
     **/
    @Override
    public void targetClassWriteAttributeFieldList(List<CreateField> createFieldList, Class targetClass) {

        URL resource = targetClass.getResource("/");
        List<String> importClassList = createFieldList.stream().map(createField -> String.format("import %s; ", createField.getFieldType().getName())).distinct().collect(Collectors.toList());
        List<String> attributeFileList = createFieldList.stream().map(createField -> String.format("private %s %s;", createField.getFieldType().getSimpleName(), createField.getFieldName())).collect(Collectors.toList());
        // 写入class文件
        String resourceFile = resource.getFile()+targetClass.getSimpleName();
        // 工作空间存储
        {
             String target = resourceFile.split("target")[0]+"src/main/java/";
            final String name = targetClass.getPackage().getName();
            resourceFile = target+name.replace(NormalUsedString.DOT, File.separator)+File.separator+targetClass.getSimpleName();
        }
        try {
            BufferedWriter bufferedWriter = FileUtil.createFile(null,"",NormalUsedString.DOT_JAVA, resourceFile);
            bufferedWriter.write(String.format("package %s;", targetClass.getPackage().getName()));
            bufferedWriter.newLine();
            for (String importClass : importClassList) {
                bufferedWriter.newLine();
                bufferedWriter.write(importClass);
            }
            bufferedWriter.newLine();
            bufferedWriter.write(String.format("public  class %s {", targetClass.getSimpleName()));
            bufferedWriter.newLine();
            for (String attributeFile : attributeFileList) {
                bufferedWriter.newLine();
                bufferedWriter.write(attributeFile);
            }
            bufferedWriter.newLine();
            bufferedWriter.write(NormalUsedString.RIGHT_BRACE);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
