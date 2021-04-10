package com.wu.framework.inner.layer;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * java驼峰法和下划线法字符串的相互转换
 */

public class CamelAndUnderLineConverter {
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 下划线转驼峰
     */
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
        chars[0] = hump.charAt(0);
        String temp = new String(chars);
        hump = hump.replaceFirst(temp, temp.toLowerCase());
        Matcher matcher = humpPattern.matcher(hump);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, character + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @param
     * @return
     * @describe 首字母大写
     * @author Jia wei Wu
     * @date 2021/3/6 5:02 下午
     **/
    public static String capitalizeFirstLetter(String name) {
        char[] cs = name.toCharArray();
        if (cs.length == 0) {
            return String.valueOf(cs);
        }
        cs[0] -= 32;
        return String.valueOf(cs);
    }


//    public static void main(String[] args) {
//        String lineToHump = lineToHump("table_Name");
//        System.out.println(lineToHump);// fParentNoLeader
//        System.out.println(humpToLine(lineToHump));// f_parent_no_leader
//        System.out.println(humpToLine2("ParentNoLeader"));// f_parent_no_leader
//    }
}