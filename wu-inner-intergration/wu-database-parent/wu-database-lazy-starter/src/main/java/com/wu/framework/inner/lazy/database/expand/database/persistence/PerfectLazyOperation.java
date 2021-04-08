package com.wu.framework.inner.lazy.database.expand.database.persistence;

import com.wu.framework.inner.layer.stereotype.MethodParamFunction;
import com.wu.framework.inner.lazy.database.domain.Page;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * description 懒人完美数据库持久层操作合集
 *
 * @author Jia wei Wu
 * @date 2021/2/22 下午7:56
 */
public interface PerfectLazyOperation extends LazyOperation {

    /**
     * description 滚动查询
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/8 下午4:33
     */
    <T> List<T> scroll(@NonNull Page page, @NonNull Class<T> returnType, String sql, MethodParamFunction methodParamFunction, Object... params);
}
