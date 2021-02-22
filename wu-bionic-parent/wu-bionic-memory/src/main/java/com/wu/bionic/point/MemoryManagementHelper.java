package com.wu.bionic.point;


import com.wu.bionic.point.so.DefaultBreakPointSo;

/**
 * description 记忆打点  在Class的任意位置进行打点记录
 *
 * @author 吴佳伟
 * @date 2021/2/4 上午10:32
 */
@Deprecated
public final class MemoryManagementHelper {

    /**
     * description 获取当前Class断点
     *
     * @param bean use this to get Method
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/2/4 上午10:34
     */
    public static DefaultBreakPointSo currentClassBreakPoint(Object bean, String methodName, Object... params) {
        return new DefaultBreakPointSo(null).setParams(null);
    }

}
