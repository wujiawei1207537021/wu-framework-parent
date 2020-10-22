package com.wuframework.system.persistence.jpa;

import com.wuframework.system.common.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleJpaRepository extends JpaRepository<SysRole, Integer> {

    List<SysRole> findAllByRoleSignInAndStatus(List<String> roleSigns, Integer status);
}
