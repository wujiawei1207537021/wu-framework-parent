package com.wuframework.system.persistence.jpa;

import com.wuframework.system.common.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRoleJpaRepository extends JpaRepository<SysUserRole, Integer> {
}
