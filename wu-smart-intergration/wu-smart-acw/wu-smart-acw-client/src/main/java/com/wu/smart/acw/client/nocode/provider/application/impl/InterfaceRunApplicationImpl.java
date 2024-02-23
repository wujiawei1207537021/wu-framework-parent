package com.wu.smart.acw.client.nocode.provider.application.impl;


import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.schema.SchemaMap;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.util.SqlMessageFormatUtil;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceRunApplication;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfo;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfoRepository;
import com.wu.smart.acw.client.nocode.provider.model.interface_.table.InterfaceTable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.stream.Collectors;

@ConditionalOnBean(LazyLambdaStream.class)
@LazyApplication
public class InterfaceRunApplicationImpl implements InterfaceRunApplication {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final InterfaceInfoRepository interfaceInfoRepository;

    private final LazyLambdaStream lazyLambdaStream;

    public InterfaceRunApplicationImpl(InterfaceInfoRepository interfaceInfoRepository, LazyLambdaStream lazyLambdaStream) {
        this.interfaceInfoRepository = interfaceInfoRepository;
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * 调用
     *
     * @return
     */
    @Override
    public Result invoke() {
        // 获取请求路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String apiPath = request.getRequestURI();
        String apiMethod = request.getMethod();

        // 获取API信息
        InterfaceInfo interfaceInfoCondition = new InterfaceInfo();
        interfaceInfoCondition.setApiPath(apiPath);
        interfaceInfoCondition.setApiMethod(apiMethod);
        InterfaceInfo interfaceInfo = interfaceInfoRepository.findOne(interfaceInfoCondition).getData();

        if (Objects.isNull(interfaceInfo)) {
            return ResultFactory.of(DefaultResultCode.NO_FOUND_ERROR);
        }
        Object result = null;
        // 转换成sql
        if (interfaceInfo.getExecuteType().equalsIgnoreCase("select")) {
            // select * from table
            String pattern = "select {0} from {1} ";
            String format = SqlMessageFormatUtil.format(pattern,
                    interfaceInfo.getInterfaceOutParamList().stream().map(interfaceOutParam -> {
                                if (ObjectUtils.isEmpty(interfaceOutParam.getMappingName())) {
                                    return interfaceOutParam.getName() + NormalUsedString.SPACE;
                                }
                                return interfaceOutParam.getName() + NormalUsedString.SPACE + NormalUsedString.AS + NormalUsedString.SPACE + interfaceOutParam.getMappingName();
                            }

                    ).collect(Collectors.joining(NormalUsedString.COMMA)),
                    interfaceInfo.getInterfaceTableList().stream().map(InterfaceTable::getTableName).collect(Collectors.joining(NormalUsedString.COMMA))
            );

            switch (interfaceInfo.getApiResultType()) {
                case 0 -> result = lazyLambdaStream.executeSQLForBean(format, SchemaMap.class);
                case 1 -> result = lazyLambdaStream.executeSQL(format, SchemaMap.class);
                case 2 -> result = lazyLambdaStream.selectPage(LazyPage.of(1, 10), SchemaMap.class, format);
            }
            return ResultFactory.successOf(result);
        } else if (interfaceInfo.getExecuteType().equalsIgnoreCase("update")) {

            String pattern = "update {0} set {1} ";
            String format = SqlMessageFormatUtil.format(pattern,
                    interfaceInfo.getInterfaceTableList().stream().map(InterfaceTable::getTableName).collect(Collectors.joining(NormalUsedString.COMMA)),
                    interfaceInfo.getInterfaceOutParamList().stream().map(interfaceOutParam ->
                            interfaceOutParam.getName() + NormalUsedString.SPACE + NormalUsedString.AS + NormalUsedString.SPACE + interfaceOutParam.getMappingName()
                    ).collect(Collectors.joining(NormalUsedString.COMMA))
            );
            lazyLambdaStream.executeSQLForBean(format, SchemaMap.class);

            return ResultFactory.successOf();
        } else if (interfaceInfo.getExecuteType().equalsIgnoreCase("insert")) {
            return ResultFactory.successOf();
        } else if (interfaceInfo.getExecuteType().equalsIgnoreCase("delete")) {
            return ResultFactory.successOf();
        }
        return ResultFactory.successOf();
    }
}
