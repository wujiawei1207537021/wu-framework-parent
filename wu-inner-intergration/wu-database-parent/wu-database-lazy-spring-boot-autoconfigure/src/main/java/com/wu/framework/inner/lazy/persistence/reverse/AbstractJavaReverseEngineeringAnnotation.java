package com.wu.framework.inner.lazy.persistence.reverse;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/14 13:25
 */
public abstract class AbstractJavaReverseEngineeringAnnotation extends AbstractJavaReverseEngineeringImport implements JavaReverseEngineeringAnnotation {
    // class 上的注解如  @LazyTableFieldId(name = "%s", comment = "%s")
    private List<String> classAnnotationParts = new ArrayList<>();

    /**
     * description:  添加 class 上的注解
     * class 上的注解如  @LazyTableFieldId(name = "%s", comment = "%s")
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassAnnotationPart() {
        // 添加注解代码

        // 添加import class 代码

    }

    /**
     * description:  添加 class 上的注解
     * class 上的注解如  @LazyTableFieldId(name = "%s", comment = "%s")
     *
     * @param classAnnotationPart 注解片段
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void addClassAnnotationPart(String classAnnotationPart) {
        this.classAnnotationParts.add(classAnnotationPart);
    }

    /**
     * description:  获取添加 class 上的注解
     *
     * @return
     * @author 吴佳伟
     * @date: 13.2.23 17:23
     */
    @Override
    public List<String> getClassAnnotationParts() {
        return classAnnotationParts;
    }
}
