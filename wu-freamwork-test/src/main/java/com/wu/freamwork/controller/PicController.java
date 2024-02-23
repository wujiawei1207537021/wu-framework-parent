package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.util.BinHexSwitchUtil;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.freamwork.domain.PicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    private final LazyOperation operation;

    public PicController(LazyOperation operation) {
        this.operation = operation;
    }


    @GetMapping("/list")
    public Result list() {
        final List<EasyHashMap> list = operation.executeSQL("select * form pic_info ", EasyHashMap.class);
        return ResultFactory.successOf(list);
    }

    @PostMapping("/save")
    public void savePic(@RequestPart MultipartFile multipartFile) throws IOException {

        PicInfo picInfo = new PicInfo();
        picInfo.setName(multipartFile.getName());
        picInfo.setFile(multipartFile.getBytes());
        operation.smartUpsert(picInfo);

        final String s = BinHexSwitchUtil.bytesToHexSql(multipartFile.getBytes());
//
        operation.executeSQLForBean(String.format(" insert into  pic_info (file,name) values (%s,'测试图片')", s), Boolean.class);
    }

    @GetMapping("/{id}")
    public void get(@PathVariable String id,
                    HttpServletResponse response) {
        final byte[] photoByte = operation.executeSQLForBean(String.format("select file from pic_info where id=%s ", id), byte[].class);
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
