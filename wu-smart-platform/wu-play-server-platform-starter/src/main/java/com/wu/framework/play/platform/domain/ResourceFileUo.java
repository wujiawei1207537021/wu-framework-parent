package com.wu.framework.play.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import lombok.Data;

@LazyTable(comment = "本地文件信息")
@Data
public class ResourceFileUo {
    /**
     * 父类路径
     */
    private String rootPath;
    /**
     * 是否文件
     */
    private Boolean isFile;

    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源绝对路径
     */
    private String absolutePath;
}
