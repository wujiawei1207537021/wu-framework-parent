package com.wu.smart.acw.core.client.api.command;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * describe 客户端 创建Javaclass 参数
 *
 * @author Jia wei Wu
 * @date 2023/12/05 09:47 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.feign.DefaultFeignLazyAPICommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_client_gen_java_api_command", description = "客户端实例")
public class AcwClientGenJavaAPICommand {

    /**
     * 生成代码的表、字段信息
     */
    private InnerReverseClassLazyTableEndpoint innerReverseClassLazyTableEndpoint;
    /**
     * 生成代码参数
     */
    private LazyOperationConfig.ReverseEngineering reverseEngineering;

    /**
     * 内部
     *
     * @see ReverseClassLazyTableEndpoint
     */
    @Data
    public static class InnerReverseClassLazyTableEndpoint {
        /**
         * 类名
         */
        private String className;
        /**
         * 表名称
         */
        private String tableName;
        /**
         * 描述信息
         */
        private String comment;
        /**
         * 数据库名 schema
         */
        private String schema;
        /**
         * 包名 com.wu.framework.inner.lazy.persistence.conf
         */
        private String packageName;

        /**
         * 输出字段
         */
        private List<InnerLazyTableFieldEndpoint> inLazyTableFieldEndpoints;

        /**
         * 输入字段
         */
        private List<InnerLazyTableFieldEndpoint> outLazyTableFieldEndpoints;

        /**
         * 转换当前对象为 ReverseClassLazyTableEndpoint
         *
         * @return ReverseClassLazyTableEndpoint
         */
        public ReverseClassLazyTableEndpoint toReverseClassLazyTableEndpoint() {
            ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint = new ReverseClassLazyTableEndpoint();
            reverseClassLazyTableEndpoint.setClassName(this.getClassName());
            reverseClassLazyTableEndpoint.setTableName(this.getTableName());
            reverseClassLazyTableEndpoint.setComment(this.getComment());
            reverseClassLazyTableEndpoint.setSchema(this.getSchema());
            reverseClassLazyTableEndpoint.setPackageName(this.getPackageName());
            reverseClassLazyTableEndpoint.setInLazyTableFieldEndpoints(this.getInLazyTableFieldEndpoints().stream().map(InnerLazyTableFieldEndpoint::toLazyTableFieldEndpoint).toList());
            reverseClassLazyTableEndpoint.setOutLazyTableFieldEndpoints(this.getOutLazyTableFieldEndpoints().stream().map(InnerLazyTableFieldEndpoint::toLazyTableFieldEndpoint).toList());
            return reverseClassLazyTableEndpoint;
        }
    }

    /**
     * @see LazyTableFieldEndpoint
     * 内部表字段信息
     */
    @Data
    public static class InnerLazyTableFieldEndpoint {

        /**
         * 字段不是空
         *
         * @return
         */
        boolean notNull;
        /**
         * 字段默认数值
         */
        String defaultValue;
        /**
         * 字段是否为主键
         *
         * @return
         */
        boolean key;
        /**
         * 序号 数值越大越靠前
         */
        int serialNumber = 0;
        /**
         * Java 字段名称
         */
        private String name;
        /**
         * 数据库列名称
         */
        private String columnName;
        /**
         * 数据库字段别名
         */
        private String alias;
        /**
         * 数据库列类型
         */
        private String columnType;
        /**
         * 字段描述
         */
        private String comment;
        /**
         * 是否存在
         */
        private boolean exist;
        /**
         * upsert 时候字段策略，对应生成upsert 的sql
         */
        private LazyFieldStrategy upsertStrategy = LazyFieldStrategy.NO_VERIFY;

        /**
         * update 时候字段策略，对应生成upsert 的sql
         */
        private LazyFieldStrategy updateStrategy = LazyFieldStrategy.NO_VERIFY;
        /**
         * extra 额外的数据 如 auto_increment、DEFAULT_GENERATED on update CURRENT_TIMESTAMP
         *
         * @return
         */
        private String extra = "";
        /**
         * 字段版本
         */
        private int version;
        private long scale;
        /**
         * 参数
         */
        private String parameters;
        /**
         * 可选的
         */
        private boolean optional;
        /**
         * 数据为空的时候使用字段默认值
         */
        private String fieldDefaultValue;
        /**
         * 数据格式 varchar、int、text
         */
        private String dataType;


        public LazyTableFieldEndpoint toLazyTableFieldEndpoint() {
            FieldLazyTableFieldEndpoint abstractLazyTableFieldEndpoint = new FieldLazyTableFieldEndpoint();
            abstractLazyTableFieldEndpoint.setNotNull(this.isNotNull());
            abstractLazyTableFieldEndpoint.setDefaultValue(this.getDefaultValue());
            abstractLazyTableFieldEndpoint.setKey(this.isKey());
            abstractLazyTableFieldEndpoint.setSerialNumber(this.getSerialNumber());
            abstractLazyTableFieldEndpoint.setName(this.getName());
            abstractLazyTableFieldEndpoint.setColumnName(this.getColumnName());
            abstractLazyTableFieldEndpoint.setAlias(this.getAlias());
            abstractLazyTableFieldEndpoint.setColumnType(this.getColumnType());
            abstractLazyTableFieldEndpoint.setComment(this.getComment());
            abstractLazyTableFieldEndpoint.setExist(this.isExist());
            abstractLazyTableFieldEndpoint.setUpsertStrategy(this.getUpsertStrategy());
            abstractLazyTableFieldEndpoint.setUpdateStrategy(this.getUpdateStrategy());
            abstractLazyTableFieldEndpoint.setExtra(this.getExtra());
            abstractLazyTableFieldEndpoint.setVersion(this.getVersion());
            abstractLazyTableFieldEndpoint.setScale(this.getScale());
            abstractLazyTableFieldEndpoint.setParameters(this.getParameters());
            abstractLazyTableFieldEndpoint.setOptional(this.isOptional());
            abstractLazyTableFieldEndpoint.setFieldDefaultValue(this.getFieldDefaultValue());
            abstractLazyTableFieldEndpoint.setDataType(this.getDataType());
            return abstractLazyTableFieldEndpoint;
        }

        public InnerLazyTableFieldEndpoint copy() {

            InnerLazyTableFieldEndpoint innerLazyTableFieldEndpoint = new InnerLazyTableFieldEndpoint();
            innerLazyTableFieldEndpoint.setNotNull(this.isNotNull());
            innerLazyTableFieldEndpoint.setDefaultValue(this.getDefaultValue());
            innerLazyTableFieldEndpoint.setKey(this.isKey());
            innerLazyTableFieldEndpoint.setSerialNumber(this.getSerialNumber());
            innerLazyTableFieldEndpoint.setName(this.getName());
            innerLazyTableFieldEndpoint.setColumnName(this.getColumnName());
            innerLazyTableFieldEndpoint.setAlias(this.getAlias());
            innerLazyTableFieldEndpoint.setColumnType(this.getColumnType());
            innerLazyTableFieldEndpoint.setComment(this.getComment());
            innerLazyTableFieldEndpoint.setExist(this.isExist());
            innerLazyTableFieldEndpoint.setUpsertStrategy(this.getUpsertStrategy());
            innerLazyTableFieldEndpoint.setUpdateStrategy(this.getUpdateStrategy());
            innerLazyTableFieldEndpoint.setExtra(this.getExtra());
            innerLazyTableFieldEndpoint.setVersion(this.getVersion());
            innerLazyTableFieldEndpoint.setScale(this.getScale());
            innerLazyTableFieldEndpoint.setParameters(this.getParameters());
            innerLazyTableFieldEndpoint.setOptional(this.isOptional());
            innerLazyTableFieldEndpoint.setFieldDefaultValue(this.getFieldDefaultValue());
            innerLazyTableFieldEndpoint.setDataType(this.getDataType());
            return innerLazyTableFieldEndpoint;
        }

    }

}



