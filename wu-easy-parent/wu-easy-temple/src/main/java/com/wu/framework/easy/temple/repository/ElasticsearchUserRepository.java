package com.wu.framework.easy.temple.repository;

import com.wu.framework.easy.temple.domain.ElasticsearchUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

@Service
public interface ElasticsearchUserRepository extends ElasticsearchRepository<ElasticsearchUser, Integer> {
}
