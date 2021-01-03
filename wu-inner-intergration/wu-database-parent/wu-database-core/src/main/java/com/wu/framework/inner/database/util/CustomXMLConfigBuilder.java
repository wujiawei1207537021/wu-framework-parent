package com.wu.framework.inner.database.util;


import com.wu.framework.inner.database.domain.CustomRepository;

import com.wu.framework.inner.lazy.database.io.CustomResources;
import com.wu.framework.inner.lazy.database.stereotype.CustomRepositoryXmlScan;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomXMLConfigBuilder {

    public final static SAXReader reader = new SAXReader();

    /**
     * 根据传入的参数，解析XML，并且封装到Map中
     *
     * @param mapperPath 映射配置文件的位置
     * @return map中包含了获取的唯一标识（key是由dao的全限定类名和方法名组成）
     * 以及执行所需的必要信息（value是一个Mapper对象，里面存放的是执行的SQL语句和要封装的实体类全限定类名）
     */
    public static Map<String, CustomRepository> loadMapperConfiguration(String mapperPath) {
        InputStream in = null;
        try {
            //定义返回值对象
            Map<String, CustomRepository> mappers = new HashMap<String, CustomRepository>();
            //1.根据路径获取字节输入流
            in = CustomResources.getResourceAsStream(mapperPath);
            //2.根据字节输入流获取Document对象
//            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            //3.获取根节点
            Element root = document.getRootElement();
            //4.获取根节点的namespace属性取值
            String namespace = root.attributeValue("namespace");//是组成map中key的部分
            //5.获取所有的select节点
            List<Element> selectElements = root.selectNodes("//select");
            //6.遍历select节点集合
            for (Element selectElement : selectElements) {
                //取出id属性的值      组成map中key的部分
                String id = selectElement.attributeValue("id");
                //取出resultType属性的值  组成map中value的部分
                String resultType = selectElement.attributeValue("resultType");
                //取出文本内容            组成map中value的部分
                String queryString = selectElement.getText();
                //创建Key
                String key = namespace + "." + id;
                //创建Value
                CustomRepository mapper = new CustomRepository();
                mapper.setQueryString(queryString);
                mapper.setResultType(resultType);
                //把key和value存入mappers中
                mappers.put(key, mapper);
            }
            return mappers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, CustomRepository> loadMapperConfiguration(InputStream in) {
        try {
            //定义返回值对象
            Map<String, CustomRepository> mappers = new HashMap<String, CustomRepository>();
            //1.根据路径获取字节输入流
            //2.根据字节输入流获取Document对象
//            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            //3.获取根节点
            Element root = document.getRootElement();
            //4.获取根节点的namespace属性取值
            String namespace = root.attributeValue("namespace");//是组成map中key的部分
            //5.获取所有的select节点
            List<Element> elementList = root.selectNodes("//select");
            elementList.addAll(root.selectNodes("//insert"));
            elementList.addAll(root.selectNodes("//delete"));
            elementList.addAll(root.selectNodes("//update"));
            //6.遍历select节点集合
            for (Element element : elementList) {
                //取出id属性的值      组成map中key的部分
                String nodeName = element.getName();
//                System.out.println(element.getName());
                String id = element.attributeValue("id");
                //取出resultType属性的值  组成map中value的部分
                String resultType = element.attributeValue("resultType");

                CustomRepository customRepository = new CustomRepository();
                List<CustomRepository.IF> ifs = new ArrayList<>();
                customRepository.setIfList(ifs);
                // if 标签
                StringBuffer stringBuffer = new StringBuffer();
                for (Object o : element.content()) {
                    if (o.getClass().isAssignableFrom(DefaultText.class)) {
                        stringBuffer.append(((DefaultText) o).getText());
                    } else {
                        stringBuffer.append("##");
                        String ifAttr = ((DefaultElement) o).attributeValue("test");
                        String ifContext = ((DefaultElement) o).getText();
                        CustomRepository.IF i = new CustomRepository.IF();
                        i.setIfAttr(ifAttr);
                        i.setIfContext(ifContext);
                        ifs.add(i);
                    }
                }
                //取出文本内容            组成map中value的部分
                String queryString = stringBuffer.toString();
                //创建Key
                String key = namespace + "." + id;
                //创建Value

                customRepository.setQueryString(queryString);
                customRepository.setResultType(resultType);
                if ("select".equals(nodeName)) {
                    customRepository.setExecuteType(CustomRepositoryXmlScan.ExecuteType.SELECT);
                } else {
                    customRepository.setExecuteType(CustomRepositoryXmlScan.ExecuteType.UPDATE);
                }
                //把key和value存入mappers中
                mappers.put(key, customRepository);
            }

            return mappers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Class loadMapperNamespace(InputStream in) {
        //1.根据路径获取字节输入流
        //2.根据字节输入流获取Document对象
//        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(in);
            //3.获取根节点
            Element root = document.getRootElement();
            //4.获取根节点的namespace属性取值
            String namespace = root.attributeValue("namespace");//是组成map中key的部分
            Class clazz = Class.forName(namespace);
            return clazz;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 根据传入的参数，得到dao中所有被select注解标注的方法。
     * 根据方法名称和类名，以及方法上注解value属性的值，组成Mapper的必要信息
     * @param daoClassPath
     * @return
     */
//    private static Map<String,Mapper> loadMapperAnnotation(String daoClassPath)throws Exception{
//        //定义返回值对象
//        Map<String,Mapper> mappers = new HashMap<String, Mapper>();
//
//        //1.得到dao接口的字节码对象
//        Class daoClass = Class.forName(daoClassPath);
//        //2.得到dao接口中的方法数组
//        Method[] methods = daoClass.getMethods();
//        //3.遍历Method数组
//        for(Method method : methods){
//            //取出每一个方法，判断是否有select注解
//            boolean isAnnotated = method.isAnnotationPresent(Select.class);
//            if(isAnnotated){
//                //创建Mapper对象
//                Mapper mapper = new Mapper();
//                //取出注解的value属性值
//                Select selectAnno = method.getAnnotation(Select.class);
//                String queryString = selectAnno.value();
//                mapper.setQueryString(queryString);
//                //获取当前方法的返回值，还要求必须带有泛型信息
//                Type type = method.getGenericReturnType();//List<User>
//                //判断type是不是参数化的类型
//                if(type instanceof ParameterizedType){
//                    //强转
//                    ParameterizedType ptype = (ParameterizedType)type;
//                    //得到参数化类型中的实际类型参数
//                    Type[] types = ptype.getActualTypeArguments();
//                    //取出第一个
//                    Class domainClass = (Class)types[0];
//                    //获取domainClass的类名
//                    String resultType = domainClass.getName();
//                    //给Mapper赋值
//                    mapper.setResultType(resultType);
//                }
//                //组装key的信息
//                //获取方法的名称
//                String methodName = method.getName();
//                String className = method.getDeclaringClass().getName();
//                String key = className+"."+methodName;
//                //给map赋值
//                mappers.put(key,mapper);
//            }
//        }
//        return mappers;
//    }
}
