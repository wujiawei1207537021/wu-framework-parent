package com.wu.smart.acw.core.rpc;

import com.wu.framework.inner.layer.data.api.TranslateApi;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class TranslateRpcTest {

    @Autowired
    TranslateRpc translateRpc;

    @Test
    void translate() {
        final String s = translateRpc.translate("你好", TranslateApi.LanguageType.zh);
        System.out.println(s);

    }
}