package com.wu.framework.database.lazy.web.plus;

import com.wu.framework.inner.layer.data.convert.LazyDataFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.repository.LazyCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe : 抽象惰性 Crud 存储库
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 19:03
 */
public abstract class AbstractLazyCrudRepository<T/*实体对象*/, MODEL/*返回数据模型*/, ID/*主键ID*/> implements LazyCrudRepository<T/*实体对象*/, MODEL/*返回数据模型*/, ID/*主键ID*/> {


    @Autowired
    private LazyLambdaStream lazyLambdaStream;
    private Class<T> type; // 实体模型

    private Class<MODEL> model;// 领域模型


    /**
     * describe  新增
     *
     * @param model 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<MODEL> save(MODEL model) {
        T handler = LazyDataFactory.handler(model, getClassT());
        lazyLambdaStream.upsert(handler);
        return ResultFactory.successOf(model);
    }

    /**
     * describe  批量存储 增量更新
     *
     * @param models 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<Collection<MODEL>> upsert(Collection<MODEL> models) {
        List<T> tList = models.stream().map(model1 -> LazyDataFactory.handler(model1, getClassT())).collect(Collectors.toList());
        lazyLambdaStream.upsert(tList);
        return ResultFactory.successOf(models);
    }

    /**
     * describe  根据更新
     *
     * @param model 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<MODEL> update(MODEL model) {
        T handler = LazyDataFactory.handler(model, getClassT());
        lazyLambdaStream.upsert(handler);
        return ResultFactory.successOf(model);
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
    public Result<MODEL> findById(ID id) {
        Class<T> classT = getClassT();
        Class<MODEL> classModel = getClassModel();
        final MODEL model = lazyLambdaStream.of(classT).selectOne(LazyWrappers.<T>wrapper().eq("id", id), classModel);
        return ResultFactory.successOf(model);
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
        final boolean exists = lazyLambdaStream.of(classT).exists(LazyWrappers.<T>wrapper().eq("id", id));
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
    public Result<Void> deleteById(ID id) {
        final Class<T> classT = getClassT();
        final Integer count = lazyLambdaStream.of(classT).delete(LazyWrappers.<T>wrapper().eq("id", id));
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

    public Class<MODEL> getClassModel() {
        if (null == model) {
            ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
            this.model = (Class<MODEL>) superClass.getActualTypeArguments()[1];
        }
        return model;
    }

    public LazyLambdaStream getLazyLambdaStream() {
        return lazyLambdaStream;
    }
}
