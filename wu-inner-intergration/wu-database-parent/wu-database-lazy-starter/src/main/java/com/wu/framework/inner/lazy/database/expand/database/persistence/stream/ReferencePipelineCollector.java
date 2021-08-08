package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import java.util.Collection;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 执行sql 并返回数据
 * @date : 2021/8/8 11:47 上午
 */
public class ReferencePipelineCollector<T> implements LambdaStreamCollector<T> {

    //    private final LazyOperation lazyOperation;
    protected T t;

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


    /**
     * @param rClass@return
     * @describe 返回指定类型的数据
     * 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:03 下午
     **/
    @Override
    public <R> Collection<R> collection(Class<R> rClass) {
        return null;
    }

    /**
     * 收集集合
     *
     * @return
     */
    @Override
    public Collection<T> collection() {
        return null;
    }

    /**
     * @param rClass@return
     * @describe 返回指定类型的数据
     * 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public <R> T collectOne(Class<R> rClass) {
        return null;
    }

    /**
     * 收集一个数据
     *
     * @return
     */
    @Override
    public T collectOne() {
        return null;
    }
}
