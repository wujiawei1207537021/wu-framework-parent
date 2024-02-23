package com.wu.smart.acw.client.nocode.provider.application.impl;


import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceOutParamApplication;
import com.wu.smart.acw.client.nocode.provider.application.assembler.InterfaceOutParamDTOAssembler;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceOutParamCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParam;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.List;

/**
 * describe 接口输出参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@LazyApplication
public class InterfaceOutParamApplicationImpl implements InterfaceOutParamApplication {

    @Autowired
    InterfaceOutParamRepository interfaceOutParamRepository;
    /**
     * describe 新增接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceOutParam> story(InterfaceOutParamCommand interfaceOutParamCommand) {
        InterfaceOutParam interfaceOutParam = InterfaceOutParamDTOAssembler.toInterfaceOutParam(interfaceOutParamCommand);
        return interfaceOutParamRepository.story(interfaceOutParam);
    }
    /**
     * describe 更新接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceOutParam> updateOne(InterfaceOutParamCommand interfaceOutParamCommand) {
        InterfaceOutParam interfaceOutParam = InterfaceOutParamDTOAssembler.toInterfaceOutParam(interfaceOutParamCommand);
        return interfaceOutParamRepository.story(interfaceOutParam);
    }

    /**
     * describe 查询单个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceOutParam> findOne(InterfaceOutParamCommand interfaceOutParamCommand) {
        InterfaceOutParam interfaceOutParam = InterfaceOutParamDTOAssembler.toInterfaceOutParam(interfaceOutParamCommand);
        return interfaceOutParamRepository.findOne(interfaceOutParam);
    }

    /**
     * describe 查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<List<InterfaceOutParam>> findList(InterfaceOutParamCommand interfaceOutParamCommand) {
        InterfaceOutParam interfaceOutParam = InterfaceOutParamDTOAssembler.toInterfaceOutParam(interfaceOutParamCommand);
        return interfaceOutParamRepository.findList(interfaceOutParam);
    }

    /**
     * describe 分页查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<LazyPage<InterfaceOutParam>> findPage(int size, int current, InterfaceOutParamCommand interfaceOutParamCommand) {
        InterfaceOutParam interfaceOutParam = InterfaceOutParamDTOAssembler.toInterfaceOutParam(interfaceOutParamCommand);
        return interfaceOutParamRepository.findPage(size,current,interfaceOutParam);
    }

    /**
     * describe 删除接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceOutParam> remove(InterfaceOutParamCommand interfaceOutParamCommand) {
     InterfaceOutParam interfaceOutParam = InterfaceOutParamDTOAssembler.toInterfaceOutParam(interfaceOutParamCommand);
     return interfaceOutParamRepository.remove(interfaceOutParam);
    }

}