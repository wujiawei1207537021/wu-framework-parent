package com.wu.framework.easy.temple.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:33
 */
@Data
public class SmartExcel {

    @JSONField(name = "标题")
    private String title = "标题";

    @JSONField(name = "内容")
    private String content = "内容";

}
