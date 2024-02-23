package com.wu.framework.inner.lazy.database.other.converter;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.other.domain.CustomRepository;
import com.wu.framework.inner.lazy.stereotype.PageNote;
import com.wu.framework.inner.lazy.stereotype.Param;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.internal.Engine;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : xml 文件解析
 * @date : 2020/7/31 下午10:43
 */

public class Parser {

    private static final JexlEngine JEXL_ENGINE = new Engine();

    /**
     * customRepository 中标签(if)内容判断
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/8/9 下午8:31
     **/
    public static String parse2(Method method, Object[] args, CustomRepository customRepository) {
        String queryString = customRepository.getQueryString();
        StringBuffer stringBuffer = new StringBuffer();

        String[] strings = queryString.split("##");
        stringBuffer.append(strings[0]);
        if (!ObjectUtils.isEmpty(customRepository.getIfList())) {
            for (int i = 0; i < customRepository.getIfList().size(); i++) {
                String ifAttr = customRepository.getIfList().get(i).getIfAttr();
                // 判断 属性条件是否满足
                if (ifAttr(method, args, ifAttr)) {
                    stringBuffer.append(customRepository.getIfList().get(i).getIfContext());
                }
                stringBuffer.append(strings[i + 1]);
            }
        }
        return parse2(method, args, stringBuffer.toString(), true);
    }

    /**
     * 判断标签内容是否添加
     *
     * @param method
     * @param args
     * @param ifAttr
     * @return
     */
    private static boolean ifAttr(Method method, Object[] args, String ifAttr) {
        ifAttr = substituteObjectsInCharacters(method, args, ifAttr);
        JexlExpression jexlExpression = JEXL_ENGINE.createExpression(ifAttr);
        return (boolean) jexlExpression.evaluate(null);
    }

    /**
     * 代替字符中的对象
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/8/12 下午8:26
     **/
    public static String substituteObjectsInCharacters(Method method, Object[] args, String s) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Param param = parameters[i].getAnnotation(Param.class);
            String parsePlaceholder;
            if (!ObjectUtils.isEmpty(param)) {
                parsePlaceholder = param.value();
            } else {
                parsePlaceholder = parameters[i].getName();
            }
            Object val = args[i];
            val = ObjectUtils.isEmpty(val) ? "null" : "\"" + val + "\"";
            s = s.replace(parsePlaceholder, (String) val);
        }
        return s.replace("and", "&&");
    }

    /**
     * 基本数据类型 字符串替换 TODO sql注入问题
     *
     * @param method
     * @param args
     * @param text
     * @return
     */
    public static String parse2(Method method, Object[] args, String text) {
        return parse2(method, args, text, true);
    }

    /**
     * @param method
     * @param args
     * @param text
     * @param isPage
     * @return
     */
    public static String parse2(Method method, Object[] args, String text, boolean isPage) {
        Parameter[] parameters = method.getParameters();
        LazyPage lazyPage = null;
        for (int i = 0; i < parameters.length; i++) {
            Param param = parameters[i].getAnnotation(Param.class);
            String parsePlaceholder;
            if (!ObjectUtils.isEmpty(param)) {
                parsePlaceholder = "#{" + param.value() + "}";
            } else {
                parsePlaceholder = "#{" + parameters[i].getName() + "}";
            }
            String val = "'" + args[i].toString() + "'";
            // TODO 表名不添加'
            if (parsePlaceholder.contains("!")) {
                val = args[i].toString();
            }
            text = text.replace(parsePlaceholder, val);
            if (isPage) {
                // 分页处理
                // 内部添加分页
                if (parameters[i].getType().isAssignableFrom(LazyPage.class)) {
                    lazyPage = (LazyPage) args[i];
                    PageNote pageNote = parameters[i].getAnnotation(PageNote.class);
                    if (null != pageNote && !ObjectUtils.isEmpty(pageNote.value())) {
                        String pageNoteAlias = pageNote.value();
                        text = text.replace(") AS " + pageNoteAlias, page2SQL(lazyPage) + ") AS " + pageNoteAlias);
                    }
                }

            }
        }
        // 外部添加分页
        text += page2SQL(lazyPage);
        return text;
    }

    /**
     * 获取分页表 统计 sql
     *
     * @param method
     * @param args
     * @return
     */
    public static String countSQL(Method method, Object[] args, String text) {
        Parameter[] parameters = method.getParameters();
        String countSQL = " select count(1)  ";
        String pageNoteAlias = null;
        text = parse2(method, args, text, false).toLowerCase();
        for (int i = 0; i < parameters.length; i++) {
            // 获取分页别名
            PageNote pageNote = parameters[i].getAnnotation(PageNote.class);
            if (null != pageNote) {
                pageNoteAlias = pageNote.value();
            }
        }
        if (ObjectUtils.isEmpty(pageNoteAlias)) {
            countSQL += text.substring(text.lastIndexOf("from"));
        } else {
            countSQL += text.substring(text.lastIndexOf("from"), text.indexOf(pageNoteAlias)).replace("\\)", "").replace("AS", "");
        }
        return countSQL;
    }

    public static String countSQL(Method method, Object[] args, CustomRepository customRepository) {
        Parameter[] parameters = method.getParameters();
        String countSQL = " select count(1)  ";
        String pageNoteAlias = null;

        String text = parse2(method, args, customRepository).toLowerCase();
        for (int i = 0; i < parameters.length; i++) {
            // 获取分页别名
            PageNote pageNote = parameters[i].getAnnotation(PageNote.class);
            if (null != pageNote) {
                pageNoteAlias = pageNote.value();
            }
        }
        if (ObjectUtils.isEmpty(pageNoteAlias)) {
            countSQL += text.substring(text.lastIndexOf("from"));
        } else {
            countSQL += text.substring(text.lastIndexOf("from"), text.indexOf(pageNoteAlias)).replace("\\)", "").replace("AS", "");
        }
        return countSQL;
    }

    /**
     * 获取接口参数中分页对象
     *
     * @param method
     * @param args
     * @return
     */
    public static LazyPage pageObject(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType().isAssignableFrom(LazyPage.class)) {
                return (LazyPage) args[i];
            }
        }
        return null;
    }

    /**
     * 分页对象转换成sql
     *
     * @param lazyPage
     * @return
     */
    private static String page2SQL(LazyPage lazyPage) {
        if (null == lazyPage) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (lazyPage.isAsc()) {
            if (!ObjectUtils.isEmpty(lazyPage.getAscs())) {
                stringBuffer.append(" ORDER BY ").append(lazyPage.getAscs()).append(" ASC ");
            }
        } else {
            if (!ObjectUtils.isEmpty(lazyPage.getDescs())) {
                stringBuffer.append(" ORDER BY ").append(lazyPage.getDescs()).append(" DESC ");
            }
        }
        stringBuffer.append(" LIMIT ").append(lazyPage.getCurrent() - 1).append(",").append(lazyPage.getSize());
        return stringBuffer.toString();
    }

    public static List<CustomRepository> xx() {
        return null;
    }

    /**
     * 使用示例
     *
     * @param args
     */
    public static void main(String... args) {

        String ss = "woshide";
        ss.replace("wo", "we");
        System.out.println(ss);
//        输出结果如下：

//        我的名字是{},结果是true，可信度是%100
//        我的名字是雷锋,结果是true，可信度是%100
//        我的名字是雷锋,结果是true，可信度是%100
        for (Method method : Parser.class.getMethods()) {
            System.out.println(method.getName());
            if ("xx".equals(method.getName())) {
                Type genericReturnType = method.getGenericReturnType();
                Class<?> returnType = method.getReturnType();

            }
        }
    }
}
