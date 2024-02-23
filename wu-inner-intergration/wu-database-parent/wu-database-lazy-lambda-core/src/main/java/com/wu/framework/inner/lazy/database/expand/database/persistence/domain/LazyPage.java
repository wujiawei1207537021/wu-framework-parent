package com.wu.framework.inner.lazy.database.expand.database.persistence.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * description 分页
 *
 * @author 吴佳伟
 * @date 1.3.23 13:47
 * @see
 */
@Data
public class LazyPage<T> implements Serializable {

    @Serial
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

    /**
     * 记录
     */
    private Collection<T> record;

    /**
     * 额外字段
     */
    private Object ext;

    public LazyPage() {
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
    public LazyPage(int current, int size) {
        this(current, size, true);
    }


    public LazyPage(int current, int size, boolean searchCount) {
        this(current, size, searchCount, true);
    }

    public LazyPage(int current, int size, boolean searchCount, boolean openSort) {

        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.searchCount = searchCount;
        this.openSort = openSort;
    }

    public LazyPage(int current, int size, Collection<T> record) {

        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.record = record;
    }

    /**
     * @param current 当前页
     * @param size    每页显示条数
     * @return
     */
    public static <T> LazyPage<T> of(int current, int size) {
        return new LazyPage<T>(current, size);
    }

    public static <T> LazyPage<T> of(int current, int size, Collection<T> record) {
        return new LazyPage<T>(current, size, record);
    }

    public Collection<T> getRecords() {
        return record;
    }

    /**
     * 转换返回的实体对象
     *
     * @param mapper 转换函数
     * @param <R>    需要转换的返回数据范型
     * @return 返回转换后的实体对象相关的分页数据
     */
    public <R> LazyPage<R> convert(Function<? super T, ? extends R> mapper) {
        LazyPage<R> lazyPage = new LazyPage<>(current, size);
        lazyPage.setTotal(total);
        Collection<R> collect = this.getRecord().stream().map(mapper).collect(Collectors.toList());
        lazyPage.setRecord(collect);
        return lazyPage;
    }
}
