package com.wu.framework.doc.platform.controller;

import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.UUID;

/**
 * description html转换成pdf
 *
 * @author 吴佳伟
 * @date 2023/10/20 12:12
 */
@Tag(name = "html转换成pdf")
@EasyController("/html/2/pdf")
public class Html2PdfProvider {

    /**
     * 将html 字符串转换成 pdf
     *
     * @param htmlContent html 字符串
     * @return pdf文件流
     */
    @EasyFile(fileName = "pdf", suffix = ".pdf", fileType = EasyFile.FileType.FILE_TYPE)
    @ApiOperation("将html 字符串转换成 pdf")
    @PostMapping("/htmlString2Pdf")
    public Object htmlString2Pdf(@RequestBody String htmlContent) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(htmlContent)));
            ITextRenderer iTextRenderer = new ITextRenderer();
            iTextRenderer.setDocument(document, null);
            iTextRenderer.layout();
            File tempFile = File.createTempFile(FileUtil.readLocalSrcMainClassFolder(Html2PdfProvider.class), UUID.randomUUID().toString());
            OutputStream outputStream = new FileOutputStream(tempFile);
            iTextRenderer.createPDF(outputStream);
            outputStream.close();
            EasyFilePoint easyFilePoint = new EasyFilePoint();
            easyFilePoint.setFileName(tempFile.getName());
            easyFilePoint.setFileType(EasyFile.FileType.FILE_TYPE);
            easyFilePoint.setSuffix(".pdf");
            DynamicEasyFileContextHolder.push(easyFilePoint);
            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
            RuntimeExceptionFactory.of(e);
        }
        return null;
    }

    /**
     * 将html 地址转换成 pdf
     *
     * @param url html 地址
     * @return
     */
    @EasyFile(fileName = "pdf", suffix = ".pdf", fileType = EasyFile.FileType.FILE_TYPE)
    @ApiOperation("将html 地址转换成 pdf")
    @PostMapping("/htmlUrl2Pdf")
    public Object htmlUrl2Pdf(String url) {
        try {
            URL u = new URL(url);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(u.openStream()));
            ITextRenderer iTextRenderer = new ITextRenderer();
            iTextRenderer.setDocument(document, null);
            iTextRenderer.layout();
            File tempFile = File.createTempFile(FileUtil.readLocalSrcMainClassFolder(Html2PdfProvider.class), UUID.randomUUID().toString());
            OutputStream outputStream = new FileOutputStream(tempFile);
            iTextRenderer.createPDF(outputStream);
            outputStream.close();
            EasyFilePoint easyFilePoint = new EasyFilePoint();
            easyFilePoint.setFileName(tempFile.getName());
            easyFilePoint.setFileType(EasyFile.FileType.FILE_TYPE);
            easyFilePoint.setSuffix(".pdf");
            DynamicEasyFileContextHolder.push(easyFilePoint);
            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
            RuntimeExceptionFactory.of(e);
        }
        return null;
    }
}
