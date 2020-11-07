package com.wu.inner.sys.adapter.apo;

import com.wu.inner.sys.adapter.DefaultConvertAdapterService;
import com.wu.inner.sys.adapter.ConvertAdapter;
import com.wu.inner.sys.adapter.stereotype.NormalConvertMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import com.wu.inner.sys.api.ConvertApi;

/**
 * description 转换控制器 基于Java JDK 数据类型转换 (不包含 Page)
 * @author Jia wei Wu
 * @date 2020/8/25 下午2:50
 */
@Slf4j
@Aspect
public class NormalConvertMapperAspectPointAspectAOP {



    private ConvertAdapter defaultConvertConvertService;

    public NormalConvertMapperAspectPointAspectAOP(ConvertApi convertApi) {
        this.defaultConvertConvertService = new DefaultConvertAdapterService(convertApi);
    }


    @Pointcut("@annotation(normalConvertMapper)")
    public void NormalConvertMapperAspectPoint(NormalConvertMapper normalConvertMapper) {
        System.out.println("进入切面");
    }


    @AfterReturning(pointcut = "NormalConvertMapperAspectPoint(normalConvertMapper)", returning = "returnValue")
    public void afterReturning(JoinPoint point, NormalConvertMapper normalConvertMapper, Object returnValue) {
       defaultConvertConvertService.convertObjects(returnValue);
    }



}
