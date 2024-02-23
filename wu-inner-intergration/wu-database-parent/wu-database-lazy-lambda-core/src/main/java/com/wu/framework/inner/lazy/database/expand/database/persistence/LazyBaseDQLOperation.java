package com.wu.framework.inner.lazy.database.expand.database.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

/**
 * describe : DQL 语言
 * 数据查询语言DQL基本结构是由SELECT子句，FROM子句，WHERE
 * 子句组成的查询块：
 * SELECT <字段名表>
 * FROM <表或视图名>
 * WHERE <查询条件>
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/4/23 22:58
 */

public interface LazyBaseDQLOperation {
    Logger log = LoggerFactory.getLogger(LazyBaseDQLOperation.class);

    /**
     * 分页查询
     *
     * @param <T>
     * @return
     */
    <T> LazyPage<T> page(@NonNull LazyPage lazyPage, @NonNull Class<T> returnType, String sql, Object... params);

    /**
     * 执行操作
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @param lazyPage              分页数据
     * @return LazyPage 分页数据
     */
    <T> LazyPage<T> executePage(PersistenceRepository persistenceRepository, LazyPage lazyPage);
}
