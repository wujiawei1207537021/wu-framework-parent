package com.wu.framework.inner.lazy.persistence.reverse;

import java.util.List;

/**
 * description 逆向工程 字段存储接口
 *
 * @author 吴佳伟
 * @date 2023/02/14 13:21
 */
public interface JavaReverseEngineeringField extends JavaReverseEngineeringClassName {
    /**
     * description:  添加 class 字段片段
     *
     * @LazyTableFieldId(comment = "api参数主键")
     * private Long id;
     * </p>
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void initClassFieldPart();

    /**
     * description:  添加 class 字段片段
     *
     * @param classFieldPart
     * @LazyTableFieldId(comment = "api参数主键")
     * private Long id;
     * </p>
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void addClassFieldPart(String classFieldPart);


    /**
     * description:  获取添加 class 的字段片段
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    List<String> getClassFieldPart();
}
