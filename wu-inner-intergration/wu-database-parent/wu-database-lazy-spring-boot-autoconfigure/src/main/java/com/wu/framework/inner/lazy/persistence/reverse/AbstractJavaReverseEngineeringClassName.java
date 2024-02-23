package com.wu.framework.inner.lazy.persistence.reverse;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/13 17:24
 */
public abstract class AbstractJavaReverseEngineeringClassName extends AbstractJavaReverseEngineeringAnnotation implements JavaReverseEngineeringClassName {
    // 导入的className
    private List<String> classNameParts = new ArrayList<>();

    private String fileName;

    /**
     * description:  添加 class
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassNamePart() {
    }

    /**
     * description:  添加 class
     *
     * @param classNamePart
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void addClassNamePart(String classNamePart) {
        this.classNameParts.add(classNamePart);
    }

    @Override
    public List<String> getClassNamePart() {
        return classNameParts;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
