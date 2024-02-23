package com.wu.framework.inner.file.wrapper.config;


import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.util.ObjectUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 文件配置
 */

public class WuFileProperties {

    @Resource
    private ServerProperties serverProperties;

    /**
     * 文件上传成功后服务器ip
     */
    public String getAccessPath() {
        return null;
    }


    public void setAccessPath(String accessPath) {
    }


    public String getRealAccessPath() {
        if (ObjectUtils.isEmpty(getAccessPath())) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                return "http://" + inetAddress.getHostAddress() + ":" + serverProperties.getPort() + "/media/";
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return "";
        } else {
            return getAccessPath();
        }
    }
//  使用 getRealAccessPath 需要实现的控制器
//    控制器 /media
//    /**
//     * http://www.yuntsoft.com:7389/group1/M00/00/00/wKgBVl6G1oeAUe6LAAAYrN2lYrE426.txt
//     * @param httpServletResponse
//     * @param groupName
//     * @param path
//     * @throws IOException
//     */
//    @ApiOperation("获取媒体接口")
//    @GetMapping("/{groupName}/{path1}/{path2}/{path3}/{path4}")
//    public void getMedia(HttpServletResponse httpServletResponse,
//                         @Parameter("分组") @PathVariable String groupName,
//                         @Parameter("路径1") @PathVariable String path1,
//                         @Parameter("路径2") @PathVariable String path2,
//                         @Parameter("路径3") @PathVariable String path3,
//                         @Parameter("路径4") @PathVariable String path4) throws IOException {
//        String path=path1+"/"+path2+"/"+path3+"/"+path4;
//        DownloadByteArray downloadByteArray = new DownloadByteArray();
//        byte [] bytes= fastDFSClientWrapper.getStorageClient().downloadFile(groupName, path,downloadByteArray);
////        callback.recv()
//        httpServletResponse.setContentType("image/jpeg");
//        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//        servletOutputStream.write(bytes);
//        servletOutputStream.close();
//    }
//

}
