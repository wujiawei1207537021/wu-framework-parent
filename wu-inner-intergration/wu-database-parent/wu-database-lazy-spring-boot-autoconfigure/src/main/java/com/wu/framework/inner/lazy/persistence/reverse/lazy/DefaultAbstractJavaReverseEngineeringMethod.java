package com.wu.framework.inner.lazy.persistence.reverse.lazy;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaKeyword;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.reverse.AbstractJavaReverseEngineeringMethod;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DefaultAbstractJavaReverseEngineeringMethod extends AbstractJavaReverseEngineeringMethod {


    private final ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint;
    private final LazyOperationConfig.ReverseEngineering reverseEngineering;

    protected DefaultAbstractJavaReverseEngineeringMethod(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        this.reverseClassLazyTableEndpoint = reverseClassLazyTableEndpoint;
        this.reverseEngineering = reverseEngineering;
    }

    /**
     * 获取当前class 对应的包名
     *
     * @return
     */
    protected String getPackage() {
        return String.format("package %s;", this.getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + getModuleType()) + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
    }

    /**
     * 驼峰转换多层文件夹
     *
     * @return SysUser ---> sys.user
     */
    protected String humpToDOT() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String domain = tableEndpoint.getClassName();
        // 驼峰转换成list
        List<String> domainNames = CamelAndUnderLineConverter.humpToArray(domain);
        return domainNames.stream().map(domainName -> {
            if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                return domainName + NormalUsedString.UNDERSCORE;
            }
            return domainName;
        }).collect(Collectors.joining(NormalUsedString.DOT));
    }

    /**
     * 驼峰转换多层文件夹
     *
     * @return SysUser ---> sys/user
     */
    protected String humpToSeparator() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String domain = tableEndpoint.getClassName();
        // 驼峰转换成list
        List<String> domainNames = CamelAndUnderLineConverter.humpToArray(domain);
        return domainNames.stream().map(domainName -> {
            if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(domainName))) {
                return domainName + NormalUsedString.UNDERSCORE;
            }
            return domainName;
        }).collect(Collectors.joining(File.separator));
    }

    /**
     * 创建 class 上下文代码
     *
     * @return
     */
    public String createJavaContextCode() {
        //  添加 import class
        initImportClassName();
        //  添加 class 注解代码片段
        initClassAnnotationPart();

        //  添加 class name、描述
        initClassNamePart();

        // 添加 字段片段
        initClassFieldPart();

        // 添加 方法片段
        initClassMethodPart();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getPackage());

        // 拼接代码
        List<String> importClassName = getImportClassNames();

        // import class
        importClassName.forEach(s -> stringBuilder.append("import ").append(s).append(NormalUsedString.SEMICOLON).append(NormalUsedString.NEWLINE));

        // 注释
        String classDescribe = generateClassDescribe(ObjectUtils.isEmpty(reverseClassLazyTableEndpoint.getComment()) ? reverseClassLazyTableEndpoint.getTableName() : reverseClassLazyTableEndpoint.getComment());
        stringBuilder.append(classDescribe);
        // 注解
        List<String> classAnnotationPart = getClassAnnotationParts();
        stringBuilder.append(String.join(NormalUsedString.NEWLINE, classAnnotationPart));
        stringBuilder.append(NormalUsedString.NEWLINE);
        // class
        List<String> classNamePart = getClassNamePart();
        stringBuilder.append(String.join(NormalUsedString.SEMICOLON + NormalUsedString.NEWLINE, classNamePart));

        // 字段
        List<String> classFieldPart = getClassFieldPart();
        stringBuilder.append(String.join(NormalUsedString.NEWLINE, classFieldPart));

        // 方法
        List<String> classMethodPart = getClassMethodPart();
        stringBuilder.append(NormalUsedString.NEWLINE);
        stringBuilder.append(String.join(NormalUsedString.NEWLINE, classMethodPart));

        stringBuilder.append(NormalUsedString.NEWLINE);
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    /**
     * 创建Java文件
     *
     * @param resourceFilePrefix
     * @return
     */
    @Override
    public String createJavaFile(String resourceFilePrefix) {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String contextCode = createJavaContextCode();
        try {
            BufferedWriter bufferedWriter = FileUtil.createFileBufferedWriter(null, getFilePrefix(), getFileSuffix(),
                    resourceFilePrefix + getModuleType() + File.separator + getFileName());
            bufferedWriter.write(contextCode);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contextCode;
    }

    /**
     * 获取 文件module type
     *
     * @return
     */
    abstract protected String getModuleType();

    /**
     * 获取文件前缀
     *
     * @return
     */
    protected String getFilePrefix() {
        return NormalUsedString.EMPTY;
    }

    /**
     * 获取文件后缀
     *
     * @return
     */
    protected String getFileSuffix() {
        return NormalUsedString.DOT_JAVA;
    }

    @Override
    public ReverseClassLazyTableEndpoint getReverseClassLazyTableEndpoint() {
        return reverseClassLazyTableEndpoint;
    }

    /**
     * 当前 表转换成驼峰后 首字母小写
     *
     * @return
     */
    public String lowercaseFirstLetterTableName() {
        String className = getReverseClassLazyTableEndpoint().getClassName();
        final String lowercaseFirstLetter = CamelAndUnderLineConverter.lowercaseFirstLetter(className);
        String methodParamName = lowercaseFirstLetter;
        if (Arrays.stream(JavaKeyword.values()).anyMatch(javaKeyword -> javaKeyword.name().toLowerCase().equals(lowercaseFirstLetter))) {
            methodParamName = methodParamName + NormalUsedString.UNDERSCORE;
        }
        return methodParamName;
    }

    @Override
    public LazyOperationConfig.ReverseEngineering getReverseEngineering() {
        return reverseEngineering;
    }
}
