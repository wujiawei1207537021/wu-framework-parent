package com.wu.framework.inner.layer.stereotype.converter;

/**
 * describe : 层注解转换器
 * <p>
 *
 * </p>
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/2 19:09
 */
public interface LayerAnnotationConverterAdapter<Annotation extends java.lang.annotation.Annotation, EndPoint> {


    /**
     * 是否支持
     *
     * @param annotation 注解
     * @param endPoint   断点
     * @return
     */
    boolean supports(Annotation annotation, EndPoint endPoint);

    /**
     * @param annotation 原始注解
     * @return 返回断点信息
     */
    EndPoint converter(Annotation annotation);

    /**
     * describe 数据转换
     *
     * @param annotation 原始注解
     * @return 返回断点信息
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/2 19:20
     **/
    default EndPoint write(Annotation annotation) throws Exception {
        if (null == annotation) {
            return null;
        }
        EndPoint endPoint = converter(annotation);
        return endPoint;
    }
}
