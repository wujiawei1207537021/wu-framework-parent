package com.wu.framework.response.handler;


import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.transaction.TransactionRequiredException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * DataAccessException(持久层异常)
 * sql异常 HttpMessageNotReadableException
 */
@RestControllerAdvice
public class GlobalDataAccessExceptionHandler {


    /**
     * sql异常
     *
     * @param exception
     * @return
     */
//    @ExceptionHandler({BadSqlGrammarException.class, SQLException.class})
//    public Result sqlException(Exception exception) {
//        exception.printStackTrace();
//        return ResultFactory.of(DefaultResultCode.SQL_EXCEPTION, exception.getMessage());
//    }
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class, ConstraintViolationException.class})
    public Result sQLIntegrityConstraintViolationException(Exception exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION, exception.getMessage());
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public Result duplicateKeyException(DuplicateKeyException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.DUPLICATE_KEY_EXCEPTION, exception.getMessage());
    }

    /**
     * HttpMessageNotReadableException
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Result httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.INVALID_FORMAT_EXCEPTION, exception.getMessage());
    }


    @ExceptionHandler({TransactionRequiredException.class})
    public Result transactionRequiredException(TransactionRequiredException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.TRANSACTION_REQUIRED_EXCEPTION, exception.getMessage());
    }


}
