package com.wu.framework.inner.lazy.persistence.reverse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法参数描述
 */
@Data
public class ParamDescribeList {
    private List<ParamDescribe> paramDescribeList = new ArrayList<>();// 参数
    private List<ParamDescribe> returnParamDescribeList = new ArrayList<>(); // 返回结果
    private List<ParamDescribe> exceptionDescribeList = new ArrayList<>(); // 异常

    public static ParamDescribeList of() {
        return new ParamDescribeList();
    }

    /**
     * 添加参数描述
     *
     * @param param    参数
     * @param describe 参数描述
     * @return 当前对象
     */
    public ParamDescribeList param(String param, String describe) {
        ParamDescribe paramDescribe = new ParamDescribe(param, describe);
        paramDescribeList.add(paramDescribe);
        return this;
    }

    /**
     * 返回结果
     *
     * @param param    返回对象
     * @param describe 返回对象描述
     * @return 当前对象
     */
    public ParamDescribeList returnParam(String param, String describe) {
        ParamDescribe paramDescribe = new ParamDescribe(param, describe);
        returnParamDescribeList.add(paramDescribe);
        return this;
    }

    /**
     * 返回结果
     *
     * @param param    返回对象
     * @param describe 返回对象描述
     * @return 当前对象
     */
    public ParamDescribeList exception(String param, String describe) {
        ParamDescribe paramDescribe = new ParamDescribe(param, describe);
        exceptionDescribeList.add(paramDescribe);
        return this;
    }

}

/**
 * 方法参数描述
 */
@AllArgsConstructor
@Data
class ParamDescribe {

    // 参数
    private String param;
    // 参数描述
    private String describe;


}
