package com.wu.framework.inner.lazy.persistence.converter;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableFieldEndpoint;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 实体转换工具
 *
 * @author Jia wei Wu
 */
public class EntityConverter {

    /**
     * 根据 insert 语句生成 实体对象
     *
     * @param insertSql 插入sql
     * @return java class code
     */
    public static String createEntity(String insertSql) {

        //INSERT INTO `` (`TABLE_CATALOG`, `TABLE_SCHEMA`, `TABLE_NAME`, `TABLE_TYPE`, `ENGINE`, `VERSION`, `ROW_FORMAT`, `TABLE_ROWS`, `AVG_ROW_LENGTH`, `DATA_LENGTH`, `MAX_DATA_LENGTH`, `INDEX_LENGTH`, `DATA_FREE`, `AUTO_INCREMENT`, `CREATE_TIME`, `UPDATE_TIME`, `CHECK_TIME`, `TABLE_COLLATION`, `CHECKSUM`, `CREATE_OPTIONS`, `TABLE_COMMENT`) VALUES ('def', 'lazy', 'chinese_characters', 'BASE TABLE', 'InnoDB', 10, 'Dynamic', 11793, 2806, 33095680, 0, 507904, 0, 48225, '2021-10-04 19:37:35', NULL, NULL, 'utf8mb4_general_ci', NULL, '', '');

        if (ObjectUtils.isEmpty(insertSql)) {
            throw new IllegalArgumentException("参数异常");
        }
        List<FieldLazyTableFieldEndpoint> lazyTableFieldEndpointList = new ArrayList<>();
        final String toLowerCase = insertSql.toLowerCase(Locale.ROOT).replaceAll("`", "").replaceAll(" ", NormalUsedString.SPACE);
        final String[] split = toLowerCase.split("value");

        // 第一个( 和最后一个)之间的数据
        final String columnString = split[0];
        final String[] columnList =
                columnString.substring(
                                columnString.indexOf(NormalUsedString.LEFT_BRACKET) + 1,
                                columnString.lastIndexOf(NormalUsedString.RIGHT_BRACKET)).
                        split(NormalUsedString.COMMA);

        //
        final String valueString = split[1];
        final String[] valueList =
                valueString.substring(
                                valueString.indexOf(NormalUsedString.LEFT_BRACKET) + 1,
                                valueString.lastIndexOf(NormalUsedString.RIGHT_BRACKET)).
                        split(NormalUsedString.COMMA);
        StringBuilder stringBuilder = new StringBuilder("public class EntityClass{");

        for (int i = 0; i < columnList.length; i++) {
            final String value = valueList[i];
            final String column = columnList[i];
            stringBuilder.append(NormalUsedString.PRIVATE).append(NormalUsedString.SPACE).
                    append(NormalUsedString.STRING).append(NormalUsedString.SPACE).
                    append(CamelAndUnderLineConverter.lineToHumpField(column)).append(NormalUsedString.SEMICOLON);
        }
        stringBuilder.append(NormalUsedString.RIGHT_BRACE);
        return stringBuilder.toString();
    }

    /**
     * @param insertSql insert sql
     * @return
     */
    public static Map<String, Object> createJson(String insertSql) {
        /**
         * INSERT INTO ``
         * (`TABLE_CATALOG`,
         * `TABLE_SCHEMA`,
         * `TABLE_NAME`,
         * `TABLE_TYPE`,
         * `ENGINE`,
         * `VERSION`,
         * `ROW_FORMAT`,
         * `TABLE_ROWS`,
         * `AVG_ROW_LENGTH`,
         * `DATA_LENGTH`,
         * `MAX_DATA_LENGTH`,
         * `INDEX_LENGTH`,
         * `DATA_FREE`,
         * `AUTO_INCREMENT`,
         * `CREATE_TIME`,
         * `UPDATE_TIME`,
         * `CHECK_TIME`,
         * `TABLE_COLLATION`,
         * `CHECKSUM`,
         * `CREATE_OPTIONS`,
         * `TABLE_COMMENT`)
         *
         * VALUES (
         * 'def',
         * 'lazy',
         * 'chinese_characters',
         * 'BASE TABLE',
         * 'InnoDB',
         * 10,
         * 'Dynamic',
         * 11793,
         * 2806,
         * 33095680,
         * 0,
         * 507904,
         * 0,
         * 48225,
         * '2021-10-04 19:37:35',
         * NULL,
         * NULL,
         * 'utf8mb4_general_ci',
         * NULL,
         * '',
         * '');
         * **/

        Assert.notNull(insertSql, "insert sql 不能为空");
        insertSql = insertSql.replaceAll(NormalUsedString.SPACE, NormalUsedString.EMPTY);
        Map<String, Object> json = new HashMap<>();
        int rowFirstIndex = insertSql.indexOf(NormalUsedString.LEFT_BRACKET);
        int rowLastIndex = insertSql.indexOf(NormalUsedString.RIGHT_BRACKET);

        int valueFirstIndex = insertSql.lastIndexOf(NormalUsedString.LEFT_BRACKET);
        int valueLastIndex = insertSql.lastIndexOf(NormalUsedString.RIGHT_BRACKET);
        String rowSubstring = insertSql.substring(rowFirstIndex + 1, rowLastIndex);
        String valueSubstring = insertSql.substring(valueFirstIndex + 1, valueLastIndex);

        String[] valueSplit = valueSubstring.split(NormalUsedString.COMMA);
        String[] rowSplit = rowSubstring.split(NormalUsedString.COMMA);
        for (int i = 0; i < rowSplit.length; i++) {
            String row = rowSplit[i];
            String value = valueSplit[i];
            String key = row.replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY);
            json.put(key, value);
        }
        return json;
    }

}
