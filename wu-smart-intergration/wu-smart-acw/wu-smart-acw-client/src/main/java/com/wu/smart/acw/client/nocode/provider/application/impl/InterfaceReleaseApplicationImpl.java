package com.wu.smart.acw.client.nocode.provider.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceReleaseApplication;
import com.wu.smart.acw.client.nocode.provider.application.assembler.InterfaceReleaseDTOAssembler;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceReleaseCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.release.InterfaceRelease;
import com.wu.smart.acw.client.nocode.provider.model.interface_.release.InterfaceReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.List;

/**
 * describe Dataway API 发布历史。 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@LazyApplication
public class InterfaceReleaseApplicationImpl implements InterfaceReleaseApplication {

    @Autowired
    InterfaceReleaseRepository interfaceReleaseRepository;
    /**
     * describe 新增Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceRelease> story(InterfaceReleaseCommand interfaceReleaseCommand) {
        InterfaceRelease interfaceRelease = InterfaceReleaseDTOAssembler.toInterfaceRelease(interfaceReleaseCommand);
        return interfaceReleaseRepository.story(interfaceRelease);
    }
    /**
     * describe 更新Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceRelease> updateOne(InterfaceReleaseCommand interfaceReleaseCommand) {
        InterfaceRelease interfaceRelease = InterfaceReleaseDTOAssembler.toInterfaceRelease(interfaceReleaseCommand);
        return interfaceReleaseRepository.story(interfaceRelease);
    }

    /**
     * describe 查询单个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceRelease> findOne(InterfaceReleaseCommand interfaceReleaseCommand) {
        InterfaceRelease interfaceRelease = InterfaceReleaseDTOAssembler.toInterfaceRelease(interfaceReleaseCommand);
        return interfaceReleaseRepository.findOne(interfaceRelease);
    }

    /**
     * describe 查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<List<InterfaceRelease>> findList(InterfaceReleaseCommand interfaceReleaseCommand) {
        InterfaceRelease interfaceRelease = InterfaceReleaseDTOAssembler.toInterfaceRelease(interfaceReleaseCommand);
        return interfaceReleaseRepository.findList(interfaceRelease);
    }

    /**
     * describe 分页查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<LazyPage<InterfaceRelease>> findPage(int size, int current, InterfaceReleaseCommand interfaceReleaseCommand) {
        InterfaceRelease interfaceRelease = InterfaceReleaseDTOAssembler.toInterfaceRelease(interfaceReleaseCommand);
        return interfaceReleaseRepository.findPage(size,current,interfaceRelease);
    }

    /**
     * describe 删除Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceRelease> remove(InterfaceReleaseCommand interfaceReleaseCommand) {
     InterfaceRelease interfaceRelease = InterfaceReleaseDTOAssembler.toInterfaceRelease(interfaceReleaseCommand);
     return interfaceReleaseRepository.remove(interfaceRelease);
    }

}