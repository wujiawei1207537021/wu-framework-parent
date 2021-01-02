package com.wu.framework.inner.redis.enums;

import com.wu.framework.inner.redis.CustomRedisDBEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * redis 数据库枚举
 *
 * @author wujiawei
 * @date 2019/11/14 10:37
 */

@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum DefaultCustomRedisDBEnum implements CustomRedisDBEnum {

    SYS_DEFAULT(0, "默认数据库");

    /**
     * 数据库
     */
    private Integer db;
    /**
     * 描述信息
     */
    private String msg;

    /**
     * @Description ：检验db 是否被占用
     * @author : wujiawei
     * @date : 2019/11/14 0014 16:59
     */
    public static boolean usedDB(Integer db) {
        if (SYS_DEFAULT.getDb().equals(db)) {
            System.out.println("Redis使用默认数据库");
            return false;
        }
        for (DefaultCustomRedisDBEnum dbEnum : values()) {
            if (dbEnum.db.equals(db)) {
                return true;
            }
        }
        return false;
    }

}
