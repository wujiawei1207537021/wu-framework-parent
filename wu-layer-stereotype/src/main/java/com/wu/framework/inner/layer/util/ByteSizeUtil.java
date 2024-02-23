package com.wu.framework.inner.layer.util;

import java.text.DecimalFormat;

/**
 * 字节大小计算
 */
public class ByteSizeUtil {

    public static String byteSize2China(long byteSize, DecimalFormat decimalFormat) {
        double size = byteSize * 1.00;
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return size + "B";
        } else {
            size = size / 1024;
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (size < 1024) {
            return decimalFormat.format(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            // 因为如果以MB为单位的话，要保留最后1位小数，
            // 因此，把此数乘以100之后再取余
            return decimalFormat.format(size )+ "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            return decimalFormat.format(size / 1024 )+ "GB";
        }

    }
    /**
     * 字节大小转换成 B、KB、MB、GB
     *
     * @param byteSize 文件长度
     * @return 字节大小转换成 B、KB、MB、GB
     */
    public static String convertSize(long byteSize) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");// 保留小数点后两位
        return byteSize2China(byteSize,decimalFormat);
    }
}
