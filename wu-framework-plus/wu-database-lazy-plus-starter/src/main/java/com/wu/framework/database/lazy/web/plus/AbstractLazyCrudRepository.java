package com.wu.framework.database.lazy.web.plus;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.repository.LazyCrudRepository;

import java.lang.reflect.ParameterizedType;

/**
 * describe : 抽象惰性 Crud 存储库
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 19:03
 */
public abstract class AbstractLazyCrudRepository<T, ID> implements LazyCrudRepository<T, ID> {

    private Class<T> type;


    private final LazyLambdaStream lazyLambdaStream;

    protected AbstractLazyCrudRepository(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }


    /**
     * describe  新增
     *
     * @param s 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public <S extends T> Result<S> save(S s) {
        lazyLambdaStream.smartUpsert(s);
        return ResultFactory.successOf(s);
    }

    /**
     * describe  根据更新
     *
     * @param s 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public <S extends T> Result<S> update(S s) {
        lazyLambdaStream.smartUpsert(s);
        return ResultFactory.successOf(s);
    }

    /**
     * describe  根据主键ID查询
     *
     * @param id 主键ID
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<T> findById(ID id) {
        final Class<T> classT = getClassT();
        final T id1 = lazyLambdaStream.of(classT).select(LazyWrappers.wrapper().eq("id", id)).collectOne(classT);
        return ResultFactory.successOf(id1);
    }

    /**
     * describe  根据主键ID查询是否存在
     *
     * @param id 主键ID
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<Boolean> existsById(ID id) {
        final Class<T> classT = getClassT();
        final boolean exists = lazyLambdaStream.of(classT).exists(LazyWrappers.wrapper().eq("id", id));
        return ResultFactory.successOf(exists);
    }

    /**
     * describe  统计表数据
     *
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<Long> count() {
        final Class<T> classT = getClassT();
        final Long count = lazyLambdaStream.of(classT).count(null);
        return ResultFactory.successOf(count);
    }

    /**
     * describe  根据主键ID 删除
     *
     * @param id 主键ID
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result deleteById(ID id) {
        final Class<T> classT = getClassT();
        final Integer count = lazyLambdaStream.of(classT).delete(LazyWrappers.wrapper().eq("id", id));
        if (1 == count) {
            return ResultFactory.successOf();
        } else {
            return ResultFactory.errorOf();
        }
    }

    public Class<T> getClassT() {
        if (null == type) {
            ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
            this.type = (Class<T>) superClass.getActualTypeArguments()[0];
        }
        return type;
    }
}
