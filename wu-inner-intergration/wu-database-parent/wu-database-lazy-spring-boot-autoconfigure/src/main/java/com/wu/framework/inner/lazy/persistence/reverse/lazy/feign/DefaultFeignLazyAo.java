package com.wu.framework.inner.lazy.persistence.reverse.lazy.feign;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultFeignLazyAo extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultFeignLazyAo(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        List<String> importClass = this.getImportClassNames();
        List<String> classAnnotation = getClassAnnotationParts();
        if (reverseEngineering.isEnableLombokData()) {
            classAnnotation.add("@Data");
            importClass.add(Data.class.getName());
        }
        if (reverseEngineering.isEnableLombokAccessors()) {
            classAnnotation.add("@Accessors(chain = true)");
            importClass.add("lombok.experimental.Accessors");
        }
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
            if (reverseEngineering.isEnableSchema()) {
                classAnnotation.add(String.format("@TableName(value = \"%s\",schema = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getSchema()));
            } else {
                classAnnotation.add(String.format("@TableName(value = \"%s\")", tableEndpoint.getTableName()));
            }

            importClass.add("com.baomidou.mybatisplus.annotation.TableName");
        }

        if (OrmArchitecture.LAZY.equals(reverseEngineering.getOrmArchitecture())
        ) {
            if (reverseEngineering.isEnableSchema()) {
                classAnnotation.add(String.format("@LazyAoTable(tableName = \"%s\")", tableEndpoint.getTableName()));
            } else {
                classAnnotation.add(String.format("@LazyAoTable(tableName = \"%s\")", tableEndpoint.getTableName()));
            }

            // 索引
            if (tableEndpoint.getOutLazyTableFieldEndpoints().stream().anyMatch(lazyTableFieldEndpoint -> ObjectUtils.isEmpty(lazyTableFieldEndpoint.getLazyTableIndexEndpoints()))) {
                importClass.add(LazyTableIndex.class.getName());
                importClass.add(LayerField.LayerFieldType.class.getName().replace(NormalUsedString.DOLLAR, NormalUsedString.DOT));
            }
            importClass.add("com.wu.framework.database.lazy.web.plus.stereotype.LazyAoTable");
            importClass.add(LazyTableField.class.getName());
        }
        if (reverseEngineering.isEnableSwagger()) {
            classAnnotation.add(String.format("@Schema(title = \"%s\",description = \"%s\")", tableEndpoint.getTableName(), tableEndpoint.getComment()));
            importClass.add("io.swagger.v3.oas.annotations.media.Schema");
            importClass.add("io.swagger.v3.oas.annotations.media.Schema");
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
        String className = tableEndpoint.getClassName();
        className += "Ao";
        setFileName(className);

        String classnamePart = String.format("public class %s {", className) + NormalUsedString.NEWLINE;
        addClassNamePart(classnamePart);
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
        super.initClassFieldPart();
    }

    /**
     * 获取当前class 对应的包名
     *
     * @return
     */
    @Override
    protected String getPackage() {
        return String.format("package %s;", this.getReverseClassLazyTableEndpoint().getPackageName() + NormalUsedString.DOT + "api.ao") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;
    }

    /**
     * 获取 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "api" + File.separator + "ao";
    }
}
