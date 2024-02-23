package com.wu.framework.inner.layer.enums;


import com.wu.framework.inner.layer.data.NormalUsedString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * description 仿真数据枚举
 *
 * @author Jia wei Wu
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SimulationEnum {

    /**
     * INT
     */
    INT(int.class, length -> {
        if (null == length) {
            return getRandom().nextInt();
        }
        return getRandom().nextInt(length);
    }),
    /**
     * INTEGER
     */
    INTEGER(Integer.class, length -> {
        if (null == length) {
            return getRandom().nextInt();
        }
        return getRandom().nextInt(length);
    }),

    /**
     * LONG
     */
    LONG(long.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            if (null == length) {
                return getRandom().nextLong();
            }
            return getRandom().nextInt(length);
//            return getRandom().nextLong(length); jdk17
        }

        @Override
        public Object text(Integer length) {
            return strategy(length) + "l";
        }
    }),
    /**
     * PACKAGING_LONG包装类
     */
    PACKAGING_LONG(Long.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            if (null == length) {
                return getRandom().nextLong();
            }
            return getRandom().nextInt(length);
        }

        @Override
        public Object text(Integer length) {
            return strategy(length) + "L";
        }
    }),
    /**
     * DOUBLE
     */
    DOUBLE(double.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return getRandom().nextDouble();
        }

        @Override
        public Object text(Integer length) {
            return strategy(length) + "d";
        }
    }),
    /**
     * PACKAGING_DOUBLE
     */
    PACKAGING_DOUBLE(Double.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return getRandom().nextDouble();
        }

        @Override
        public Object text(Integer length) {
            return strategy(length) + "D";
        }
    }),
    /**
     * FLOAT
     */
    FLOAT(float.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return getRandom().nextFloat();
        }

        @Override
        public Object text(Integer length) {
            return strategy(length) + "f";
        }
    }),
    /**
     * PACKAGING_FLOAT
     */
    PACKAGING_FLOAT(Float.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return getRandom().nextFloat();
        }

        @Override
        public Object text(Integer length) {
            return strategy(length) + "F";
        }
    }),

    /**
     * BOOLEAN
     */
    BOOLEAN(boolean.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return getRandom().nextBoolean();
        }

        @Override
        public Object text(Integer length) {
            return strategy(length);
        }
    }),
    /**
     * PACKAGING_BOOLEAN
     */
    PACKAGING_BOOLEAN(Boolean.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return getRandom().nextBoolean();
        }

        @Override
        public Object text(Integer length) {
            return strategy(length);
        }
    }),
    /**
     * DATE
     */
    DATE(java.util.Date.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return new java.util.Date();
        }

        @Override
        public Object text(Integer length) {
            final java.util.Date date = new java.util.Date();
            return String.format("new java.util.Date(%sL)", date.getTime());
        }
    }),
    /**
     * SQL_DATE
     */
    SQL_DATE(java.sql.Date.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            final java.util.Date date = new java.util.Date();
            return new java.sql.Date(date.getTime());
        }

        @Override
        public Object text(Integer length) {
            final java.util.Date date = new java.util.Date();
            return String.format("new java.sql.Date(%sL)", date.getTime());
        }
    }),
    /**
     * LOCAL_DATE_TIME
     */
    LOCAL_DATE_TIME(LocalDateTime.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return LocalDateTime.now();
        }

        @Override
        public Object text(Integer length) {
            final LocalDateTime now = LocalDateTime.now();
            return String.format("LocalDateTime.of(%s,%s,%s,%s,%s,%s)",
                    now.getYear(),
                    now.getMonth(),
                    now.getDayOfYear(),
                    now.getHour(),
                    now.getMinute(),
                    now.getSecond());
        }
    }),
    /**
     * LOCAL_DATE
     */
    LOCAL_DATE(LocalDate.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return LocalDate.now();
        }

        @Override
        public Object text(Integer length) {
            final LocalDate now = LocalDate.now();
            return String.format("LocalDate.of(%s,%s,%s)",
                    now.getYear(),
                    now.getMonth(),
                    now.getDayOfYear());
        }
    }),
    /**
     * BIG_DECIMAL
     */
    BIG_DECIMAL(BigDecimal.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            if (null == length) {
                return new BigDecimal(getRandom().nextInt());
            }
            return new BigDecimal(getRandom().nextInt(length));
        }
    }),
    /**
     * 字符串
     */
    STRING(String.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            if (null == length) {
                return getRandomChar(getRandom().nextInt(3));
            }
            return getRandomChar(length);
        }

        @Override
        public Object text(Integer length) {
            return NormalUsedString.SINGLE_QUOTE + strategy(length) + NormalUsedString.SINGLE_QUOTE;
        }
    }),
    /**
     * map
     */
    MAP(Map.class, new SimulationStrategy() {
        @Override
        public Object strategy(Integer length) {
            return new HashMap<>();
        }
    }),
    /**
     * 文件
     */
    FILE(File.class, new SimulationStrategy() {

        /**
         * description 获取策略对象
         *
         * @param length 长度
         * @return
         * @exception/throws
         * @author Jia wei Wu
         * @date 2021/10/11 4:47 下午
         */
        @Override
        public Object strategy(Integer length) {
            return null;
        }

        /**
         * description 获取text文本数据
         *
         * @param length 长度
         * @return
         * @exception/throws
         * @author Jia wei Wu
         * @date 2021/10/11 4:47 下午
         */
        @Override
        public Object text(Integer length) {
            return "";
        }
    }),
    ;

    /**
     * 本地缓存使用数据
     */
    public static Map<Class<?>, SimulationStrategy> SIMULATION_MAP = Arrays.stream(values()).
            collect(Collectors.toMap(SimulationEnum::getJavaType, SimulationEnum::getSimulation));
    /**
     * 类型
     */
    private Class javaType;
    /**
     * 仿真数据策略
     */
    private SimulationStrategy simulation;

    /**
     * 获取随机 Random
     *
     * @return
     */
    private static Random getRandom() {
        return new Random();
    }

    /**
     * description 获取多个随机字符
     *
     * @param length 字符个数
     * @return String String
     * @author Jia wei Wu
     * @date 2021/10/11 2:14 下午
     */
    private static String getRandomChar(int length) {
        StringBuilder character = new StringBuilder();
        for (int i = 0; i < length; i++) {
            final char randomChar = getRandomChar();
            character.append(randomChar);
        }
        return character.toString();
    }


    /**
     * 获取随机字符
     *
     * @return char char
     */
    private static char getRandomChar() {
        String str = "";
        int heightPos; //
        int lowPos;

        Random random = getRandom();

        heightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(heightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }
        return str.charAt(0);
    }

    /**
     * description 仿真策略
     *
     * @author Jia wei Wu
     * @date 2021/10/11 2:43 下午
     */
    public interface SimulationStrategy {

        /**
         * description 获取策略对象
         *
         * @param length 长度
         * @return Object 返回对象
         * @author Jia wei Wu
         * @date 2021/10/11 4:47 下午
         */
        Object strategy(Integer length);

        /**
         * description 获取策略对象
         *
         * @return Object 返回对象
         * @author Jia wei Wu
         * @date 2022/1/23 8:38 下午
         **/
        default Object strategy() {
            return strategy(null);
        }


        /**
         * description 获取text文本数据
         *
         * @param length 长度
         * @return Object 返回对象
         * @author Jia wei Wu
         * @date 2021/10/11 4:47 下午
         */
        default Object text(Integer length) {
            return strategy(length);
        }

        /**
         * description 获取text文本数据
         *
         * @return Object 返回对象
         * @author Jia wei Wu
         * @date 2022/1/23 8:38 下午
         **/
        default Object text() {
            return NormalUsedString.SINGLE_QUOTE + text(null) + NormalUsedString.SINGLE_QUOTE;
        }

    }

    /**
     * description 默认模拟策略
     *
     * @author Jia wei Wu
     * @date 2021/10/11 4:59 下午
     */
    public static class DefaultSimulationStrategy implements SimulationStrategy {

        /**
         * description 获取策略对象
         *
         * @param length 长度
         * @return Object 返回对象
         * @author Jia wei Wu
         * @date 2021/10/11 4:47 下午
         */
        @Override
        public Object strategy(Integer length) {
            return null;
        }

        /**
         * description 获取text文本数据
         *
         * @param length 长度
         * @return Object 返回对象
         * @author Jia wei Wu
         * @date 2021/10/11 4:47 下午
         */
        @Override
        public Object text(Integer length) {
            return null;
        }
    }
}



   

