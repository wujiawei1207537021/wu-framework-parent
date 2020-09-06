package com.supconit.its.transform.util;


import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.kafka.connect.errors.DataException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * kafka connect decimal类型数据工具类
 *
 * @author 黄珺
 * @date 2020/3/30
 **/
public class DecimalUtils {

    /**
     * 将BigDecimal对象转换为字符串
     *
     * @param value       BigDecimal对象
     * @param expectScale 预期scale
     * @return 编码字符串
     */
    public static String toEncodeString(BigDecimal value, int expectScale) {
        if (value.scale() != expectScale) {
            throw new DataException("BigDecimal的scale与预期scale不符");
        }
        byte[] bytes = value.unscaledValue().toByteArray();
        return Base64Variants.getDefaultVariant().encode(bytes, false);
    }

    /**
     * 将decimal字符串转换为BigDecimal对象
     *
     * @param decimalString decimal字符串
     * @param scale         小数精度
     * @return BigDecimal
     * @throws IOException decimal字符串转换二进制数组失败
     */
    public static BigDecimal toBigDecimal(String decimalString, int scale) throws IOException {
        TextNode jsonNodes = new TextNode(decimalString);
        return new BigDecimal(new BigInteger(jsonNodes.binaryValue()), scale);
    }
}
