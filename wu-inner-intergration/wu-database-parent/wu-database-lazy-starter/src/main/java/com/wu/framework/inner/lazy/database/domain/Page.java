package com.wu.framework.inner.lazy.database.domain;

import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 分页
 * @date : 2020/7/17 下午10:58
 */
@Data
public class Page<T> {

    private static final long serialVersionUID = 1L;

    /**
     * 总数
     */
    private long total;

    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 当前页
     */
    private int current = 1;

    /**
     * 查询总记录数（默认 true）
     */
    private boolean searchCount = true;

    /**
     * 开启排序（默认 true） 只在代码逻辑判断 并不截取sql分析
     *
     * @see com.baomidou.mybatisplus.mapper.SqlHelper#fillWrapper
     **/
    private boolean openSort = true;

    /**
     * 优化 Count Sql 设置 false 执行 select count(1) from (listSql)
     */
    private boolean optimizeCountSql = true;

    /**
     * <p>
     * SQL 排序 ASC 集合
     * </p>
     */
    private List<String> ascs;
    /**
     * <p>
     * SQL 排序 DESC 集合
     * </p>
     */
    private List<String> descs;

    /**
     * 是否为升序 ASC（ 默认： true ）
     *
     * @see #ascs
     * @see #descs
     */
    private boolean isAsc = true;

    /**
     * <p>
     * SQL 排序 ORDER BY 字段，例如： id DESC（根据id倒序查询）
     * </p>
     * <p>
     * DESC 表示按倒序排序(即：从大到小排序)<br>
     * ASC 表示按正序排序(即：从小到大排序)
     *
     * @see #ascs
     * @see #descs
     * </p>
     */
    private String orderByField;

    private Collection<T> record;

    public Page() {
        super();
    }

    /**
     * <p>
     * 分页构造函数
     * </p>
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public Page(int current, int size) {
        this(current, size, true);
    }

    public Page(int current, int size, boolean searchCount) {
        this(current, size, searchCount, true);
    }

    public Page(int current, int size, boolean searchCount, boolean openSort) {

        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.searchCount = searchCount;
        this.openSort = openSort;
    }


}
