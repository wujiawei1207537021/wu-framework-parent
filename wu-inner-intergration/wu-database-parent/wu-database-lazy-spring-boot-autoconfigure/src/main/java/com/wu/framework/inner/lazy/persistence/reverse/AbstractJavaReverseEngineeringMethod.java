package com.wu.framework.inner.lazy.persistence.reverse;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/02/13 17:24
 */
public abstract class AbstractJavaReverseEngineeringMethod extends AbstractJavaReverseEngineeringField implements JavaReverseEngineeringMethod {


    // 方法片段
    private List<String> classMethodPartList = new ArrayList<>();

    /**
     * description:  添加 class 方法片段
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void initClassMethodPart() {

    }

    /**
     * description:  添加 class 方法片段
     *
     * @param classMethodPart class 方法片段
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    @Override
    public void addClassMethodPart(String classMethodPart) {
        this.classMethodPartList.add(classMethodPart);
    }

    @Override
    public List<String> getClassMethodPart() {
        return classMethodPartList;
    }

}
