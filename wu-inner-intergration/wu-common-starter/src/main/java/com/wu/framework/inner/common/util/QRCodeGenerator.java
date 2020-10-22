package com.wu.framework.inner.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/6/17
 */

public class QRCodeGenerator {


    /**
     * @return byte[]
     * @params [text, width, height]
     * @author 吴佳伟
     * @date 2020/6/17 10:48 下午
     **/
    public static byte[] getQRCodeImage(String contents, int width, int height) {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());

        }
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }


}
