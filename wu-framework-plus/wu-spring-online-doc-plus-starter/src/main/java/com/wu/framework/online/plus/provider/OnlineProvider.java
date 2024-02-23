package com.wu.framework.online.plus.provider;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.online.plus.provider.dto.AnalysisPathDTO;
import com.wu.framework.online.plus.provider.dto.ApplicationPathDTO;
import com.wu.framework.online.plus.provider.dto.ParamDTO;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description 查询项目中所有的接口
 *
 * @author 吴佳伟
 * @date 2023/10/13 14:06
 */
@Tag(name = "查询项目中所有的接口")
@EasyController("/v1/online")
public class OnlineProvider {

    private final WebApplicationContext applicationContext;
    private final List<RequestMappingHandlerMapping> requestMappingHandlerMappingList;

    public OnlineProvider(WebApplicationContext applicationContext, List<RequestMappingHandlerMapping> requestMappingHandlerMappingList) {
        this.applicationContext = applicationContext;
        this.requestMappingHandlerMappingList = requestMappingHandlerMappingList;
    }
    // 查询所有path

    @Operation(summary = "查询应用所有Path")
    @GetMapping("/findApplicationPath")
    public Result<List<ApplicationPathDTO>> findApplicationPath() {
        List<ApplicationPathDTO> applicationPathDTOS = new ArrayList<>();

        for (RequestMappingHandlerMapping mapping : requestMappingHandlerMappingList) {
            // 获取URL与类和方法的对应信息
            Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

            for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : map.entrySet()) {

                ApplicationPathDTO applicationPathDTO = new ApplicationPathDTO();
                RequestMappingInfo info = requestMappingInfoHandlerMethodEntry.getKey();
                HandlerMethod handlerMethod = requestMappingInfoHandlerMethodEntry.getValue();
                PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
                PathPatternsRequestCondition pathPatternsCondition = info.getPathPatternsCondition();
                Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
                if (!ObjectUtils.isEmpty(pathPatternsCondition)) {
                    for (PathPattern pattern : pathPatternsCondition.getPatterns()) {
                        String url = pattern.getPatternString();
                        applicationPathDTO.setPath(url);
                    }
                }

                Method method = handlerMethod.getMethod();
                AnalysisPathDTO analysisPathDTOByMethod = getAnalysisPathDTOByMethod(method);
                String className = method.getDeclaringClass().getName();
                String classSimpleName = method.getDeclaringClass().getSimpleName();


                String methodName = method.getName();

                RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
                for (RequestMethod requestMethod : methodsCondition.getMethods()) {

                }
                applicationPathDTO.setClassName(className);

                applicationPathDTO.setGroup(classSimpleName);
                applicationPathDTO.setMethodName(methodName);

                applicationPathDTO.setRequestMethods(methods);
                applicationPathDTO.setAnalysisPathDTO(analysisPathDTOByMethod);
                applicationPathDTOS.add(applicationPathDTO);

            }
        }
        return ResultFactory.successOf(applicationPathDTOS);
    }

    private AnalysisPathDTO getAnalysisPathDTOByMethod(Method method) {
        AnalysisPathDTO analysisPathDTO = new AnalysisPathDTO();
        List<ParamDTO> paramDTOList = Arrays.stream(method.getParameters()).map(parameter -> {
            ParamDTO paramDTO = new ParamDTO();
            Class<?> type = parameter.getType();
            String name = parameter.getName();
            paramDTO.setType(type);
            paramDTO.setName(name);
            paramDTO.setLabel(name);
            return paramDTO;
        }).collect(Collectors.toList());

        Class<?> returnType = method.getReturnType();
        if (returnType != void.class) {

        }
        analysisPathDTO.setParamList(paramDTOList);

        return analysisPathDTO;

    }

}
