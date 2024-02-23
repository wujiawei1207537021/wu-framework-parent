package com.wu.smart.acw.core.domain.uo;


import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.smart.acw.core.domain.enums.FileType;
import lombok.Data;

import java.util.List;

/**
 * description 行 code
 *
 * @author Jia wei Wu
 * @date 2022/08/15 8:54 下午
 */
@Data
@LazyTable(comment = "ACW 行 code")
public class AcwCode {

    // 文件中的代码
    private List<AcwLineCode> acwLineCodeList;
    // 文件名称
    private String name;
    // 文件目录
    private String packageName;
    // 文件类型 class、xml、。。。
    private FileType fileType;

    /**
     * 行对应的代码
     */
    @Data
    public static class AcwLineCode {

        // 行数
        private Long line;
        // 代码
        private String code;
        // 版本
        private double version;
    }

}

