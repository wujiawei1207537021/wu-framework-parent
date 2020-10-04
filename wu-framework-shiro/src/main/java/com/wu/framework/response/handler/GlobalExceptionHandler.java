package com.wu.framework.response.handler;


import com.google.common.collect.Maps;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import org.apache.shiro.ShiroException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

@Deprecated
//@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 所有异常报错
     * TODO  策略处理
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public Result allExceptionHandler(Exception ex) {
        ex.printStackTrace();
        if (ex instanceof BadSqlGrammarException) {
            return ResultFactory.of(DefaultResultCode.SQL_EXCEPTION);
        } else if (ex instanceof SQLException) {
            return ResultFactory.of(DefaultResultCode.SQL_EXCEPTION);
        } else if (ex instanceof ClassCastException) {
            return ResultFactory.of(DefaultResultCode.CLASS_CAST_EXCEPTION);
        } else if (ex instanceof ArrayIndexOutOfBoundsException) {
            return ResultFactory.of(DefaultResultCode.ARR_EXCEPTION);
        } else if (ex instanceof FileNotFoundException) {
            return ResultFactory.of(DefaultResultCode.FILE_NOT_FOUND_EXCEPTION);
        } else if (ex instanceof IOException) {
            return ResultFactory.of(DefaultResultCode.IO_EXCEPTION);
            //} else if (ex instanceof UnauthorizedException) {
            // return ResultFactory.of(DefaultResultCode.TOKEN_INVALID);
        } else if (ex instanceof NullPointerException) {
            return ResultFactory.of(DefaultResultCode.NULL_EXCEPTION, ex.getMessage());
        } else if (ex instanceof HttpMessageNotReadableException) {
            return ResultFactory.of(DefaultResultCode.INVALID_FORMAT_EXCEPTION);
//        } else if (ex instanceof DuplicateKeyException) {
//            return ResultFactory.of(ResponseCode.DUPLICATE_KEY_EXCEPTION);
        } else if (ex instanceof BindException) {
            return handlerNotValidException(ex);
        } else if (ex instanceof SQLIntegrityConstraintViolationException) {
            return ResultFactory.of(DefaultResultCode.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION);
        } else  if(ex instanceof ShiroException){
            return ResultFactory.of(DefaultResultCode.TOKEN_INVALIDATION);
        }
            else {
            // 其他异常错误
            return ResultFactory.of(DefaultResultCode.DEFAULT_ERROR, ex.getMessage());
        }
    }

    /**
     * 对方法参数校验异常处理方法(仅对于表单提交有效，对于以json格式提交将会失效)
     * 如果是表单类型的提交，则spring会采用表单数据的处理类进行处理（进行参数校验错误时会抛出BindException异常）
     */
    @ExceptionHandler(BindException.class)
    public Result handlerBindException(BindException exception) {
        return handlerNotValidException(exception);
    }

    /**
     * 对方法参数校验异常处理方法(前端提交的方式为json格式出现异常时会被该异常类处理)
     * json格式提交时，spring会采用json数据的数据转换器进行处理（进行参数校验时错误是抛出MethodArgumentNotValidException异常）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerArgumentNotValidException(MethodArgumentNotValidException exception) {
        return handlerNotValidException(exception);
    }

    private Result handlerNotValidException(Exception e) {
        BindingResult result;
        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            result = exception.getBindingResult();
        } else {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            result = exception.getBindingResult();
        }

        Map<String, Object> map = Maps.newHashMap();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                map.put(error.getField(), error.getDefaultMessage());
            });
        }
        return ResultFactory.of(DefaultResultCode.INVALID_PARAMETER, map.toString());
    }
}
