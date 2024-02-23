package com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.DefaultAbstractJavaReverseEngineeringMethod;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 14:37
 */
public class DefaultDDDLazyServiceImpl extends DefaultAbstractJavaReverseEngineeringMethod {


    public DefaultDDDLazyServiceImpl(ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint, LazyOperationConfig.ReverseEngineering reverseEngineering) {
        super(reverseClassLazyTableEndpoint, reverseEngineering);
    }

    @Override
    public void initImportClassName() {
        addImportClassName("org.springframework.stereotype.Service");
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
//            @Service
//public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {}

            String mapper = tableEndpoint.getClassName() + "Mapper";
            String service = tableEndpoint.getClassName() + "Service";

            addImportClassName(Service.class.getName());
            addImportClassName("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");

            addImportClassName(tableEndpoint.getPackageName() + NormalUsedString.DOT + "entity" + NormalUsedString.DOT + tableEndpoint.getClassName());
            addImportClassName(tableEndpoint.getPackageName() + NormalUsedString.DOT + "service" + NormalUsedString.DOT + service);
            addImportClassName(tableEndpoint.getPackageName() + NormalUsedString.DOT + "mapper" + NormalUsedString.DOT + mapper);

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
        addClassAnnotationPart("@Service");

        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();

        String service = tableEndpoint.getClassName() + "Service";

        addImportClassName(tableEndpoint.getPackageName() + NormalUsedString.DOT + "service." + service);


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
        LazyOperationConfig.ReverseEngineering reverseEngineering = this.getReverseEngineering();
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();

        String classname;
        String serviceImpl = tableEndpoint.getClassName() + "ServiceImpl";
        setFileName(serviceImpl);
        String service = tableEndpoint.getClassName() + "Service";


        if (OrmArchitecture.MYBATIS.equals(reverseEngineering.getOrmArchitecture())
        ) {
//            @Service
//public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {}

            String mapper = tableEndpoint.getClassName() + "Mapper";

            classname = String.format("public class %s extends  ServiceImpl<%s, %s> implements %s {" + NormalUsedString.NEWLINE,
                    serviceImpl, mapper, tableEndpoint.getClassName(), service) + NormalUsedString.NEWLINE;

        } else {
            classname = String.format("public class %s implements %s {" + NormalUsedString.NEWLINE, serviceImpl, service) + NormalUsedString.NEWLINE;
        }


        super.initClassNamePart();
        addClassNamePart(classname);
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
    protected String getPackage() {
        ReverseClassLazyTableEndpoint tableEndpoint = this.getReverseClassLazyTableEndpoint();
        String classPackage = String.format("package %s;", tableEndpoint.getPackageName() + NormalUsedString.DOT + "service.impl") + NormalUsedString.NEWLINE + NormalUsedString.NEWLINE;

        return classPackage;
    }

    /**
     * 获取 文件module type
     *
     * @return
     */
    @Override
    protected String getModuleType() {
        return "service" + File.separator + "impl";
    }
}
