package com.wu.framework.authorization.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * describe : 接口
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 18:36
 */
@LazyTable(comment = "接口")
@Data
public class Interface {

    /**
     * 应用名称
     */
    @LazyTableField(comment = "应用名称")
    private String applicationName;
    /**
     * 请求方法
     */
    @LazyTableField(comment = "请求方法")
    private List<RequestMethod> requestMethods;

    /**
     * 父路径
     */
    @LazyTableField(comment = "父路径")
    private List<String> parentPath;
    /**
     * 路径
     */
    @LazyTableField(comment = "路径")
    private List<String> path;

    /**
     * 接口描述
     */
    @LazyTableField(comment = "接口描述")
    private String desc;

    /**
     * 接口标签
     */
    @LazyTableField(comment = "接口标签")
    private List<String> tag;
}
