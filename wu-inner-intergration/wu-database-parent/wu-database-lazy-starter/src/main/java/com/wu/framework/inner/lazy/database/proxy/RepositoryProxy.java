package com.wu.framework.inner.lazy.database.proxy;

import com.wu.framework.inner.lazy.database.domain.CustomRepository;
import com.wu.framework.inner.lazy.database.domain.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 执行sql 的代理类
 * @date : 2020/6/25 下午11:19
 */
@Deprecated
public class RepositoryProxy implements InvocationHandler, InitializingBean {


    private final Log log = LogFactory.getLog(RepositoryProxy.class);

    private final Connection connection;
    private Map<String, CustomRepository> customRepositoryMap;


    public RepositoryProxy(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    /**
     * 获取接口参数中分页对象
     *
     * @param method
     * @param args
     * @return
     */
    public static Page pageObject(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType().isAssignableFrom(Page.class)) {
                return (Page) args[i];
            }
        }
        return null;
    }

//    public Map<String, CustomRepository> getCustomRepositoryMap() {
//        if (customRepositoryMap == null) {
//            // 静态获取
//            this.customRepositoryMap = xxConfig.customRepositoryMap;
//        }
//        return customRepositoryMap;
//    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        CustomRepository customRepository;
//        // TODO 数据库操作
//        //1.获取方法名
//        String methodName = method.getName();
//        if ("toString".equals(methodName)) {
//            return this.toString();
//        }
//
//        Select select = method.getAnnotation(Select.class);
//        if (null == select) {
//            //2.获取方法所在类的名称
//            String className = method.getDeclaringClass().getName();
//            //3.组合key
//            String key = className + "." + methodName;
//            //4.获取mappers中的Mapper对象
//            customRepository = getCustomRepositoryMap().get(key);
//        } else {
//            customRepository = new CustomRepository();
//            customRepository.setQueryString(select.methodName());
//            customRepository.setExecuteType(CustomRepositoryXmlScan.ExecuteType.SELECT);
//            Type genericReturnType = method.getGenericReturnType();
//            if (null != genericReturnType) {
//                ParameterizedType pt = (ParameterizedType) method.getGenericReturnType();
//                // 得到泛型里的class类型对象
//                Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];
//                customRepository.setResultClass(actualTypeArgument);
//            } else {
//                customRepository.setResultClass(method.getReturnType());
//            }
//        }
//
//        //5.判断是否有mapper
//        if (customRepository == null) {
//            throw new IllegalArgumentException("传入的参数有误");
//        }
//        Class returnType = method.getReturnType();
//        //6.调用工具类执行查询所有
//        if (customRepository.getExecuteType().equals(CustomRepositoryXmlScan.ExecuteType.SELECT)) {
//            Collection resultCollection = CustomExecutor.selectList(method, args, customRepository, connection);
//            Page page = pageObject(method, args);
//            if (null != page) {
//                long total = CustomExecutor.selectCount(method, args, customRepository, connection);
//                page.setTotal(total);
//                page.setPages(total / page.getSize());
//                page.setRecord(resultCollection);
//            }
//            if (Collection.class.isAssignableFrom(returnType)) {
//                return resultCollection;
//            } else {
//                if (resultCollection.size() > 1) {
//                    throw new RuntimeException(" expect one but found" + resultCollection.size());
//                } else {
//                    if (resultCollection.iterator().hasNext()) {
//                        return resultCollection.iterator().next();
//                    }
//                    return null;
//                }
//
//            }
//        } else {
//            PreparedStatement preparedStatement = connection.prepareStatement(customRepository.getQueryString());
//            int row = preparedStatement.executeUpdate();
//            preparedStatement.close();
//            return row;
//        }

        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("init bean of RepositoryProxy");
        if (ObjectUtils.isEmpty(customRepositoryMap)) {
            return;
        }
    }


}
