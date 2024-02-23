package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.enums.RowValueType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.persistence.util.LazySQLUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 片段条件
 * @date : 2021/8/21 7:35 下午
 */
//@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
public class SqlPart
        extends SqlPartAbstract
//        extends ArrayList<Condition>
{

    /**
     * 执行类型
     */
    private Persistence.ExecutionEnum executionEnum;

    /**
     * 列字段对应的映射
     */
    private Map<String, String> columnAsMap = new HashMap<>();
    /**
     * 忽略字段映射
     */
    private List<String> ignoreColumnList = new ArrayList<>();

    /**
     * 仅仅使用as（忽略其他的字段）
     */
    private Boolean onlyUseAs = false;
    /**
     * 前缀 select * from
     */
    private String prefix;
    /**
     * 主表
     */
    private String primaryTable;


    /**
     * 主表实体 class
     */
    private Class<?> primaryClass;


    /**
     * 获取Update 中的set
     */
    private Map<String, Object> updateSet;
    /**
     * 条件类型 where、on
     */
    private String conditionType;

    /**
     * 字段条件
     */
    private List<Condition> conditionList = new ArrayList<>();
    /**
     * or 查询
     */
    private List<SqlPart> orSqlPart = new ArrayList<>();
    /**
     * 链表查询
     */
    private List<SqlPart> joinConditions = new ArrayList<>();


    /**
     * 分组
     */
    private List<Condition> groupByConditions = new ArrayList<>();

    /**
     * having
     */
    private List<SqlPart> havingConditions = new ArrayList<>();

    /**
     * 排序
     */
    private List<Condition> orderConditions = new ArrayList<>();
    /**
     * limit sql
     */
    private String limitSql;
    /**
     * 放在最后的sql
     */
    private String lastSql;

    /**
     * 预执行SQL需要的属性
     */
    private PersistenceRepository persistenceRepository = null;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public SqlPart() {
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public SqlPart(String conditionType) {
        this.conditionType = conditionType;
    }

    /**
     * 添加字段条件
     *
     * @param condition
     */
    private void addAnd(Condition condition) {
        conditionList.add(condition);
    }

    /**
     * @param rowName  id
     * @param type     =
     * @param rowValue 1
     */
    public void put(Condition.AndOr andOr, Object rowName, String type, Object rowValue) {
        addAnd(new Condition().setAndOr(andOr).setRowName(rowName).setType(type).setRowValue(rowValue));
    }

    /**
     * @param rowName  id
     * @param type     =
     * @param rowValue 1
     */
    public void put(Object rowName, String type, Object rowValue) {
        addAnd(new Condition().setRowName(rowName).setType(type).setRowValue(rowValue));
    }

    /**
     * @param rowName      id
     * @param type         =
     * @param rowValueType RowValueType
     * @param rowValue     1
     */
    public void put(Condition.AndOr andOr, Object rowName, String type, RowValueType rowValueType, Object rowValue) {
        addAnd(new Condition().setAndOr(andOr).setRowName(rowName).setType(type).setRowValueType(rowValueType).setRowValue(rowValue));
    }

    /**
     * @param rowName      id
     * @param type         =
     * @param rowValueType RowValueType
     * @param rowValue     1
     */
    public void put(Object rowName, String type, RowValueType rowValueType, Object rowValue) {
        addAnd(new Condition().setRowName(rowName).setType(type).setRowValueType(rowValueType).setRowValue(rowValue));
    }

    /**
     * @param fieldName      模型对应字段名称
     * @param rowName        id
     * @param type           =
     * @param rowValueType   RowValueType
     * @param fieldValueName EXPRESSION 方式下对应的模型对应字段名称
     * @param rowValue       1
     */
    public void put(Condition.AndOr andOr, String fieldName, Object rowName, String type, RowValueType rowValueType, String fieldValueName, Object rowValue) {
        addAnd(new Condition().setAndOr(andOr).setFieldName(fieldName).setRowName(rowName).setType(type).setRowValueType(rowValueType)
                .setFieldValueName(fieldValueName).setRowValue(rowValue));
    }

    /**
     * @param fieldName      模型对应字段名称
     * @param rowName        id
     * @param type           =
     * @param rowValueType   RowValueType
     * @param fieldValueName EXPRESSION 方式下对应的模型对应字段名称
     * @param rowValue       1
     */
    public void put(String fieldName, Object rowName, String type, RowValueType rowValueType, String fieldValueName, Object rowValue) {
        addAnd(new Condition().setFieldName(fieldName).setRowName(rowName).setType(type).setRowValueType(rowValueType)
                .setFieldValueName(fieldValueName).setRowValue(rowValue));
    }


    /**
     * 添加as 字段
     *
     * @param columnName 列字段
     * @param asName     as字段
     */
    public void columnAs(String columnName, String asName) {
        this.columnAsMap.put(columnName, asName);
    }

    /**
     * 忽略字段映射
     *
     * @param columnName
     */
    public void ignoreColumnAs(String columnName) {
        this.ignoreColumnList.add(columnName);
    }

    /**
     * 仅仅使用as（忽略其他的字段）
     *
     * @param onlyUseAs
     */
    public void setOnlyUseAs(boolean onlyUseAs) {
        this.onlyUseAs = onlyUseAs;
    }

    /**
     * describe 添加 join 条件
     *
     * @param sqlPart join 条件
     * @return void 空
     * @author Jia wei Wu
     * @date 2022/1/21 10:26 下午
     **/
    public void join(SqlPart sqlPart) {
        this.joinConditions.add(sqlPart);
    }

    /**
     * describe 添加 or 条件
     *
     * @param sqlPart 参数条件
     * @return void 空
     * @author Jia wei Wu
     * @date 2022/1/21 10:26 下午
     **/
    public void or(SqlPart sqlPart) {
        this.orSqlPart.add(sqlPart);
    }

    /**
     * describe 添加 分组 条件
     *
     * @param condition join 条件
     * @return void 空
     * @author Jia wei Wu
     * @date 2022/1/21 10:26 下午
     **/
    public void groupBy(Condition condition) {
        this.groupByConditions.add(condition);
    }

    /**
     * describe 添加 having 条件
     *
     * @param condition join 条件
     * @return void 空
     * @author Jia wei Wu
     * @date 2022/1/21 10:26 下午
     **/
    public void having(SqlPart condition) {
        this.havingConditions.add(condition);
    }

    /**
     * describe 添加 排序 条件
     *
     * @param condition join 条件
     * @return void 空
     * @author Jia wei Wu
     * @date 2022/1/21 10:26 下午
     **/
    public void orderBy(Condition condition) {
        this.orderConditions.add(condition);
    }

    /**
     * @param
     * @return describe 拼接
     * @author Jia wei Wu
     * @date 2021/8/21 7:50 下午
     **/
    public StringBuilder splice(String prefix) {
// TODO as
        final StringBuilder builder = new StringBuilder(prefix);
        // select  t1.*,t2.* from  t1,t2 中的 t1,t2
        String join = joinConditions.stream().map(conditionList ->
                // 前缀
                NormalUsedString.SPACE + conditionList.getPrefix() +
                        // 主表
                        NormalUsedString.SPACE + conditionList.getPrimaryTable() +
                        // 条件类型
                        NormalUsedString.SPACE + conditionList.getConditionType() +
                        conditionList.conditionList.stream().map(condition -> {
                            if (RowValueType.STRING.equals(condition.getRowValueType())) {
                                return NormalUsedString.SPACE + condition.getRowName() + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.getType() + NormalUsedString.SPACE +
                                        LazySQLUtil.sqlValue(condition.getRowValue(), !NormalUsedString.IN.equals(condition.getType())) +
                                        NormalUsedString.SPACE;
                            } else {
                                return NormalUsedString.SPACE + condition.getRowName() + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.getType() + NormalUsedString.SPACE +
                                        LazySQLUtil.sqlValue(condition.getRowValue(), false) +
                                        NormalUsedString.SPACE;
                            }
                        }).collect(Collectors.joining(NormalUsedString.AND)) + NormalUsedString.SPACE).collect(Collectors.joining());
        builder.append(join);

        // and 条件
        String splice = conditionList.stream().map(condition -> {

                            if (RowValueType.STRING.equals(condition.getRowValueType())) {
                                return NormalUsedString.SPACE + condition.getRowName() + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.getType() + NormalUsedString.SPACE +
                                        LazySQLUtil.valueToSqlValue(condition.getRowValue(), RowValueType.STRING) +
                                        NormalUsedString.SPACE;
                            } else {
                                return NormalUsedString.SPACE + condition.getRowName() + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.getType() + NormalUsedString.SPACE +
                                        LazySQLUtil.valueToSqlValue(condition.getRowValue(), RowValueType.EXPRESSION) +
                                        NormalUsedString.SPACE;
                            }
                        }
                )
                .collect(Collectors.joining(NormalUsedString.SPACE + NormalUsedString.AND + NormalUsedString.SPACE));
        // or 条件
        String orSplice = orSqlPart.stream().map(sqlPart -> NormalUsedString.LEFT_BRACKET + sqlPart.getConditionList().stream().map(condition -> {

            if (RowValueType.STRING.equals(condition.getRowValueType())) {
                return NormalUsedString.SPACE + condition.getRowName() + NormalUsedString.SPACE +
                        NormalUsedString.SPACE + condition.getType() + NormalUsedString.SPACE +
                        LazySQLUtil.valueToSqlValue(condition.getRowValue(), RowValueType.STRING) +
                        NormalUsedString.SPACE;
            } else {
                return NormalUsedString.SPACE + condition.getRowName() + NormalUsedString.SPACE +
                        NormalUsedString.SPACE + condition.getType() + NormalUsedString.SPACE +
                        LazySQLUtil.valueToSqlValue(condition.getRowValue(), RowValueType.EXPRESSION) +
                        NormalUsedString.SPACE;
            }
        }).collect(Collectors.joining(NormalUsedString.SPACE + NormalUsedString.AND + NormalUsedString.SPACE)) + NormalUsedString.RIGHT_BRACKET).collect(Collectors.joining(NormalUsedString.SPACE + NormalUsedString.OR + NormalUsedString.SPACE));

        if (ObjectUtils.isEmpty(splice)) {
            splice = orSplice;
        } else if (!ObjectUtils.isEmpty(orSplice)) {
            splice = splice + NormalUsedString.OR + NormalUsedString.SPACE + orSplice;
        }
        if (ObjectUtils.isEmpty(splice)) {
            // 添加 group by 和order by
            appendGroupAndOrder(builder);
            return builder;
        }
        // where
        builder.append(conditionType);
        builder.append(splice);
        // 添加 group by 和order by
        appendGroupAndOrder(builder);
        // 添加 limit last sql
        appendLimitAndLast(builder);
        return builder;
    }

    /**
     * describe 添加 group by 和order by
     *
     * @param builder 原始builder
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/26 22:44
     **/
    public StringBuilder appendGroupAndOrder(StringBuilder builder) {
        // group by
        if (!ObjectUtils.isEmpty(groupByConditions)) {
            String groupBy = groupByConditions.stream().map(condition -> condition.getRowName().toString()).collect(Collectors.joining(","));
            builder.append(" group by ");
            builder.append(groupBy);
        }
        // order by
        if (!ObjectUtils.isEmpty(orderConditions)) {
            String orderBy = orderConditions.stream().map(condition -> condition.getRowName().toString()).collect(Collectors.joining(","));
            builder.append(" order by ");
            builder.append(orderBy);
            Optional<String> first = orderConditions.stream().map(Condition::getType).findFirst();
            first.ifPresent(s -> builder.append(NormalUsedString.SPACE).append(s));

        }
        return builder;
    }

    /**
     * describe 添加 limit last sql
     *
     * @param builder 原始builder
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/26 22:44
     **/
    public StringBuilder appendLimitAndLast(StringBuilder builder) {
        // limit
        if (!ObjectUtils.isEmpty(limitSql)) {
            builder.append(limitSql);
        }
        // lastSql
        if (!ObjectUtils.isEmpty(lastSql)) {
            builder.append(lastSql);
        }
        return builder;
    }

    /**
     * 返回持久成对象
     *
     * @return
     */
    public PersistenceRepository persistenceRepository() {
        if (persistenceRepository == null) {
            persistenceRepository = PersistenceRepositoryFactory.create();
        }
        String sql = sql();
        persistenceRepository.setSqlPart(this);
        persistenceRepository.setQueryString(sql);
        return persistenceRepository;
    }

    public String sql() {
        String prefixSql = prefix;
        // 保留原始写法
        if (ObjectUtils.isEmpty(prefixSql)) {
            prefixSql = switch (this.getExecutionEnum()) {
                case SELECT -> select().toString();
                case DELETE -> delete().toString();
                case UPDATE -> update().toString();
                case INSERT -> insert().toString();
            };
        }
        return splice(prefixSql).toString();
    }

    /**
     * describe 创建 Condition 对象
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/21 10:35 下午
     **/
    public Condition createCondition() {
        return new Condition();
    }

    /**
     * 拷贝当前对象
     *
     * @return
     */
    public SqlPart copy() {
        SqlPart conditions = new SqlPart();
        for (Condition condition : this.conditionList) {
            conditions.addAnd(condition.copy());
        }
        conditions.setExecutionEnum(this.getExecutionEnum());
        conditions.setPrefix(this.getPrefix());
        conditions.setPrimaryTable(this.getPrimaryTable());
        conditions.setPrimaryClass(this.getPrimaryClass());
        conditions.setConditionType(this.getConditionType());
        conditions.setConditionList(this.getConditionList());
        conditions.setJoinConditions(this.getJoinConditions());
        conditions.setOrSqlPart(this.getOrSqlPart());
        conditions.setGroupByConditions(this.getGroupByConditions());
        conditions.setOrderConditions(this.getOrderConditions());
        conditions.setLimitSql(this.getLimitSql());
        conditions.setLastSql(this.getLastSql());
        conditions.setColumnAsMap(this.getColumnAsMap());
        conditions.setIgnoreColumnList(this.getIgnoreColumnList());
        conditions.setUpdateSet(this.getUpdateSet());
        conditions.setHavingConditions(this.getHavingConditions());
        conditions.setOnlyUseAs(this.getOnlyUseAs());
        return conditions;
    }

    @Override
    public String toString() {
        return "SqlPart{" +
                "executionEnum=" + executionEnum +
                ", columnAsMap=" + columnAsMap +
                ", ignoreColumnList=" + ignoreColumnList +
                ", onlyUseAs=" + onlyUseAs +
                ", prefix='" + prefix + '\'' +
                ", primaryTable='" + primaryTable + '\'' +
                ", primaryClass=" + primaryClass +
                ", updateSet=" + updateSet +
                ", conditionType='" + conditionType + '\'' +
                ", conditionList=" + conditionList +
                ", orSqlPart=" + orSqlPart +
                ", joinConditions=" + joinConditions +
                ", groupByConditions=" + groupByConditions +
                ", havingConditions=" + havingConditions +
                ", orderConditions=" + orderConditions +
                ", limitSql='" + limitSql + '\'' +
                ", lastSql='" + lastSql + '\'' +
                '}';
    }
}
