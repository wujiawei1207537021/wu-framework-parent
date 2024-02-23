package com.wu.framework.inner.layer.data;

import java.util.Scanner;
 
public class StringToASCII {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //三个方法按需求调用
        toASC(in);
        ASCtoString(in);
        ASCtoString2(in);
        in.close();
    }
 
    /*String转化成ASCII*/
    public static void toASC(Scanner in) {
        System.out.println("请输入要转化为ASCII的字符串：");
        String ins = in.nextLine();
        System.out.print("ASCII码为：");
 
        for (int i = 0; i < ins.length(); i++) {
            char c = ins.charAt(i);
            if (i == ins.length() - 1) {
                System.out.println((int) c);
            } else {
                System.out.print((int) c + ",");
            }
        }
    }
 
    /*ASCII转化成字符串*/
    public static void ASCtoString(Scanner in) {
        System.out.println("请输入要转化为字符串的ASCII：");
        String ins = in.nextLine();
        String[] codeArray = ins.split(", ");
        StringBuilder sb = new StringBuilder();
 
        for (String code : codeArray) {
            int asciiValue = Integer.parseInt(code);
            char character = (char) asciiValue;
            sb.append(character);
        }
 
        String convertedString = sb.toString();
        System.out.println("String为: " + convertedString);
    }
 
    /*byte[]转化ASCII成字符串*/
    public static void ASCtoString2(Scanner in) {
        String ins = in.nextLine();
        String[] valuesArray = ins.split(", ");
        byte[] byteArray = new byte[valuesArray.length];
 
        for (int i = 0; i < valuesArray.length; i++) {
            int decimalValue = Integer.parseInt(valuesArray[i]);
            byteArray[i] = (byte) decimalValue;
        }
        //byte[] chars = new byte[]{72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100};
        System.out.println("String为: " + new String(byteArray));
    }
}