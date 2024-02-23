package com.wu.framework.database.lazy.table.structure.plus.dto;

import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/06/16 23:33
 */
@Data
public class DataBaseTableStructure {
    private  Info info;
    private List<LazyColumn> lazyColumnList;


    @Data
    public static class Info{
        private String title;

    }
}
