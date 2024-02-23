package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.util.BinHexSwitchUtil;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.freamwork.domain.PicInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/26 8:13 下午
 */
@Slf4j
@EasyController("/public/pic")
public class PicController {

    private final LazyLambdaStream lazyLambdaStream;

    public PicController(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }


    @GetMapping("/list")
    public Result list() {
        final List<EasyHashMap> list = lazyLambdaStream.executeSQL("select * from pic_info ", EasyHashMap.class);
        return ResultFactory.successOf(list);
    }

    @PostMapping("/save")
    public void savePic(@RequestPart MultipartFile multipartFile) throws IOException {

        PicInfo picInfo = new PicInfo();
        picInfo.setName(multipartFile.getName());
        picInfo.setFile(multipartFile.getBytes());
        lazyLambdaStream.upsert(picInfo);

        final String s = BinHexSwitchUtil.bytesToHexSql(multipartFile.getBytes());
//
        lazyLambdaStream.executeSQLForBean(String.format(" insert into  pic_info (file,name) values (%s,'测试图片')", s), Boolean.class);
    }

    @GetMapping("/{id}")
    public void get(@PathVariable String id,
                    HttpServletResponse response) {
        final byte[] photoByte = lazyLambdaStream.executeSQLForBean(String.format("select file from pic_info where id=%s ", id), byte[].class);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(photoByte);
            outputStream.flush();
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.error("error", e);
            }
        }
    }


}
