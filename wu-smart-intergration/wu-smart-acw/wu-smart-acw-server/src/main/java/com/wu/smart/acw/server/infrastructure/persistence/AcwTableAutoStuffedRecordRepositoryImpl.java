package com.wu.smart.acw.server.infrastructure.persistence;

import com.wu.framework.inner.layer.data.translation.NormalTranslation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.domain.model.model.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecord;
import com.wu.smart.acw.server.domain.model.model.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordRepository;
import com.wu.smart.acw.server.infrastructure.converter.AcwTableAutoStuffedRecordConverter;
import com.wu.smart.acw.server.infrastructure.entity.AcwTableAutoStuffedRecordDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AcwTableAutoStuffedRecordRepositoryImpl implements AcwTableAutoStuffedRecordRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 04:43 下午
     **/

    @Override
    public Result<AcwTableAutoStuffedRecord> story(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord) {
        AcwTableAutoStuffedRecordDO acwTableAutoStuffedRecordDO = AcwTableAutoStuffedRecordConverter.INSTANCE.fromAcwTableAutoStuffedRecord(acwTableAutoStuffedRecord);
        lazyLambdaStream.upsert(acwTableAutoStuffedRecordDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 04:43 下午
     **/

    @Override
    public Result<List<AcwTableAutoStuffedRecord>> batchStory(List<AcwTableAutoStuffedRecord> acwTableAutoStuffedRecordList) {
        List<AcwTableAutoStuffedRecordDO> acwTableAutoStuffedRecordDOList = acwTableAutoStuffedRecordList.stream().map(AcwTableAutoStuffedRecordConverter.INSTANCE::fromAcwTableAutoStuffedRecord).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwTableAutoStuffedRecordDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 04:43 下午
     **/

    @Override
    public Result<AcwTableAutoStuffedRecord> findOne(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord) {
        AcwTableAutoStuffedRecordDO acwTableAutoStuffedRecordDO = AcwTableAutoStuffedRecordConverter.INSTANCE.fromAcwTableAutoStuffedRecord(acwTableAutoStuffedRecord);
        AcwTableAutoStuffedRecord acwTableAutoStuffedRecordOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwTableAutoStuffedRecordDO), AcwTableAutoStuffedRecord.class);
        return ResultFactory.successOf(acwTableAutoStuffedRecordOne);
    }

    /**
     * describe 查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 04:43 下午
     **/

    @NormalTranslation
    @Override
    public Result<List<AcwTableAutoStuffedRecord>> findList(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord) {
        AcwTableAutoStuffedRecordDO acwTableAutoStuffedRecordDO = AcwTableAutoStuffedRecordConverter.INSTANCE.fromAcwTableAutoStuffedRecord(acwTableAutoStuffedRecord);
        List<AcwTableAutoStuffedRecord> acwTableAutoStuffedRecordList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwTableAutoStuffedRecordDO), AcwTableAutoStuffedRecord.class);
        return ResultFactory.successOf(acwTableAutoStuffedRecordList);
    }

    /**
     * describe 分页查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 04:43 下午
     **/
    @NormalTranslation
    @Override
    public Result<LazyPage<AcwTableAutoStuffedRecord>> findPage(int size, int current, AcwTableAutoStuffedRecord acwTableAutoStuffedRecord) {
        AcwTableAutoStuffedRecordDO acwTableAutoStuffedRecordDO = AcwTableAutoStuffedRecordConverter.INSTANCE.fromAcwTableAutoStuffedRecord(acwTableAutoStuffedRecord);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<AcwTableAutoStuffedRecord> acwTableAutoStuffedRecordLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(acwTableAutoStuffedRecordDO), lazyPage, AcwTableAutoStuffedRecord.class);
        return ResultFactory.successOf(acwTableAutoStuffedRecordLazyPage);
    }

    /**
     * describe 删除数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 04:43 下午
     **/

    @Override
    public Result<AcwTableAutoStuffedRecord> remove(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord) {
        AcwTableAutoStuffedRecordDO acwTableAutoStuffedRecordDO = AcwTableAutoStuffedRecordConverter.INSTANCE.fromAcwTableAutoStuffedRecord(acwTableAutoStuffedRecord);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwTableAutoStuffedRecordDO));
        return ResultFactory.successOf();
    }

    /**
     * 数据批量删除
     *
     * @param acwTableAutoStuffedRecordList 删除出数据对象
     */
    @Override
    public void batchRemove(List<AcwTableAutoStuffedRecord> acwTableAutoStuffedRecordList) {
        lazyLambdaStream.delete(LazyWrappers.<AcwTableAutoStuffedRecordDO>lambdaWrapper().in(AcwTableAutoStuffedRecordDO::getId, acwTableAutoStuffedRecordList.stream().map(AcwTableAutoStuffedRecord::getId).collect(Collectors.toList())));
    }
}
