package com.wu.framework.inner.lazy.database.datasource.proxy.audit;

/**
 * description 审计抽象接口
 *
 * @author 吴佳伟
 * @date 2023/05/13 12:35
 */
public interface LazyAudit<PersistenceRepository> {

    /**
     * 判断当前是否允许sql审计
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @return
     */
    boolean supports(PersistenceRepository persistenceRepository);

    /**
     * 审计 发送sql 的对象
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    void audit(PersistenceRepository persistenceRepository);
}