package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.data.encryption.NormalConvertEncryptionDecryptionMapper;
import com.wu.framework.inner.layer.data.encryption.adapter.EncryptionDecryptionAdapter;
import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.freamwork.domain.EncryptionDecryptionConvertUserTest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * description 加密、解密测试
 *
 * @author 吴佳伟
 * @date 2023/08/30 13:55
 */
@Tag(name = "加密、解密测试")
@EasyController("/convert/encryption/decryption")
public class ConvertEncryptionDecryptionController {
    private final EncryptionDecryptionAdapter encryptionDecryptionAdapter;

    public ConvertEncryptionDecryptionController(EncryptionDecryptionAdapter encryptionDecryptionAdapter) {
        this.encryptionDecryptionAdapter = encryptionDecryptionAdapter;
    }

    /**
     * 返回list集合
     *
     * @return
     */
    @NormalConvertEncryptionDecryptionMapper
    @GetMapping("/findList")
    public List<EncryptionDecryptionConvertUserTest> findList() {
        List<EncryptionDecryptionConvertUserTest> EncryptionDecryptionConvertUserTests = DataTransformUntil.simulationBeanList(EncryptionDecryptionConvertUserTest.class, 10);
        for (int i = 0; i < EncryptionDecryptionConvertUserTests.size(); i++) {
            EncryptionDecryptionConvertUserTest EncryptionDecryptionConvertUserTest = EncryptionDecryptionConvertUserTests.get(i);
            EncryptionDecryptionConvertUserTest.setSex(i % 3);
        }
        return EncryptionDecryptionConvertUserTests;
    }

    /**
     * 返回分页
     *
     * @return
     */
    @NormalConvertEncryptionDecryptionMapper
    @GetMapping("/findPage")
    public LazyPage<EncryptionDecryptionConvertUserTest> findPage() {
        LazyPage<EncryptionDecryptionConvertUserTest> EncryptionDecryptionConvertUserTestLazyPage = new LazyPage<>();
        EncryptionDecryptionConvertUserTestLazyPage.setRecord(findList());
        return EncryptionDecryptionConvertUserTestLazyPage;
    }

    /**
     * 返回result  list
     *
     * @return
     */
    @NormalConvertEncryptionDecryptionMapper
    @GetMapping("/findListResult")
    public Result<List<EncryptionDecryptionConvertUserTest>> findListResult() {
        return ResultFactory.successOf(findList());
    }

    /**
     * 返回result page
     *
     * @return
     */
    @NormalConvertEncryptionDecryptionMapper
    @GetMapping("/findPageResult")
    public Result<LazyPage<EncryptionDecryptionConvertUserTest>> findPageResult() {
        return ResultFactory.successOf(findPage());
    }

    /**
     * 加密
     *
     * @param encryptionDecryptionEnum 加密方式
     * @param source                   加密数据
     * @return
     */
    @GetMapping("/encryption")
    public Result<String> encryption(@RequestParam EncryptionDecryptionEnum encryptionDecryptionEnum, String source) {
        Object decryption = encryptionDecryptionAdapter.encryption(encryptionDecryptionEnum, source);
        return ResultFactory.successOf(decryption.toString());
    }

    /**
     * 解密
     *
     * @param encryptionDecryptionEnum 加密方式
     * @param encryptionSource         加密数据
     * @return
     */
    @GetMapping("/decryption")
    public Result<String> decryption(@RequestParam EncryptionDecryptionEnum encryptionDecryptionEnum, String encryptionSource) {
        Object decryption = encryptionDecryptionAdapter.decryption(encryptionDecryptionEnum, encryptionSource);
        return ResultFactory.successOf(decryption.toString());
    }
}
