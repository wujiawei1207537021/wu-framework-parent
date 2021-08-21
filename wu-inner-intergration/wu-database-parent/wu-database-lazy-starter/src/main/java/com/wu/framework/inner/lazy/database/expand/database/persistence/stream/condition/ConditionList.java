package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.data.NormalUsedString;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 条件
 * @date : 2021/8/21 7:35 下午
 */
public class ConditionList extends ArrayList<ConditionList.Condition> {


    public void put(Object rowName, String type, Object rowValue) {
        add(new Condition().setRowName(rowName).setType(type).setRowValue(rowValue));
    }

    /**
    * @describe 拼接
    * @param
    * @return
    * @author Jia wei Wu
    * @date 2021/8/21 7:50 下午
    **/
    public StringBuilder splice(String prefix){
        final StringBuilder builder = new StringBuilder(prefix);
        final String splice = this.stream().map(condition -> condition.rowName + NormalUsedString.SPACE + condition.type + NormalUsedString.SPACE +
                NormalUsedString.SINGLE_QUOTE + condition.rowValue + NormalUsedString.SINGLE_QUOTE).collect(Collectors.joining(NormalUsedString.AND));
        return builder.append(splice);
    }
    @Accessors(chain = true)
    @Data
    public class Condition {
        private Object rowName;
        private String type;
        private Object rowValue;
    }
}
