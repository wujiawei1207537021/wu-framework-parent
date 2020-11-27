package com.wu.framework.easy.temple.service.impl;

import com.wu.framework.easy.temple.domain.ElasticsearchUser;
import com.wu.framework.easy.temple.repository.ElasticsearchUserRepository;
import com.wu.framework.easy.temple.service.ElasticsearchRepositoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/11/25 下午9:15
 */
@Service
public class ElasticsearchRepositoryServiceImpl implements ElasticsearchRepositoryService {

    private final ElasticsearchUserRepository elasticsearchUserRepository;

    public ElasticsearchRepositoryServiceImpl(ElasticsearchUserRepository elasticsearchUserRepository) {
        this.elasticsearchUserRepository = elasticsearchUserRepository;
    }


    /**
     * 保存user
     *
     * @return
     * @params
     * @author 吴佳伟
     * @date 2020/11/25 下午9:17
     **/
    @Override
    public void save() {
        List<ElasticsearchUser> elasticsearchUserList=new ArrayList<>();

        for (int i = 0; i <10 ; i++) {
            ElasticsearchUser elasticsearchUser=new ElasticsearchUser();
            elasticsearchUser.setId(i);
            elasticsearchUser.setName(i+"号");
            elasticsearchUserList.add(elasticsearchUser);
        }
        elasticsearchUserRepository.saveAll(elasticsearchUserList);
    }

}
