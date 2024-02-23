package com.wu.framework.easy.upsert.sink;

import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringUpsertAutoConfigure;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.inner.dynamic.database.DynamicLazyDSAdapter;
import com.wu.framework.inner.layer.data.ClassSchema;
import com.wu.framework.inner.layer.data.UserConvertService;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.persistence.analyze.DefaultMySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.persistence.analyze.EasyAnnotationConverter;
import com.wu.framework.inner.lazy.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description MySQL数据插入
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午10:22
 */
@Slf4j
@ConditionalOnBean(DataSource.class)
@EasyUpsertStrategy(value = EasyUpsertType.MySQL)
public class MySQLEasyUpsertSink implements IEasyUpsert, InitializingBean {


    private final UserConvertService userConvertService;
    private final SpringUpsertAutoConfigure springUpsertAutoConfigure;
    private final LazyOperation lazyOperation;
    private final DynamicLazyDSAdapter dynamicLazyDSAdapter;
    private final DefaultMySQLDataProcessAnalyze processAnalyze = new DefaultMySQLDataProcessAnalyze();

    public MySQLEasyUpsertSink(UserConvertService userConvertService, SpringUpsertAutoConfigure springUpsertAutoConfigure, LazyOperation lazyOperation, DynamicLazyDSAdapter dynamicLazyDSAdapter) {
        this.userConvertService = userConvertService;
        this.springUpsertAutoConfigure = springUpsertAutoConfigure;
        this.lazyOperation = lazyOperation;
        this.dynamicLazyDSAdapter = dynamicLazyDSAdapter;
    }


    @Override
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException {
        synchronized (dynamicLazyDSAdapter) {
            DataSource dataSource = dynamicLazyDSAdapter.determineDataSource();
            Integer total = (list.size() + springUpsertAutoConfigure.getBatchLimit() - 1) / springUpsertAutoConfigure.getBatchLimit();
            log.info("计划处理步骤 【{}】 步", total);
            List<List<T>> splitList = splitList(list, springUpsertAutoConfigure.getBatchLimit());
            AtomicInteger stepCount = new AtomicInteger(0);
            splitList.stream().parallel().forEach(ts -> {
                stepCount.getAndIncrement();
                log.info("处理步骤第 【{}】 步 ,总步数 【{}】", stepCount, total);
                try {
                    execute(dataSource, ts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            log.info("分步操作完成✅");
            return true;
        }
    }

    protected <T> Object execute(DataSource dataSource, List<T> list) throws Exception {
        Future task = EASY_UPSERT_EXECUTOR.submit((Callable) () -> {
            // 第一个参数 clazz
//            ParameterizedType parameterizedType = (ParameterizedType) list.getClass().getGenericSuperclass();
            Class clazz = list.get(0).getClass();
            EasySmart easySmart = AnnotatedElementUtils.getMergedAnnotation(clazz, EasySmart.class);
            Map<String, Map<String, String>> iEnumList = new HashMap<>();
            if (null != userConvertService) {
                iEnumList = userConvertService.userConvert(clazz);
            }
            iEnumList.putAll(EasyAnnotationConverter.collectionConvert(clazz));
            ClassLazyTableEndpoint lazyTableAnnotation = processAnalyze.dataAnalyze(clazz, EasyHashMap.class.isAssignableFrom(clazz) ? (EasyHashMap) list.get(0) : null);
            lazyTableAnnotation.setIEnumList(iEnumList);
            final MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult = processAnalyze.upsertDataPack(list, lazyTableAnnotation);
            if (springUpsertAutoConfigure.isPrintSql()) {
                System.err.println(String.format("Execute SQL : %s", mySQLProcessResult.getSql()));
            }
            PreparedStatement upsertStatement = null;
            Connection connection = null;
            boolean rs;
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                //初始化表
                if ((null != easySmart && easySmart.perfectTable()) | EasyHashMap.class.isAssignableFrom(clazz)) {
                    synchronized (this) {
                        processAnalyze.perfectTable(lazyTableAnnotation, connection);
                    }
                }
                //获取PreparedStatement对象
                upsertStatement = connection.prepareStatement(mySQLProcessResult.getSql());
                //执行SQL语句，获取结果集
                rs = upsertStatement.execute();
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                log.error(e.toString());
                throw new RuntimeException(e);
            } finally {
                if (upsertStatement != null) {
                    try {
                        connection.close();
//                        System.out.println("关闭链接");
                        upsertStatement.close();
                    } catch (SQLException throwables) {
//                        System.out.println("关闭链接异常");
                        throwables.printStackTrace();
                    }
                }
            }
            return rs;
        });
        return task.get();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (springUpsertAutoConfigure.isRecordLog()) {
//            TODO
//            perfectTable(classLazyTableAnalyze(EasyUpsertLog.class), determineDataSource());
        }
    }
}
