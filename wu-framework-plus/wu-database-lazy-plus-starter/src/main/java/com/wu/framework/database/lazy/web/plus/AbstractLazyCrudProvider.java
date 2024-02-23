package com.wu.framework.database.lazy.web.plus;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyAoTable;
import com.wu.framework.inner.layer.data.convert.LazyDataFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.framework.response.repository.LazyCrudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe : 抽象惰性 Crud 存储库
 *
 * @param <T>  Uo、Ao、Entity
 * @param <ID> 主键
 *             如果在微服务内部使用请使用注解
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 19:03
 * @see LazyAoTable
 */
public abstract class AbstractLazyCrudProvider<T/*实体对象*/, MODEL/*返回数据模型*/, ID/*主键ID*/> implements LazyCrudProvider<T/*实体对象*/, MODEL/*返回数据模型*/, ID/*主键ID*/> {

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
    @Transactional(rollbackFor = Exception.class)
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
        final Class<T> classT = getClassT();
        Class<MODEL> classModel = getClassModel();
        final MODEL model = lazyLambdaStream.of(classT).selectOne(LazyWrappers.<T>wrapper().eq("id", id), classModel);
        return ResultFactory.successOf(model);
    }

    /**
     * 查询一条数据
     *
     * @param comparison
     * @return
     */
    @Override
    public Result<MODEL> findOne(LambdaBasicComparison<T> comparison) {
        Class<MODEL> classModel = getClassModel();
        MODEL model = lazyLambdaStream.selectOne(comparison.limit(1), classModel);
        return ResultFactory.successOf(model);
    }

    /**
     * 查询所有数据
     *
     * @param comparison
     * @return
     */
    @Override
    public Result<List<MODEL>> findAll(LambdaBasicComparison<T> comparison) {
        Class<MODEL> classModel = getClassModel();
        Collection<MODEL> modelCollection = lazyLambdaStream.selectList(comparison, classModel);
        return ResultFactory.successOf(modelCollection.stream().toList());
    }

    /**
     * describe  查询出所有的数据
     *
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<List<MODEL>> retrieveAll(@ModelAttribute T t) {
        final Class<T> classT = getClassT();
        Class<MODEL> classModel = getClassModel();
        final Collection<MODEL> collection = lazyLambdaStream.of(classT).selectList(LazyWrappers.lambdaWrapperBean(t), classModel);
        return ResultFactory.successOf(collection.stream().toList());
    }

    /**
     * describe  查询出分页的数据
     *
     * @param size    分页大小
     * @param current 当前页数
     * @param t       查询参数对象
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<LazyPage<MODEL>> retrievePage(int size, int current, T t) {
        final LazyPage<MODEL> lazyPage = new LazyPage<MODEL>(current, size);
        Class<MODEL> classModel = getClassModel();
        final LazyPage<MODEL> resultLazyPage = lazyLambdaStream.of(getClassT()).selectPage(LazyWrappers.lambdaWrapperBean(t), lazyPage, classModel);
        return ResultFactory.successOf(resultLazyPage);
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
    public Result deleteById(ID id) {
        // TODO 主键不是ID问题
        final Class<T> classT = getClassT();
        final Integer count = lazyLambdaStream.of(classT).delete(LazyWrappers.<T>wrapper().eq("id", id));
        if (1 == count) {
            return ResultFactory.successOf();
        } else {
            return ResultFactory.of(DefaultResultCode.DATA_NOT_FOUND);
        }
    }

    /**
     * describe  根据主键ID 批量删除
     *
     * @param ids 主键ID
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result batchDeleteById(List<ID> ids) {
        final Class<T> classT = getClassT();
        final Integer count = lazyLambdaStream.of(classT).delete(LazyWrappers.<T>wrapper().in("id", ids));
        return ResultFactory.successOf(count);

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
