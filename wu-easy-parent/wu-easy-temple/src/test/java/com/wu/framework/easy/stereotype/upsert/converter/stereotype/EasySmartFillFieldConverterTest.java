package com.wu.framework.easy.stereotype.upsert.converter.stereotype;

import com.wu.framework.easy.temple.domain.SmartFillField;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.map.EasySmartFillFieldConverter;

import java.io.File;
import java.io.IOException;

public class EasySmartFillFieldConverterTest {


    public static void main(String[] args) throws IOException {
        final EasySmartFillFieldConverter easySmartFillFieldConverter = new EasySmartFillFieldConverter();
        final EasyHashMap<Object, Object> objectObjectEasyHashMap = new EasyHashMap<>();
        for (int i = 0; i < 10; i++) {
            objectObjectEasyHashMap.put("i" + i, i);
        }

        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        System.out.println(courseFile);

        easySmartFillFieldConverter.smartFillField(objectObjectEasyHashMap, SmartFillField.class);
    }

}