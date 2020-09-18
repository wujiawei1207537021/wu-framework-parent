package com.wu.framework.easy.stereotype.upsert.upsert.entity.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.connect.data.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kafka connect的JsonSchema格式
 *
 * @author 黄珺
 * @date 2019-08-30
 */
@Data
@JsonIgnoreProperties({"optional"})
public class TargetJsonSchema {

    /**
     * schema名称
     */
    private String name;

    /**
     * schema类型，固定为struct
     */
    private final String type = Schema.Type.STRUCT.getName().toLowerCase();

    /**
     * 字段列表
     */
    private List<Field> fields;


    @NoArgsConstructor
    @Data
    public static class Field {
        /**
         * 特殊格式名称
         * Timestamp:org.apache.kafka.connect.data.Timestamp
         * Time:org.apache.kafka.connect.data.Time
         * Date:org.apache.kafka.connect.data.Date
         * Decimal:org.apache.kafka.connect.data.Decimal
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;

        /**
         * 特殊格式版本
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer version;

        /**
         * Decimal的小数位数
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer scale;

        /**
         * 参数
         * Decimal: scale小数点的位数
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Map<String, String> parameters;

        /**
         * 字段名称
         */
        private String field;

        /***
         * 字段类型
         *
         * {@link Schema.Type} 小写的枚举名称
         * 特殊格式：
         * Timestamp
         * Time
         * Date
         * Decimal
         */
        private String type;

        /**
         * 是否可以为null
         */
        private boolean optional;

        public Field(String field, String type, boolean optional, Integer scale) {
            this.field = field;
            this.type = type;
            this.optional = optional;
            this.scale = scale == null ? null : ((scale >= 0) ? scale : null);
        }

        public void init() {
            switch (type) {
                case "Decimal":
                case "decimal":
                    if (scale == null) {
                        throw new IllegalArgumentException("Decimal类型请填写小数位数参数scale");
                    }
                    this.name = Decimal.LOGICAL_NAME;
                    this.version = 1;
                    this.type = Schema.BYTES_SCHEMA.type().getName();
                    this.parameters = new HashMap<>(1, 1.0f);
                    parameters.put("scale", Integer.toString(scale));
                    this.scale = null;
                    break;
                case "Timestamp":
                case "timestamp":
                    this.name = Timestamp.LOGICAL_NAME;
                    this.type = Schema.INT64_SCHEMA.type().getName();
                    this.version = 1;
                    break;
                case "Time":
                case "time":
                    this.name = Time.LOGICAL_NAME;
                    this.type = Schema.INT32_SCHEMA.type().getName();
                    this.version = 1;
                    break;
                case "Date":
                case "date":
                    this.name = Date.LOGICAL_NAME;
                    this.type = Schema.INT32_SCHEMA.type().getName();
                    this.version = 1;
                    break;
                case "GeoPoint":
                case "geopoint":
                    this.name = "org.apache.kafka.connect.data.GeoPoint";
                    this.type = Schema.STRING_SCHEMA.type().getName();
                    this.version = 1;
                    break;
                default:
                    break;
            }
        }
    }
}
