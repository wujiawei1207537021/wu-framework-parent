package com.wu.framework.inner.lazy.persistence.reverse;

import java.util.List;

/**
 * description 逆向工程class 注解接口
 *
 * @author 吴佳伟
 * @date 2023/02/14 13:19
 */
public interface JavaReverseEngineeringAnnotation extends JavaReverseEngineeringImport {

    /**
     * description:  添加 class 上的注解
     * class 上的注解如  @LazyTableFieldId(name = "%s", comment = "%s")
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void initClassAnnotationPart();

    /**
     * description:  添加 class 上的注解
     * class 上的注解如  @LazyTableFieldId(name = "%s", comment = "%s")
     *
     * @param classAnnotationPart 注解片段
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void addClassAnnotationPart(String classAnnotationPart);

    /**
     * description:  获取添加 class 上的注解
     *
     * @return
     * @author 吴佳伟
     * @date: 13.2.23 17:23
     */
    List<String> getClassAnnotationParts();
}
