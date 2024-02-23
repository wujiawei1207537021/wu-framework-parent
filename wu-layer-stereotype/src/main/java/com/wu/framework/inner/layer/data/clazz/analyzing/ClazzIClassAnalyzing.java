package com.wu.framework.inner.layer.data.clazz.analyzing;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.clazz.ClassType;
import com.wu.framework.inner.layer.data.clazz.LazyClass;
import com.wu.framework.inner.layer.data.clazz.LazyField;
import com.wu.framework.inner.layer.util.FileUtil;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description class 解析
 *
 * @author 吴佳伟
 * @date 2023/10/10 16:07
 */
public final class ClazzIClassAnalyzing extends AbstractIClassAnalyzing implements IClassAnalyzing {

    // Java 关键字
    private final List<String> java_key = List.of("public", "private", "final", "abstract", "class", "@interface", "interface");

    private final FieldIClassAnalyzing fieldIClassAnalyzing = new FieldIClassAnalyzing();

    /**
     * 是否支持当前文本转换
     *
     * @param classText class 内容文本
     * @return
     */
    @ /**/ Override
    public boolean support(String classText) {
        return true;
    }

    /**
     * @param clazz class 内容文本
     * @return 解析class文件获取的class
     */
    @Override
    public LazyClass getLazyClass(Class<?> clazz) {
        LazyClass lazyClass = new LazyClass();
        // 获取包名
        String packageName = clazz.getPackageName();
        // 获取class 文件地址
        String readLocalSrcMainClassPath = FileUtil.readLocalSrcMainClassPath(clazz);
        // 是否是public
        boolean primitive = clazz.isPrimitive();
        // class 完整名称
        String name = clazz.getName();

        // 获取class 类型
        ClassType classType = getClassType(clazz);
        // 获取class 行数
        Map<Integer, String> lineContent = FileUtil.readFileLineContent(readLocalSrcMainClassPath);
        int totalLineNum = lineContent.size();

        // 解析import class
        List<String> importClasses = getImportClasses(lineContent);

        // 解析class 上的注解
        List<String> annotations = getClassAnnotation(lineContent);

        // 解析class 头
        String classHeader = getClassHeader(lineContent);
        // 解析字段
        List<LazyField> lazyFields = fieldIClassAnalyzing.getFieldList(lineContent);


        lazyClass.setPackageName(packageName);
        lazyClass.setReadLocalSrcMainClassPath(readLocalSrcMainClassPath);
        lazyClass.setPublic(!primitive);
        lazyClass.setName(name);
        lazyClass.setClassType(classType);
        lazyClass.setTotalLineNum(totalLineNum);
        lazyClass.setLineContent(lineContent);
        lazyClass.setImportClasses(importClasses);
        lazyClass.setAnnotations(annotations);
        lazyClass.setClassHeader(classHeader);
        lazyClass.setFields(lazyFields);


        return lazyClass;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 解析class 头
     *
     * @param lineContent 行数、行内容
     * @return class 头  <p>public class LazyClass {</p>
     */
    private String getClassHeader(Map<Integer, String> lineContent) {

        // 命中 public final  class xx{

        AtomicBoolean hit = new AtomicBoolean();
        int hitCodeLineIndex = 0;
        AtomicInteger hitLineNum = new AtomicInteger();


        StringBuilder classHeader = new StringBuilder();
        for (Map.Entry<Integer, String> integerStringEntry : lineContent.entrySet()) {
            Integer line = integerStringEntry.getKey();
            String content = integerStringEntry.getValue();
            String[] contentSplit = content.split(NormalUsedString.SPACE);
            final AtomicBoolean[] jump = {new AtomicBoolean(false)};
            // 命中 class 而后以{ 结尾
            for (int i = 0; i < contentSplit.length; i++) {
                String code = contentSplit[i];
                if (ObjectUtils.isEmpty(code)) {
                    continue;
                }
                if (code.startsWith("//")) {
                    continue;
                }
                // 命中注释
                if (code.equals("/*") || code.startsWith(NormalUsedString.ASTERISK)) {
                    jump[0].set(true);
                    continue;
                }
                // 命中注释结束
                if (jump[0].get() && code.equals("*/")) {
                    jump[0].set(false);
                    continue;
                }
                if (jump[0].get()) {
                    continue;
                }
                if (code.equals("class") || code.equals("@interface") || code.equals("interface")) {
                    hitLineNum.set(line);
                    hitCodeLineIndex = i;
                    hit.set(true);
                }

                if (hit.get()) {
                    if (code.endsWith(NormalUsedString.LEFT_BRACE)) {
                        // 从命中到class 的一行开始 到左大括号结束
                        for (int j = hitLineNum.get(); j < line; j++) {
                            String hitLineCode = lineContent.get(i);
                            classHeader.append(hitLineCode);
                            classHeader.append(NormalUsedString.SPACE);
                        }
                        // 最后一行
                        String lastLine = lineContent.get(hitLineNum.get());
                        String[] split = lastLine.split(NormalUsedString.SPACE);
                        for (int i1 = hitCodeLineIndex; i1 <= i; i1++) {
                            classHeader.append(split[i1]);
                            classHeader.append(NormalUsedString.SPACE);
                        }
                        return classHeader.toString();
                    }
                } else {
                    if (java_key.contains(code)) {
                        classHeader.append(code);
                        classHeader.append(NormalUsedString.SPACE);
                        continue;
                    }
                    // 命中到非关键字 清空
                    classHeader = new StringBuilder();
                }


            }


        }
        throw new IllegalArgumentException("无法获取class 头部信息");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取class 类型
     *
     * @param clazz class
     * @return class 类型
     */
    protected ClassType getClassType(Class<?> clazz) {
        boolean anEnum = clazz.isEnum();
        boolean annotation = clazz.isAnnotation();
        boolean anInterface = clazz.isInterface();
        if (anEnum) {
            return ClassType.ENUM;
        }
        if (annotation) {
            return ClassType.ANNOTATION;
        }
        if (anInterface) {
            return ClassType.INTERFACE;
        }
        return ClassType.CLASS;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取class上的注解
     *
     * @param lineContent 行数、行内容
     * @return class上的注解
     **/
    private List<String> getClassAnnotation(Map<Integer/*行数*/, String/*内容*/> lineContent) {
        List<String> annotationList = new ArrayList<>();

        lineContent.forEach((line, content) -> {
            List<String> lineAnnotations = getLineAnnotations(content);
            annotationList.addAll(lineAnnotations);
        });
        return annotationList;
    }

    /**
     * 通过行代码解析出 class 上的注解
     *
     * @param lineContent 行代码
     * @return class 上的注解
     */
    private List<String> getLineAnnotations(String lineContent) {
        List<String> annotationList = new ArrayList<>();
        String[] contentSplit = lineContent.split(NormalUsedString.SPACE);
        //  遇到@ 开始 遇到 public、private、protect、class 结束
        int hitNum = 0;
        int startIndex = 0;
        boolean jump = false; // 是否 跳过
        for (int i = 0; i < contentSplit.length; i++) {

            String content = contentSplit[i];
            if (ObjectUtils.isEmpty(content)) {
                continue;
            }
            if (content.startsWith("//")) {
                continue;
            }
            // 命中注释
            if (content.equals("/*") || content.startsWith(NormalUsedString.ASTERISK)) {
                jump = true;
                continue;
            }
            // 命中注释结束
            if (jump && content.equals("*/")) {
                jump = false;
                continue;
            }

            if (jump) {
                continue;
            }
            if (content.startsWith("@") && i == contentSplit.length - 1) {
                // 这就是一个注解
                annotationList.add(content);
            }
            if (content.startsWith("@")) {
                hitNum = 1;
                startIndex = i;
                continue;
            }
            if (hitNum == 1 &&
                    (content.startsWith(NormalUsedString.PUBLIC)
                            || content.startsWith(NormalUsedString.PRIVATE)
                            || content.startsWith(NormalUsedString.CLASS)
                            || content.startsWith(NormalUsedString.PROTECTED)
                    )
            ) {
                hitNum = 0;
                StringBuilder hitLineCode = new StringBuilder();
                // 命中数据
                for (int hitIndex = startIndex; hitIndex <= i; hitIndex++) {
                    String hitLineCodePart = contentSplit[hitIndex];
                    hitLineCode.append(hitLineCodePart);
                    hitLineCode.append(NormalUsedString.SPACE);
                }
                annotationList.add(hitLineCode.toString());
            }
        }
        return annotationList;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取class中import class
     *
     * @param lineContent 行数、行内容
     * @return class中import class
     */
    private List<String> getImportClasses(Map<Integer/*行数*/, String/*内容*/> lineContent) {
        List<String> importClasses = new ArrayList<>();
        lineContent.forEach((line, content) -> {
            List<String> importClasses1 = getLineImportClasses(content);
            importClasses.addAll(importClasses1);

        });
        return importClasses;
    }

    /**
     * 通过行代码解析出import class
     *
     * @param lineContent 行代码
     * @return import class
     */
    private List<String> getLineImportClasses(String lineContent) {
        List<String> importClasses = new ArrayList<>();
        String[] contentSplit = lineContent.split(NormalUsedString.SPACE);
        // 代码命中两次作为一个轮询
        int hitNum = 0;
        int startIndex = 0;
        boolean jump = false; // 是否 跳过
        for (int i = 0; i < contentSplit.length; i++) {

            String content = contentSplit[i];
            if (ObjectUtils.isEmpty(content)) {
                continue;
            }
            if (content.startsWith("//") || content.startsWith(NormalUsedString.ASTERISK)) {
                continue;
            }
            // 命中注释
            if (content.equals("/*")) {
                jump = true;
                continue;
            }
            // 命中注释结束
            if (jump && content.equals("*/")) {
                jump = false;
                continue;
            }

            if (jump) {
                continue;
            }
            if (content.equals("import")) {
                hitNum = 1;
                startIndex = i;
                continue;
            }
            if (hitNum == 1 && content.contains(NormalUsedString.SEMICOLON)) {
                hitNum = 0;
                StringBuilder hitLineCode = new StringBuilder();
                // 命中数据
                for (int hitIndex = startIndex; hitIndex <= i; hitIndex++) {
                    String hitLineCodePart = contentSplit[hitIndex];
                    hitLineCode.append(hitLineCodePart);
                    hitLineCode.append(NormalUsedString.SPACE);
                }
                importClasses.add(hitLineCode.toString());
            }
        }
        return importClasses;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
