package com.wu.bionic.point;

import com.wu.bionic.point.so.DefaultBreakPointSo;
import com.wu.bionic.point.so.ExplorationSo;

import java.util.Collection;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2021/2/11 下午12:28
 */
public abstract class BreakPointMemoryAbstract implements BreakPointMemory {

    /**
     * description 记忆探索
     *
     * @param explorationSo 探索对象
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/2/11 下午12:22
     */
    @Override
    public Collection<DefaultBreakPointSo> exploration(ExplorationSo explorationSo) {
        return null;
    }
}

