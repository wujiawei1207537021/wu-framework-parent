package com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;

import java.io.File;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultDDDLazyInfrastructureMapper extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyInfrastructureMapper(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
            addImportClassName("com.baomidou.mybatisplus.core.mapper.BaseMapper");
            addImportClassName("org.apache.ibatis.annotations.Mapper");
            addImportClassName(tableEndpoint.getPackageName() + NormalUsedString.DOT + "infrastructure.entity" + NormalUsedString.DOT + tableEndpoint.getClassName() + "DO");
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
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
            addClassAnnotationPart("@Mapper");
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
        String mapperName = String.format("%sMapper", tableEndpoint.getClassName());
        setFileName(mapperName);

        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
            addClassNamePart(String.format("public interface %s extends BaseMapper<%sDO> {" + NormalUsedString.NEWLINE, mapperName, tableEndpoint.getClassName()) + NormalUsedString.NEWLINE);
        } else {
            addClassNamePart(String.format("public interface %s {" + NormalUsedString.NEWLINE, mapperName) + NormalUsedString.NEWLINE);
        }


    }

    @Override
    protected String getPackage() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        return String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "infrastructure" + NormalUsedString.DOT + "mapper") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
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

    /**
     * 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "infrastructure" + File.separator + "mapper";
    }

}
