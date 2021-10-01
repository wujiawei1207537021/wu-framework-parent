package com.wu.framework.inner.database.util;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/8/12 下午7:39
 */
public class DyMethodUtil {

    public static Object invokeMethod(String expression, Map<String, Object> map) {
        JexlEngine jexlEngine = new Engine();
        JexlExpression e = jexlEngine.createExpression(expression);
        JexlContext jc = new MapContext();
        if (null != map) {
            for (String key : map.keySet()) {
                jc.set(key, map.get(key));
            }
        }
        e.evaluate(null);
        if (null == e.evaluate(jc)) {
            return "";
        }
        return e.evaluate(jc);
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("alive", "4==3");
        map.put("out", System.out);
        map.put("money", 2100);
        String expression = "2100>=2000&&2100<=4000";
//        String expression = "out.print(alive)";
        Object o = invokeMethod(expression, null);
        System.out.println(o);

    }

}
