package com.wu.freamwork.translation.controller;

import com.wu.framework.inner.layer.data.translation.NormalTranslation;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.freamwork.translation.domain.UserBankCard;
import com.wu.freamwork.translation.domain.UserTranslationAdvancedDO;
import com.wu.freamwork.translation.domain.UserZoneTranslationAdvancedDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * description 用户信息转换
 *
 * @author 吴佳伟
 * @date 2023/09/25 15:20
 */
@Tag(name = "用户信息转换")
@EasyController("/user/translation/advanced/target")
public class UserTranslationAdvancedTargetController {

    private final LazyLambdaStream lazyLambdaStream;

    public UserTranslationAdvancedTargetController(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    @Operation(summary = "查询用户然后转化地址温度、银行卡")
    @NormalTranslation
    @GetMapping("/findUserAndTranslationAddressName")
    public Result<List<UserTranslationAdvancedDO>> findUserAndTranslationAddressName() {
        List<UserTranslationAdvancedDO> userTranslationAdvancedDOS = lazyLambdaStream
                .selectList(
                        LazyWrappers
                                .<UserTranslationAdvancedDO>lambdaWrapper()
                                .eq(UserTranslationAdvancedDO::getIsDeleted, false)
                                .limit(10)
                );
        // 添加用户区域信息
        for (UserTranslationAdvancedDO userTranslationAdvancedDO : userTranslationAdvancedDOS) {
            UserZoneTranslationAdvancedDO userZoneTranslationAdvancedDO = DataTransformUntil.simulationBean(UserZoneTranslationAdvancedDO.class);
            userTranslationAdvancedDO.setUserBankCardList(DataTransformUntil.simulationBeanList(UserBankCard.class, 10));
            userTranslationAdvancedDO.setUserZoneTranslationAdvancedDO(userZoneTranslationAdvancedDO);
        }
        return ResultFactory.successOf(userTranslationAdvancedDOS);
    }

    @Operation(summary = "添加用户信息转换测试")
    @PostMapping("/addUserTranslationAdvanced")
    public Result<Void> addUserTranslationAdvanced() {
        lazyLambdaStream
                .upsert(DataTransformUntil.simulationBeanList(UserTranslationAdvancedDO.class, 10));
        return ResultFactory.successOf();
    }

}
