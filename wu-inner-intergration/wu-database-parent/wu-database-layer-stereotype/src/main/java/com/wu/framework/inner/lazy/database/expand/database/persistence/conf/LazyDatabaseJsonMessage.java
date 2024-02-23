package com.wu.framework.inner.lazy.database.expand.database.persistence.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description 更新插入json数据
 *
 * @author Jia wei Wu
 * @date 2020/9/7 下午2:19
 */
public class LazyDatabaseJsonMessage {
    /**
     * 忽略的字段
     */
    public static List<String> ignoredFields = Arrays.asList("serialVersionUID");

    /**
     * 特殊字段
     */
    public static List<String> specialFields= new ArrayList<>();

}
