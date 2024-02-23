package com.wu.framework.inner.layer.data.encryption.adapter;


import com.wu.framework.inner.layer.data.encryption.*;
import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;

import java.util.ArrayList;

class EncryptionDecryptionAdapterTest {

    public static EncryptionDecryptionAdapter encryptionDecryptionAdapter = new EncryptionDecryptionAdapter(new ArrayList<>() {
        {
            add(new EncryptionDecryptionAdvancedEncryptionStandard());
            add(new EncryptionDecryptionAsymmetricCryptography());
            add(new EncryptionDecryptionBase64());
            add(new EncryptionDecryptionMd5());
            add(new EncryptionDecryptionSecureHashAlgorithm());
        }
    });

    public static void main(String[] args) {

        String source = "吴佳伟";
        Object encryption, decryption;
        encryption = encryptionDecryptionAdapter.encryption(EncryptionDecryptionEnum.MD5, source);
        decryption = encryptionDecryptionAdapter.decryption(EncryptionDecryptionEnum.MD5, encryption);
        System.out.println("加密方式:" + EncryptionDecryptionEnum.MD5 + " 加密结果:" + encryption + " 解密结果:" + decryption);


        encryption = encryptionDecryptionAdapter.encryption(EncryptionDecryptionEnum.BASE64, source);
        decryption = encryptionDecryptionAdapter.decryption(EncryptionDecryptionEnum.BASE64, encryption);
        System.out.println("加密方式:" + EncryptionDecryptionEnum.BASE64 + " 加密结果:" + encryption + " 解密结果:" + decryption);


        encryption = encryptionDecryptionAdapter.encryption(EncryptionDecryptionEnum.AES, source);
        decryption = encryptionDecryptionAdapter.decryption(EncryptionDecryptionEnum.AES, encryption);
        System.out.println("加密方式:" + EncryptionDecryptionEnum.AES + " 加密结果:" + encryption + " 解密结果:" + decryption);

        encryption = encryptionDecryptionAdapter.encryption(EncryptionDecryptionEnum.RSA, source);
        decryption = encryptionDecryptionAdapter.decryption(EncryptionDecryptionEnum.RSA, encryption);
        System.out.println("加密方式:" + EncryptionDecryptionEnum.RSA + " 加密结果:" + encryption + " 解密结果:" + decryption);

        encryption = encryptionDecryptionAdapter.encryption(EncryptionDecryptionEnum.SHA, source);
        decryption = encryptionDecryptionAdapter.decryption(EncryptionDecryptionEnum.SHA, encryption);
        System.out.println("加密方式:" + EncryptionDecryptionEnum.SHA + " 加密结果:" + encryption + " 解密结果:" + decryption);


    }
}