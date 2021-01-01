package com.wu.framework.easy.stereotype.upsert.component;

import com.wu.framework.easy.stereotype.log.EasyUpsertLog;
import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.converter.EasyAnnotationConverter;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.EasyTableAnnotation;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;
import com.wu.framework.easy.stereotype.upsert.ienum.UserConvertService;
import com.wu.framework.easy.stereotype.upsert.process.MySQLDataProcess;
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

/**
 * description MySQL数据插入
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午10:22
 */
@Slf4j
public abstract class MySQLEasyUpsertAbstract implements IEasyUpsert, InitializingBean {


    private final UserConvertService userConvertService;
    private final UpsertConfig upsertConfig;
    private final MySQLDataProcess mySQLDataProcess;

    public MySQLEasyUpsertAbstract(UserConvertService userConvertService, UpsertConfig upsertConfig, MySQLDataProcess mySQLDataProcess) {
        this.userConvertService = userConvertService;
        this.upsertConfig = upsertConfig;
        this.mySQLDataProcess = mySQLDataProcess;
    }

    protected abstract DataSource determineDataSource();


    @Override
    public <T> Object upsert(List<T> list) throws Exception {
        DataSource dataSource = determineDataSource();
        String threadName = Thread.currentThread().getName();
        Integer total = (list.size() + upsertConfig.getBatchLimit() - 1) / upsertConfig.getBatchLimit();
        log.info("计划处理步骤 【{}】 步", total);
        List<List<T>> splitList = splitList(list, upsertConfig.getBatchLimit());
        int stepCount = 1;
        for (List<T> ts : splitList) {
            log.info("处理步骤第 【{}】 步 ,总步数 【{}】", stepCount, total);
            execute(threadName, dataSource, ts);
            stepCount++;
        }
        log.info("分步操作完成✅");
        return true;
    }

    protected <T> Object execute(String threadName, DataSource dataSource, List<T> list) throws Exception {
        Future task = easyUpsertExecutor.submit((Callable) () -> {
            Thread.currentThread().setName(threadName);
            // 第一个参数 clazz
//            ParameterizedType parameterizedType = (ParameterizedType) list.getClass().getGenericSuperclass();
            Class clazz = list.get(0).getClass();
            EasySmart easySmart = AnnotatedElementUtils.getMergedAnnotation(clazz, EasySmart.class);
            Map<String, Map<String, String>> iEnumList = new HashMap<>();
            if (null != userConvertService) {
                iEnumList = userConvertService.userConvert(clazz);
            }
            iEnumList.putAll(EasyAnnotationConverter.collectionConvert(clazz));
            EasyTableAnnotation easyTableAnnotation = mySQLDataProcess.dataAnalyze(clazz, EasyHashMap.class.isAssignableFrom(clazz) ? (EasyHashMap) list.get(0) : null);
            easyTableAnnotation.setIEnumList(iEnumList);
            String upsertSQL = mySQLDataProcess.dataPack(list, easyTableAnnotation);
            if (upsertConfig.isPrintSql()) {
                log.error("Execute SQL : {}", upsertSQL);
            }
            PreparedStatement upsertStatement = null;
            Connection connection = null;
            boolean rs;
            try {
                connection = dataSource.getConnection();
                //初始化表
                if ((null != easySmart && easySmart.perfectTable()) | EasyHashMap.class.isAssignableFrom(clazz)) {
                    mySQLDataProcess.perfectTable(easyTableAnnotation, dataSource);
                }
                //获取PreparedStatement对象
                upsertStatement = connection.prepareStatement(upsertSQL);
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
        if (upsertConfig.isRecordLog()) {
            mySQLDataProcess.perfectTable(
                    LocalStorageClassAnnotation.getEasyTableAnnotation(EasyUpsertLog.class, true), determineDataSource());
        }
    }
}
