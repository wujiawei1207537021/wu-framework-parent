package com.wu.freamwork.domain;

import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.EasySmartField;
import lombok.Data;
import lombok.experimental.Accessors;


@EasySmart(perfectTable = true)
@Data
@Accessors(chain = true)
public class PicInfo {


    @EasySmartField(columnType = "longblob")
    private byte[] file;
    private String name;


}
