package com.wu.framework.easy.excel.endpoint;

import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.inner.layer.toolkit.DynamicLazyAttributeContextHolder;
import lombok.Data;

/**
 * describe : 简单文件断点
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/2 18:59
 * @see EasyFile
 * @see DynamicEasyFileContextHolder
 */
@Data
public class EasyFilePoint {


    /**
     * 文件名
     *
     * @return String
     * <p>
     * to dynamic fileName
     * Class:EasyExcel  Object:fileName
     * @see DynamicLazyAttributeContextHolder#push(Class, Object)
     * @see EasyFile#fileName()
     */
    private String fileName = "temp";

    /**
     * 文件后缀
     *
     * @return String
     * @see EasyFile#suffix()
     */
    private String suffix = "easy";

    /**
     * 文件类型
     *
     * @see EasyFile#fileType()
     */
    private EasyFile.FileType fileType = EasyFile.FileType.STRING_TYPE;

}
