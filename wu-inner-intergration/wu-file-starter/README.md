### 使用方法配置
    #fastdfs配置
    fdfs:
      connect-timeout: 600
      file:
        access-path: http://www.wu.com:7389/
    #  pool:
    #    max-total: 100
    #    max-wait-millis: 100
    #  soTimeout: 1500
      tracker-list: www.wu.com:22122
    dev:
      fdfs:
        file:
          access-path: http://www.wu.com:7389/
        tracker-list: 192.168.1.86:22122
### 服务器下载图片
        使用 getRealAccessPath 需要实现的控制器
        控制器 /media
        /**
         * http://www.wu.com:7389/group1/M00/00/00/wKgBVl6G1oeAUe6LAAAYrN2lYrE426.txt
         * @param httpServletResponse
         * @param groupName
         * @param path
         * @throws IOException
         */
        @ApiOperation("获取媒体接口")
        @GetMapping("/{groupName}/{path1}/{path2}/{path3}/{path4}")
        public void getMedia(HttpServletResponse httpServletResponse,
                             @ApiParam("分组") @PathVariable String groupName,
                             @ApiParam("路径1") @PathVariable String path1,
                             @ApiParam("路径2") @PathVariable String path2,
                             @ApiParam("路径3") @PathVariable String path3,
                             @ApiParam("路径4") @PathVariable String path4) throws IOException {
            String path=path1+"/"+path2+"/"+path3+"/"+path4;
            DownloadByteArray downloadByteArray = new DownloadByteArray();
            byte [] bytes= fastDFSClientWrapper.getStorageClient().downloadFile(groupName, path,downloadByteArray);
    //        callback.recv()
            httpServletResponse.setContentType("image/jpeg");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            servletOutputStream.write(bytes);
            servletOutputStream.close();
        }
