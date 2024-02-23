package com.wu.framework.inner.layer.data.encryption;

import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;

import java.security.MessageDigest;
import java.util.function.Supplier;

/**
 * description md5 加密
 *
 * @author 吴佳伟
 * @date 2023/08/29 13:28
 * @see EncryptionDecryptionEnum#MD5
 */
public class EncryptionDecryptionMd5 extends AbstractEncryptionDecryption {


    /**
     * 是否支持当前加密
     *
     * @param encryptionDecryptionEnum 加密方式
     * @return
     */
    @Override
    public boolean support(EncryptionDecryptionEnum encryptionDecryptionEnum) {
        return EncryptionDecryptionEnum.MD5.equals(encryptionDecryptionEnum);
    }

    /**
     * @param source   原数据
     * @param supplier 消费参数
     * @return 加密后数据
     */
    @Override
    public Object encryption(Object source, Supplier<?> supplier) {
        try {
            // 创建MessageDigest对象并指定其算法为MD5
            MessageDigest md = MessageDigest.getInstance(EncryptionDecryptionEnum.MD5.name());
            // 将输入字符串转换为字节数组
            byte[] inputBytes = source.toString().getBytes();
            // 使用指定的字节数组更新摘要
            md.update(inputBytes);
            // 获取摘要的字节数组
            byte[] digestBytes = md.digest();
            // 将摘要字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digestBytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
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
        return null;
    }


}
