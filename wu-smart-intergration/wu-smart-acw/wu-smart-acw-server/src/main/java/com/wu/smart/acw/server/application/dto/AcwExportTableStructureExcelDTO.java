package com.wu.smart.acw.server.application.dto;

import com.wu.framework.easy.excel.stereotype.EasyExcelBean;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import lombok.Data;

import java.util.List;

/**
 * description 导出表结构对象
 *
 * @author 吴佳伟
 * @date 2023/10/25 16:26
 */
@Data
public class AcwExportTableStructureExcelDTO {

    @EasyExcelFiled(name = "表名称")
    private String tableName;

    @EasyExcelBean
    private List<LazyExcelColumn> excelColumnList;


    @Data
    public static final class LazyExcelColumn {
        /**
         * 字符八位字节长度
         */
        @EasyExcelFiled(name = "字符长度")
        private String characterOctetLength;
        @EasyExcelFiled(name = "排序规则名称")
        private String collationName;
        @EasyExcelFiled(name = "表名称")
        private String tableName;
        // 主键 PRI
        @EasyExcelFiled(name = "主键")
        private String columnKey;
        /**
         * 额外的
         * auto_increment(自增)
         */
        @EasyExcelFiled(name = "额外的")
        private String extra;
        @EasyExcelFiled(name = "是否允许空")
        private String isNullable;
        @EasyExcelFiled(name = "顺序位置")
        private Long ordinalPosition;

        /**
         * varchar(20)
         */
        @EasyExcelFiled(name = "字段类型", serialNumber = 2)
        private String columnType;

        @EasyExcelFiled(name = "数字精度")
        private String numericPrecision;

        @EasyExcelFiled(name = "给予权限")
        private String privileges;
        @EasyExcelFiled(name = "字段描述")
        private String columnComment;
        @EasyExcelFiled(name = "字符集名称")
        private String characterSetName;
        @EasyExcelFiled(name = "生成表达式")
        private String generationExpression;

        /**
         * varchar、int、text、bigint
         */
        @EasyExcelFiled(name = "数据类型")
        private String dataType;
        @EasyExcelFiled(name = "字段名称", serialNumber = 10)
        private String columnName;
        @EasyExcelFiled(name = "srsId")
        private String srsId;
        @EasyExcelFiled(name = "数字比例")
        private long numericScale;
        @EasyExcelFiled(name = "日期时间精度")
        private Long datetimePrecision;
        @EasyExcelFiled(name = "字符最大长度")
        private Long characterMaximumLength;
        @EasyExcelFiled(name = "表格目录")
        private String tableCatalog;
        @EasyExcelFiled(name = "数据库")
        private String schemaName;
        @EasyExcelFiled(name = "字段默认值")
        private String columnDefault;
    }
}
