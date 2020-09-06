package com.wuframework.system.persistence.jpa;

import com.wuframework.system.common.entity.SysUserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMessageJpaRepository extends JpaRepository<SysUserMessage,Integer> {
}
