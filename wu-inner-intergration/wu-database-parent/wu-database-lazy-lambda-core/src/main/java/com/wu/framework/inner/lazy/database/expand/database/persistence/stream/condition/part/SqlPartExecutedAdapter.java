package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part;

/**
 * description 片段执行类型适配器
 *
 * @author Jia wei Wu
 * @date 2022/12/28 10:29 上午
 */
public interface SqlPartExecutedAdapter {

    /**
     * 获取查询片段
     *
     * @return
     */
    SqlPartStringBuilder select();

    /**
     * 获取更新片段
     *
     * @return
     */
    SqlPartStringBuilder update();

    /**
     * 获取插入片段
     *
     * @return
     */
    SqlPartStringBuilder insert();

    /**
     * 获取删除片段
     *
     * @return
     */
    SqlPartStringBuilder delete();
}
