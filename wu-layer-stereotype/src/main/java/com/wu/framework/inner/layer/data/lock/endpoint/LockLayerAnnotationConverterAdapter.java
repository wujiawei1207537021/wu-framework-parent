package com.wu.framework.inner.layer.data.lock.endpoint;

import com.wu.framework.inner.layer.data.lock.EasyLock;
import com.wu.framework.inner.layer.stereotype.converter.LayerAnnotationConverterAdapter;

import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2023/01/04 11:40 上午
 */
public class LockLayerAnnotationConverterAdapter implements LayerAnnotationConverterAdapter<EasyLock, LockEndPoint> {
    /**
     * 是否支持
     *
     * @param annotation   注解
     * @param lockEndPoint 断点
     * @return
     */
    @Override
    public boolean supports(EasyLock annotation, LockEndPoint lockEndPoint) {
        return true;
    }

    /**
     * @param annotation 原始注解
     * @return 返回断点信息
     */
    @Override
    public LockEndPoint converter(EasyLock annotation) {
        if (null == annotation) {
            return null;
        }
        String key = annotation.key();
        String lockGroup = annotation.lockGroup();
        long time = annotation.time();
        TimeUnit unit = annotation.unit();
        LockEndPoint lockEndPoint = new LockEndPoint();
        lockEndPoint.setKey(key);
        lockEndPoint.setLockGroup(lockGroup);
        lockEndPoint.setTime(time);
        lockEndPoint.setUnit(unit);
        return lockEndPoint;
    }
}
