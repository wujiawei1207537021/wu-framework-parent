package com.wu.freamwork.controller;


import com.wu.framework.inner.common.util.EasyCaptcha;
import com.wu.framework.inner.common.util.QRBO;
import com.wu.framework.inner.common.util.QRCodeGenerator;
import com.wu.framework.inner.common.util.TransparentLineCaptcha;
import com.wu.framework.inner.layer.web.EasyController;
import org.apache.pulsar.shade.io.swagger.annotations.Api;
import org.apache.pulsar.shade.io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Api(tags = "测试二维码 验证码")
@EasyController("/public/test")
public class CommonTestController {


    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response) {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        EasyCaptcha simpleCaptcha = new EasyCaptcha(200, 50, 4, 20);
        try {
            simpleCaptcha.write(response.getOutputStream());
//            RedisUtil.set(SecurityConstants.SPRING_SECURITY_RESTFUL_IMAGE_CODE+uuid, simpleCaptcha.getCode(), 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @ApiOperation("透明验证码")
    @GetMapping("/captcha2.jpg")
    public ResponseEntity<byte[]> captcha2() {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        TransparentLineCaptcha simpleCaptcha = new TransparentLineCaptcha(200, 50, 4, 20);
        // Set headers
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<byte[]>(simpleCaptcha.getImageBytes(), headers, HttpStatus.CREATED);
    }


    @GetMapping(value = "/qrimage")
    public ResponseEntity<byte[]> getQRImage(QRBO qrbo) {

        //二维码内的信息
        String info = "This is my first QR Code";

        byte[] qrcode = null;
        qrcode = QRCodeGenerator.getQRCodeImage(qrbo.clearText(), 360, 360);
        // Set headers
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(qrcode, headers, HttpStatus.CREATED);
    }


    @ApiOperation("解析二维码")
    @PostMapping(value = "/decode")
    public String decode(@RequestPart MultipartFile multipartFile) throws Exception {
        File file = File.createTempFile("temp", "file");
        multipartFile.transferTo(file);
        file.deleteOnExit();
        final String decode = QRCodeGenerator.decode(file);
        return decode;
    }


}
