package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.analyze.AnalyzeParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTableField;
import lombok.SneakyThrows;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 灵活更新 去除null的字段、创建表
 * 插入的对象数据不能时list 空数据过滤问题
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodSmartUpsert extends AbstractLazyOperationMethod {


    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws IllegalArgumentException {

        // TODO EASYHASHMAP
        Persistence persistence = smartUpsert(param);
        StringBuffer stringBuffer = new StringBuffer(persistence.getExecutionEnum().getExecution());
        stringBuffer.append(persistence.getTableName());
        stringBuffer.append(NormalUsedString.LEFT_BRACKET);
        stringBuffer.append(String.join(",", persistence.getColumnList()));
        stringBuffer.append(") values ( ");
        stringBuffer.append(persistence.getCondition());
        stringBuffer.append(" ) ON DUPLICATE KEY UPDATE ");
        stringBuffer.append(persistence.getColumnList().stream().map(s -> s + " =VALUES (" + s + ")").collect(Collectors.joining(",")));
        String sql = stringBuffer.toString();
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        persistenceRepository.setQueryString(sql);
        persistenceRepository.setBinaryList(persistence.getBinaryList());
        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param dataSource
     * @param sourceParams
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(DataSource dataSource, Object[] sourceParams) throws Exception {
        int num = 0;
        Object param = sourceParams[0];
        if (param instanceof Object[]) {
            Object[] objects = (Object[]) param;
            // 是否修改
            AtomicBoolean modify = new AtomicBoolean(false);
            for (Object o : objects) {
                if(Collection.class.isAssignableFrom(o.getClass())){
                    throw new IllegalArgumentException("插入的对象数据不能时list 空数据无法过滤过滤，请使用upsert方法操作！");
                }
                if (!modify.get()) {
                    perfectTable(classLazyTableAnalyze(o.getClass()), dataSource);
                }
                modify.set(true);
                accurateExecution(dataSource, o);
            }
            num += objects.length;
        } else {
            perfectTable(classLazyTableAnalyze(param.getClass()), dataSource);
            num += (int) accurateExecution(dataSource, param);
        }


        return num;
    }


    /**
     * description 活动插入物已准备好
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/26 3:54 下午
     */
    @SneakyThrows
    public Persistence smartUpsert(Object object) {
        // 数据库列
        List<String> columnList = new ArrayList<>();
        // 列对应值
        List<String> columnValueList = new ArrayList<>();
        // 表名
        String tableName = analyze(AnalyzeParameter.create().setClazz(object.getClass())).name();

        // 二进制数据
        List<InputStream> binaryList = new ArrayList<>();
        if (EasyHashMap.class.isAssignableFrom(object.getClass())) {
            EasyHashMap easyHashMap = (EasyHashMap) object;
            if (easyHashMap.isModifyUniqueLabel()) {
                easyHashMap.forEach((key, value) -> {
                    if (!ObjectUtils.isEmpty(value)) {
                        columnList.add(key.toString());
                        if (InputStream.class.isAssignableFrom(value.getClass())) {
                            binaryList.add((InputStream) value);
                            columnValueList.add(NormalUsedString.QUESTION_MARK);
                        } else {
                            columnValueList.add(value.toString());
                        }
                    }
                });
            } else {
                throw new IllegalAccessException("自动创建的uniqueLabel 无法被使用为表名 " + easyHashMap.getUniqueLabel());
            }
        } else {
            for (Field declaredField : object.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object o = declaredField.get(object);
                if (o == null) {
                    continue;
                }
                LazyTableField tableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyTableField.class);
                if (tableField != null && !tableField.exist()) {
                    continue;
                }
                String column = ObjectUtils.isEmpty(tableField) || ObjectUtils.isEmpty(tableField.name()) ?
                        CamelAndUnderLineConverter.humpToLine2(declaredField.getName()) : tableField.name();
                columnList.add(column);
                if (InputStream.class.isAssignableFrom(declaredField.getType())) {
                    binaryList.add((InputStream) o);
                    columnValueList.add(NormalUsedString.QUESTION_MARK);
                    continue;
                }
                if (byte[].class.isAssignableFrom(declaredField.getType())) {
                    columnValueList.add("'" + new String((byte[]) o) + "'");
                } else {
                    columnValueList.add("'" + o + "'");
                }
            }

        }
        return new Persistence()
                .setExecutionEnum(Persistence.ExecutionEnum.INSERT)
                .setTableName(tableName)
                .setColumnList(columnList)
                .setCondition(String.join(NormalUsedString.COMMA, columnValueList))
                .setBinaryList(binaryList);
    }

}
