package com.wu.framework.inner.lazy.persistence.reverse;

import java.util.ArrayList;
import java.util.List;

/**
 * description 记录class的导入
 *
 * @author 吴佳伟
 * @date 2023/02/13 17:24
 */
public abstract class AbstractJavaReverseEngineeringImport extends AbstractJavaReverseEngineering implements JavaReverseEngineeringImport {
    // 导入的className
    private List<String> importClassNames = new ArrayList<>();

    /**
     * description:  添加导入的class
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initImportClassName() {

    }

    /**
     * @param importClassName 导入的class名称 如：org.mapstruct.Mapper
     */
    @Override
    public void addImportClassName(String importClassName) {
        if (importClassNames.contains(importClassName)) {
            return;
        }
        importClassNames.add(importClassName);
    }

    /**
     * description:  获取导入的class
     *
     * @return
     * @author 吴佳伟
     * @date: 13.2.23 17:23
     */
    @Override
    public List<String> getImportClassNames() {
        return importClassNames;
    }
}
