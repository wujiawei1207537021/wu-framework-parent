package com.wu.framework.inner.layer.data.encryption.adapter;


import com.wu.framework.inner.layer.data.encryption.convert.ConvertEncryptionDecryption;
import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionTypeEnum;
import org.springframework.core.Ordered;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 加解密转换适配器
 */
public class ConvertEncryptionDecryptionAdapter {

    private final List<ConvertEncryptionDecryption> convertEncryptionDecryptionList;
    private List<ConvertEncryptionDecryption> convertEncryptionDecryptionOrderList;

    public ConvertEncryptionDecryptionAdapter(List<ConvertEncryptionDecryption> convertEncryptionDecryptionList) {
        this.convertEncryptionDecryptionList = convertEncryptionDecryptionList;
    }

    /**
     * 字段转译
     *
     * @param source 需要加密的对象
     */
    public void transformation(Object source, EncryptionDecryptionTypeEnum encryptionDecryptionTypeEnum) {
        for (ConvertEncryptionDecryption convertEncryptionDecryption : getConvertEncryptionDecryptionOrderList()) {
            if (convertEncryptionDecryption.support(source)) {
                convertEncryptionDecryption.transformation(source, encryptionDecryptionTypeEnum);
                return;
            }
        }
        throw new IllegalArgumentException("无法匹配到可以使用的转换器");
    }

    /**
     * 获取排序后的加解密对象
     *
     * @return 排序后的转换处理器
     */

    public List<ConvertEncryptionDecryption> getConvertEncryptionDecryptionOrderList() {
        if (ObjectUtils.isEmpty(convertEncryptionDecryptionList)) {
            throw new IllegalArgumentException("需要至少一个加密解密实现类但是未发现任何实现类" +
                    "请实现一个 com.wu.framework.inner.layer.data.encryption.convert.ConvertEncryptionDecryption");
        }
        if (ObjectUtils.isEmpty(convertEncryptionDecryptionOrderList)) {
            convertEncryptionDecryptionOrderList = convertEncryptionDecryptionList.stream().sorted(Comparator.comparing(Ordered::getOrder)).collect(Collectors.toList());
        }
        return convertEncryptionDecryptionOrderList;
    }
}
