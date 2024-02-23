package com.wu.framework.automation.platform.server.starter.infrastructure.persistence;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action.AutomationNodeHttpAction;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action.AutomationNodeHttpActionRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wu.framework.automation.platform.server.starter.infrastructure.converter.AutomationNodeHttpActionConverter;
import com.wu.framework.automation.platform.server.starter.infrastructure.entity.AutomationNodeHttpActionDO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 自动化http节点动作
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class AutomationNodeHttpActionRepositoryImpl implements AutomationNodeHttpActionRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<AutomationNodeHttpAction> story(AutomationNodeHttpAction automationNodeHttpAction) {
        AutomationNodeHttpActionDO automationNodeHttpActionDO = AutomationNodeHttpActionConverter.INSTANCE.fromAutomationNodeHttpAction(automationNodeHttpAction);
        lazyLambdaStream.upsert(automationNodeHttpActionDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<List<AutomationNodeHttpAction>> batchStory(List<AutomationNodeHttpAction> automationNodeHttpActionList) {
        List<AutomationNodeHttpActionDO> automationNodeHttpActionDOList = automationNodeHttpActionList.stream().map(AutomationNodeHttpActionConverter.INSTANCE::fromAutomationNodeHttpAction).collect(Collectors.toList());
        lazyLambdaStream.upsert(automationNodeHttpActionDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<AutomationNodeHttpAction> findOne(AutomationNodeHttpAction automationNodeHttpAction) {
        AutomationNodeHttpActionDO automationNodeHttpActionDO = AutomationNodeHttpActionConverter.INSTANCE.fromAutomationNodeHttpAction(automationNodeHttpAction);
        AutomationNodeHttpAction automationNodeHttpActionOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(automationNodeHttpActionDO), AutomationNodeHttpAction.class);
        return ResultFactory.successOf(automationNodeHttpActionOne);
    }

    /**
     * describe 查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<List<AutomationNodeHttpAction>> findList(AutomationNodeHttpAction automationNodeHttpAction) {
        AutomationNodeHttpActionDO automationNodeHttpActionDO = AutomationNodeHttpActionConverter.INSTANCE.fromAutomationNodeHttpAction(automationNodeHttpAction);
        List<AutomationNodeHttpAction> automationNodeHttpActionList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(automationNodeHttpActionDO), AutomationNodeHttpAction.class);
        return ResultFactory.successOf(automationNodeHttpActionList);
    }

    /**
     * describe 分页查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<LazyPage<AutomationNodeHttpAction>> findPage(int size, int current, AutomationNodeHttpAction automationNodeHttpAction) {
        AutomationNodeHttpActionDO automationNodeHttpActionDO = AutomationNodeHttpActionConverter.INSTANCE.fromAutomationNodeHttpAction(automationNodeHttpAction);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<AutomationNodeHttpAction> automationNodeHttpActionLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(automationNodeHttpActionDO), lazyPage, AutomationNodeHttpAction.class);
        return ResultFactory.successOf(automationNodeHttpActionLazyPage);
    }

    /**
     * describe 删除自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<AutomationNodeHttpAction> remove(AutomationNodeHttpAction automationNodeHttpAction) {
        AutomationNodeHttpActionDO automationNodeHttpActionDO = AutomationNodeHttpActionConverter.INSTANCE.fromAutomationNodeHttpAction(automationNodeHttpAction);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(automationNodeHttpActionDO));
        return ResultFactory.successOf();
    }

}