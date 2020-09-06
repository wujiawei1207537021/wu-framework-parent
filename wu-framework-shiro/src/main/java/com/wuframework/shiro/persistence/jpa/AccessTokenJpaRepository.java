package com.wuframework.shiro.persistence.jpa;

import com.wuframework.shiro.model.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenJpaRepository extends JpaRepository<AccessToken, Integer> {
}
