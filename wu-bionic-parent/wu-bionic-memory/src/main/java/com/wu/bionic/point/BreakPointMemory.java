package com.wu.bionic.point;


import com.wu.bionic.point.so.DefaultBreakPointSo;
import com.wu.bionic.point.so.ExplorationSo;

import java.util.Collection;

/**
 * description 记忆获取、存储、删除
 *
 * @author Jia wei Wu
 * @date 2021/2/4 上午10:20
 */
public interface BreakPointMemory {

    /**
     * description 断点记忆存储
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/2 下午6:55
     */
    void storage(DefaultBreakPointSo defaultBreakPointSo);

    /**
     * description 记忆清除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/2 下午6:56
     */
    void clear(DefaultBreakPointSo defaultBreakPointSo);

    /**
     * description 记忆获取
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/2 下午6:57
     */
    Collection<DefaultBreakPointSo> acquisition();

    /**
     * description 记忆探索
     *
     * @param explorationSo 探索对象
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/11 下午12:22
     */
    Collection<DefaultBreakPointSo> exploration(ExplorationSo explorationSo);
}
