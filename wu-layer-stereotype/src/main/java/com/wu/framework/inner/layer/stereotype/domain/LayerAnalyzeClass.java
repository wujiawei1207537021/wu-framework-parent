package com.wu.framework.inner.layer.stereotype.domain;

import lombok.Data;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 层解析class 对象
 * @date : 2020/7/4 下午10:17
 */
@Data
public class LayerAnalyzeClass {


    /**
     * 类型
     */
    private Class clazz;

    private List<LayerAnalyzeField> layerAnalyzeFieldList;


}
