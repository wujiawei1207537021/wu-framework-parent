package com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultDDDLazyMapperXml extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyMapperXml(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {

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
    }

    /**
     * description:  添加 class
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassNamePart() {

    }

    @Override
    public String createJavaContextCode() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();

        String className = tableEndpoint.getClassName();
        String packageName = tableEndpoint.getPackageName();
        String mapper = tableEndpoint.getClassName() + "Mapper";
        setFileName(mapper);
        //   <?xml version="1.0" encoding="UTF-8"?>
        //<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
        //<mapper namespace="com.xx.dao">
        //      <resultMap id="BaseResultMap" type="com.xx.entity">
        //          <id column="id" property="id" />
        //          <result column="code" property="code" />
        //      </resultMap>
        // </mapper>
        StringBuilder classContext = new StringBuilder();
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append(String.format("<mapper namespace=\"%s\">", packageName + NormalUsedString.DOT + "mapper" + NormalUsedString.DOT + mapper));
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append(String.format("<resultMap id=\"BaseResultMap\" type=\"%s\">",
                packageName + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + className));

        for (LazyTableFieldEndpoint fieldEndpoint : tableEndpoint.getOutLazyTableFieldEndpoints()) {
            classContext.append(NormalUsedString.NEWLINE);
            String name = fieldEndpoint.getName();
            String columnName = fieldEndpoint.getColumnName();
            if ("id".equals(name)) {
                classContext.append("<id column=\"id\" property=\"id\" />");
            } else {
                classContext.append(String.format("<result column=\"%s\" property=\"%s\" />", columnName, name));
            }
        }
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append("</resultMap>");
        classContext.append(NormalUsedString.NEWLINE);
        classContext.append("</mapper>");
        return classContext.toString();

    }

    @Override
    protected String getPackage() {
        return NormalUsedString.EMPTY;
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

    }

    @Override
    public String generateClassDescribe(String comment) {
        return NormalUsedString.EMPTY;
    }

    /**
     * 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "xml";
    }

    /**
     * 获取文件后缀
     *
     * @return
     */
    @Override
    protected String getFileSuffix() {
        return ".xml";
    }
}
