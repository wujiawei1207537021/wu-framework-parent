package com.wu.framework.inner.layer;


import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * java驼峰法和下划线法字符串的相互转换
 */

public class CamelAndUnderLineConverter {
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 下划线转驼峰 针对字段
     * {@link CamelAndUnderLineConverter#lineToHumpField(String)}
     */
    @Deprecated
    public static String lineToHump(String str) {
//        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 下划线转驼峰 针对字段
     */
    public static String lineToHumpField(String str) {
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转驼峰 针对class
     * create_time CreateTime
     *
     * @param str
     * @return
     */
    public static String lineToHumpClass(String str) {

        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return capitalizeFirstLetter(sb.toString());
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
     */
    public static String humpToLine(String hump) {
        return hump.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 驼峰转下划线,效率比上面高
     *
     * @param hump humpToLine2
     * @return String hump_to_line_2
     */
    public static String humpToLine2(String hump) {
        return humpTo2(hump, "_");
    }

    /**
     * 驼峰转中划线
     *
     * @param hump humpToMidLine2
     * @return String hump-to-mid-line-2
     * @author Jia wei Wu
     * @date 2020/11/30 下午9:05
     **/
    public static String humpToMidLine2(String hump) {
        return humpTo2(hump, "-");
    }

    /**
     * 驼峰转字符
     *
     * @param hump      humpToMidLine2
     * @param character -
     * @return String hump-to-mid-line-2
     * @author Jia wei Wu
     * @date 2020/11/30 下午9:05
     **/
    public static String humpTo2(String hump, String character) {
        char[] chars = new char[1];
        if(ObjectUtils.isEmpty(hump)){
            return hump;
        }
        chars[0] = hump.charAt(0);
        String temp = new String(chars);
        try {
            hump = hump.replaceFirst(temp, temp.toLowerCase());
        } catch (Exception e) {
            System.out.println(hump);
            e.printStackTrace();
        }
        Matcher matcher = humpPattern.matcher(hump);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, character + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转数组
     *
     * @param hump humpToMidLine2
     * @return List<String>  hump、to、mid、line、2
     * @author Jia wei Wu
     * @date 2020/11/30 下午9:05
     **/
    public static List<String> humpToArray(String hump) {
        char[] chars = new char[1];
        chars[0] = hump.charAt(0);
        String temp = new String(chars);
        try {
            hump = hump.replaceFirst(temp, temp.toLowerCase());
        } catch (Exception e) {
            System.out.println(hump);
            e.printStackTrace();
        }
        Matcher matcher = humpPattern.matcher(hump);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return Arrays.asList(sb.toString().split("_"));
    }

    /**
     * describe 首字母大写
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2021/3/6 5:02 下午
     **/
    public static String capitalizeFirstLetter(String name) {
        if (ObjectUtils.isEmpty(name)) {
            return name;
        }
        if (isUppercase(name)) {
            return name;
        }
        char[] cs = name.toCharArray();
        if (cs.length == 0) {
            return String.valueOf(cs);
        }
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 判断首字母是否大写
     *
     * @param name
     * @return
     */
    public static boolean isUppercase(String name) {
        char c = name.charAt(0);
        return Character.isUpperCase(c);
    }

    /**
     * 首字母小写
     *
     * @param name 字符串 User --》user
     * @return
     */
    public static String lowercaseFirstLetter(String name) {
        if (!isUppercase(name)) {
            return name;
        }
        char[] cs = name.toCharArray();
        if (cs.length == 0) {
            return String.valueOf(cs);
        }
        cs[0] += 32;
        return String.valueOf(cs);
    }

    public static void main(String[] args) {
        String lineToHump = lineToHump("table_Name");
        System.out.println(lineToHump);// fParentNoLeader
        System.out.println(humpToLine(lineToHump));// f_parent_no_leader
        System.out.println(humpToLine2("ParentNoLeader"));// f_parent_no_leader
        System.out.println(lowercaseFirstLetter("UserName")); //userName
    }


    /**
     * ♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬
     * ♬                                                                                                                                              ♬
     * ♬                                                                                                                                              ♬
     * ♬                                                     以下是方法转换成字段的使用                                                                   ♬
     * ♬                                                                                                                                              ♬
     * ♬                                                                                                                                              ♬
     * ♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬
     */
    public static String methodToField(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        } else {
            throw new RuntimeException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        }

        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    public static boolean isField(String name) {
        return isGetter(name) || isSetter(name);
    }

    public static boolean isGetter(String name) {
        return (name.startsWith("get") && name.length() > 3) || (name.startsWith("is") && name.length() > 2);
    }

    public static boolean isSetter(String name) {
        return name.startsWith("set") && name.length() > 3;
    }

}