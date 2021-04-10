package com.wu.framework.easy.temple.run;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.easy.temple.domain.DynGpsVehRun;
import com.wu.framework.easy.temple.domain.ElasticsearchUser;
import com.wu.framework.easy.temple.repository.ElasticsearchGPSRepository;
import com.wu.framework.easy.temple.service.ElasticsearchRepositoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/11/25 下午8:13
 */
@EasyController
public class ElasticsearchTestRun {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ElasticsearchRepositoryService elasticsearchRepositoryService;
    private final ElasticsearchGPSRepository elasticsearchGPSRepository;

    public ElasticsearchTestRun(ElasticsearchRestTemplate elasticsearchRestTemplate,
                                ElasticsearchRepositoryService elasticsearchRepositoryService, ElasticsearchGPSRepository elasticsearchGPSRepository) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.elasticsearchRepositoryService = elasticsearchRepositoryService;
        this.elasticsearchGPSRepository = elasticsearchGPSRepository;
    }

    @ApiOperation(value = "Elasticsearch-Search", tags = "Elasticsearch")
    @PostMapping("/_search")
    public SearchHits<ElasticsearchUser> elasticsearchSearch() {
        Query query = Query.findAll();
        SearchHits<ElasticsearchUser> userLogSearchHits = elasticsearchRestTemplate.search(query, ElasticsearchUser.class);
        return userLogSearchHits;
    }

    @ApiOperation(value = "Elasticsearch-Save", tags = "Elasticsearch")
    @PostMapping("/_save")
    public void save() {
//        elasticsearchRestTemplate.bulkUpdate();
        elasticsearchRepositoryService.save();
    }

    @ApiOperation(value = "Elasticsearch-Create", tags = "Elasticsearch")
    @PostMapping("/_create")
    public void create() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(ElasticsearchUser.class);
        if (!indexOperations.exists()) {
            indexOperations.create();
        }
        indexOperations.createMapping(ElasticsearchUser.class);
    }

    @ApiOperation(value = "findAll", tags = "Elasticsearch")
    @GetMapping("/_findAll")
    public List<DynGpsVehRun> findAll() {
        List<DynGpsVehRun> dynGpsVehRunList =
                elasticsearchGPSRepository.findAllByIndustryAndGpsVdate("080", "2020-12-02");
        return dynGpsVehRunList;
    }
}
