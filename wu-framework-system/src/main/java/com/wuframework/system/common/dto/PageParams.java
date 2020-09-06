package com.wuframework.system.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongtiantian
 * @Date 2018/7/17 15:54
 * @Description 分页查询
 * @Version 1.0
 */
@Data
public class PageParams implements Serializable {

    private static final long serialVersionUID = -8445500869821839109L;

    /**
     * 最大页面数量
     */
    private static final Integer MAX_PAGE_SIZE = 100;

    /**
     * 第几页
     */
    private Integer current;

    /**
     * 多少条
     */
    private Integer size;

    /**
     * 初始化，设置默认参数
     */
    public PageParams() {
        this.current = 0;
        this.size = 10;
    }

    /**
     * 初始化
     *
     * @param current 当前索引
     * @param size    页面大小
     */
    public PageParams(final Integer current, final Integer size) {
        if (size > MAX_PAGE_SIZE) {
            this.size = MAX_PAGE_SIZE;
        } else {
            this.size = size;
        }
        this.current = current;
    }
}
