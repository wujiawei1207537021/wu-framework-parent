package com.wu.framework.inner.layer.util;

import org.springframework.util.ObjectUtils;

import java.io.*;
import java.net.URL;
import java.util.UUID;

/**
 * description http 文件获取
 *
 * @author Jia wei Wu
 * @date 2022/2/24$ 1:20 下午$
 */
public class HttpFileUtil {


    /**
     * description 获取网络地址 BufferedReader 流
     *
     * @param netUrl 网络地址
     * @return BufferedReader 流
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/4/9 2:24 下午
     */
    public static BufferedReader getNetUrlHttpBufferedReader(String netUrl) {
        URL url;
        BufferedReader bufferedReader = null;
        try {
            //下载
            url = new URL(netUrl);
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }

    /**
     * 获取网络数据二进制
     */
    public static byte[] readStream(String netUrl) {
        try {
            URL url = new URL(netUrl);
            InputStream inputStream = url.openStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            inputStream.close();
            return outStream.toByteArray();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * 获取网络文件
     *
     * @param netUrl
     * @return
     */
    public static File getNetUrlHttp(String netUrl) {
        //对本地文件命名
        String fileName = reloadFile(netUrl);
        File file = null;
        URL url;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            file = File.createTempFile("net_url", fileName);
            //下载
            url = new URL(netUrl);
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            bufferedWriter = new BufferedWriter(new FileWriter(file));

            int BUFFER_SIZE = 4096;
            char[] buffer = new char[BUFFER_SIZE];
            int charsRead = 0;
            while ((charsRead = bufferedReader.read(buffer, 0, BUFFER_SIZE)) != -1) {
                bufferedWriter.write(buffer, 0, charsRead);
            }
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedWriter) {
                    bufferedWriter.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * 重命名，UUIU
     *
     * @param oleFileName
     * @return
     */
    public static String reloadFile(String oleFileName) {
        oleFileName = getFileName(oleFileName);
        if (ObjectUtils.isEmpty(oleFileName)) {
            return oleFileName;
        }
        //得到后缀
        if (oleFileName.indexOf(".") == -1) {
            //对于没有后缀的文件，直接返回重命名
            return UUID.randomUUID().toString();
        }
        String[] arr = oleFileName.split("\\.");
        // 根据uuid重命名图片
        String fileName = UUID.randomUUID() + "." + arr[arr.length - 1];

        return fileName;
    }

    /**
     * 把带路径的文件地址解析为真实文件名 /25h/upload/hc/1448089199416_06cc07bf-7606-4a81-9844-87d847f8740f.mp4 解析为 1448089199416_06cc07bf-7606-4a81-9844-87d847f8740f.mp4
     *
     * @param url
     */
    public static String getFileName(final String url) {
        if (ObjectUtils.isEmpty(url)) {
            return url;
        }
        String newUrl = url;
        newUrl = newUrl.split("[?]")[0];
        String[] bb = newUrl.split("/");
        String fileName = bb[bb.length - 1];
        return fileName;
    }

}
