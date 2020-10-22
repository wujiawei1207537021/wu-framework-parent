package com.wuframework.system.persistence.jpa;

import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.system.common.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionJpaRepository extends JpaRepository<SysPermission, Integer> {

    /**
     * 条件查询 权限
     *
     * @param universalSearchQO
     * @return
     */
    @Query("select s from SysPermission s where (s.permissionType=:#{#universalSearchQO.type} or :#{#universalSearchQO.type} is null) and (s.permissionCompletePath like concat('%',:#{#universalSearchQO.keyWord},'%') or :#{#universalSearchQO.keyWord} is null) order by  s.permissionId")
    List<SysPermission> queryList(@Param("universalSearchQO") UniversalSearchQO universalSearchQO);
}
