package com.wu.framework.easy.stereotype.upsert.entity;

import com.wu.framework.easy.stereotype.upsert.converter.stereotype.EasySmartFillFieldConverter;
import com.wu.framework.easy.stereotype.upsert.converter.stereotype.EasySmartFillFieldConverterAbstract;
import com.wu.framework.easy.stereotype.upsert.enums.NormalUsedString;
import org.springframework.web.client.RestTemplate;

public class EasyHashMapTest {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.apiopen.top/searchMusic?name=%E5%A5%BD%E4%B9%85%E4%B8%8D%E8%A7%81";
        EasyHashMap forObject = restTemplate.getForObject(url, EasyHashMap.class);

        EasySmartFillFieldConverterAbstract.CreateInfo createInfo = EasyHashMap.mapConverterJava(forObject);

        EasySmartFillFieldConverter easySmartFillFieldConverter = new EasySmartFillFieldConverter();

        createInfo.setFileSuffix(NormalUsedString.DOT_JAVA);
        System.out.println(forObject);
        easySmartFillFieldConverter.targetClassWriteAttributeFieldList(createInfo);
//        System.out.println(createInfo);

    }

}