package com.wuframework.system.persistence.jpa;

import com.wuframework.system.common.entity.SysDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictJpaRepository extends JpaRepository<SysDict, Integer> {

    @Query("SELECT CONCAT(sd.type,sd.value) from  SysDict sd where CONCAT(sd.type,sd.value) in (:typeValues)")
    List<String> findOldByTypeAndValue(List<String> typeValues);
}
