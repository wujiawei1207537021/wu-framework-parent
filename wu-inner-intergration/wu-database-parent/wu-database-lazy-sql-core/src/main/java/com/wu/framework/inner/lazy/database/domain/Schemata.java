package com.wu.framework.inner.lazy.database.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@LazyTable(tableName = "SCHEMATA", schema = "information_schema", comment = "null")
public class Schemata {

    /**
     * null
     */
    @LazyTableField(name = "CATALOG_NAME", comment = "null", columnType = "varchar(64)")
    private String catalogName;
    /**
     * null
     */
    @LazyTableField(name = "SCHEMA_NAME", comment = "null", columnType = "varchar(64)")
    private String schemaName;
    /**
     * null
     */
    @LazyTableField(name = "DEFAULT_CHARACTER_SET_NAME", comment = "null", notNull = true, columnType = "varchar(64)")
    private String defaultCharacterSetName;
    /**
     * null
     */
    @LazyTableField(name = "DEFAULT_COLLATION_NAME", comment = "null", notNull = true, columnType = "varchar(64)")
    private String defaultCollationName;
    /**
     * null
     */
    @LazyTableField(name = "SQL_PATH", comment = "null", columnType = "binary(0)")
    private String sqlPath;
    /**
     * null
     */
    @LazyTableField(name = "DEFAULT_ENCRYPTION", comment = "null", notNull = true, exist = false)
    private String defaultEncryption;
}