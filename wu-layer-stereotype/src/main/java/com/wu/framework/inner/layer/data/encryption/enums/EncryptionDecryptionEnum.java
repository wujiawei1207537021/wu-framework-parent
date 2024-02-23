package com.wu.framework.inner.layer.data.encryption.enums;

import com.wu.framework.inner.layer.data.encryption.*;

/**
 * description 加解密枚举
 *
 * @author 吴佳伟
 * @date 2023/08/29 13:30
 */
public enum EncryptionDecryptionEnum {
    /**
     * @see EncryptionDecryptionMd5
     */
    MD5,
    /**
     * @see EncryptionDecryptionBase64
     */
    BASE64,
    /**
     * @see EncryptionDecryptionAdvancedEncryptionStandard
     */
    AES,
    /**
     * @see EncryptionDecryptionAsymmetricCryptography
     */
    RSA,
    /**
     * @see EncryptionDecryptionSecureHashAlgorithm
     */
    SHA,


}
