package com.wuframework.response.handler;


import com.google.common.collect.Maps;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.response.exceptions.CustomResponseException;
import com.wuframework.shiro.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.jdbc.BadSqlGrammarException;

/**
 * 基础异常抛出
 * 包含 类型转换异常  数组下表越界 文件没有找到 输入输出  空指针  参数校验 自定义异常 除数为零
 */

@Slf4j
@RestControllerAdvice
public class GlobalBaseExceptionHandler {
    /**
     * 所有异常报错
     * TODO  策略处理
     * @param ex
     * @return
     * @throws Exception
     */

    /**
     * 类转换异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ClassCastException.class)
    public Result classCastException(ClassCastException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.CLASS_CAST_EXCEPTION, exception.getMessage());
    }

    /**
     * 数组下表越界异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public Result arrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.ARR_EXCEPTION, exception.getMessage());
    }

    /**
     * @param exception
     * @return
     */
    @ExceptionHandler(FileNotFoundException.class)
    public Result fileNotFoundException(FileNotFoundException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.FILE_NOT_FOUND_EXCEPTION, exception.getMessage());
    }

    /**
     * 输入输出流异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(IOException.class)
    public Result iOException(IOException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.IO_EXCEPTION, exception.getMessage());
    }

    /**
     * 空指针异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException(NullPointerException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.NULL_EXCEPTION, exception.getMessage());
    }

    /**
     * 除数为零
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    public Result arithmeticException(ArithmeticException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.DIVISOR_IS_ZERO_EXCEPTION, exception.getMessage());
    }

    /**
     * 自定义异常
     *
     * @param exception
     * @return
     */
    @Deprecated
    @ExceptionHandler(CustomException.class)
    public Result customException(CustomException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.DEFAULT_ERROR, exception.getMessage());
    }

    /**
     * 自定义异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(com.wuframework.response.exceptions.CustomException.class)
    public Result customException(com.wuframework.response.exceptions.CustomException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.DEFAULT_ERROR, exception.getMessage());
    }

    /**
     * 自定义返回异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(CustomResponseException.class)
    public Result customResponseException(CustomResponseException exception) {
        exception.printStackTrace();
        return ResultFactory.of(exception);
    }

    /**
     * 对方法参数校验异常处理方法(仅对于表单提交有效，对于以json格式提交将会失效)
     * 如果是表单类型的提交，则spring会采用表单数据的处理类进行处理（进行参数校验错误时会抛出BindException异常）
     */
    @ExceptionHandler(BindException.class)
    public Result handlerBindException(BindException exception) {
        exception.printStackTrace();
        return handlerNotValidException(exception);
    }

    /**
     * 对方法参数校验异常处理方法(前端提交的方式为json格式出现异常时会被该异常类处理)
     * json格式提交时，spring会采用json数据的数据转换器进行处理（进行参数校验时错误是抛出MethodArgumentNotValidException异常）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerArgumentNotValidException(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        return handlerNotValidException(exception);
    }

    private Result handlerNotValidException(Exception e) {
        log.debug("begin resolve argument exception");
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
