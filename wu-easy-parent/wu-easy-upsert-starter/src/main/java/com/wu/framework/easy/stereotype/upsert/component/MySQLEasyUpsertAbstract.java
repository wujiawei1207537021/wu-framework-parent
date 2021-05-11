package com.wu.framework.easy.stereotype.upsert.component;

import com.wu.framework.easy.stereotype.log.EasyUpsertLog;
import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.config.SpringUpsertConfig;
import com.wu.framework.inner.layer.data.UserConvertService;
import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.EasyAnnotationConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyTableAnnotation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.map.EasyHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
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
public abstract class MySQLEasyUpsertAbstract implements IEasyUpsert, MySQLDataProcessAnalyze, InitializingBean {


    private final UserConvertService userConvertService;
    private final SpringUpsertConfig springUpsertConfig;

    public MySQLEasyUpsertAbstract(UserConvertService userConvertService, SpringUpsertConfig springUpsertConfig) {
        this.userConvertService = userConvertService;
        this.springUpsertConfig = springUpsertConfig;
    }

    protected abstract DataSource determineDataSource();


    @Override
    public <T> Object upsert(List<T> list) throws Exception {
        DataSource dataSource = determineDataSource();
        Integer total = (list.size() + springUpsertConfig.getBatchLimit() - 1) / springUpsertConfig.getBatchLimit();
        log.info("计划处理步骤 【{}】 步", total);
        List<List<T>> splitList = splitList(list, springUpsertConfig.getBatchLimit());
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

    protected <T> Object execute(DataSource dataSource, List<T> list) throws Exception {
        Future task = easyUpsertExecutor.submit((Callable) () -> {
            // 第一个参数 clazz
//            ParameterizedType parameterizedType = (ParameterizedType) list.getClass().getGenericSuperclass();
            Class clazz = list.get(0).getClass();
            EasySmart easySmart = AnnotatedElementUtils.getMergedAnnotation(clazz, EasySmart.class);
            Map<String, Map<String, String>> iEnumList = new HashMap<>();
            if (null != userConvertService) {
                iEnumList = userConvertService.userConvert(clazz);
            }
            iEnumList.putAll(EasyAnnotationConverter.collectionConvert(clazz));
            LazyTableAnnotation lazyTableAnnotation = dataAnalyze(clazz, EasyHashMap.class.isAssignableFrom(clazz) ? (EasyHashMap) list.get(0) : null);
            lazyTableAnnotation.setIEnumList(iEnumList);
            final MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult = upsertDataPack(list, lazyTableAnnotation);
            if (springUpsertConfig.isPrintSql()) {
                System.err.println(String.format("Execute SQL : %s", mySQLProcessResult.getSql()));
            }
            PreparedStatement upsertStatement = null;
            Connection connection = null;
            boolean rs;
            try {
                connection = dataSource.getConnection();
                //初始化表
                if ((null != easySmart && easySmart.perfectTable()) | EasyHashMap.class.isAssignableFrom(clazz)) {
                    perfectTable(lazyTableAnnotation, dataSource);
                }
                //获取PreparedStatement对象
                upsertStatement = connection.prepareStatement(mySQLProcessResult.getSql());
                if (mySQLProcessResult.isHasBinary()) {
                    for (int i = 0; i < mySQLProcessResult.getBinaryList().size(); i++) {
                        upsertStatement.setBinaryStream(i + 1, mySQLProcessResult.getBinaryList().get(i));
                    }
                }
                //执行SQL语句，获取结果集
                rs = upsertStatement.execute();
            } catch (Exception e) {
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
        if (springUpsertConfig.isRecordLog()) {
            perfectTable(classLazyTableAnalyze(EasyUpsertLog.class), determineDataSource());
        }
    }
}
