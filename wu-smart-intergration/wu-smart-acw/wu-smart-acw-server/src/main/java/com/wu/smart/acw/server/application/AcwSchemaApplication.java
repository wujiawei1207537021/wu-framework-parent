package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.AcwSchemaUo;
import com.wu.smart.acw.server.application.command.AcwSchemaBatchExportCommand;
import com.wu.smart.acw.server.application.command.AcwSchemaDeriveViewCommand;
import com.wu.smart.acw.server.application.command.AcwSchemaImportDataCommand;
import com.wu.smart.acw.server.application.command.acw.schema.AcwSchemaQueryListCommand;
import com.wu.smart.acw.server.application.command.acw.schema.AcwSchemaStoryCommand;
import com.wu.smart.acw.server.application.dto.AcwSchemaDTO;
import com.wu.smart.acw.server.application.dto.SchemaSameColumnDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/11/28 21:36
 */
public interface AcwSchemaApplication {
    /**
     * 重新初始化数据库
     *
     * @param instanceId 实例名称
     * @param schema     数据库
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/11/28 21:51
     **/
    Result reload(String instanceId, String schema);

    /**
     * 获取schema分页
     *
     * @param size
     * @param current
     * @param acwSchemaQueryListCommand
     * @return
     */
    Result<LazyPage<AcwSchemaDTO>> retrievePage(int size, int current, AcwSchemaQueryListCommand acwSchemaQueryListCommand);

    /**
     * 查询出所有的数据页面使用
     *
     * @param acwSchemaQueryListCommand
     * @return
     */
    Result<List<AcwSchemaDTO>> findSchemaListWithSize(AcwSchemaQueryListCommand acwSchemaQueryListCommand);

    /**
     * /**
     * 批量导出数据
     *
     * @param acwSchemaBatchExportCommandList schema 信息
     * @return
     * @author 吴佳伟
     * @date: 1.6.23 10:21
     */
    File batchExportSchemaData(List<AcwSchemaBatchExportCommand> acwSchemaBatchExportCommandList);

    /**
     * 数据库中获取相同字段集合
     *
     * @param instanceId
     * @param schema
     * @return
     */
    Result<List<SchemaSameColumnDTO>> findSchemaSameColumnList(String instanceId, String schema);

    /**
     * 数据库衍生视图
     *
     * @param acwSchemaDeriveViewCommand
     * @return
     */
    Result schemaDeriveView(AcwSchemaDeriveViewCommand acwSchemaDeriveViewCommand);

    /**
     * 新增
     *
     * @param acwSchemaStoryCommand
     * @return
     */
    Result<AcwSchemaUo> save(AcwSchemaStoryCommand acwSchemaStoryCommand);

    /**
     * 批量导入数据
     *
     * @param multipartFileList          导入的文件
     * @param acwSchemaImportDataCommand schema 信息
     * @return Result<Void>
     */
    Result<Void> batchImportSchemaData(List<MultipartFile> multipartFileList, AcwSchemaImportDataCommand acwSchemaImportDataCommand);

    /**
     * 查询数据库信息
     * @param acwSchemaQueryListCommand 查询参数
     * @return 返回查询数据库信息
     */
    Result<List<AcwSchemaDTO>> findList(AcwSchemaQueryListCommand acwSchemaQueryListCommand);
}
