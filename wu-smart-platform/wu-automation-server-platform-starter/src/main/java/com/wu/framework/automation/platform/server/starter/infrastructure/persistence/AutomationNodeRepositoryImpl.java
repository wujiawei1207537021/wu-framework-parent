package com.wu.framework.automation.platform.server.starter.infrastructure.persistence;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNode;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNodeRepository;
import com.wu.framework.automation.platform.server.starter.infrastructure.entity.AutomationNodeDO;
import com.wu.framework.automation.platform.server.starter.infrastructure.converter.AutomationNodeConverter;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 自动化节点 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:03 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class AutomationNodeRepositoryImpl  implements AutomationNodeRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<AutomationNode> story(AutomationNode automationNode) {
        AutomationNodeDO automationNodeDO = AutomationNodeConverter.INSTANCE.fromAutomationNode(automationNode);
        lazyLambdaStream.upsert(automationNodeDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<List<AutomationNode>> batchStory(List<AutomationNode> automationNodeList) {
        List<AutomationNodeDO> automationNodeDOList = automationNodeList.stream().map(AutomationNodeConverter.INSTANCE::fromAutomationNode).collect(Collectors.toList());
        lazyLambdaStream.upsert(automationNodeDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<AutomationNode> findOne(AutomationNode automationNode) {
        AutomationNodeDO automationNodeDO = AutomationNodeConverter.INSTANCE.fromAutomationNode(automationNode);
        AutomationNode automationNodeOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(automationNodeDO), AutomationNode.class);
        return ResultFactory.successOf(automationNodeOne);
    }

    /**
     * describe 查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<List<AutomationNode>> findList(AutomationNode automationNode) {
        AutomationNodeDO automationNodeDO = AutomationNodeConverter.INSTANCE.fromAutomationNode(automationNode);
        List<AutomationNode> automationNodeList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(automationNodeDO), AutomationNode.class);
        return ResultFactory.successOf(automationNodeList);
    }

    /**
     * describe 分页查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<LazyPage<AutomationNode>> findPage(int size,int current,AutomationNode automationNode) {
        AutomationNodeDO automationNodeDO = AutomationNodeConverter.INSTANCE.fromAutomationNode(automationNode);
        LazyPage lazyPage = new LazyPage(current,size);
        LazyPage<AutomationNode> automationNodeLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(automationNodeDO),lazyPage, AutomationNode.class);
        return ResultFactory.successOf(automationNodeLazyPage);
    }

    /**
     * describe 删除自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<AutomationNode> remove(AutomationNode automationNode) {
        AutomationNodeDO automationNodeDO = AutomationNodeConverter.INSTANCE.fromAutomationNode(automationNode);
          lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(automationNodeDO));
        return ResultFactory.successOf();
    }

}