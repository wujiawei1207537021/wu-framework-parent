package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 条件
 * @date : 2021/8/21 7:35 下午
 */
@Accessors(chain = true)
@Data
public class ConditionList extends ArrayList<ConditionList.Condition> {

    /**
     * 执行类型
     */
    private Persistence.ExecutionEnum executionEnum;

    /**
     * 前缀 select * from
     */
    private String prefix;
    /**
     * 主表
     */
    private String primaryTable;

    /**
     * 实体 class
     */
    private Class entityClass;

    /**
     * 条件类型 where、on
     */
    private String conditionType;

    private List<ConditionList> joinConditions = new ArrayList<>();

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ConditionList() {
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ConditionList(String conditionType) {
        this.conditionType = conditionType;
    }

    /**
     * @param rowName  id
     * @param type     =
     * @param rowValue 1
     */
    public void put(Object rowName, String type, Object rowValue) {
        add(new Condition().setRowName(rowName).setType(type).setRowValue(rowValue));
    }

    /**
     * @param rowName      id
     * @param type         =
     * @param rowValueType RowValueType
     * @param rowValue     1
     */
    public void put(Object rowName, String type, RowValueType rowValueType, Object rowValue) {
        add(new Condition().setRowName(rowName).setType(type).setRowValueType(rowValueType).setRowValue(rowValue));
    }

    /**
     * describe 添加 join 条件
     *
     * @param conditionList join 条件
     * @return void 空
     * @author Jia wei Wu
     * @date 2022/1/21 10:26 下午
     **/
    public void join(ConditionList conditionList) {
        this.joinConditions.add(conditionList);
    }

    /**
     * @param
     * @return describe 拼接
     * @author Jia wei Wu
     * @date 2021/8/21 7:50 下午
     **/
    public StringBuilder splice(String prefix) {

        final StringBuilder builder = new StringBuilder(prefix);
        String join = joinConditions.stream().map(conditionList ->
                // 前缀
                NormalUsedString.SPACE + conditionList.getPrefix() +
                        // 主表
                        NormalUsedString.SPACE + conditionList.getPrimaryTable() +
                        // 条件类型
                        NormalUsedString.SPACE + conditionList.getConditionType() +
                        conditionList.stream().map(condition -> {
                            if (RowValueType.STRING.equals(condition.getRowValueType())) {
                                return NormalUsedString.SPACE + condition.rowName + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.type + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + NormalUsedString.SINGLE_QUOTE + condition.rowValue + NormalUsedString.SINGLE_QUOTE + NormalUsedString.SPACE;
                            } else {
                                return NormalUsedString.SPACE + condition.rowName + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.type + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.rowValue + NormalUsedString.SPACE;
                            }
                        }).collect(Collectors.joining(NormalUsedString.AND)) + NormalUsedString.SPACE).collect(Collectors.joining());
        builder.append(join);
        builder.append(conditionType);
        final String splice = this.stream().map(condition -> {

                            if (RowValueType.STRING.equals(condition.getRowValueType())) {
                                return NormalUsedString.SPACE + condition.rowName + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.type + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + NormalUsedString.SINGLE_QUOTE + condition.rowValue + NormalUsedString.SINGLE_QUOTE + NormalUsedString.SPACE;
                            } else {
                                return NormalUsedString.SPACE + condition.rowName + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.type + NormalUsedString.SPACE +
                                        NormalUsedString.SPACE + condition.rowValue + NormalUsedString.SPACE;
                            }
                        }
                )
                .collect(Collectors.joining(NormalUsedString.AND));
        return builder.append(splice);
    }

    /**
     * 返回持久成对象
     *
     * @return
     */
    public PersistenceRepository persistenceRepository() {
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        persistenceRepository.setQueryString(splice(prefix).toString());
        return persistenceRepository;
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
     * describe: 行value字段类型
     *
     * @author : Jia wei Wu
     * @version : 1.0
     * @date : 2022/1/21 7:58 下午
     */
    public enum RowValueType {
        // 字符串
        STRING,
        // 表达式
        EXPRESSION;

    }

    @Accessors(chain = true)
    @Data
    public class Condition {
        // id
        private Object rowName;

        // > = <
        private String type;
        /**
         * 字段类型
         */
        private RowValueType rowValueType = RowValueType.STRING;
        // 1
        private Object rowValue;

    }
}
