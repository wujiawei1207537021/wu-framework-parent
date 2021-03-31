package com.wu.freamwork;


import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import lombok.Data;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * description Es使用sql 工具
 *
 * @author Jiawei Wu
 * @date 2021/1/5 下午4:37
 */
public class ElasticsearchSQLTemplate {

    private static final String URL = "http://81.69.3.45:30820/_xpack/sql";
    // 后缀
    private static final String suffix = "/_sql?format=json";
    // 数据条数
    private final Integer defaultSize = 1000;
    // 滚动间隔时间
    private final Long defaultScrollIntervalTime;

    private final RestTemplate defaultRestTemplate;
    // ES地址
    private final String esUrl;


    private ElasticsearchSQLTemplate(RestTemplate defaultRestTemplate, String esUrl, Long defaultScrollIntervalTime) {
        this.defaultRestTemplate = defaultRestTemplate;
        this.esUrl = esUrl;
        this.defaultScrollIntervalTime = defaultScrollIntervalTime;
    }

    public static ElasticsearchSQLTemplate build(String esUrl) {
        return new ElasticsearchSQLTemplate(new RestTemplateBuilder().build(), esUrl, 10000L);
    }

    public static ElasticsearchSQLTemplate build(RestTemplate restTemplate, String esUrl) {
        return new ElasticsearchSQLTemplate(restTemplate, esUrl, 10000L);
    }

    public static ElasticsearchSQLTemplate build(RestTemplate restTemplate, String esUrl, Long defaultScrollIntervalTime) {
        return new ElasticsearchSQLTemplate(restTemplate, esUrl, defaultScrollIntervalTime);
    }

    public <T> List<T> search(String sql, Class<T> clazz, Object... objects) {
        return search(sql, defaultSize, clazz, objects);
    }

    public <T> List<T> search(String sql, Integer size, Class<T> clazz, Object... objects) {
        return result2JavaBean(searchForESResult(sql, size, objects), clazz);
    }


    private ESResult searchForESResult(String sql, Integer size, Object... objects) {
        Map<String, Object> request = new HashMap<>(2);
        String query = ObjectUtils.isEmpty(objects) ? sql : MessageFormat.format(sql, objects);
        request.put("query", query);
        request.put("fetch_size", size);
        HttpEntity httpEntity = new HttpEntity(request, null);
        System.err.printf("执行的查询语句:%s \n", query);
        ResponseEntity<ESResult> exchange = defaultRestTemplate.exchange(esUrl + suffix, HttpMethod.POST, httpEntity, ESResult.class);
        return exchange.getBody();
    }


    public <T> List<T> scroll(String sql, Class<T> clazz, Object... objects) {
        try {
            return scroll(sql, clazz, defaultScrollIntervalTime, objects);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> List<T> scroll(String sql, Class<T> clazz, Long scrollIntervalTime, Object... objects) throws InterruptedException {
        // 睡眠
        if (scrollIntervalTime > 0)
            Thread.sleep(scrollIntervalTime);
        ESResult esResult = searchForESResult(sql, defaultSize, objects);
        List<T> scrollList = new ArrayList<>();
        int scrollTime = 1;
        scrollList.addAll(result2JavaBean(esResult, clazz));
        while (!ObjectUtils.isEmpty(esResult.getCursor())) {
            System.out.println(String.format("ES滚动查询数据 第%s次", scrollTime++));
            ESResult cursorEsResult = cursor(esResult.getCursor());
            cursorEsResult.setColumns(esResult.getColumns());
            esResult = cursorEsResult;
            List<T> list = result2JavaBean(esResult, clazz);
            scrollList.addAll(list);
        }
        return scrollList;
    }


    private ESResult cursor(String cursor) {
        Map<String, String> request = new HashMap<>(1);
        request.put("cursor", cursor);
        HttpEntity httpEntity = new HttpEntity(request, null);
        return defaultRestTemplate.exchange(esUrl + suffix, HttpMethod.POST, httpEntity, ESResult.class).getBody();
    }


    /**
     * description 将数据简单转换为对象 当前只支持String 类型数据转换
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2021/1/6 上午10:01
     */
    private <T> List<T> result2JavaBean(ESResult esResult, Class<T> clazz) {

        return esResult.rows.stream().map(list -> {
            T t = null;
            if (Map.class.isAssignableFrom(clazz)) {
                EasyHashMap easyHashMap = new EasyHashMap();
                // 获取行列名称
                Stream.iterate(0, i -> i + 1).
                        limit(esResult.getColumns().size()).
                        collect(Collectors.toMap(i -> i, i -> CamelAndUnderLineConverter.lineToHump(esResult.getColumns().get(i).getName()))).entrySet().
                        stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).forEach((index, fileName) -> {
                    Object o = list.get(index);
                    easyHashMap.put(fileName, o);
                });
                t = (T) easyHashMap;
            } else {
                // 获取实体字段
                List<String> declaredFieldList = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
                // 获取查询语句中列名称
                Map<Integer, String> columnMap = Stream.iterate(0, i -> i + 1).
                        limit(esResult.getColumns().size()).
                        collect(Collectors.toMap(i -> i, i -> CamelAndUnderLineConverter.lineToHump(esResult.getColumns().get(i).getName()))).entrySet().
                        stream().
                        filter(integerStringEntry -> declaredFieldList.contains(integerStringEntry.getValue())).
                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                try {
                    t = clazz.newInstance();
                    T finalT = t;
                    columnMap.forEach((index, fileName) -> {
                        Object o = list.get(index);
                        try {
                            Field declaredField = clazz.getDeclaredField(fileName);
                            declaredField.setAccessible(true);
                            declaredField.set(finalT, o);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return t;
        }).collect(Collectors.toList());
    }

    @Data
    private static class ESResult {
        private List<Column> columns;
        private List<List<Object>> rows;
        private String cursor;
    }

    @Data
    private static class Column {
        private String name;
        private String type;
    }


}
