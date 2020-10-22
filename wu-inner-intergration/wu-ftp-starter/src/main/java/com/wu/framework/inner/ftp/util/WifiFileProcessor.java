package com.wu.framework.inner.ftp.util;

/**
 * @Description
 * @Author 吴佳伟
 * @Date 2020-05-22 2:31 下午
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;


@Slf4j
//@Component
public class WifiFileProcessor implements Processor {

    public static final String SIGNALCHANNEL_MAC = "signalChannel_mac";

    @Value("${ftp.wifi-info.unzip-temp-dir}")
    private String unZipTempPath;

    @Value("${ftp.mobile-info.local-save-dir}")
    private String localDir;
//
//    @Autowired
//    private IEqMobileMacService eqMobileMacService;
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("开始解析wifi下载的文件。。。");
//        GenericFileMessage<RandomAccessFile> inFileMessage = (GenericFileMessage<RandomAccessFile>) exchange.getIn();
////        gf.getFile().
//        String fileName = inFileMessage.getGenericFile().getFileName();
//        File zipFile = new File(localDir + File.separator + fileName);
////        Message message = exchange.getIn();
////        GenericFile<?> gf = (GenericFile<?>) message.getBody();
////        File zipFile = (File) gf.getFile();//两种File对象获取方式，这一种有可能会报异常，类型转换异常
//        //解压文件，
//        List<File> files = null;
//        String zipName = zipFile.getName();
//        if (Pattern.matches(ZipUtil.ZIP_PATTERN, zipName)) {
//            if (FileHandlerMap.readedFilesWithMobiles.get(zipFile.getName()) != null) {
//                return;
//            }
//            files = ZipUtil.unZip(zipFile, unZipTempPath, ZipUtil.BCP_PATTERN);
//        } else {
//            log.debug("下载到不是zip的压缩文件：" + zipFile.getName());
//            return;
//        }
//        if (files == null) {
//            log.error("压缩文件中没有解析到bcp文件:" + zipFile.getName());
//        } else {
//            //读取文件
//            List<String> textList = ZipUtil.batchReadFile(files, "GBK");
//            //批量解析文件中数据
//            List<EqMobileMac> eqMobileMacList = this.batchStrToEntity(textList);
//            //删除临时解压文件
//            FileUtil.batchDeleteFile(files);
//            //批量存入数据库
//            if (eqMobileMacList.size() > 0) {
//                eqMobileMacService.batchSave(eqMobileMacList);
//                for (EqMobileMac eqMobileMac : eqMobileMacList) {
//                    redisTemplate.convertAndSend(SIGNALCHANNEL_MAC, JSON.toJSONString(eqMobileMac));
//                }
//            } else {
//                log.debug("文件中没有解析到任何数据!" + unZipTempPath + zipFile.getName());
//            }
//        }

    }
}
