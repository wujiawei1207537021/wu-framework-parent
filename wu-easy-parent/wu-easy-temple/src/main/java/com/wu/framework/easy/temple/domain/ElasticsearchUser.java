package com.wu.framework.easy.temple.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/11/25 下午9:12
 */
@Data
@NoArgsConstructor
@Document(indexName = "elasticsearch-user")
public class ElasticsearchUser {
    @Id
    private int id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String tags;
    public ElasticsearchUser(int id, String name, String tags){
        this.id=id;
        this.name=name;
        this.tags=tags;
    }
}

