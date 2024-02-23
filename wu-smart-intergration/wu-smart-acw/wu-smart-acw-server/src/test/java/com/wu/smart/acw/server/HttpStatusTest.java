package com.wu.smart.acw.server;

import org.springframework.http.HttpStatus;

public class HttpStatusTest {
    public static void main(String[] args) {
        for (HttpStatus value : HttpStatus.values()) {
//            	CONTINUE(100, Series.INFORMATIONAL, "Continue"),
            System.out.println(value.name()+"("+value.value()+",\""+value.getReasonPhrase()+"\",\""+value.getReasonPhrase()+"\",\"服务异常["+value.getReasonPhrase()+"]\")"+",");
        }
    }
}
