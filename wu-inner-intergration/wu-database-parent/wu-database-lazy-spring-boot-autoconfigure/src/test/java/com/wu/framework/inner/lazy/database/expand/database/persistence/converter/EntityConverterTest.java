//package com.wu.framework.inner.lazy.database.expand.database.persistence.converter;
//
//import com.wu.framework.inner.lazy.persistence.converter.EntityConverter;
//
//class EntityConverterTest {
//
//    public static void main(String[] args) {
//        String insertSQL = "INSERT INTO `` (`TABLE_CATALOG`, `TABLE_SCHEMA`, `TABLE_NAME`, `TABLE_TYPE`, `ENGINE`, `VERSION`, `ROW_FORMAT`, `TABLE_ROWS`, `AVG_ROW_LENGTH`, `DATA_LENGTH`, `MAX_DATA_LENGTH`, `INDEX_LENGTH`, `DATA_FREE`, `AUTO_INCREMENT`, `CREATE_TIME`, `UPDATE_TIME`, `CHECK_TIME`, `TABLE_COLLATION`, `CHECKSUM`, `CREATE_OPTIONS`, `TABLE_COMMENT`) VALUES ('def', 'lazy', 'chinese_characters', 'BASE TABLE', 'InnoDB', 10, 'Dynamic', 11793, 2806, 33095680, 0, 507904, 0, 48225, '2021-10-04 19:37:35', NULL, NULL, 'utf8mb4_general_ci', NULL, '', '');";
//        final String entity = EntityConverter.createEntity(insertSQL);
//        System.out.println(entity);
//    }
//}