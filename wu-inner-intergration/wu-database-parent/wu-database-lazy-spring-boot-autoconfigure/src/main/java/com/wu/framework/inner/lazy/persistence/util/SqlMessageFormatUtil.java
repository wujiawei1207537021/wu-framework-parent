package com.wu.framework.inner.lazy.persistence.util;

/**
 * sql 信息转换
 */
public class SqlMessageFormatUtil {


    /**
     * @param pattern   select * from {0}
     * @param arguments user
     * @return select * from user
     */
    public static String format(String pattern, Object... arguments) {
        int index = 0;
        for (Object argument : arguments) {
            String target = "\\{" + index + "}";
            if (null == argument) {
                continue;
            }
            pattern = pattern.replaceAll(target, argument.toString());
            index++;
        }

        return pattern;
    }

    public static void main(String[] args) {
        String xx = "select * from {0}";
        System.out.println(xx.replaceAll("\\{0}", "table"));
    }
}
