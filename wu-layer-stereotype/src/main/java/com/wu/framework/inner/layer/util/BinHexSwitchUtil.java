package com.wu.framework.inner.layer.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BinHexSwitchUtil {

    public static final int BUFFSIZE_1024 = 1024;

    /**
     * describe 字节转十六进制字符串
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2021/10/26 10:23 下午
     **/
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            //0x
            int temp = b & 0xFF;
            String tempStr = Integer.toHexString(temp);
//            System.out.println("tempStr:"+tempStr);
            if (tempStr.length() == 1) {//如果转换完了是 一位数 需要前面加 0
                result.append("0").append(tempStr);
            } else {
                result.append(tempStr);
            }
        }
        return result.toString();
    }

    /**
     * describe 字节转十六进制SQL
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2021/10/26 10:50 下午
     **/
    public static String bytesToHexSql(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder result = new StringBuilder("0x");
        for (byte b : bytes) {
            int temp = b & 0xFF;
            String tempStr = Integer.toHexString(temp);
//            System.out.println("tempStr:"+tempStr);
            if (tempStr.length() == 1) {//如果转换完了是 一位数 需要前面加 0
                result.append("0").append(tempStr);
            } else {
                result.append(tempStr);
            }
        }
        return result.toString();
    }

    /**
     * describe 十六进制字符串到字节
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2021/10/26 10:23 下午
     **/
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.length() == 0) {
            return null;
        }
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0, foot = 0; i < hexString.length(); i += 2, foot++) {
            String temp = hexString.substring(i, i + 2);//切割 16进制 二位为一个单位转换
            result[foot] = (byte) Integer.parseInt(temp, 16);
        }
        return result;
    }


    /**
     * 读取 InputStream 的字节
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] readBytes(InputStream in) throws IOException {
        BufferedInputStream bufin = new BufferedInputStream(in);
        int buffSize = BUFFSIZE_1024;
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);

        // System.out.println("Available bytes:" + in.available());

        byte[] temp = new byte[buffSize];
        int size = 0;
        while ((size = bufin.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        bufin.close();

        byte[] content = out.toByteArray();
        return content;
    }


    public static void main(String[] args) {
        String value = "a2\tcdefghijklmnxyz";
        System.out.println("value 2进制:" + Arrays.toString(value.getBytes()));
        String hexStr = bytesToHexString(value.getBytes());
        System.out.println("value 16进制：" + hexStr);

        System.out.println("value转回二进制：" + Arrays.toString(hexStringToBytes(hexStr)));
        System.out.println("value转回二进制：" + new String(hexStringToBytes(hexStr)));
        System.out.println((byte) Integer.parseInt("-129"));

    }
}