package com.wu.smart.acw.server.application.command;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.persistence.util.LazyTableFieldUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表查询参数
 */
@Data
public class AcwTableConsoleCommand {

    private String instanceId;
    private String schemaName;
    private String tableName;


    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;

    /**
     * 当前页
     */
    private int current = 1;

    /**
     * asc 排序
     */
    private List<String> ascs;
    /**
     * desc 排序
     */
    private List<String> descs;

    /**
     * 查询条件
     */
    private List<TableQueryCriteriaColumn> queryCriteriaColumnList;

    /**
     * 返回结果
     */
    private List<String> selectColumnList;

    @Data
    public static final class TableQueryCriteriaColumn {
        private String columnName;
        private String condition;
        private String data;
        // true 使用当前条件、false 不使用当前条件
        private boolean checked;

    }


    public String getSql() {
        return queryCriteriaColumnList
                .stream()
                .filter(TableQueryCriteriaColumn::isChecked)
                .map(
                        tableQueryColumn -> {
                            if (NormalUsedString.IN.equals(tableQueryColumn.getCondition())) {
                                return LazyTableFieldUtil.cleanSpecialColumn(tableQueryColumn.getColumnName()) + NormalUsedString.SPACE +
                                        tableQueryColumn.getCondition() + NormalUsedString.SPACE + NormalUsedString.LEFT_BRACKET +
                                        NormalUsedString.SINGLE_QUOTE + tableQueryColumn.getData() + NormalUsedString.SINGLE_QUOTE + NormalUsedString.RIGHT_BRACKET + NormalUsedString.SPACE;
                            } else {
                                return LazyTableFieldUtil.cleanSpecialColumn(tableQueryColumn.getColumnName()) + NormalUsedString.SPACE +
                                        tableQueryColumn.getCondition() + NormalUsedString.SPACE +
                                        NormalUsedString.SINGLE_QUOTE + tableQueryColumn.getData() + NormalUsedString.SINGLE_QUOTE + NormalUsedString.SPACE;
                            }
                        }
                )
                .collect(Collectors.joining(NormalUsedString.SPACE + NormalUsedString.AND + NormalUsedString.SPACE));
    }

}
