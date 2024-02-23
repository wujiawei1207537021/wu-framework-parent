package com.wu.framework.inner.common.util;


import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * description  代理发送请求
 *
 * @author 吴佳伟
 * @date 2021/5/12 9:39 上午
 */
public class ProxyRestTemplate {


    public static void proxy(ProxyInfo proxyInfo, String spec) throws IOException {
        URL url = new URL(spec);
        System.getProperties().setProperty("proxySet", "true");
        System.getProperty("http.maxRedirects", "50");
        System.getProperties().setProperty("proxySet", "true");
        System.out.println("此时的代理服务器设置为" + proxyInfo.getProxy()
                + "端口号设置为" + proxyInfo.getPort());

        URLConnection connection = url.openConnection();
        String ip = proxyInfo.getProxy();
        connection.setRequestProperty("X-Forwarded-For", ip);

        connection.setRequestProperty("HTTP_X_FORWARDED_FOR", ip);

        connection.setRequestProperty("HTTP_CLIENT_IP", ip);

        connection.setRequestProperty("REMOTE_ADDR", ip);

        connection.setRequestProperty("Host", "");

        connection.setRequestProperty("Connection", "keep-alive");

        connection.setRequestProperty("Content-Length", "17");

        connection.setRequestProperty("Accept", "application/json");

        connection.setRequestProperty("Origin", "ORIGIN");

        connection.setRequestProperty("User-Agent",

                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        connection.setRequestProperty("Referer", "REFERER");

//        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,pt;q=0.2");

//        connection.setDoOutput(true);
//
//        connection.setDoInput(true);

        connection.setRequestProperty(//设置终端类型
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)"
        );
        System.getProperties().setProperty("http.proxyHost", proxyInfo.getProxy());
        System.getProperties().setProperty("http.proxyPort", String.valueOf(proxyInfo.getPort()));

        //  POST 请求
//        connection.setDoOutput(true);
//        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
//        out.write("id=19&submit="); // 向页面传递数据。post的关键所在！
//        out.flush();
//        out.close();
        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine;
        String sTotalString;
        sTotalString = "";
        InputStream l_urlStream = connection.getInputStream();
        // 传说中的三层包装阿！
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString += sCurrentLine + "\r\n";

        }
//        System.out.println(sTotalString);
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        for (int i = 0; i < 100; i++) {
            Thread.sleep(30000);
            proxy(new ProxyInfo(randIP(), 2018), "https://blog.csdn.net/qq_22903677/article/details/116056730");
        }

        //  http://127.0.0.1:2018/test/captcha.jpg
        proxy(new ProxyInfo(randIP(), 2018), "http://localhost:2018/test/captcha.jpg");
    }

    public static String randIP() {
        Random random = new Random(System.currentTimeMillis());

        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "."

                + (random.nextInt(255) + 1);

    }

    @Data
    public static class ProxyInfo {
        private String proxy;
        private int port;
        private String socksProxy;
        private int socksPort;

        public ProxyInfo(String proxy, int port) {
            this.proxy = proxy;
            this.port = port;
        }
    }


}
