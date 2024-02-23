package com.wu.framework.play.platform.provider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.play.platform.domain.TranslateUo;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * describe : 翻译
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/10/1 21:13
 */
@Tag(name = "翻译")
//@EasyController("/translate")
public class TranslateProvider extends AbstractLazyCrudProvider<TranslateUo,TranslateUo, Long> {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();
    private final LazyLambdaStream lazyLambdaStream;

    protected TranslateProvider(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    public static void main(String[] args) {
        /**
         *    q=apple
         *      from=en
         *      to=zh
         *      appid=2015063000000001（请替换为您的appid）
         *      salt=1435660288（随机码）
         *      平台分配的密钥: 12345678
         *      拼接appid=2015063000000001+q=apple+salt=1435660288+密钥=12345678得到字符串1：“2015063000000001apple143566028812345678”
         *      sign=MD5(2015063000000001apple143566028812345678)，得到sign=f89f9594663708c1605f3d736d01d2d4
         */
        String q = "平台分配的密钥";
        String from = "zh";
        String to = "en";
        String appid = "20210302000713203";
        long salt = System.currentTimeMillis();
        String appSecret = "hKDKIQwN0vQHkL_BUY5X";


        String cipherText = appid + q + salt + appSecret;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var5) {
            throw new IllegalStateException(
                    "MD5 algorithm not available.  Fatal (should be in the JDK).");
        }
        byte[] bytes = digest.digest(cipherText.getBytes(StandardCharsets.UTF_8));

        String sign = String.format("%032x", new BigInteger(1, bytes));

        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?q={1}&from=zh&to=en&appid={2}&salt={3}&sign={4}";
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        JSONObject s = restTemplate.getForObject(url,
                JSONObject.class, q, appid, salt, sign
        );
        System.out.println(s);
    }

    /**
     * describe 翻译数据
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/2 00:00
     **/
    @ApiOperation("翻译数据")
    @GetMapping()
    public Result translate(@RequestParam String word) {
        return baiduTranslate(word, "zh", "en");
    }

    /**
     * describe 翻译数据
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/2 00:00
     **/
    @ApiOperation("翻译数据")
    @GetMapping("/baidu")
    public Result baiduTranslate(@RequestParam String word, @RequestParam String from, @RequestParam String to) {
        /**
         *    q=apple
         *      from=en
         *      to=zh
         *      appid=2015063000000001（请替换为您的appid）
         *      salt=1435660288（随机码）
         *      平台分配的密钥: 12345678
         *      拼接appid=2015063000000001+q=apple+salt=1435660288+密钥=12345678得到字符串1：“2015063000000001apple143566028812345678”
         *      sign=MD5(2015063000000001apple143566028812345678)，得到sign=f89f9594663708c1605f3d736d01d2d4
         */
        String q = word;
//        String from = "zh";
//        String to = "en";
        String appid = "20210302000713203";
        long salt = System.currentTimeMillis();
        String appSecret = "hKDKIQwN0vQHkL_BUY5X";


        String cipherText = appid + q + salt + appSecret;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var5) {
            throw new IllegalStateException(
                    "MD5 algorithm not available.  Fatal (should be in the JDK).");
        }
        byte[] bytes = digest.digest(cipherText.getBytes(StandardCharsets.UTF_8));

        String sign = String.format("%032x", new BigInteger(1, bytes));

        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?q={1}&from={2}&to={3}&appid={4}&salt={5}&sign={6}";
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        JSONObject jsonObject = restTemplate.getForObject(url,
                JSONObject.class,
                q, from, to,
                appid, salt, sign
        );
        JSONArray trans_result = jsonObject.getJSONArray("trans_result");
        List<Map> maps = trans_result.toJavaList(Map.class);
        for (Map map : maps) {
            String source = (String) map.get("src");
            String dst = (String) map.get("dst");
            TranslateUo translateUo = new TranslateUo();
            translateUo.setFrom(from);
            translateUo.setTo(to);
            translateUo.setSourceWord(word);
            translateUo.setTranslateWord(dst);
            lazyLambdaStream.upsert(translateUo);
        }

        return ResultFactory.successOf(jsonObject);
    }

}
