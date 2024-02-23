package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description sql 段落 抽象接口
 *
 * @author Jia wei Wu
 * @date 2022/12/28 10:31 上午
 */
public abstract class SqlPartAbstract implements SqlPartExecutedAdapter {


    /**
     * 执行类型
     */
    abstract Persistence.ExecutionEnum getExecutionEnum();

    /**
     * 列字段对应的映射
     *
     * @return
     */
    abstract Map<String, String> getColumnAsMap();


    /**
     * 主表
     */
    abstract String getPrimaryTable();

    /**
     * 主表 class
     */
    abstract Class getPrimaryClass();

    /**
     * 链表
     */
    abstract List<SqlPart> getJoinConditions();

    /**
     * or 查询
     */
    abstract List<SqlPart> getOrSqlPart();

    /**
     * 获取Update 中的set
     *
     * @return
     */
    abstract Map<String, Object> getUpdateSet();

    /**
     * 忽略映射的列
     *
     * @return
     */
    abstract List<String> getIgnoreColumnList();

    /**
     * 仅仅使用as（忽略其他的字段）
     *
     * @return
     */
    abstract Boolean getOnlyUseAs();

    /**
     * 获取查询片段
     *
     * @return
     */
    @Override
    public SqlPartStringBuilder select() {
        Assert.isTrue(Persistence.ExecutionEnum.SELECT.equals(getExecutionEnum()), "执行方法错误：{" + getExecutionEnum() + "}");
        // select %s.* from %s
        SqlPartStringBuilder sqlPartStringBuilder = new SqlPartStringBuilder();
        sqlPartStringBuilder.append(Persistence.ExecutionEnum.SELECT.getExecution());

        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(getPrimaryClass());
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();

//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(getPrimaryClass());
        Map<String, String> columnAsMap = new HashMap<>();
        if (!getOnlyUseAs()) {
            String primaryTable = getPrimaryTable();
            Map<String, String> masterTablesColumnsAsMap = lazyTableEndpoint.getFieldEndpoints()
                    .stream()
                    .collect(Collectors.toMap(fieldLazyTableFieldEndpoint -> primaryTable + NormalUsedString.DOT + fieldLazyTableFieldEndpoint.getColumnName(), LazyTableFieldEndpoint::getAlias));
            columnAsMap.putAll(masterTablesColumnsAsMap);
            // 添加 join 表中的字段
            if (!ObjectUtils.isEmpty(getJoinConditions())) {
                // 去除链表重复字段 优先级 master 第一 后续表出现的顺序
                Map<String, String> joinTablesColumnsAsMap = getJoinConditions().stream().map(conditions -> {
                            Class<?> entityClass = conditions.getPrimaryClass();
                            SqlSourceClass joinSqlSourceClass =  SqlSourceClass.getInstance(entityClass);
                            LazyTableEndpoint joinClassLazyTableEndpoint = joinSqlSourceClass.getLazyTableEndpoint();
//                            LazyTableEndpoint joinClassLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(entityClass);
                            List<LazyTableFieldEndpoint> fieldEndpoints = joinClassLazyTableEndpoint.getFieldEndpoints();
                            String fullTableName = joinClassLazyTableEndpoint.getFullTableName();
                            return fieldEndpoints.stream()
                                    .collect(Collectors.toMap(fieldLazyTableFieldEndpoint -> fullTableName + NormalUsedString.DOT + fieldLazyTableFieldEndpoint.getColumnName(), LazyTableFieldEndpoint::getAlias));
                        }).filter(joinTableColumnSql -> !ObjectUtils.isEmpty(joinTableColumnSql)).flatMap(m -> m.entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (A, B) -> B));
                columnAsMap.putAll(joinTablesColumnsAsMap);
            }
            columnAsMap.putAll(getColumnAsMap());
        } else {
            columnAsMap = getColumnAsMap();
        }
        columnAsMap = columnAsMap.entrySet().stream()
                .filter(stringStringEntry -> !getIgnoreColumnList().contains(stringStringEntry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (A, B) -> B))
                // K-V  ---》 V-K   去重
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (A, B) -> {
                    // 优先选取 as map 中的key
                    if (getColumnAsMap().containsKey(A)) {
                        return A;
                    } else if (getColumnAsMap().containsKey(B)) {
                        return B;
                    }
                    return B;
                }))
                // 去重
                // V-K  ---》 K-V
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (A, B) -> B));
        // 添加as
        if (!ObjectUtils.isEmpty(columnAsMap)) {
            String as = columnAsMap.entrySet().stream().map(stringStringEntry -> NormalUsedString.SPACE + stringStringEntry.getKey() + NormalUsedString.SPACE + NormalUsedString.AS + NormalUsedString.SPACE + stringStringEntry.getValue() + NormalUsedString.SPACE).collect(Collectors.joining(NormalUsedString.COMMA));

            sqlPartStringBuilder.append(as);
        } else {
            sqlPartStringBuilder.append(NormalUsedString.ASTERISK);
        }
        String primaryFullTableName = lazyTableEndpoint.getFullTableName();
        // 添加 from
        sqlPartStringBuilder
                .append(NormalUsedString.FROM)
                .append(primaryFullTableName);
        return sqlPartStringBuilder;
    }

    /**
     * 获取更新片段
     *
     * @return
     */
    @Override
    public SqlPartStringBuilder update() {
        // update sys_user set id_deleted=false where id >1
        Assert.isTrue(Persistence.ExecutionEnum.UPDATE.equals(getExecutionEnum()), "执行方法错误：{" + getExecutionEnum() + "}");

        SqlPartStringBuilder sqlPartStringBuilder = new SqlPartStringBuilder();
        sqlPartStringBuilder.append(Persistence.ExecutionEnum.UPDATE.getExecution())
                .append(getPrimaryTable())
                .append(NormalUsedString.SET);
        if (!ObjectUtils.isEmpty(getUpdateSet())) {
            String setSql = getUpdateSet().entrySet().stream().map(stringStringEntry -> {
                String columnName = stringStringEntry.getKey();
                Object fieldValue = stringStringEntry.getValue();
                return columnName + NormalUsedString.EQUALS + fieldValue;
            }).collect(Collectors.joining(NormalUsedString.COMMA));
            sqlPartStringBuilder.append(setSql);
        }
        return sqlPartStringBuilder;
    }

    /**
     * 获取插入片段
     *
     * @return
     */
    @Override
    public SqlPartStringBuilder insert() {
        return null;
    }

    /**
     * 获取删除片段
     *
     * @return
     */
    @Override
    public SqlPartStringBuilder delete() {
        // delete from
        Assert.isTrue(Persistence.ExecutionEnum.DELETE.equals(getExecutionEnum()), "执行方法错误：{" + getExecutionEnum() + "}");
        SqlPartStringBuilder sqlPartStringBuilder = new SqlPartStringBuilder();
        sqlPartStringBuilder.append(Persistence.ExecutionEnum.DELETE.getExecution())
                .append(NormalUsedString.FROM)
                .append(getPrimaryTable());

        return sqlPartStringBuilder;
    }
}
