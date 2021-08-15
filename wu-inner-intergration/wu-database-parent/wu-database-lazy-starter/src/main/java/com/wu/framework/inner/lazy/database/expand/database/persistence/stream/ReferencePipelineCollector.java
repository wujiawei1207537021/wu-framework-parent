package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;

import java.util.Collection;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 执行sql 并返回数据
 * @date : 2021/8/8 11:47 上午
 */
public abstract class ReferencePipelineCollector<T, R> extends ReferencePipelineSplicing<T, R>
        implements LambdaStreamCollector<T, R> {
    private final LazyOperation lazyOperation;

    protected ReferencePipelineCollector(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    //    private final LazyOperation lazyOperation;
//    protected T t;

    /**
     * @param r1Class@return
     * @describe 返回指定类型的数据
     * 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:03 下午
     **/
    @Override
    public <R> Collection<R> collection(Class<R> r1Class) {
        return lazyOperation.executeSQL(getSqlStatement(), r1Class);
    }

    /**
     * @return
     * @describe 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public Collection<T> collection() {
        return null;
    }

    /**
     * @param r1Class@return
     * @describe 返回指定类型的数据
     * 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public <R1> T collectOne(Class<R1> r1Class) {
        return null;
    }

    /**
     * @return
     * @describe 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public T collectOne() {
        return null;
    }


//    public ReferencePipelineCollector(LazyOperation lazyOperation) {
//        this.lazyOperation = lazyOperation;
//        Class<?> entitiClass = null;
//        Type genericSuperclass = this.getClass().getGenericSuperclass();
//        if (genericSuperclass instanceof ParameterizedType) {
//            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
//                    .getActualTypeArguments();
//            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
//                entitiClass = (Class<?>) actualTypeArguments[0];
//            }
//        }
//        System.out.println(entitiClass);
//    }


}
