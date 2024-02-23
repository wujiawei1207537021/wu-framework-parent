package com.wu.framework.response.enmus;

import com.wu.framework.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ Description : 框架code @ Author : wujiawei @ CreateDate : 2019/12/20 0020 16:20 @ UpdateUser :
 * wujiawei @ UpdateDate : 2019/12/20 0020 16:20 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum DefaultResultCode implements ResultCode {
    /**
     * 数据请求成功
     */
    SUCCESS(0, "请求成功!"),
    PARAMETER_ERROR(100001, "参数不能为空!"),
    DATA_UPLOAD_ERROR(100002, "数据上传有误!"),
    INVALID_PARAMETER(100003, "参数错误！"),
    INVALID_USER(100004, "用户未登陆，或者登陆时效已经超时"),
    MISS_PARAMETER(100005, "缺少参数!"),
    RESOURCE_NOT_FOUNT(100006, "请求的资源不存在或已删除!"),
    PERSISTENT_DATA_ERROR(100007, "数据有误!"),
    UPDATE_FAIL(100008, "修改数据失败!"),
    DATA_NOT_FOUND(100009, "数据未找到"),
    DATA_OCCUPATION_DELETE_FAIL(100010, "数据占用删除错误"),
    DATA_OCCUPATION_UPDATE_FAIL(100011, "数据占用更新错误"),
    CLASS_CAST_EXCEPTION(100013, "类型强制异常"),
    ARR_EXCEPTION(100014, "数组下标越界异常"),
    FILE_NOT_FOUND_EXCEPTION(100015, "文件未找到异常"),
    IO_EXCEPTION(100016, "文件操作失败"),
    NULL_EXCEPTION(100017, "空指针异常"),
    TOKEN_INVALID(100018, "token无效"),
    INVALID_FORMAT_EXCEPTION(100019, "格式转换异常"),
    DUPLICATE_KEY_EXCEPTION(100020, "数据重复异常"),
    TOKEN_AUTHORIZATION_FAILED(100021, "令牌授权失败"),
    DIVISOR_IS_ZERO_EXCEPTION(100022, "除数为零"),
    INTERNAL_SERVER_ERROR(500, "服务器遇到错误，无法完成请求"),
    /**
     * 权限错误
     */
    PASSWORD_ERROR(4, "密码错误!"),
    UN_AUTHORIZATION(4001, "没有权限访问!"),
    AUTHORIZATION_FAILURE(4002, "授权失败！"),
    TOKEN_INVALIDATION(4003, "令牌失效！"),
    /**
     * SQL
     */
    SQL_EXCEPTION(110001, "SQL语法错误异常"),
    SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION(110002, "SQL完整性约束违反异常"),
    TRANSACTION_REQUIRED_EXCEPTION(110003, "要求开启事务管理"),
    /**
     * QR
     */
    QR_ASSOCIATION_USER_EXCEPTION(600001, "当前用户没有关联第三方平台"),
    QR_OPENID_EXCEPTION(600002, "第三方平台openid获取失败"),
    QR_NET_EXCEPTION(600003, "第三方平台连接网络异常"),
    /**
     * 默认的系统控制
     */
    REFLEX_EXCEPTION(9998, "反射异常"),
    DEFAULT_ERROR(9999, "请求异常!");

    /**
     * 错误编码
     */
    public Integer code;

    /**
     * 错误信息
     */
    public String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}