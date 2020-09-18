package com.wu.framework.easy.stereotype.upsert.upsert.entity;

import java.util.Arrays;
import java.util.List;

/**
 * description 更新插入json数据
 *
 * @author 吴佳伟
 * @date 2020/9/7 下午2:19
 */
public class UpsertJsonMessage {
    /**
     * 忽略的字段
     */
    public static List<String> ignoredFields= Arrays.asList("serialVersionUID");

}
