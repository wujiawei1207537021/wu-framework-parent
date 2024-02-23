package com.wu.framework.easy.excel.endpoint.convert;

import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.inner.layer.stereotype.converter.LayerAnnotationConverterAdapter;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/2 19:23
 */
public class EasyFilePointConvert implements LayerAnnotationConverterAdapter<EasyFile, EasyFilePoint> {
    /**
     * 是否支持
     *
     * @param annotation    注解
     * @param easyFilePoint 断点
     * @return
     */
    @Override
    public boolean supports(EasyFile annotation, EasyFilePoint easyFilePoint) {
        return true;
    }

    /**
     * @param annotation 原始注解
     * @return 返回断点信息
     */
    @Override
    public EasyFilePoint converter(EasyFile annotation) {
        if (null == annotation) {
            return null;
        }
        String fileName = annotation.fileName();
        EasyFile.FileType fileType = annotation.fileType();
        String suffix = annotation.suffix();
        EasyFilePoint easyFilePoint = new EasyFilePoint();
        easyFilePoint.setFileName(fileName);
        easyFilePoint.setFileType(fileType);
        easyFilePoint.setSuffix(suffix);
        return easyFilePoint;
    }
}
