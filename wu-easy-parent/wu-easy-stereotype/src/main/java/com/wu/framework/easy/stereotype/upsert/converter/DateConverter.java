package com.wu.framework.easy.stereotype.upsert.converter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 日期转换
 * @date : 2020/9/26 下午2:47
 */
public class DateConverter {


    public static Object toSQLDate(Object date){
        if(date instanceof Date){
            return date;
        }else if (date instanceof LocalDateTime){
            Date date1=new Date(((LocalDateTime) date).toEpochSecond(ZoneOffset.UTC));
            return date1;
        }
        return date;
    }
}
