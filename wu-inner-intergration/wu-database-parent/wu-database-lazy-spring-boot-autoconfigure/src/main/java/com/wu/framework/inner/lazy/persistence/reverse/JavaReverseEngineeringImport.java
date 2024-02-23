package com.wu.framework.inner.lazy.persistence.reverse;

import java.util.List;

/**
 * description 逆向工程 顶级接口 记录class的导入
 *
 * @author 吴佳伟
 * @date 2023/02/13 17:10
 */
public interface JavaReverseEngineeringImport extends JavaReverseEngineering {

    /**
     * description:  添加导入的class
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void initImportClassName();

    /**
     * description:  添加导入的class
     *
     * @param importClassName 导入的class名称 如：org.mapstruct.Mapper
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void addImportClassName(String importClassName);


    /**
     * description:  获取导入的class
     *
     * @return
     * @author 吴佳伟
     * @date: 13.2.23 17:23
     */
    List<String> getImportClassNames();
}
