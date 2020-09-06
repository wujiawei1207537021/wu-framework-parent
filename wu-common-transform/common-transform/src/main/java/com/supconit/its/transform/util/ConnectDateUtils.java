package com.supconit.its.transform.util;


import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author 黄珺
 * @date 2020/4/14
 **/
public class ConnectDateUtils {

    private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    /**
     * 将时间对象转换为偏移天数
     */
    public static int toOffsetDay(Date value) {
        Calendar calendar = Calendar.getInstance(UTC);
        calendar.setTime(value);
        long unixMillis = calendar.getTimeInMillis();
        return (int) (unixMillis / MILLIS_PER_DAY);
    }

    /**
     * 将毫秒时间戳转换为偏移天数
     */
    public static int toOffsetDay(long unixMillis) {
        return (int) (unixMillis / MILLIS_PER_DAY);
    }

    /**
     * 将偏移天数转换为时间对象
     */
    public static Date toDate(int offsetDay) {
        return new java.util.Date(offsetDay * MILLIS_PER_DAY);
    }

    /**
     * 将偏移天数转换为毫秒时间戳
     */
    public static long toTimeMillis(int offsetDay) {
        return offsetDay * MILLIS_PER_DAY;
    }
}
