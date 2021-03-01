package com.wu.framework.easy.temple.domain;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import lombok.Data;

import java.io.File;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 *
 * @date : 2021/3/1 7:44 下午
 */
@EasySmart(perfectTable = true)
@Data
public class UpsertBinary {

    private File file=new File("/Users/wujiawei/Desktop/aa.mp3");
    private String name=file.getName();
}
