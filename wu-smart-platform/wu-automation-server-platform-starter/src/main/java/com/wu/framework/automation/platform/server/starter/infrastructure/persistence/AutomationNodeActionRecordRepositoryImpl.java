package com.wu.framework.automation.platform.server.starter.infrastructure.persistence;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.action.record.AutomationNodeActionRecord;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.action.record.AutomationNodeActionRecordRepository;
import com.wu.framework.automation.platform.server.starter.infrastructure.entity.AutomationNodeActionRecordDO;
import com.wu.framework.automation.platform.server.starter.infrastructure.converter.AutomationNodeActionRecordConverter;
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
 * describe 自动化记录 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class AutomationNodeActionRecordRepositoryImpl  implements AutomationNodeActionRecordRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<AutomationNodeActionRecord> story(AutomationNodeActionRecord automationNodeActionRecord) {
        AutomationNodeActionRecordDO automationNodeActionRecordDO = AutomationNodeActionRecordConverter.INSTANCE.fromAutomationNodeActionRecord(automationNodeActionRecord);
        lazyLambdaStream.upsert(automationNodeActionRecordDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<List<AutomationNodeActionRecord>> batchStory(List<AutomationNodeActionRecord> automationNodeActionRecordList) {
        List<AutomationNodeActionRecordDO> automationNodeActionRecordDOList = automationNodeActionRecordList.stream().map(AutomationNodeActionRecordConverter.INSTANCE::fromAutomationNodeActionRecord).collect(Collectors.toList());
        lazyLambdaStream.upsert(automationNodeActionRecordDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<AutomationNodeActionRecord> findOne(AutomationNodeActionRecord automationNodeActionRecord) {
        AutomationNodeActionRecordDO automationNodeActionRecordDO = AutomationNodeActionRecordConverter.INSTANCE.fromAutomationNodeActionRecord(automationNodeActionRecord);
        AutomationNodeActionRecord automationNodeActionRecordOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(automationNodeActionRecordDO), AutomationNodeActionRecord.class);
        return ResultFactory.successOf(automationNodeActionRecordOne);
    }

    /**
     * describe 查询多个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<List<AutomationNodeActionRecord>> findList(AutomationNodeActionRecord automationNodeActionRecord) {
        AutomationNodeActionRecordDO automationNodeActionRecordDO = AutomationNodeActionRecordConverter.INSTANCE.fromAutomationNodeActionRecord(automationNodeActionRecord);
        List<AutomationNodeActionRecord> automationNodeActionRecordList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(automationNodeActionRecordDO), AutomationNodeActionRecord.class);
        return ResultFactory.successOf(automationNodeActionRecordList);
    }

    /**
     * describe 分页查询多个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<LazyPage<AutomationNodeActionRecord>> findPage(int size,int current,AutomationNodeActionRecord automationNodeActionRecord) {
        AutomationNodeActionRecordDO automationNodeActionRecordDO = AutomationNodeActionRecordConverter.INSTANCE.fromAutomationNodeActionRecord(automationNodeActionRecord);
        LazyPage lazyPage = new LazyPage(current,size);
        LazyPage<AutomationNodeActionRecord> automationNodeActionRecordLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(automationNodeActionRecordDO),lazyPage, AutomationNodeActionRecord.class);
        return ResultFactory.successOf(automationNodeActionRecordLazyPage);
    }

    /**
     * describe 删除自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<AutomationNodeActionRecord> remove(AutomationNodeActionRecord automationNodeActionRecord) {
        AutomationNodeActionRecordDO automationNodeActionRecordDO = AutomationNodeActionRecordConverter.INSTANCE.fromAutomationNodeActionRecord(automationNodeActionRecord);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(automationNodeActionRecordDO));
        return ResultFactory.successOf();
    }

}