package com.wu.framework.inner.lazy.database.expand.database.persistence.cure;

import org.springframework.util.ObjectUtils;

import java.sql.SQLException;

/**
 * description 抽象治愈
 *
 * @author Jia wei Wu
 * @date 2023/01/15 19:01
 */
public abstract class AbstractCure implements Cure {


    /**
     * 判断是否支持 SQLException 异常治愈
     *
     * @param sqlException
     */
    public abstract boolean supportsSQLException(SQLException sqlException);

    /**
     * 判断当前能否治愈此异常
     *
     * @param throwable 异常信息
     * @return
     */
    @Override
    public boolean supports(Throwable throwable) {
        if(throwable==null){
            return false;
        }
        //Table 'acw3333449900012222.api' doesn't exist
        if (throwable instanceof SQLException) {
            SQLException sqlException = (SQLException) throwable;
            String sqlState = sqlException.getSQLState();
            if (ObjectUtils.isEmpty(sqlState)) {
                return supports(sqlException.getCause());
            }
            return supportsSQLException(sqlException);
        } else {
            if (throwable.getCause() instanceof SQLException) {
                SQLException sqlException = (SQLException) throwable.getCause();
                return supportsSQLException(sqlException);
            }
        }

        return false;
    }

    /**
     * 通过异常治愈
     *
     * @param retryTime 尝试次数
     * @param throwable 异常
     * @throws Throwable 异常
     */
    public abstract void cureByThrowable(int retryTime, Throwable throwable) throws Throwable;

    /**
     * 治愈
     *
     * @param retryTime
     * @param throwable 异常信息
     */
    @Override
    public void cure(int retryTime, Throwable throwable) throws Throwable {
//        if (throwable instanceof SQLException) {
//            SQLException sqlException = (SQLException) throwable;
//            String sqlState = sqlException.getSQLState();
//            if (ObjectUtils.isEmpty(sqlState)) {
//                cure(retryTime, sqlException.getCause());
//                return;
//            } else {
//                cureByThrowable(retryTime, sqlException);
//            }
//
//        } else {
//            Throwable throwableCause = throwable.getCause();
//            cure(retryTime, throwableCause);
//        }
        cureByThrowable(retryTime, throwable);
    }
}
