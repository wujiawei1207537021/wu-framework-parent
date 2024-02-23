package com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;

import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultDDDLazyMapper extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyMapper(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        List<String> importClass = this.getImportClassNames();
        List<String> classAnnotationParts = this.getClassAnnotationParts();
        List<String> classNamePart = this.getClassNamePart();

        String mapper = tableEndpoint.getClassName() + "Mapper";
        setFileName(mapper);
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {

            classAnnotationParts.add("@Mapper");

            classNamePart.add(String.format("public interface %s extends BaseMapper<%s> {" + NormalUsedString.NEWLINE,
                    mapper, tableEndpoint.getClassName()) + NormalUsedString.NEWLINE);

            importClass.add("org.apache.ibatis.annotations.Mapper");
            importClass.add("com.baomidou.mybatisplus.core.mapper.BaseMapper");
            importClass.add(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());

        } else {
            classNamePart.add(String.format("public interface %s  {" + NormalUsedString.NEWLINE,
                    mapper) + NormalUsedString.NEWLINE);
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
        super.initClassAnnotationPart();
    }

    /**
     * description:  添加 class
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassNamePart() {
        super.initClassNamePart();
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
        return "mapper";
    }
}
