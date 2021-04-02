package com.wu.framework.easy.stereotype.upsert.ienum;

/**
 * description 枚举配置适配器
 *
 * @author Jia wei Wu
 * @date 2020/8/15 下午1:25
 */
public interface IEnumAdapter {

    /**
     * 转换内容分隔符
     *
     * @return
     */
    default String[] getConvertContentSeparator() {
        return new String[]{","};
    }

    /**
     * 默认的字典
     *
     * @return
     */
    default String getDefaultCode() {
        return "-1";
    }
}
