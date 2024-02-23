package com.wu.framework.database.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 封装的表信息
 * @date : 2020/8/3 下午9:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EncapsulatedTableInfo extends TemplateParameterAbstractEntity {

    /**
     * 表信息
     */
    private TableEntity tableEntity;
    /**
     * 列信息
     */
    private List<ColumnEntity> columnEntityList;
    /**
     * 一条记录
     */
    private Map aRecord;
}
