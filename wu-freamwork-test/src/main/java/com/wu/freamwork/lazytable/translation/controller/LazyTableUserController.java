package com.wu.freamwork.lazytable.translation.controller;

import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.freamwork.lazytable.translation.domain.LazyTableAddress;
import com.wu.freamwork.lazytable.translation.domain.LazyTableUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "lazy 框架跨表映射 用户提供者")
@EasyController("/lazy/table/user")
public class LazyTableUserController {
    private final LazyLambdaStream lazyLambdaStream;

    public LazyTableUserController(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe 查询多个lazy 框架跨表映射 用户
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/06 12:51 下午
     **/

    @Operation(summary = "查询多个lazy 框架跨表映射 用户")
    @GetMapping("/findList")
    public Result<List<LazyTableUser>> findList() {
        List<LazyTableUser> lazyTableUsers = lazyLambdaStream.selectList(LazyWrappers.<LazyTableUser>lambdaWrapper()
                .eq(LazyTableUser::getIsDeleted, false));
        return ResultFactory.successOf(lazyTableUsers);
    }

    /**
     * describe 新增lazy 框架跨表映射 用户
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/06 12:51 下午
     **/
    @Operation(summary = "新增lazy 框架跨表映射 用户")
    @PostMapping("/storyUser")
    public Result<Void> storyUser() {
        List<LazyTableUser> lazyTableUserList = DataTransformUntil.simulationBeanList(LazyTableUser.class, 10).stream().
                peek(lazyTableUser -> lazyTableUser.setAddressId(lazyTableUser.getAddressId() % 5)).collect(Collectors.toList());
        lazyLambdaStream.upsert(lazyTableUserList);
        return ResultFactory.successOf();
    }


    /**
     * describe 新增lazy 框架跨表映射 地址
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/06 12:51 下午
     **/

    @Operation(summary = "新增lazy 框架跨表映射 地址")
    @PostMapping("/storyAddress")
    public Result<Void> storyAddress() {
        lazyLambdaStream.upsert(
                DataTransformUntil.simulationBeanList(LazyTableAddress.class, 10)
                        .stream()
                        .peek(lazyTableAddress1 -> lazyTableAddress1.setId(lazyTableAddress1.getId() % 5))
                        .collect(Collectors.toList())
        );
        return ResultFactory.successOf();
    }
}
