package com.wu.framework.inner.layer.data.encryption;

import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;
import org.springframework.util.ObjectUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.function.Supplier;

/**
 * description 对称加密算法 加解密
 *
 * @author 吴佳伟
 * @date 2023/08/29 13:28
 * @see EncryptionDecryptionEnum#AES
 */
public class EncryptionDecryptionAdvancedEncryptionStandard extends AbstractEncryptionDecryption {

    public static String AES_KEY = "123456789ABCDEFG";//秘钥(需要使用长度为16、24或32的字节数组作为AES算法的密钥，否则就会遇到java.security.InvalidKeyException异常)

    /**
     * 是否支持当前加密
     *
     * @param encryptionDecryptionEnum 加密方式
     * @return
     */
    @Override
    public boolean support(EncryptionDecryptionEnum encryptionDecryptionEnum) {
        return EncryptionDecryptionEnum.AES.equals(encryptionDecryptionEnum);
    }

    /**
     * @param source   原数据
     * @param supplier 消费参数
     * @return 加密后数据
     */
    @Override
    public Object encryption(Object source, Supplier<?> supplier) {
        try {
            String aesKey;
            if (supplier == null) {
                aesKey = AES_KEY;
            } else {
                aesKey = (String) supplier.get();
                if (ObjectUtils.isEmpty(aesKey)) {
                    aesKey = AES_KEY;
                }
            }
            // 创建AES加密算法实例(根据传入指定的秘钥进行加密)
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(), "AES");

            // 初始化为加密模式，并将密钥注入到算法中
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // 将传入的文本加密
            byte[] encrypted = cipher.doFinal(source.toString().getBytes());

            //生成密文
            // 将密文进行Base64编码，方便传输
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param encryptionSource 加密后数据
     * @param supplier         消费参数
     * @return 解密后数据
     */
    @Override
    public Object decryption(Object encryptionSource, Supplier<?> supplier) {
        try {
            String aesKey;
            if (supplier == null) {
                aesKey = AES_KEY;
            } else {
                aesKey = (String) supplier.get();
                if (ObjectUtils.isEmpty(aesKey)) {
                    aesKey = AES_KEY;
                }
            }
            // 创建AES解密算法实例
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(), "AES");

            // 初始化为解密模式，并将密钥注入到算法中
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            // 将Base64编码的密文解码
            byte[] encrypted = Base64.getDecoder().decode(encryptionSource.toString());

            // 解密
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
