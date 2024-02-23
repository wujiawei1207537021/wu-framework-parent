package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.command.AcwTableAutoStuffedRecordCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordQueryListCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordRemoveCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordStoryCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordUpdateCommand;
import com.wu.smart.acw.server.application.dto.AcwTableAutoStuffedRecordDTO;
import com.wu.smart.acw.server.domain.model.model.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecord;

import java.util.List;

public interface AcwTableAutoStuffedRecordApplication {
    /**
     * describe 查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/25 02:15 下午
     **/

    Result<List<AcwTableAutoStuffedRecordDTO>> findList(AcwTableAutoStuffedRecordQueryListCommand acwTableAutoStuffedRecordQueryListCommand);

    /**
     * describe 分页查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/25 02:15 下午
     **/

    Result<LazyPage<AcwTableAutoStuffedRecordDTO>> findPage(int size, int current, AcwTableAutoStuffedRecordQueryListCommand acwTableAutoStuffedRecordQueryListCommand);

    /**
     * describe 删除数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/25 02:15 下午
     **/

    Result<AcwTableAutoStuffedRecord> remove(AcwTableAutoStuffedRecordRemoveCommand acwTableAutoStuffedRecordRemoveCommand);

    /**
     * 新增
     *
     * @return
     */
    Result save(AcwTableAutoStuffedRecordStoryCommand acwTableAutoStuffedRecordStoryCommand);

    /**
     * 批量新增
     *
     * @param acwTableAutoStuffedRecordCommand
     * @return
     */
    Result batchSave(AcwTableAutoStuffedRecordCommand acwTableAutoStuffedRecordCommand);

    /**
     * 根据更新
     *
     * @param acwTableAutoStuffedRecordUpdateCommand 实体对象
     * @return
     */
    Result<Void> update(AcwTableAutoStuffedRecordUpdateCommand acwTableAutoStuffedRecordUpdateCommand);

    /**
     * 根据主键ID 删除
     *
     * @param id id
     * @return
     */
    Result<Void> deleteById(String id);

    /**
     * 根据主键ID 批量删除
     *
     * @param ids ids
     * @return
     */
    Result<Void> batchDeleteById(List<String> ids);
}
