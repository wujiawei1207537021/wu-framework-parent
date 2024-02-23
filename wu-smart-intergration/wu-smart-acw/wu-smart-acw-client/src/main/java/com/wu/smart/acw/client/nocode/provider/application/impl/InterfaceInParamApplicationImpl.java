package com.wu.smart.acw.client.nocode.provider.application.impl;


import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceInParamApplication;
import com.wu.smart.acw.client.nocode.provider.application.assembler.InterfaceInParamDTOAssembler;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInParamCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParam;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.List;

/**
 * describe 接口输入参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@LazyApplication
public class InterfaceInParamApplicationImpl implements InterfaceInParamApplication {

    @Autowired
    InterfaceInParamRepository interfaceInParamRepository;
    /**
     * describe 新增接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceInParam> story(InterfaceInParamCommand interfaceInParamCommand) {
        InterfaceInParam interfaceInParam = InterfaceInParamDTOAssembler.INSTANCE.toInterfaceInParam(interfaceInParamCommand);
        return interfaceInParamRepository.story(interfaceInParam);
    }
    /**
     * describe 更新接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceInParam> updateOne(InterfaceInParamCommand interfaceInParamCommand) {
        InterfaceInParam interfaceInParam = InterfaceInParamDTOAssembler.INSTANCE.toInterfaceInParam(interfaceInParamCommand);
        return interfaceInParamRepository.story(interfaceInParam);
    }

    /**
     * describe 查询单个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceInParam> findOne(InterfaceInParamCommand interfaceInParamCommand) {
        InterfaceInParam interfaceInParam = InterfaceInParamDTOAssembler.INSTANCE.toInterfaceInParam(interfaceInParamCommand);
        return interfaceInParamRepository.findOne(interfaceInParam);
    }

    /**
     * describe 查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<List<InterfaceInParam>> findList(InterfaceInParamCommand interfaceInParamCommand) {
        InterfaceInParam interfaceInParam = InterfaceInParamDTOAssembler.INSTANCE.toInterfaceInParam(interfaceInParamCommand);
        return interfaceInParamRepository.findList(interfaceInParam);
    }

    /**
     * describe 分页查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<LazyPage<InterfaceInParam>> findPage(int size, int current, InterfaceInParamCommand interfaceInParamCommand) {
        InterfaceInParam interfaceInParam = InterfaceInParamDTOAssembler.INSTANCE.toInterfaceInParam(interfaceInParamCommand);
        return interfaceInParamRepository.findPage(size,current,interfaceInParam);
    }

    /**
     * describe 删除接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceInParam> remove(InterfaceInParamCommand interfaceInParamCommand) {
     InterfaceInParam interfaceInParam = InterfaceInParamDTOAssembler.INSTANCE.toInterfaceInParam(interfaceInParamCommand);
     return interfaceInParamRepository.remove(interfaceInParam);
    }

}