package com.wu.framework.easy.temple.domain;

import java.io.IOException;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/12/2 下午9:54
 */
public class xx {

    public static void main(String[] args) throws IOException {
//        WebClient webClient = WebClient.create("http://k8s.wu2020.top:9200/_bulk");
//
//        File tempFile = new File("es.xx");
//        if (!tempFile.exists()) {
//            tempFile.createNewFile();
//        }
//
//        String absolutePath = tempFile.getAbsolutePath();//得到文件/文件夹的绝对路径
//        System.out.println("得到文件/文件夹的绝对路径" + absolutePath);
//
//        FileSystemResource resource = new FileSystemResource(tempFile);
//        InputStream inputStream = resource.getInputStream();
//        OutputStream outputStream = resource.getOutputStream();
//
//        OutputStreamWriter osw = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
//        BufferedWriter bufferedWriter = new BufferedWriter(osw);
//        bufferedWriter.write("{\"create\":{\"_index\":\"test_index\",\"_type\":\"test_type\",\"_id\":11}}");
//        bufferedWriter.newLine();
//        bufferedWriter.write("{\"test_field\":\"test201712291140\"}");
//        bufferedWriter.newLine();
//
//        bufferedWriter.close();
//
//        Mono<String> bodyToMono = webClient
//                .post()
//                .uri("/_bulk")
////                .uri("/{1}/{2}/_bulk",
////                        elasticsearchPreProcessResult.getBulkIndexDoc(),
////                        elasticsearchPreProcessResult.getIndexType())  //服务请求路径，基于baseUrl
//                .contentType(MediaType.APPLICATION_JSON)
////                .bodyValue(new FileSystemResource("/Users/wujiawei/Desktop/a.json1"))//发送请求体
//                .bodyValue(resource)
//                .retrieve() // 获取响应体
//                .bodyToMono(String.class);//响应数据类型转换
//        System.out.println(bodyToMono.block());
//        tempFile.deleteOnExit();

//        ElasticsearchEasyDataProcessAnalyze.ElasticsearchProcessResult<String,Object> map=new LinkedHashMap<String,Object>();
//        map.put("shide","是的");
//        map.put("haode","好的");
//        System.out.println(JSONObject.toJSONString(map));
//        SQLAnalyze.createTableSQL(UseExcel.class);
    }

//    public static InputStream getStrToStream(String sInputString) {
//        try {
//            FileOutputStream fos;   //节点类
//            OutputStreamWriter osw;  //字符类
//            BufferedWriter bw;   //装饰类
//            fos = new FileOutputStream("D:/temp/hello.txt");//传入文件所在路径
//            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8); //将fos作为参数传入osw，并且设置字符编码
//            bw = new BufferedWriter(osw);
//            bw.write("我是瓶子中的玉米");
//            bw.newLine();  //换行
//            bw.write("我喜欢学习java");
//            bw.newLine();
//            System.out.println("文本写入成功");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            bw.close();  //涉及到IO流操作的时候一定要记得关闭文件，关闭最外层流即可关掉所有的流
//        }
//
//    }

}
