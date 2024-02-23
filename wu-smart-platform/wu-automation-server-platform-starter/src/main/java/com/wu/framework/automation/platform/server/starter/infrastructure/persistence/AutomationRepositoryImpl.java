package com.wu.framework.automation.platform.server.starter.infrastructure.persistence;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.Automation;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.AutomationRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wu.framework.automation.platform.server.starter.infrastructure.converter.AutomationConverter;
import com.wu.framework.automation.platform.server.starter.infrastructure.entity.AutomationDO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 自动化
 *
 * @author Jia wei Wu
 * @date 2023/11/06 01:57 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class AutomationRepositoryImpl implements AutomationRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<Automation> story(Automation automation) {
        AutomationDO automationDO = AutomationConverter.INSTANCE.fromAutomation(automation);
        lazyLambdaStream.upsert(automationDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<List<Automation>> batchStory(List<Automation> automationList) {
        List<AutomationDO> automationDOList = automationList.stream().map(AutomationConverter.INSTANCE::fromAutomation).collect(Collectors.toList());
        lazyLambdaStream.upsert(automationDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<Automation> findOne(Automation automation) {
        AutomationDO automationDO = AutomationConverter.INSTANCE.fromAutomation(automation);
        Automation automationOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(automationDO), Automation.class);
        return ResultFactory.successOf(automationOne);
    }

    /**
     * describe 查询多个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<List<Automation>> findList(Automation automation) {
        AutomationDO automationDO = AutomationConverter.INSTANCE.fromAutomation(automation);
        List<Automation> automationList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(automationDO), Automation.class);
        return ResultFactory.successOf(automationList);
    }

    /**
     * describe 分页查询多个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<LazyPage<Automation>> findPage(int size, int current, Automation automation) {
        AutomationDO automationDO = AutomationConverter.INSTANCE.fromAutomation(automation);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<Automation> automationLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(automationDO), lazyPage, Automation.class);
        return ResultFactory.successOf(automationLazyPage);
    }

    /**
     * describe 删除自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<Automation> remove(Automation automation) {
        AutomationDO automationDO = AutomationConverter.INSTANCE.fromAutomation(automation);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(automationDO));
        return ResultFactory.successOf();
    }

}